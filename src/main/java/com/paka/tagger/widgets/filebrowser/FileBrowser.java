package com.paka.tagger.widgets.filebrowser;

import static com.paka.tagger.common.constants.IconPaths.COMPUTER_ICON_PATH;
import static com.paka.tagger.common.constants.IconPaths.FOLDER_COLLAPSE_ICON_PATH;
import static com.paka.tagger.common.constants.IconPaths.FOLDER_EXPAND_ICON_PATH;

import com.paka.tagger.common.graphics.IconProvider;
import com.paka.tagger.common.model.Tag;
import com.paka.tagger.config.MainAppConfig;
import com.paka.tagger.model.TreeEntity;
import com.paka.tagger.state.AppState;
import com.paka.tagger.state.filters.Filter;
import com.paka.tagger.utils.FileUtils;
import com.paka.tagger.widgets.filebrowser.items.FilePathTreeItem;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

//TODO: add file alternation listener (in case of external file system changes the tree should rebuild itself)
public class FileBrowser extends TreeView<TreeEntity> {

    private static final String STARTING_DIR = "D:/";
    private FilePathTreeItem initialTreeRoot;
    private boolean lazyScan; //TODO implement fully functional lazy scanning
    private int scanningDepth;

    public FileBrowser() {
        this.scanningDepth = 5;
        this.lazyScan = true;
        initTree();
        addSelectionHandler();
        addFilteringHandler();
    }

    //TODO: implement "scan" feature to hide directories that don't contain images

    private void initTree() {
//        createDefaultRoot();
        createRoot();

        VBox.setVgrow(this, Priority.ALWAYS);
    }

    private void createRoot() {
        Path path = Paths.get(STARTING_DIR);
        TreeEntity root = new TreeEntity(path);
        FilePathTreeItem rootNode = new FilePathTreeItem(root, new ImageView(IconProvider.getImage(FOLDER_COLLAPSE_ICON_PATH)));
        setData(rootNode);

        if (!lazyScan) {
            rescan(rootNode);
        }
    }

    //starts scanning from the very top level (e.g. "My Computer" in Win)
    private void createDefaultRoot() {
        String hostName = "Localhost"; // local computer "name"
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException x) {
            System.out.println("Something went wrong, leaving default host name");
        }
        TreeEntity rootItem = new TreeEntity(Paths.get(hostName));
        FilePathTreeItem rootNode = new FilePathTreeItem(rootItem, new ImageView(IconProvider.getImage(COMPUTER_ICON_PATH)));

        Iterable<Path> rootDirectories = FileSystems.getDefault().getRootDirectories();
        for (Path dir : rootDirectories) {
            FilePathTreeItem treeNode = new FilePathTreeItem(new TreeEntity(dir));
            rootNode.getChildren().addAll(getChildren(treeNode, scanningDepth));
        }
        rootNode.setExpanded(true);
        setData(rootNode);
    }

    //re-sets the node at the tree root
    public void setData(FilePathTreeItem root) {
        setRoot(root);
        this.initialTreeRoot = root;
    }

    private void addSelectionHandler() {
        getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Path fullPath = newValue.getValue().getPathItem().getFullPath();
            System.out.println("Clicked on selected item: " + fullPath);
            boolean isDirectory = fullPath.toFile().isDirectory();
            if (isDirectory && newValue.isExpanded()) {
                ImageView iv = (ImageView) newValue.getGraphic();
                iv.setImage(IconProvider.getImage(FOLDER_EXPAND_ICON_PATH));
            }
            AppState.get().setSelectedNode(newValue);
            try {
                if (isDirectory) {
                    if (newValue.getChildren().isEmpty()) { //happens on first dir opening
                        DirectoryStream<Path> dir = Files.newDirectoryStream(fullPath);
                        for (Path path : dir) {
                            if (pathSupported(path)) {
                                TreeEntity treeEntity = new TreeEntity(path);
                                if (!path.toFile().isDirectory()) {
                                    treeEntity.addTag(new Tag(FileUtils.getFileExt(path)));
                                }
                                FilePathTreeItem treeNode = new FilePathTreeItem(treeEntity);
                                if (lazyScan) {
                                    newValue.getChildren().add(treeNode);
                                } else {
                                    newValue.getChildren().addAll(getChildren(treeNode));
                                }

                            }
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Error accessing file: " + e);
            }
        });
    }

    private void addFilteringHandler() {
        AppState.get().getAppliedFilters().addListener(new ChangeListener<List<Filter>>() {
            @Override
            public void changed(ObservableValue<? extends List<Filter>> observable, List<Filter> oldValue, List<Filter> newValue) {
                filter(newValue);
            }
        });
    }

    //TODO: re-scan all folder tree from the root and leave only those folder which match the filtering rules
    private void filter(List<Filter> filters) {

    }

    //TODO implement multithreaded scanning
    private void rescan(FilePathTreeItem starting) {
        long start = System.currentTimeMillis();
        starting.getChildren().clear();
        starting.getChildren().addAll(getChildren(starting, scanningDepth));
        starting.setExpanded(true);
        long took = System.currentTimeMillis() - start;
        System.out.println("Scanning took: " + took + "ms");
    }

    public void rescanCurrent() {
        rescan(AppState.get().getSelectedNode().get());
    }

    private List<FilePathTreeItem> getChildren(FilePathTreeItem item) {
        return getChildren(item, Integer.MAX_VALUE);
    }

    private List<FilePathTreeItem> getChildren(FilePathTreeItem item, int depth) {
        List<FilePathTreeItem> result = new ArrayList<>();
        if (depth == 0) return result;
        item.setScanned(true);

        Path fullPath = item.getValue().getPathItem().getFullPath();
        boolean isDirectory = fullPath.toFile().isDirectory();

        if (isDirectory) {
            DirectoryStream<Path> stream = null;
            try {
                System.out.println("Scanning dir: " + fullPath);
                stream = Files.newDirectoryStream(fullPath);
            } catch (Exception e) {
                System.out.println("Can't dig into directory: " + fullPath);
            }
            if (stream != null) {
                for (Path path : stream) {
                    if (pathSupported(path)) {
                        TreeEntity treeEntity = new TreeEntity(path);
                        if (!path.toFile().isDirectory()) {
                            treeEntity.addTag(new Tag(FileUtils.getFileExt(path)));
                        }
                        FilePathTreeItem treeNode = new FilePathTreeItem(treeEntity);
                        treeNode.getChildren().addAll(getChildren(treeNode, depth - 1));
                        result.add(treeNode);
                    }
                }
            }
        }

        return result;
    }

    private boolean pathSupported(Path path) {
        return path.toFile().isDirectory() || MainAppConfig.isPathSupported(path);
    }
}
