package com.ujujzk.easyengmaterial.eeapp.dictionary;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.ujujzk.easyengmaterial.eeapp.R;
import com.ujujzk.easyengmaterial.eeapp.model.Dictionary;
import java.util.ArrayList;
import java.util.List;


public class DictionaryListAdapter extends RecyclerView.Adapter<DictionaryListAdapter.DictionaryViewHolder>{

    private List<Dictionary> dictionaries;
    private DictionaryViewHolder.ClickListener clickListener;
    private Context context;

    public DictionaryListAdapter(List<Dictionary> dictionaries, DictionaryViewHolder.ClickListener clickListener, Context context) {
        super();
        this.context = context;
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dictionary_list_item, parent, false);
        DictionaryViewHolder holder = new DictionaryViewHolder(v, clickListener, context);
        return holder;
    }

    @Override
    public void onBindViewHolder(DictionaryViewHolder holder, int position) {
        final Dictionary dict = dictionaries.get(position);

        holder.dictionaryName.setText(dict.getDictionaryName());

    }

    @Override
    public int getItemCount() {
        return dictionaries.size();
    }


    public static class DictionaryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView dictionaryName;
        ImageButton removeBtn;
        private DictionaryViewHolder.ClickListener clickListener;

        public DictionaryViewHolder(View v, ClickListener clickListener, Context context) {
            super(v);

            dictionaryName = (TextView) v.findViewById(R.id.dictionary_list_item_title);
            removeBtn = (ImageButton) v.findViewById(R.id.dictionary_list_item_remove_btn);
            removeBtn.setImageDrawable(
                    new IconicsDrawable(context, GoogleMaterial.Icon.gmd_clear)
                            .sizeDp((int)context.getResources().getDimension(R.dimen.dictionary_list_item_remove_btn_size))
                            .color(ContextCompat.getColor(context, R.color.accent_light))
            );

            this.clickListener = clickListener;
            removeBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.onItemClicked(getAdapterPosition());
            }
        }

        public interface ClickListener {
            void onItemClicked(int position);
        }

    }
}
