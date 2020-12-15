package com.example.ifarm.model;

import java.io.Serializable;

public class ProductModel implements Serializable {
    String name, superficie, production, percentage,humidite,idf,idc;
    int color, icon, trendingIcon;

    public ProductModel(String name, String superficie, String production, String percentage, String humidite, int color, int icon, int trendingIcon, String idf, String idc) {
        this.name = name;
        this.superficie = superficie;
        this.production = production;
        this.percentage = percentage;
        this.humidite = humidite;
        this.color = color;
        this.icon = icon;
        this.trendingIcon = trendingIcon;
        this.idf = idf;
        this.idc = idc;
    }

    public String getIdf() {
        return idf;
    }

    public void setIdf(String idf) {
        this.idf = idf;
    }

    public String getIdc() {
        return idc;
    }

    public void setIdc(String idc) {
        this.idc = idc;
    }

    public String getHumidite() {
        return humidite;
    }

    public void setHumidite(String humidite) {
        this.humidite = humidite;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuperficie() {
        return superficie;
    }

    public void setSuperficie(String superficie) {
        this.superficie = superficie;
    }

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getTrendingIcon() {
        return trendingIcon;
    }

    public void setTrendingIcon(int trendingIcon) {
        this.trendingIcon = trendingIcon;
    }
}
