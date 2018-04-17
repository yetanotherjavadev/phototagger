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

    private TreeEntity image;

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
        AppState.get().getSelectedNode().addListener((observable, oldValue, newValue) -> showData(newValue.getValue()));
    }

    private void setData(TreeEntity entity) {
        this.image = entity;
        render();
    }

    private void render() {
        dateLabel.setValue(image.getMetadata().getDate());
        makeLabel.setValue(image.getMetadata().getMake());
        modelLabel.setValue(image.getMetadata().getModel());
        exposureLabel.setValue(image.getMetadata().getExposure());
        apertureLabel.setValue(image.getMetadata().getAperture());
        focalLengthLabel.setValue(image.getMetadata().getFocalLength());
        isoSpeedLabel.setValue(image.getMetadata().getIsoSpeed());
        flashLabel.setValue(String.valueOf(image.getMetadata().isFlash()));

        if (image.hasTagsAssigned()) {
            tagsContainer.getChildren().clear();
            for (Tag tag : image.getTagsAssigned()) {
                tagsContainer.getChildren().add(new TagWidget(tagsContainer, tag.getText(), true));
            }
        }
    }

    private void showData(TreeEntity entity) {
        if (entity == null) return;
        try {
            setData(entity);
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e);
        }
    }
}
