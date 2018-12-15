package com.nihanabaci.knowyourgovernment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Official {

    private String government_o;
    private String name;
    private String RepubOrDemoc;

    public String address;
    public String phone;
    public String email;
    public String website;

    public String image;

    public String google;
    public String facebook;
    public String twitter;
    public String youtube;






    private static int ctr = 1;

    public Official(String g, String n, String r) {
        this.government_o = g;
        this.name  = n;
        this.RepubOrDemoc = r;
        ctr++;
    }

    public Official() {
        this.government_o = this.getOfficial();
        this.name  = this.getName();
        this.RepubOrDemoc = this.getROrD();
        ctr++;
    }


    public String getOfficial() {
        return government_o;
    }

    public void setOfficial(String o) {
        this.government_o = o;
    }

    public String getName() {
        return name;
    }

    public void setName(String n) {
        this.name = n;
    }

    public String getROrD() {
        return RepubOrDemoc;
    }

    public void setParty(String p) {
        this.RepubOrDemoc = p;
    }



    public String getAddress() {
        return address;
    }

    public void setAddress(String a) { this.address = a; }


    public String getPhone() { return phone; }

    public void setPhone(String s) {
        this.phone = s;
    }


    public String getEmail() { return email; }

    public void setEmail(String e) {
        this.email = e;
    }


    public String getWebsite() { return website; }

    public void setWebsite(String w) {
        this.website = w;
    }

    public String getGoogle() { return google; }

    public void setGoogle(String w) {
        this.google = w;
    }

    public String getFacebook() { return facebook; }

    public void setFacebook(String w) {
        this.facebook = w;
    }

    public String getTwitter() { return twitter; }

    public void setTwitter(String w) {
        this.twitter = w;
    }

    public String getYoutube() { return youtube; }

    public void setYouTube(String w) {
        this.youtube = w;
    }

    public String getImage() { return image; }

    public void setImage(String w) {
        this.image = w;
    }






    @Override
    public String toString() {
        return government_o + " (" + name + "), " + RepubOrDemoc + " "+ address;
    }
}
