package com.paka.tagger.app.layout.menu;

import com.paka.tagger.common.model.Tag;
import com.paka.tagger.model.TreeEntity;
import com.paka.tagger.state.AppState;
import com.paka.tagger.state.filters.TagFilter;
import com.paka.tagger.widgets.filebrowser.items.FilePathTreeItem;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TreeItem;
import javafx.util.Callback;


//TODO: get rid of callbacks, use native JavaFX event handling mechanism
public class MainMenuBarController extends MenuBar {

    private Callback<String, String> scanCallback;

    public MainMenuBarController() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmls/MainMenuBar.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        fxmlLoader.load();
    }

    @FXML
    public void exitAction() {
        System.out.println("Thanks for using Phototagger!");
        System.exit(0);
    }

    @FXML
    public void performScan() {
        if (scanCallback != null) {
            String scan = scanCallback.call(null);
            System.out.println(scan);
        }
    }

    @FXML
    public void removeAllTags() {
        AppState.get().setAppliedFilters(new TagFilter());
    }

    @FXML
    public void tagAllFolderContents() {
        FilePathTreeItem selectedNode = AppState.get().getSelectedNodeProperty().get();
        if (selectedNode.getValue().isDirectory()) {
            String folderName = selectedNode.getValue().getPathItem().getDisplayPath();
            Tag testTag = new Tag(folderName.toLowerCase());

            tagEverything(selectedNode, testTag);
        } else { //TODO: create information popups instead of sout logging
            System.out.println("Folder tagging works only when folder is selected in the tree");
        }
    }

    private void tagEverything(TreeItem<TreeEntity> node, Tag tag) {
        if (!node.getValue().isDirectory()) {
            node.getValue().getTagsAssigned().add(tag);
        }
        if (node.getChildren().size() > 0) {
            for (TreeItem<TreeEntity> treeEntityTreeItem : node.getChildren()) {
                tagEverything(treeEntityTreeItem, tag);
            }
        }
    }

    //explicit scan callback should be set here
    public void setScanCallback(Callback<String, String> callback) {
        this.scanCallback = callback;
    }
}
