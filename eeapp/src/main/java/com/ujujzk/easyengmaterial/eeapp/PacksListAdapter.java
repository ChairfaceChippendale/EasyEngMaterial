package com.ujujzk.easyengmaterial.eeapp;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ujujzk.easyengmaterial.eeapp.model.Pack;
import java.util.List;


public class PacksListAdapter
    extends PackListSelectableAdapter<PacksListAdapter.PackViewHolder>{

    @SuppressWarnings("unused")
    private static final String TAG = PacksListAdapter.class.getSimpleName();

    public static class PackViewHolder extends RecyclerView.ViewHolder {
        TextView packTitle;
        TextView packSize;
        PackViewHolder(View itemView) {
            super(itemView);
            packTitle = (TextView)itemView.findViewById(R.id.packs_list_item_title);
            packSize = (TextView)itemView.findViewById(R.id.packs_list_item_cards_number);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Item clicked at position " + getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.d(TAG, "Item clicked at position " + getAdapterPosition());
                    return false;
                }
            });
        }


    }

    private List<Pack> packs;

    PacksListAdapter(List<Pack> packs){
        this.packs = packs;
    }

    @Override
    public int getItemCount() {
        if (packs != null) {
            return packs.size();
        }
        return 0;
    }

    @Override
    public PackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.packs_list_item,parent,false);
        PackViewHolder holder = new PackViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(PackViewHolder holder, int position) {
        holder.packTitle.setText(packs.get(position).getTitle());
        holder.packSize.setText("" + packs.get(position).getCardsNumber() + " cards");

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


}

