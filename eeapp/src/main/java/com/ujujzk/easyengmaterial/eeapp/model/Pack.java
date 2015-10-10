package com.ujujzk.easyengmaterial.eeapp.model;


import java.util.ArrayList;
import java.util.List;

public class Pack extends Base {

    private String title;
    private List<Card> cards;

    public Pack() {
        this.title = "";
        this.cards = new ArrayList<Card>();
    }

    @Override
    public String toString() {
        return title;
    }

    public Pack(String title, List<Card> cards) {
        this.title = title;
        this.cards = new ArrayList<Card>(cards);

    }



    public int getCardsNumber () {
        return cards.size();
    }

    public List<Card> getAllCards() {
        return cards;
    }
    public void addCards(ArrayList<Card> cards) {
        this.cards.addAll(cards);
    }
    public void addCard(Card card) {
        this.cards.add(card);
    }
    public void removeCards() {
        this.cards.clear();
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}
