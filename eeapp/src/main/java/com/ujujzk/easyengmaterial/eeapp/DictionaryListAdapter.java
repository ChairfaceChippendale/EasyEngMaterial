package com.ujujzk.easyengmaterial.eeapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ujujzk.easyengmaterial.eeapp.model.Dictionary;

import java.util.ArrayList;
import java.util.List;


public class DictionaryListAdapter extends RecyclerView.Adapter<DictionaryListAdapter.DictionaryViewHolder>{

    private List<Dictionary> dictionaries;
    private DictionaryViewHolder.ClickListener clickListener;

    public DictionaryListAdapter(List<Dictionary> dictionaries, DictionaryViewHolder.ClickListener clickListener) {
        super();
        this.clickListener = clickListener;
        this.dictionaries = new ArrayList<Dictionary>();
        if (dictionaries.size() > 0) {
            this.dictionaries.addAll(dictionaries);
        }
    }

    public List<Dictionary> getDictionaries () {
        return dictionaries;
    }

    public void removeDictionary(int position) {

        if (position < dictionaries.size()) {
            dictionaries.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void addDictionary() {
        addDictionary(new Dictionary("New Dictionary"));
        notifyDataSetChanged();
    }

    public void addDictionary(Dictionary dictionary) {
        dictionaries.add(dictionary);
        notifyDataSetChanged();
    }

    public Dictionary getDictionary(int position) {
        if (position < dictionaries.size()) {
            return dictionaries.get(position);
        }
        return null;
    }

    @Override
    public DictionaryListAdapter.DictionaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        DictionaryViewHolder holder = new DictionaryViewHolder(v, clickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(DictionaryViewHolder holder, int position) {
        final Dictionary d = dictionaries.get(position);
        holder.dictionaryName.setText(d.getDictionaryName());
    }

    @Override
    public int getItemCount() {
        return dictionaries.size();
    }

    public static class DictionaryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView dictionaryName;
        private DictionaryViewHolder.ClickListener clickListener;

        public DictionaryViewHolder(View v, ClickListener clickListener) {
            super(v);

            dictionaryName = (TextView) v;
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
