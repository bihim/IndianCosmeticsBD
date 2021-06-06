package com.indiancosmeticsbd.app.Model.CategoryWiseViewModel;

public class CategoryWiseViewModel
{
    private String id, name;

    public CategoryWiseViewModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String titleText(String title){
        if (title.equals("keya-seth-aromatherapy")) {
            String[] titleSplet = title.split("-");
            StringBuilder bobTheBuilder = new StringBuilder();
            for (int i = 0; i < titleSplet.length; i++) {
                bobTheBuilder.append(Character.toUpperCase(titleSplet[i].charAt(0))).append(titleSplet[i].substring(1));
                if (titleSplet.length != i) {
                    bobTheBuilder.append(" ");
                } else {
                    break;
                }
            }
            return bobTheBuilder.toString();
        } else {
            return Character.toUpperCase(title.charAt(0)) + title.substring(1);
            //return title;
        }
    }
}
