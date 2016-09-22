package com.zdfy.purereader.domain;

/**
 * Created by Yaozong on 2016/9/22.
 */

public class VideoCategoryInfo {
    /**
     * id : 26
     * name : 萌宠
     * alias : null
     * description : 不止有汪星人和喵星人哟~
     * bgPicture : http://img.kaiyanapp.com/ac6971c1b9fc942c7547d25fc6c60ad2.jpeg
     * bgColor :
     */

    private int id;
    private String name;
    private Object alias;
    private String description;
    private String bgPicture;
    private String bgColor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getAlias() {
        return alias;
    }

    public void setAlias(Object alias) {
        this.alias = alias;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBgPicture() {
        return bgPicture;
    }

    public void setBgPicture(String bgPicture) {
        this.bgPicture = bgPicture;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }
}
