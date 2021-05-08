package com.indiancosmeticsbd.app.Model.Category;

public class CategorySelectedModel
{
    private String main;
    private String header;
    private String id;

    public CategorySelectedModel(String main, String header, String id) {
        this.main = main;
        this.header = header;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
