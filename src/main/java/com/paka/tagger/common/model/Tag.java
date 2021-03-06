package com.paka.tagger.common.model;

import java.util.Date;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Tag {

    private String text;
    private Date createdOn;
    private String createdBy;
    private boolean used;

    public Tag() {
    }

    public Tag(String text) {
        this(text, new Date(), "ElNinho", false);
    }

    @Override //tags now are equal by text only, this should be changed further
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(text, tag.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }
}
