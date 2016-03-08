package com.ujujzk.easyengmaterial.eeapp.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.github.aleksandrsavosh.simplestore.Base;

import java.io.Serializable;

public class Card extends Base {

    private String front;
    private String back;


    public Card() {}

    public Card(String front, String back) {
        this.front = front;
        this.back = back;
    }

    public String getFront() {
        return front;
    }

    public void setFront(String front) {
        this.front = front;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }


    @Override
    public String toString() {
        return "Card{" +
                "front='" + front + '\'' +
                ", back='" + back + '\'' +
                "} " + super.toString();
    }

}
