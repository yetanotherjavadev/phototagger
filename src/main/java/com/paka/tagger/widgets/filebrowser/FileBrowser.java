package com.paka.tagger.widgets.filebrowser;

import static com.paka.tagger.common.constants.IconPaths.COMPUTER_ICON_PATH;
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

    public FileBrowser() {
        initTree();
        addSelectionHandler();
        addFilteringHandler();
    }

    //TODO: implement "scan" feature to hide directories that don't contain images
    //TODO: do not start from the host node
    private void initTree() {
        String hostName = "Localhost"; // local computer "name"
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException x) {
            System.out.println("Something went wrong, leaving default host name");
        }

        TreeEntity rootItem = new TreeEntity(Paths.get(hostName));
        FilePathTreeItem rootNode = new FilePathTreeItem(rootItem, new ImageView(IconProvider.getImage(COMPUTER_ICON_PATH)));

        Iterable<Path> rootDirectories = FileSystems.getDefault().getRootDirectories();
        for (Path name : rootDirectories) {
            FilePathTreeItem treeNode = new FilePathTreeItem(new TreeEntity(name));
            rootNode.getChildren().add(treeNode);
        }
        rootNode.setExpanded(true);
        setRoot(rootNode);

        VBox.setVgrow(this, Priority.ALWAYS);
    }

    private void addSelectionHandler() {
        getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Clicked on selected item: " + newValue.getValue().getPathItem().getFullPath());
            TreeItem<TreeEntity> source = observable.getValue();
            boolean isDirectory = source.getValue().getPathItem().getFullPath().toFile().isDirectory();
            if (isDirectory && source.isExpanded()) {
                ImageView iv = (ImageView) source.getGraphic();
                iv.setImage(IconProvider.getImage(FOLDER_EXPAND_ICON_PATH));
            }
            try {
                if (isDirectory) {
                    if (source.getChildren().isEmpty()) { //happens on first dir opening
                        DirectoryStream<Path> dir = Files.newDirectoryStream(source.getValue().getPathItem().getFullPath());
                        for (Path path : dir) {
                            if (pathSupported(path)) {
                                TreeEntity treeEntity = new TreeEntity(path);
                                if (!path.toFile().isDirectory()) {
                                    treeEntity.addTag(new Tag(FileUtils.getFileExt(path)));
                                }
                                FilePathTreeItem treeNode = new FilePathTreeItem(treeEntity);
                                source.getChildren().add(treeNode);
                            }
                        }
                    }
                } else { //TODO: implement rescanning a directory for changes this would be the place to do it
                    AppState.get().setSelectedItem(source.getValue());
                }
            } catch (IOException x) {
                x.printStackTrace();
            }
        });
    }



    private void addFilteringHandler() {
        AppState.get().getAppliedFilters().addListener(new ChangeListener<List<Filter>>() {
            @Override
            public void changed(ObservableValue<? extends List<Filter>> observable, List<Filter> oldValue, List<Filter> newValue) {
                //TODO: re-scan all folder tree from the root and leave only those folder which match the filtering rules

            }
        });
    }

    private boolean pathSupported(Path path) {
        return path.toFile().isDirectory() || MainAppConfig.isPathSupported(path);
    }
}
