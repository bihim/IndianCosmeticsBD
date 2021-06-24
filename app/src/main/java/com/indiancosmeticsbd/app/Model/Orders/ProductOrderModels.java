package com.indiancosmeticsbd.app.Model.Orders;

public class ProductOrderModels
{
    private String p, s, c, q;

    public ProductOrderModels(String p, String s, String c, String q) {
        this.p = p;
        this.s = s;
        this.c = c;
        this.q = q;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }
}
