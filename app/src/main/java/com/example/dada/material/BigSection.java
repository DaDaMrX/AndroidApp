package com.example.dada.material;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DaDa on 2/3/2018.
 */

public class BigSection {
    private String id;
    private String bigTitle;
    private List<Section> sections;

    public BigSection() {
        id = "";
        bigTitle = "";
        sections = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBigTitle() {
        return bigTitle;
    }

    public void setBigTitle(String bigTitle) {
        this.bigTitle = bigTitle;
    }

    public void addSection(Section section) {
        sections.add(section);
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }
}
