package com.ujujzk.easyengmaterial.eeapp.dictionary;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.ujujzk.easyengmaterial.eeapp.Application;
import com.ujujzk.easyengmaterial.eeapp.R;
import com.ujujzk.easyengmaterial.eeapp.model.Dictionary;
import java.util.ArrayList;
import java.util.List;


    class DictionaryListAdapter extends RecyclerView.Adapter<DictionaryListAdapter.DictionaryViewHolder>{

    private List<Dictionary> dictionaries;
    private DictionaryViewHolder.ClickListener clickListener;
    private Context context;
    private String dictInProcess = "";

    DictionaryListAdapter(DictionaryViewHolder.ClickListener clickListener, Context context) {
        super();
        this.context = context;
        this.clickListener = clickListener;
        dictionaries = new ArrayList<Dictionary>();
        dictionaries.addAll(Application.localStore.readAll(Dictionary.class));
    }

    void setDictInProcess (String dictInProcessName) {
        if (!dictInProcess.equals(dictInProcessName)) {
            dictInProcess = dictInProcessName;
            updateDictionaries();
        }
    }

    private void updateDictionaries () {
        dictionaries.clear();
        dictionaries.addAll(Application.localStore.readAll(Dictionary.class));
        notifyDataSetChanged();
    }

    void removeDictionary(int position) {

        if (position < dictionaries.size()) {
            dictionaries.remove(position);
            notifyItemRemoved(position);
        }
    }

    Dictionary getDictionary(int position) {
        if (position < dictionaries.size()) {
            return dictionaries.get(position);
        }
        return null;
    }

    @Override
    public DictionaryListAdapter.DictionaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dictionary_list_item, parent, false);
        return new DictionaryViewHolder(v, clickListener, context);
    }

    @Override
    public void onBindViewHolder(DictionaryViewHolder holder, int position) {
        final Dictionary dict = dictionaries.get(position);

        holder.dictionaryName.setText(dict.getDictionaryName());
        if (dict.getDictionaryName().equals(dictInProcess)) {
            holder.removeBtn.setVisibility(View.GONE);
            holder.progressView.setVisibility(View.VISIBLE);
        } else {
            holder.removeBtn.setVisibility(View.VISIBLE);
            holder.progressView.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return dictionaries.size();
    }


    static class DictionaryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView dictionaryName;
        ImageButton removeBtn;
        CircularProgressView progressView;
        private DictionaryViewHolder.ClickListener clickListener;

        DictionaryViewHolder(View v, ClickListener clickListener, Context context) {
            super(v);

            dictionaryName = (TextView) v.findViewById(R.id.dictionary_list_item_title);
            progressView = (CircularProgressView) v.findViewById(R.id.dictionary_list_item_progress_bar);
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
