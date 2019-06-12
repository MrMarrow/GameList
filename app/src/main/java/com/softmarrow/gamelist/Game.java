package com.softmarrow.gamelist;

public class Game {
    private String name;
    private String realPrice;
    private String discountPrice;

    public Game(String name, String realPrice, String discountPrice){
        this.name = name;
        this.realPrice = realPrice;
        this.discountPrice = discountPrice;
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
}
