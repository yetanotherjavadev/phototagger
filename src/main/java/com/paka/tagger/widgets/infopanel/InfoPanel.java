package com.paka.tagger.widgets.infopanel;

import com.paka.tagger.common.model.ImageMetadata;
import com.paka.tagger.utils.ImageUtils;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

public class InfoPanel extends VBox {

    private ImageMetadata imageMetadata;

    //TODO: create FXML
    private KeyValue dateLabel;
    private KeyValue makeLabel;
    private KeyValue modelLabel;
    private KeyValue exposureLabel;
    private KeyValue apertureLabel;
    private KeyValue focalLengthLabel;
    private KeyValue isoSpeedLabel;
    private KeyValue flashLabel;

    public InfoPanel() {
        setPadding(new Insets(10, 10, 10, 10));
        setWidth(300);
        init();
        this.getChildren().addAll(dateLabel,
                makeLabel,
                modelLabel,
                exposureLabel,
                apertureLabel,
                focalLengthLabel,
                isoSpeedLabel,
                flashLabel);
    }

    private void init() {
        dateLabel = new KeyValue("Date: ", "01/01/1970");
        makeLabel = new KeyValue("Make: " , "A");
        modelLabel = new KeyValue("Model: ", "A");
        exposureLabel = new KeyValue("Exposure: ", "A");
        apertureLabel = new KeyValue("Aperture: ", "A");
        focalLengthLabel = new KeyValue("Focal Length: ", "A");
        isoSpeedLabel = new KeyValue("ISO Speed: ", "A");
        flashLabel = new KeyValue("Flash: ", "A");
    }

    private void setData(ImageMetadata metadata) {
        this.imageMetadata = metadata;
        render();
    }

    private void render() {
        dateLabel.setValue(imageMetadata.getDate());
        makeLabel.setValue(imageMetadata.getMake());
        modelLabel.setValue(imageMetadata.getModel());
        exposureLabel.setValue(imageMetadata.getExposure());
        apertureLabel.setValue(imageMetadata.getAperture());
        focalLengthLabel.setValue(imageMetadata.getFocalLength());
        isoSpeedLabel.setValue(imageMetadata.getIsoSpeed());
        flashLabel.setValue(String.valueOf(imageMetadata.isFlash()));
    }

    public void showDataFor(String path) {
        try {
            Path aPath = Paths.get(path);
            setData(ImageUtils.getImageMetadata(aPath));
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e);
        }
    }
}
