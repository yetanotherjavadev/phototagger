package com.paka.tagger.common.model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Tag {

    private Date createdOn;
    private String createdBy;
    private String text;

    public Tag(String text) {
        this(new Date(), "ElNinho", text);
    }
}
