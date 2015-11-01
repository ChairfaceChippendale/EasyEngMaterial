package com.ujujzk.easyengmaterial.eeapp;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ujujzk.easyengmaterial.eeapp.model.Card;
import com.ujujzk.easyengmaterial.eeapp.model.MOC;
import com.ujujzk.easyengmaterial.eeapp.model.Pack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class PacksListAdapter
        extends PackListSelectableAdapter<PacksListAdapter.PackViewHolder> {

    private List<Pack> packs;
    private ArrayList<Card> aggregateCardsToLearn;

    private PackViewHolder.ClickListener clickListener;

    public PacksListAdapter(PackViewHolder.ClickListener clickListener) {
        super();
        this.clickListener = clickListener;

        packs = new ArrayList<Pack>();
    }

    public void addPack(){
        addPack(new Pack("New Pack", new ArrayList<Card>()));
        notifyDataSetChanged();
    }

    public void addPack(Pack newPack) {
        packs.add(newPack);
        notifyDataSetChanged();
    }

    public void addPacks(List<Pack> newPacks) {
        packs.addAll(newPacks);
        notifyDataSetChanged();
    }

    public Pack getPack(int position) {
        return packs.get(position);
    }

    public List<String> getSelectedPacksId (List<Integer> positions) {
        List <String> ids = new ArrayList<String>();

        Collections.sort(positions, new Comparator<Integer>() {
            @Override
            public int compare(Integer lhs, Integer rhs) {
                return rhs - lhs;
            }
        });

        while (!positions.isEmpty()) {
            if (positions.size() == 1) {
                ids.add(getPack(positions.get(0)).getObjectId());
                positions.remove(0);
            } else {
                int count = 1;
                while (positions.size() > count && positions.get(count).equals(positions.get(count - 1) - 1)) {
                    ++count;
                }

                if (count == 1) {
                    ids.add(getPack(positions.get(0)).getObjectId());
                } else {
                    for (int i = 0; i < count; ++i) {
                        ids.add(getPack(positions.get(count - 1) ).getObjectId());
                    }
                }
                for (int i = 0; i < count; ++i) {
                    positions.remove(0);
                }
            }
        }

        return ids;
    }

    public List<String> getSelectedPacksCardsIds (List<Integer> positions) {
        List <String> ids = new ArrayList<String>();

        Collections.sort(positions, new Comparator<Integer>() {
            @Override
            public int compare(Integer lhs, Integer rhs) {
                return rhs - lhs;
            }
        });

        while (!positions.isEmpty()) {
            if (positions.size() == 1) {
                ids.addAll(getPack(positions.get(0)).getAllCardIds());
                positions.remove(0);
            } else {
                int count = 1;
                while (positions.size() > count && positions.get(count).equals(positions.get(count - 1) - 1)) {
                    ++count;
                }

                if (count == 1) {
                    ids.addAll(getPack(positions.get(0)).getAllCardIds());
                } else {
                    for (int i = 0; i < count; ++i) {
                        ids.addAll(getPack(positions.get(count - 1) ).getAllCardIds());
                    }
                }
                for (int i = 0; i < count; ++i) {
                    positions.remove(0);
                }
            }
        }
        return ids;
    }

    public void removePack(int position){
        if(position < packs.size()) {
            Application.packLocalCrudDao.deleteWithRelations(packs.get(position).getObjectId());
            packs.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void removePacks(List<Integer> positions) {
        // Reverse-sort the list
        Collections.sort(positions, new Comparator<Integer>() {
            @Override
            public int compare(Integer lhs, Integer rhs) {
                return rhs - lhs;
            }
        });

        while (!positions.isEmpty()) {
            if (positions.size() == 1) {
                removePack(positions.get(0));
                positions.remove(0);
            } else {
                int count = 1;
                while (positions.size() > count && positions.get(count).equals(positions.get(count - 1) - 1)) {
                    ++count;
                }

                if (count == 1) {
                    removePack(positions.get(0));
                } else {
                    removeRange(positions.get(count - 1), count);
                }

                for (int i = 0; i < count; ++i) {
                    positions.remove(0);
                }
            }
        }
    }

    private void removeRange(int positionStart, int packCount) {
        for (int i = 0; i < packCount; ++i) {
            removePack(positionStart);
        }
        notifyItemRangeRemoved(positionStart, packCount);
    }

    public void updatePacks(List<Pack> newPacks){
        packs.clear();
        packs.addAll(newPacks);
        notifyDataSetChanged();

    }

    @Override
    public PackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.packs_list_item, parent, false);
        PackViewHolder holder = new PackViewHolder(v, clickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(PackViewHolder holder, int position) {

        holder.packTitle.setText(packs.get(position).getTitle());
        holder.packSize.setText("" + packs.get(position).getCardsNumber() + " cards");
        holder.selectedOverlay.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        if (packs != null) {
            return packs.size();
        }
        return 0;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public static class PackViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        TextView packTitle;
        TextView packSize;
        View selectedOverlay;

        private PackViewHolder.ClickListener clickListener;

        public PackViewHolder(View itemView, ClickListener clickListener) {
            super(itemView);

            packTitle = (TextView) itemView.findViewById(R.id.packs_list_item_title);
            packSize = (TextView) itemView.findViewById(R.id.packs_list_item_cards_number);
            selectedOverlay = itemView.findViewById(R.id.selected_overlay);

            selectedOverlay = itemView.findViewById(R.id.selected_overlay);

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
            public void onItemClicked(int position);
            public boolean onItemLongClicked(int position);
        }
    }
}

