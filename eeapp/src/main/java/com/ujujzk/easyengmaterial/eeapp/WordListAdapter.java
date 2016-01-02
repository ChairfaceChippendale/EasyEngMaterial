package com.ujujzk.easyengmaterial.eeapp;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ujujzk.easyengmaterial.eeapp.model.Word;

import java.util.ArrayList;
import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    private List<Word> words;
    private WordViewHolder.ClickListener clickListener;

    public WordListAdapter(List<Word> words, WordViewHolder.ClickListener clickListener) {
        super();
        this.clickListener = clickListener;
        this.words = new ArrayList<Word>();
        if (words.size() > 0) {
            this.words.addAll(words);
        }
    }

    public Word getWord(int position) {
        if (position < words.size()) {
            return words.get(position);
        }
        return null;
    }

    @Override
    public WordListAdapter.WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        WordViewHolder holder = new WordViewHolder(v, clickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        final Word word = words.get(position);
        holder.text.setText(word.getWordName());
    }

    @Override
    public int getItemCount() {
        return words.size();
    }


    public static class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView text;

        private WordViewHolder.ClickListener clickListener;

        public WordViewHolder(View v, ClickListener clickListener) {
            super(v);

            text = (TextView) v;
            this.clickListener = clickListener;

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.onItemClicked(getAdapterPosition());
            }
        }

        public interface ClickListener {
            public void onItemClicked(int position);
        }
    }
}
