package com.ujujzk.easyengmaterial.eeapp.dictionary;


import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.aleksandrsavosh.simplestore.SimpleStoreManager;
import com.github.aleksandrsavosh.simplestore.sqlite.SimpleStoreUtil;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.ujujzk.easyengmaterial.eeapp.Application;
import com.ujujzk.easyengmaterial.eeapp.R;
import com.ujujzk.easyengmaterial.eeapp.model.Word;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class WordListFragment extends Fragment implements
        WordListCursorAdapter.WordViewHolder.ClickListener,
        android.support.v7.widget.SearchView.OnQueryTextListener{

    @SuppressWarnings("unused")
    private static final String TAG = WordListFragment.class.getSimpleName();

    private static final int QUERY_WORD_RESULT_LIMIT = 100;
    private static final String QUERY_WORD_LIMIT_KEY = "limit";
    private static final String QUERY_WORD_KEY = "word";

    android.support.v7.widget.SearchView searchView;
    //private CircularProgressView progressBar;
    private RecyclerView wordList;
    private WordListCursorAdapter wordListAdapter;
    private LinearLayoutManager wordListManager;
    private Context context;
    private OnWordSelectedListener wordSelectedListener;

    private AsyncQuerySearcher querySearcher;

    public WordListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_word_list, container, false);

        searchView = (android.support.v7.widget.SearchView) v.findViewById(R.id.word_list_fr_search);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });
        searchView.setOnQueryTextListener(this);

        //progressBar = (CircularProgressView) v.findViewById(R.id.word_list_fr_progress_bar);

        wordList = (RecyclerView) v.findViewById(R.id.word_list_fr_list);
        wordList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).build());

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        wordListManager = new LinearLayoutManager(getActivity());
        wordList.setLayoutManager(wordListManager);
        wordListAdapter = new WordListCursorAdapter(getContext(), null, this);
        wordList.setAdapter(wordListAdapter);
        wordList.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            wordSelectedListener = (OnWordSelectedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onButtonPressed");
        }
    }

    @Override
    public void onItemClicked(int position) {

        wordSelectedListener.OnWordSelected(wordListAdapter.getWordId(position));

        if (context != null) {
            TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.dict_act_tabs);
            int wordArticleTabPosition = ((DictionaryActivity)getActivity()).getTabPositionByTitle(getResources().getString(R.string.word_article_fragment_title));
            if (wordArticleTabPosition < tabLayout.getTabCount()) {
                tabLayout.getTabAt(wordArticleTabPosition).select();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        context = null;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        Bundle args = new Bundle();
        args.putString(QUERY_WORD_KEY, query);
        args.putInt(QUERY_WORD_LIMIT_KEY, QUERY_WORD_RESULT_LIMIT);


        if(querySearcher != null && querySearcher.getStatus() == AsyncTask.Status.RUNNING){
            querySearcher.cancel(true);
        }
        querySearcher = new AsyncQuerySearcher();
        querySearcher.execute(args);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        //TODO check if there is this word and
        //TODO go to WordArticleFragment
        return true;
    }


    class AsyncQuerySearcher extends AsyncTask<Bundle,Void,Pair<Cursor,Integer>> {

        @Override
        protected Pair<Cursor,Integer> doInBackground(Bundle... bndl) {

            if (bndl == null || bndl.length == 0){

                //return new Pair<Cursor, Integer>(db.getAllData(),db.getAllData().getCount());
                return new Pair<Cursor, Integer>(null,0);
            }
            final String queryWord = bndl[0].getString(QUERY_WORD_KEY);
            final int limit = bndl[0].getInt(QUERY_WORD_LIMIT_KEY);

            if (queryWord == null || limit <= 0) {
                //return new Pair<Cursor, Integer>(db.getAllData(),db.getAllData().getCount());
                return new Pair<Cursor, Integer>(null,0);
            }
            if (queryWord.length() == 0){
                return new Pair<Cursor, Integer>(null,0);
            }

            return getLimitedDataByQueryWord(queryWord, limit);
        }

        @Override
        protected void onPostExecute(Pair<Cursor, Integer> cursorAndSize) {
            super.onPostExecute(cursorAndSize);
            if (cursorAndSize != null){

                wordListAdapter.changeCursor(cursorAndSize.first);
                wordListAdapter.setCursorSize(cursorAndSize.second);
                wordListManager.scrollToPosition(0);
            }
        }
    }


    public static Pair<Cursor,Integer> getLimitedDataByQueryWord(String queryWord, int limitNum) {

        //TODO add command "DISTINCT" to rawQuery

        String selection = "wordName" + " LIKE '" + queryWord + "%'";
        String limit = "" + limitNum;

        String tableName = SimpleStoreUtil.getTableName(Word.class);

        Cursor cursor = Application.getStoreManager().getSqLiteHelper().getReadableDatabase().query(tableName, null, selection, null, null, null, "wordName", limit);
        int cursorCount = getLimitedDataByQueryWordCount(queryWord, limitNum);

        return new Pair<Cursor,Integer>(cursor, cursorCount);
    }

    private static int getLimitedDataByQueryWordCount (String queryWord, int limitNum) {
        String tableName = SimpleStoreUtil.getTableName(Word.class);
        int count;
        String countQuery = "select count(*) from " + tableName + " WHERE " + "wordName" + " LIKE '" + queryWord + "%'";
        Cursor c = Application.getStoreManager().getSqLiteHelper().getReadableDatabase().rawQuery(countQuery, null);
        c.moveToFirst();
        count = c.getInt(0);
        if (count > limitNum) {
            return limitNum;
        } else if (count <= 0) {
            return 0;
        } else {
            return count;
        }
    }


}
