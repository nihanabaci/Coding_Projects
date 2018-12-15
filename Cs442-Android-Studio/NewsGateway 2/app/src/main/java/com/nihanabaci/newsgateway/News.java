package com.nihanabaci.newsgateway;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.io.Serializable;

public class News implements Serializable{

    public String id;
    public String name;
    public String category;


    public ArrayList<Articles> articles= new ArrayList<>();






    public News( String n, String r, String j) {
        this.id  = n;
        this.name = r;
        this.category = j;


    }
    public News() {

        this.id  = this.getId();
        this.name = this.getName();
        this.category = this.getCategory();


    }

    public void setArticles(ArrayList<Articles> ar)
    {
        articles.clear();
        articles.addAll(ar);
    }
    public ArrayList<Articles> getArticles0()
    {
        return articles;
    }


    public String getId() {
        return id;
    }

    public void setId(String o) {
        this.id = o;
    }

    public String getName() {
        return name;
    }

    public void setName(String o) {
        this.name = o;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String o) {
        this.category = o;
    }





}










