package com.ujujzk.easyengmaterial.eeapp.grammar;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ujujzk.easyengmaterial.eeapp.R;
import com.ujujzk.easyengmaterial.eeapp.model.Topic;
import com.ujujzk.easyengmaterial.eeapp.vocabulary.PackListSelectableAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


class TopicListAdapter
        extends PackListSelectableAdapter<TopicListAdapter.TopicViewHolder> {

    private List<Topic> topics;
    private TopicViewHolder.ClickListener clickListener;
    private final Context mContext;

    TopicListAdapter (TopicViewHolder.ClickListener clickListener) {
        super();
        this.clickListener = clickListener;
        topics = new ArrayList<Topic>();
        this.mContext = (Context) clickListener;

    }

    public void addTopic(Topic newPack) {
        topics.add(newPack);
        notifyDataSetChanged();
    }

    boolean isTopicListEmpty(){
        return topics.isEmpty();
    }

    void addTopics(List<Topic> newPacks) {
        topics.addAll(newPacks);
        notifyDataSetChanged();
    }

    private Topic getTopic(int position) {
        return topics.get(position);
    }

    List<String> getSelectedTopicsIds (List<Integer> positions) {
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

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_list_item_2, parent, false);
        return new TopicViewHolder(v, clickListener);
    }

    @Override
    public void onBindViewHolder(TopicViewHolder holder, int position) {

        holder.topicTitle.setText(topics.get(position).getTitle());
        if (isSelected(position)){
            holder.topicTitle.setBackgroundColor(ContextCompat.getColor(mContext, R.color.gramm_list_item_selected_overlay_light));
        } else {
            holder.topicTitle.setBackgroundColor(ContextCompat.getColor(mContext, R.color.gramm_list_item_bgr_light));
        }
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

    static class TopicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView topicTitle;

        private TopicViewHolder.ClickListener clickListener;

        TopicViewHolder(View itemView, ClickListener clickListener) {
            super(itemView);

            topicTitle = (TextView) itemView.findViewById(R.id.topic_list_item_title);

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
