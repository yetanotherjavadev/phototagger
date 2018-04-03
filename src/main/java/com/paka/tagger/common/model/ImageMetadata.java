package com.paka.tagger.common.model;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageMetadata {

    private String make;
    private String model;
    private String exposure;
    private Date date;
    private Double aperture;
    private Double focalLength;
    private Double isoSpeed;
    private boolean flash;

}
