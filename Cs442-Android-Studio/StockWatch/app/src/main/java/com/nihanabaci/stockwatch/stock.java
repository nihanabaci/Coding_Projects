package com.nihanabaci.stockwatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.Serializable;

public class stock implements Serializable{

    private String symbol;
    private String name;
    private double price;
    private double change;
    private double percentage;
    private String id;

    public stock(String symbol1, String name1, double price1, double change1, double percentage1, String ii) {
        this.symbol = symbol1;
        this.name = name1;
        this.price = price1;
        this.change = change1;
        this.percentage = percentage1;
        this.id = ii;
    }
    public stock(String symbol, String name, String id)
    {
        this.symbol = symbol;
        this.name = name;
        this.id = id;
    }


    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol2) {
        this.symbol = symbol2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price2) {
        this.price = price2;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change2) {
        this.change = change2;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage2) {
        this.percentage = percentage2;
    }

    public String getId() {
        return id;
    }



    @Override
    public String toString() {
        return "stock{" +
                "symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", change='" + change + '\'' +
                ", percentage='" + percentage + '\'' +
                '}';
    }
}
