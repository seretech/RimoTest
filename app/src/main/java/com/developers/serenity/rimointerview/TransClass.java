package com.developers.serenity.rimointerview;

public class TransClass {

    public TransClass(){}

    private String img, desc, amount, state, date;

    public TransClass(String img, String desc, String amount, String state, String date) {
        this.img = img;
        this.desc = desc;
        this.amount = amount;
        this.state = state;
        this.date = date;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
