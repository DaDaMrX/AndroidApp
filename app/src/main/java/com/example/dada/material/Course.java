package com.example.dada.material;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DaDa on 1/31/2018.
 */

public class Course {
    private int avatarId;
    private String id;
    private String title;
    private List<String> descriptions;

    public Course() {
        this.avatarId = 0;
        this.id = "0000000000";
        this.title = "Course Title";
        this.descriptions = new ArrayList<>();
    }

    public Course(int avatarId, String id, String title,
                  List<String> descriptions) {
        this.avatarId = avatarId;
        this.id = id;
        this.title = title;
        this.descriptions = descriptions;
    }

    public Course(int avatarId, String id, String title) {
        this.avatarId = avatarId;
        this.id = id;
        this.title = title;
        this.descriptions = new ArrayList<>();
    }

    @Override
    public String toString() {
        String s = "id: " + id + "\n" +
                "title: " + title + "\n" +
                "descriptions: " + descriptions.get(0) + "\n";
        return s;
    }

    public int getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(int avatarId) {
        this.avatarId = avatarId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<String> descriptions) {
        this.descriptions = descriptions;
    }

    public void addDescription(String description) {
        descriptions.add(description);
    }
}
