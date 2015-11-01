package com.ujujzk.easyengmaterial.eeapp;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ujujzk.easyengmaterial.eeapp.model.Card;

import java.util.ArrayList;
import java.util.List;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.CardViewHolder> {

    private List<Card> cards;
    private CardViewHolder.ClickListener clickListener;

    public CardListAdapter(List<Card> cards, CardViewHolder.ClickListener clickListener) {
        super();
        this.clickListener = clickListener;
        this.cards = new ArrayList<Card>();
        if (cards.size() > 0) {
            this.cards.addAll(cards);
        }
    }

    public List<Card> getCards () {
        return cards;
    }

    public void removeCard(int position) {

        if (position < cards.size()) {
            cards.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void addCard() {
        addCard("front", "back");
        notifyDataSetChanged();
    }

    public void addCard(String front, String back) {
        cards.add(new Card(front, back));
        notifyDataSetChanged();
    }

    public Card getCard(int position) {
        if (position < cards.size()) {
            return cards.get(position);
        }
        return null;
    }

    public void editCard (int position, Card newCard) {
        if (position < cards.size() && newCard != null) {
            cards.get(position).setFront(newCard.getFront());
            cards.get(position).setBack(newCard.getBack());
        }
        notifyDataSetChanged();
    }


    @Override
    public CardListAdapter.CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list_item, parent, false);
        CardViewHolder holder = new CardViewHolder(v, clickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        final Card card = cards.get(position);
        holder.front.setText(card.getFront());
        holder.back.setText(card.getBack());
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }


    public static class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener {

        TextView front;
        TextView back;

        private CardViewHolder.ClickListener clickListener;

        public CardViewHolder(View v, ClickListener clickListener) {
            super(v);

            front = (TextView) v.findViewById(R.id.card_list_item_front);
            back = (TextView) v.findViewById(R.id.card_list_item_back);

            this.clickListener = clickListener;

            v.setOnClickListener(this);
            v.setOnLongClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.onItemClicked(getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (clickListener != null) {
                return clickListener.onItemLongClicked(getAdapterPosition());
            }
            return false;
        }

        public interface ClickListener {
            public void onItemClicked(int position);
            public boolean onItemLongClicked(int position);
        }
    }
}
