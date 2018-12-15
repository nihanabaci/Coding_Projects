package com.nihanabaci.newsgateway;

import java.io.Serializable;
import java.util.ArrayList;

public class Articles implements Serializable {
    public String author;
    public String title;
    public String description;
    public String date;
    public String urlImage;
    public String url;



    public Articles( String a, String t, String d, String dd, String u, String uu) {
        this.author  = a;
        this.title = t;
        this.description = d;
        this.date = dd;
        this.urlImage = u;
        this.url = uu;

    }
    public Articles() {

        this.author  = this.getAuthor();
        this.title = this.getTitle();
        this.description = this.getDescription();
        this.date = this.getDate();
        this.urlImage = this.getUrlImage();
        this.url = this.getUrl();


    }


    public String getAuthor() { return author; }

    public void setAuthor(String o) {
        this.author = o;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String o) {
        this.title = o;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String o) {
        this.description = o;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String o) {
        this.date = o;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String o) {
        this.urlImage = o;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String o) {
        this.url = o;
    }



}
