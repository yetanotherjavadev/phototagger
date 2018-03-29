package com.paka.tagger.app.layout.mainform;

import com.paka.tagger.common.graphics.IconProvider;
import com.paka.tagger.widgets.filebrowser.items.FilePathTreeItem;
import com.paka.tagger.widgets.filebrowser.items.PathItem;
import com.paka.tagger.widgets.renderingarea.RenderingArea;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static com.paka.tagger.common.constants.Icons.COMPUTER_ICON_PATH;
import static com.paka.tagger.common.constants.Icons.FOLDER_EXPAND_ICON_PATH;

public class JavaFXFileBrowseDemoApp extends Application {

	private static final String WINDOW_TITLE = "JavaFX File Browse Demo";

	private TreeView<PathItem> treeView;
	private BorderPane mainPane = new BorderPane();
	private StackPane root = new StackPane();

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		//setup and show the window
		primaryStage.setTitle(WINDOW_TITLE);

		setupLayout();

		root.getChildren().add(mainPane);

		primaryStage.setScene(new Scene(root, 800, 600));
		primaryStage.show();
	}

	private void setupLayout() {
		mainPane.setPadding(new Insets(10, 10, 10, 10));

		mainPane.setLeft(createTree());
		mainPane.setCenter(getRenderingArea());
		mainPane.setRight(createInfoBox());
	}

	private VBox createInfoBox() {
		VBox treeBox = new VBox();
		treeBox.setPadding(new Insets(10, 10, 10, 10));
		treeBox.setSpacing(10);
		VBox.setVgrow(treeView, Priority.ALWAYS);
		return treeBox;
	}

	private VBox createTree() {
		//create tree pane
		VBox treeBox = new VBox();
		treeBox.setPadding(new Insets(10, 10, 10, 10));
		treeBox.setSpacing(10);

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

		treeView = new TreeView<>(rootNode);

		addHandlers();

		treeBox.getChildren().add(treeView);
		VBox.setVgrow(treeView, Priority.ALWAYS);

		return treeBox;
	}

	private RenderingArea getRenderingArea() {
		RenderingArea renderingArea = new RenderingArea();
		renderingArea.setBackground(new Background(new BackgroundFill(new Color(.7d,.7d,.7d,1d),
				new CornerRadii(4d), new Insets(10,10,10,10))));
		return renderingArea;
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
							FilePathTreeItem treeNode = new FilePathTreeItem(new PathItem(aFile.toString(), aFile));
							source.getChildren().add(treeNode);
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
}
