package com.zdfy.purereader.domain;

import java.io.Serializable;

/**
 * Created by ZhangPeng on 2016/9/11.
 */

public class DouBanInfo implements Serializable{
    private int id;
    private String title;
    private String abs;
    private String thumb;
    private String shareUrl;

    public DouBanInfo(int id, String title, String abs, String thumb, String shareUrl) {
        this.id = id;
        this.title = title;
        this.abs = abs;
        this.thumb = thumb;
        this.shareUrl = shareUrl;
    }

    @Override
    public String toString() {
        return "DouBanInfo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", abs='" + abs + '\'' +
                ", thumb='" + thumb + '\'' +
                ", shareUrl='" + shareUrl + '\'' +
                '}';
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public DouBanInfo(int id, String title, String abs, String thumb) {
        this.id = id;
        this.title = title;
        this.abs = abs;
        this.thumb = thumb;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbs() {
        return abs;
    }

    public void setAbs(String abs) {
        this.abs = abs;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
