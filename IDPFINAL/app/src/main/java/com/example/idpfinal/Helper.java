package com.example.idpfinal;

public class Helper
{
    String pname;
    String price;

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getBarc() {
        return barc;
    }

    public void setBarc(String barc) {
        this.barc = barc;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    String quantity;
    String barc;

    public Helper(String pname, String price, int quantity, String barc, String username) {
        this.pname = pname;
        this.price = price;
        this.quantity= String.valueOf(quantity);
        this.barc = barc;
        this.username = username;
    }

    String username;
}
