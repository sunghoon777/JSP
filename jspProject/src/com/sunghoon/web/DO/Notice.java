package com.sunghoon.web.DO;

import java.sql.Date;

public class Notice {

    private long id;
    private String title;
    private String writerId;
    private String content;
    private Date regdate;
    private long hit;

    private String files;


    public Notice(long id, String title, String writerId, String content, Date regdate, long hit, String files) {
        this.id = id;
        this.title = title;
        this.writerId = writerId;
        this.content = content;
        this.regdate = regdate;
        this.hit = hit;
        this.files = files;
    }

    public Notice() {
        this(0,"","","",null,0,"");
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriterId() {
        return writerId;
    }

    public void setWriterId(String writerId) {
        this.writerId = writerId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    public long getHit() {
        return hit;
    }

    public void setHit(long hit) {
        this.hit = hit;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", writerId='" + writerId + '\'' +
                ", content='" + content + '\'' +
                ", date=" + regdate +
                ", hit=" + hit +
                '}';
    }
}
