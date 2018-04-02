package com.paka.tagger.app.layout.mainform;

import com.paka.tagger.common.graphics.IconProvider;
import com.paka.tagger.config.MainAppConfig;
import com.paka.tagger.widgets.filebrowser.items.FilePathTreeItem;
import com.paka.tagger.widgets.filebrowser.items.PathItem;
import com.paka.tagger.widgets.renderingarea.RenderingArea;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static com.paka.tagger.common.constants.IconPaths.COMPUTER_ICON_PATH;
import static com.paka.tagger.common.constants.IconPaths.FOLDER_EXPAND_ICON_PATH;

public class MainLayoutController {

    @FXML private TreeView<PathItem> treeView;
    @FXML private RenderingArea renderingArea;
    @FXML public StackPane root;

    public MainLayoutController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MainLayout.fxml"));
        fxmlLoader.setController(this);

        try {
            root = fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        init();
    }

    public StackPane getRoot() {
        return root;
    }

    @FXML
    public void init() {
        initLayouts();
        addHandlers();
    }

    private void initLayouts() {
        initTree();
        initCentralArea();
    }

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
        treeView.setRoot(rootNode);

        VBox.setVgrow(treeView, Priority.ALWAYS);
    }

    private void initCentralArea() {
        renderingArea.setBackground(new Background(new BackgroundFill(new Color(.7d, .7d, .7d, 1d),
                new CornerRadii(4d), new Insets(10, 10, 10, 10))));
    }

    private void addHandlers() {
        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            TreeItem<PathItem> source = observable.getValue();
            if (source.getValue().getFullPath().toFile().isDirectory() && source.isExpanded()) {
                ImageView iv = (ImageView) source.getGraphic();
                iv.setImage(IconProvider.getImage(FOLDER_EXPAND_ICON_PATH));
            }
            try {
                if (source.getChildren().isEmpty()) {
                    Path path = Paths.get(source.getValue().getDisplayPath());
                    BasicFileAttributes attribs = Files.readAttributes(path, BasicFileAttributes.class);
                    if (attribs.isDirectory()) {
                        DirectoryStream<Path> dir = Files.newDirectoryStream(path);
                        for (Path aFile : dir) {
                            if (isExtSupported(aFile)) {
                                FilePathTreeItem treeNode = new FilePathTreeItem(new PathItem(aFile.toString(), aFile));
                                source.getChildren().add(treeNode);
                            }
                        }
                    }
                } else {
                    //if you want to implement rescanning a directory for changes this would be the place to do it
                }
            } catch (IOException x) {
                x.printStackTrace();
            }
        });
    }

    private boolean isExtSupported(Path path) throws IOException {
        BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
        return attributes.isDirectory() || MainAppConfig.isPathSupported(path);
    }
}
