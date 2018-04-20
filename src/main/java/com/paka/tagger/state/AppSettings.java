package com.paka.tagger.state;

import javafx.beans.property.SimpleObjectProperty;
import lombok.Getter;

@Getter
public class AppSettings {

    //starting folder to scan from - is it needed?
    private String treeTop;

    private SimpleObjectProperty<Boolean> resizeableScreenProperty = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Integer> totalNumberOfImgIndexedProperty = new SimpleObjectProperty<>();
    private SimpleObjectProperty<TagFilterMode> taggingModeProperty = new SimpleObjectProperty<>();

    public AppSettings() {
        this.taggingModeProperty.setValue(TagFilterMode.MATCH_ANY);
    }

    public void setResizeableScreen(boolean resizeable) {
        this.resizeableScreenProperty.setValue(resizeable);
    }

    public void setNoOfItems(Integer noOfItems) {
        this.totalNumberOfImgIndexedProperty.setValue(noOfItems);
    }

    public void setTaggingMode(TagFilterMode mode) {
        this.taggingModeProperty.setValue(mode);
    }
}
