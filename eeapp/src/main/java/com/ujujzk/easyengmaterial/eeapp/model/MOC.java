package com.ujujzk.easyengmaterial.eeapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ���� on 06.10.2015.
 */
public class MOC {

    public static ArrayList<Pack> getPacksMOC() {
        List<Pack> newPacks = new ArrayList<Pack>();
        List<Card> cards = new ArrayList<Card>();

        cards.add(new Card("pat", "cat"));
        cards.add(new Card("beast", "tiger"));
        newPacks.add(new Pack("animals", cards));
        newPacks.add(new Pack("cats", cards));
        newPacks.add(new Pack("soft pats", cards));

        cards.add(new Card("big pet","dog"));
        newPacks.add(new Pack("more pets",cards));

        return (ArrayList<Pack>) newPacks;
    }
}