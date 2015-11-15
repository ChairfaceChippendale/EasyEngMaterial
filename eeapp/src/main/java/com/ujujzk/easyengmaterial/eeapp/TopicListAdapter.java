package com.ujujzk.easyengmaterial.eeapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.TextView;
import com.ujujzk.easyengmaterial.eeapp.model.Topic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Юлия on 11.11.2015.
 */
public class TopicListAdapter {

    private List<Topic> topics;
    private TopicViewHolder.ClickListener clickListener;

    public TopicListAdapter (TopicViewHolder.ClickListener clickListener) {
        super();
        this.clickListener = clickListener;
        topics = new ArrayList<Topic>();
    }




    public static class TopicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {



        private TopicViewHolder.ClickListener clickListener;

        public TopicViewHolder(View itemView, ClickListener clickListener) {
            super(itemView);



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
