package com.ujujzk.easyengmaterial.eeapp.model;


import com.github.aleksandrsavosh.simplestore.Base;

public class Settings {

    private int fontSize;
    private int mainColor;

    public Settings() {
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public int getMainColor() {
        return mainColor;
    }

    public void setMainColor(int mainColor) {
        this.mainColor = mainColor;
    }
}
