package com.paka.tagger.widgets.filebrowser;

import static com.paka.tagger.common.constants.IconPaths.COMPUTER_ICON_PATH;
import static com.paka.tagger.common.constants.IconPaths.FOLDER_EXPAND_ICON_PATH;

import com.paka.tagger.common.graphics.IconProvider;
import com.paka.tagger.config.MainAppConfig;
import com.paka.tagger.widgets.filebrowser.items.FilePathTreeItem;
import com.paka.tagger.widgets.filebrowser.items.PathItem;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class FileBrowser extends TreeView<PathItem> {

    private Callback<Path, Void> imgClickCallBack;

    public FileBrowser() {
        initTree();
        addHandlers();
    }

    //TODO: implement "scan" feature to hide directories that don't contain images
    //TODO: do not start from the host node
    private void initTree() {
        String hostName = "Computer"; // local computer "name"
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException x) {
            System.out.println("Something went wrong, leaving default host name");
        }

        PathItem rootItem = new PathItem(hostName, hostName);
        FilePathTreeItem rootNode = new FilePathTreeItem(rootItem, new ImageView(IconProvider.getImage(COMPUTER_ICON_PATH)));

        Iterable<Path> rootDirectories = FileSystems.getDefault().getRootDirectories();
        for (Path name : rootDirectories) {
            FilePathTreeItem treeNode = new FilePathTreeItem(new PathItem(name.toString(), name));
            rootNode.getChildren().add(treeNode);
        }
        rootNode.setExpanded(true);
        setRoot(rootNode);

        VBox.setVgrow(this, Priority.ALWAYS);
    }

    private void addHandlers() {
        getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            TreeItem<PathItem> source = observable.getValue();
            boolean isDirectory = source.getValue().getFullPath().toFile().isDirectory();
            if (isDirectory && source.isExpanded()) {
                ImageView iv = (ImageView) source.getGraphic();
                iv.setImage(IconProvider.getImage(FOLDER_EXPAND_ICON_PATH));
            }
            try {
                if (isDirectory) {
                    if (source.getChildren().isEmpty()) { //happens on first dir opening
                        Path path = Paths.get(source.getValue().getDisplayPath());
                        DirectoryStream<Path> dir = Files.newDirectoryStream(path);
                        for (Path file : dir) {
                            if (file.toFile().isDirectory() || isExtSupported(file)) {
                                FilePathTreeItem treeNode = new FilePathTreeItem(new PathItem(file.toString(), file));
                                source.getChildren().add(treeNode);
                            }
                        }
                    }
                } else { //TODO: implement rescanning a directory for changes this would be the place to do it
                    imgClickCallBack.call(source.getValue().getFullPath());
                }
            } catch (IOException x) {
                x.printStackTrace();
            }
        });
    }

    private boolean isExtSupported(Path path) {
        return MainAppConfig.isPathSupported(path);
    }

    public void setImgClickCallBack(Callback<Path, Void> callBack) {
        this.imgClickCallBack = callBack;
    }
}
