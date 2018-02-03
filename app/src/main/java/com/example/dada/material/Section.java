package com.example.dada.material;

/**
 * Created by DaDa on 2/2/2018.
 */

public class Section {
    private int type;
    private String id;
    private String title;
    private String pptUrl;
    private String codeUrl;
    private int bigTitleNum;


    public Section() {
        this.type = 0;
        this.id = "";
        this.title = "";
        this.pptUrl = "";
        this.codeUrl = "";
        this.bigTitleNum = -1;
    }

    public Section(int type) {
        this.type = type;
        this.id = "";
        this.title = "";
        this.pptUrl = "";
        this.codeUrl = "";
        this.bigTitleNum = -1;
    }

    @Override
    public String toString() {
        String s = "id: " + id + "\n" +
                "title: " + title + "\n" +
                "pptUrl: " + pptUrl + "\n" +
                "codeUrl: " + codeUrl + "\n";
        return s;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getPptUrl() {
        return pptUrl;
    }

    public void setPptUrl(String pptUrl) {
        this.pptUrl = pptUrl;
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }


    public int getBigTitleNum() {
        return bigTitleNum;
    }

    public void setBigTitleNum(int bigTitleNum) {
        this.bigTitleNum = bigTitleNum;
    }
}
