package com.paka.tagger.state;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppSettings {

    //starting folder to scan from
    private String treeTop;
    private boolean resizeableScreen;
    private Integer totalNumberOfImgIndexed;

    private static AppSettings INSTANCE = new AppSettings();

    private AppSettings() {}

    public AppSettings get() {
        return INSTANCE;
    }
}
