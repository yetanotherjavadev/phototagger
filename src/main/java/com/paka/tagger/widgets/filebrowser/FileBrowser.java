package com.paka.tagger.widgets.filebrowser;

import static com.paka.tagger.common.constants.IconPaths.COMPUTER_ICON_PATH;
import static com.paka.tagger.common.constants.IconPaths.FOLDER_COLLAPSE_ICON_PATH;
import static com.paka.tagger.common.constants.IconPaths.FOLDER_EXPAND_ICON_PATH;

import com.paka.tagger.common.graphics.IconProvider;
import com.paka.tagger.common.model.Tag;
import com.paka.tagger.config.MainAppConfig;
import com.paka.tagger.model.TreeEntity;
import com.paka.tagger.state.AppState;
import com.paka.tagger.state.filters.TagFilter;
import com.paka.tagger.utils.FileUtils;
import com.paka.tagger.widgets.filebrowser.items.FilePathTreeItem;
import com.paka.tagger.widgets.filebrowser.utils.TreeUtils;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
        this.lazyScan = false;
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
            rootNode.getInternalChildren().addAll(TreeUtils.getChildren(treeNode, scanningDepth));
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
            if (newValue == null) return; //in case if selection is reset to null

            FilePathTreeItem nV = (FilePathTreeItem) newValue;
            Path fullPath = nV.getValue().getPathItem().getFullPath();
            System.out.println("Clicked on selected item: " + fullPath);
            boolean isDirectory = fullPath.toFile().isDirectory();
            if (isDirectory && nV.isExpanded()) {
                ImageView iv = (ImageView) nV.getGraphic();
                iv.setImage(IconProvider.getImage(FOLDER_EXPAND_ICON_PATH));
            }
            AppState.get().setSelectedNode(nV);
            try {
                if (isDirectory) {
                    if (!nV.isScanned()) { //should happen only on first dir opening
                        nV.setScanned(true);
                        DirectoryStream<Path> dir = Files.newDirectoryStream(fullPath);
                        for (Path path : dir) {
                            if (pathSupported(path)) {
                                TreeEntity treeEntity = new TreeEntity(path);
                                if (!path.toFile().isDirectory()) {
                                    treeEntity.addTag(new Tag(FileUtils.getFileExt(path)));
                                }
                                FilePathTreeItem treeNode = new FilePathTreeItem(treeEntity);
                                if (lazyScan) {
                                    nV.getInternalChildren().add(treeNode);
                                } else {
                                    nV.getInternalChildren().addAll(TreeUtils.getChildrenUnlimitedDepth(treeNode));
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
        AppState.get().getAppliedFiltersProperty().addListener(new ChangeListener<TagFilter>() {
            @Override
            public void changed(ObservableValue<? extends TagFilter> observable, TagFilter oldValue, TagFilter newValue) {
                filter(newValue);
            }
        });
    }

    private void filter(TagFilter filter) {
        initialTreeRoot.setPredicate((parent, value) -> {
            return tagMatch(filter, value); //TODO consider parent value here too
        });
    }

    //TODO convert to java8 map matcher
    private boolean tagMatch(TagFilter filter, TreeEntity entity) {
        if (filter == null || filter.getSelectedTags().isEmpty()) return true;
        List<Tag> tags = entity.getTagsAssigned();

        List<Tag> filterTags = filter.getSelectedTags();
        for (Tag filterTag : filterTags) {
            if (tags != null && tags.contains(filterTag)) return true;
        }

        return false;
    }

    //TODO implement multithreaded scanning
    private void rescan(FilePathTreeItem starting) {
        long start = System.currentTimeMillis();
        starting.getInternalChildren().clear();
        starting.getInternalChildren().addAll(TreeUtils.getChildren(starting, scanningDepth));
        starting.setExpanded(true);
        long took = System.currentTimeMillis() - start;
        System.out.println("Scanning took: " + took + "ms");
    }

    public void rescanCurrent() {
        rescan(AppState.get().getSelectedNodeProperty().get());
    }

    private boolean pathSupported(Path path) {
        return path.toFile().isDirectory() || MainAppConfig.isPathSupported(path);
    }
}
