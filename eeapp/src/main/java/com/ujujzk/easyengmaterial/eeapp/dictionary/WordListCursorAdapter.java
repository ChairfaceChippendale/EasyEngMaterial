package com.ujujzk.easyengmaterial.eeapp.dictionary;


import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

class WordListCursorAdapter extends CursorRecyclerViewAdapter<WordListCursorAdapter.WordViewHolder> {

    private static final String TAG = WordListCursorAdapter.class.getSimpleName();

    private int cursorSize;
    private WordViewHolder.ClickListener clickListener;

    WordListCursorAdapter(Context context, Cursor cursor, WordViewHolder.ClickListener clickListener) {
        super(context, cursor);
        this.clickListener = clickListener;

    }

    Long getWordId(int position) {

        if (position < cursorSize) {

            Cursor c = super.getCursor();
            c.moveToPosition(position);

            return c.getLong(super.getCursor().getColumnIndex("_id"));
        }
        return null;

    }

    String getWordName (int position) {
        if (position < cursorSize) {

            Cursor c = super.getCursor();
            c.moveToPosition(position);

            return c.getString(super.getCursor().getColumnIndex("wordName"));
        }
        return null;
    }

    void setCursorSize (int size) {
        cursorSize = size;
    }

    @Override
    public WordListCursorAdapter.WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new WordViewHolder(v, clickListener);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, Cursor cursor) {

        String wordName = cursor.getString(cursor.getColumnIndex("wordName"));
        holder.text.setText(wordName);
    }

    @Override
    public int getItemCount() {
        return cursorSize;
    }

    static class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView text;
        private WordViewHolder.ClickListener clickListener;


        WordViewHolder(View v, ClickListener clickListener) {
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
            void onItemClicked(int position);
        }
    }
}
