package com.ujujzk.easyengmaterial.eeapp.dictionary;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ujujzk.easyengmaterial.eeapp.model.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    private List<Word> mWords;
    private WordViewHolder.ClickListener clickListener;

    public WordListAdapter(List<Word> words, WordViewHolder.ClickListener clickListener) {
        super();
        this.clickListener = clickListener;
        Collections.sort(words);
        mWords = new ArrayList<Word>(words);

    }

    public Word getWord(int position) {
        if (position < mWords.size()) {
            return mWords.get(position);
        }
        return null;
    }

    public int getPositionOf(String query) {
        for (int i = 0; i < mWords.size(); i++) {

            query = query.toLowerCase();
            final String word = mWords.get(i).getWordName().toLowerCase();

            if (word.startsWith(query)){
                return i;
            }
//            if (word.equalsIgnoreCase(query)){
//                return i;
//            }
        }
        return 0;
    }

    public void setWords(List<Word> newWords){
        Collections.sort(newWords);
        mWords = new ArrayList<Word>(newWords);

        notifyDataSetChanged();
    }

    @Override
    public WordListAdapter.WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        WordViewHolder holder = new WordViewHolder(v, clickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        final Word word = mWords.get(position);
        holder.text.setText(word.getWordName());
    }

    @Override
    public int getItemCount() {
        return mWords.size();
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
