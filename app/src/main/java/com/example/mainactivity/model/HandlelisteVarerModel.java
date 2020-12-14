package com.example.mainactivity.model;

public class HandlelisteVarerModel {
    private String id, vare;
    private boolean isChecked;

    public HandlelisteVarerModel(String id, String vare, boolean isChecked) {
        this.id = id;
        this.vare = vare;
        this.isChecked = isChecked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVare() {
        return vare;
    }

    public void setVare(String vare) {
        this.vare = vare;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
