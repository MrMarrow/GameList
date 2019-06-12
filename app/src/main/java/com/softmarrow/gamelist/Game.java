package com.softmarrow.gamelist;

import android.graphics.Bitmap;

public class Game {

    private String name;
    private String realPrice;
    private String discountPrice;
    private String iconUrl;

    Game(String name, String realPrice, String discountPrice, String iconUrl) {
        this.name = name;
        this.realPrice = realPrice;
        this.discountPrice = discountPrice;
        this.iconUrl = iconUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(String realPrice) {
        this.realPrice = realPrice;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
