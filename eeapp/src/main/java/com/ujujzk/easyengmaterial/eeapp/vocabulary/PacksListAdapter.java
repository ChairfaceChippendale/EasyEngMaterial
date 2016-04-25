package com.ujujzk.easyengmaterial.eeapp.vocabulary;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ujujzk.easyengmaterial.eeapp.Application;
import com.ujujzk.easyengmaterial.eeapp.R;
import com.ujujzk.easyengmaterial.eeapp.model.Card;
import com.ujujzk.easyengmaterial.eeapp.model.Pack;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class PacksListAdapter
        extends PackListSelectableAdapter<PacksListAdapter.PackViewHolder> {

    private List<Pack> packs;
    private PackViewHolder.ClickListener clickListener;

    PacksListAdapter(PackViewHolder.ClickListener clickListener) {
        super();
        this.clickListener = clickListener;
        packs = new ArrayList<Pack>();
    }

    public void addPack(){
        addPack(new Pack("New Pack", new ArrayList<Card>()));
        notifyDataSetChanged();
    }

    private void addPack(Pack newPack) {
        packs.add(newPack);
        notifyDataSetChanged();
    }

    void addPackOnPosition(int position, Pack newPack) {
        if (position <= packs.size()) {
            packs.add(position, newPack);
            notifyDataSetChanged();
        }
    }

    void addPacks(List<Pack> newPacks) {
        packs.addAll(newPacks);
        notifyDataSetChanged();
    }

    void updatePacks(List<Pack> newPacks){
        packs.clear();
        addPacks(newPacks);
    }

    private Pack getPack(int position) {
        return packs.get(position);
    }

    List<Long> getSelectedPacksId (List<Integer> positions) {

        List <Long> ids = new ArrayList<Long>();

        Collections.sort(positions, new Comparator<Integer>() {
            @Override
            public int compare(Integer lhs, Integer rhs) {
                return rhs - lhs;
            }
        });

        for (Integer i : positions) {
            ids.add(getPack(i).getLocalId());
        }

        return ids;
    }

    List<Long> getSelectedPacksCardsIds (List<Integer> positions) {
        List <Long> ids = new ArrayList<Long>();

        Collections.sort(positions, new Comparator<Integer>() {
            @Override
            public int compare(Integer lhs, Integer rhs) {
                return rhs - lhs;
            }
        });

        for (Integer pos: positions){
            ids.addAll(getPack(pos).getAllCardIds());
        }
        return ids;
    }

    private void removePack(int position){
        if(position < packs.size()) {
            Application.localStore.deleteWithRelations(packs.get(position).getLocalId(), Pack.class);
            packs.remove(position);
            notifyItemRemoved(position);
        }
    }

    void removePacks(List<Integer> positions) {
        // Reverse-sort the list
        Collections.sort(positions, new Comparator<Integer>() {
            @Override
            public int compare(Integer lhs, Integer rhs) {
                return rhs - lhs;
            }
        });

        for (Integer pos: positions){
            removePack(pos);
        }
    }

    @Override
    public PackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.packs_list_item, parent, false);
        return new PackViewHolder(v, clickListener);
    }

    @Override
    public void onBindViewHolder(PackViewHolder holder, int position) {

        holder.packTitle.setText(packs.get(position).getTitle());
        String packSize = packs.get(position).getCardsNumber() + " cards";
        holder.packSize.setText(packSize);
        holder.selectedOverlay.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        if (packs != null) {
            return packs.size();
        }
        return 0;
    }

    boolean isEmpty () {
        if (getItemCount() > 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    static class PackViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        TextView packTitle;
        TextView packSize;
        View selectedOverlay;
        private PackViewHolder.ClickListener clickListener;

        PackViewHolder(View itemView, ClickListener clickListener) {
            super(itemView);

            packTitle = (TextView) itemView.findViewById(R.id.packs_list_item_title);
            packSize = (TextView) itemView.findViewById(R.id.packs_list_item_cards_number);
            selectedOverlay = itemView.findViewById(R.id.pack_list_item_selected_overlay);

            this.clickListener = clickListener;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
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
            void onItemClicked(int position);
            boolean onItemLongClicked(int position);
        }
    }
}

