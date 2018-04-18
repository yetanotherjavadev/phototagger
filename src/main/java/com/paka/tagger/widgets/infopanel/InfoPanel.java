package com.paka.tagger.widgets.infopanel;

import com.paka.tagger.common.model.Tag;
import com.paka.tagger.model.TreeEntity;
import com.paka.tagger.state.AppState;
import com.paka.tagger.widgets.tagspanel.TagWidget;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

//TODO: change the way of rendering. The item should appear in the details panel iif it is in the metadata
public class InfoPanel extends VBox {

    private TreeEntity entity;

    @FXML
    private KeyValue dateLabel;
    @FXML
    private KeyValue makeLabel;
    @FXML
    private KeyValue modelLabel;
    @FXML
    private KeyValue exposureLabel;
    @FXML
    private KeyValue apertureLabel;
    @FXML
    private KeyValue focalLengthLabel;
    @FXML
    private KeyValue isoSpeedLabel;
    @FXML
    private KeyValue flashLabel;

    @FXML
    private VBox generalInfoContainer;
    @FXML
    private VBox tagsContainer;

    public InfoPanel() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmls/InfoPanel.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        fxmlLoader.load();
    }

    @FXML
    public void initialize() {
        bind();
    }

    private void bind() {
        AppState.get().getSelectedNodeProperty().addListener((observable, oldValue, newValue) -> showData(newValue.getValue()));
    }

    private void setData(TreeEntity entity) {
        this.entity = entity;
        render();
    }

    private void render() {
        dateLabel.setValue(entity.getMetadata().getDate());
        makeLabel.setValue(entity.getMetadata().getMake());
        modelLabel.setValue(entity.getMetadata().getModel());
        exposureLabel.setValue(entity.getMetadata().getExposure());
        apertureLabel.setValue(entity.getMetadata().getAperture());
        focalLengthLabel.setValue(entity.getMetadata().getFocalLength());
        isoSpeedLabel.setValue(entity.getMetadata().getIsoSpeed());
        flashLabel.setValue(String.valueOf(entity.getMetadata().isFlash()));

        if (entity.hasTagsAssigned()) {
            tagsContainer.getChildren().clear();
            for (Tag tag : entity.getTagsAssigned()) {
                tagsContainer.getChildren().add(new TagWidget(tagsContainer, tag, true));
            }
        } else {
            tagsContainer.getChildren().clear();
        }
    }

    private void clear() {
        dateLabel.setValue(null);
        makeLabel.setValue(null);
        modelLabel.setValue(null);
        exposureLabel.setValue(null);
        apertureLabel.setValue(null);
        focalLengthLabel.setValue(null);
        isoSpeedLabel.setValue(null);
        flashLabel.setValue(null);
        tagsContainer.getChildren().clear();
    }

    private void showData(TreeEntity entity) {
        if (entity == null || entity.isDirectory()) {
            clear();
            return;
        }
        try {
            setData(entity);
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e);
        }
    }
}
