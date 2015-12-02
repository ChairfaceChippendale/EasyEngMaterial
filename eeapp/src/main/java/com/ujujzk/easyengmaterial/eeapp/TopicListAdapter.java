package com.ujujzk.easyengmaterial.eeapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;
import com.ujujzk.easyengmaterial.eeapp.model.Topic;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class TopicListAdapter
        extends PackListSelectableAdapter<TopicListAdapter.TopicViewHolder>{

    private List<Topic> topics;
    private TopicViewHolder.ClickListener clickListener;

    public TopicListAdapter (TopicViewHolder.ClickListener clickListener) {
        super();
        this.clickListener = clickListener;
        topics = new ArrayList<Topic>();
    }

    public void addTopic(Topic newPack) {
        topics.add(newPack);
        notifyDataSetChanged();
    }

    public boolean isTopicListEmpty(){
        if (topics.isEmpty()){
            return true;
        }else {
            return false;
        }
    }

    public void addTopics(List<Topic> newPacks) {
        topics.addAll(newPacks);
        notifyDataSetChanged();
    }

    public Topic getTopic(int position) {
        return topics.get(position);
    }

    public List<String> getSelectedTopicsIds (List<Integer> positions) {
        List <String> ids = new ArrayList<String>();

        Collections.sort(positions, new Comparator<Integer>() {
            @Override
            public int compare(Integer lhs, Integer rhs) {
                return rhs - lhs;
            }
        });
        for (Integer i: positions){
            ids.add(getTopic(i).getCloudId());
        }
        return ids;
    }

    @Override
    public TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_list_item, parent, false);
        TopicViewHolder holder = new TopicViewHolder(v, clickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(TopicViewHolder holder, int position) {

        holder.topicTitle.setText(topics.get(position).getTitle());
        holder.selectedOverlay.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        if (topics != null) {
            return topics.size();
        }
        return 0;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class TopicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView topicTitle;
        View selectedOverlay;

        private TopicViewHolder.ClickListener clickListener;

        public TopicViewHolder(View itemView, ClickListener clickListener) {
            super(itemView);

            topicTitle = (TextView) itemView.findViewById(R.id.topic_list_item_title);
            selectedOverlay = itemView.findViewById(R.id.topic_list_item_selected_overlay);

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
