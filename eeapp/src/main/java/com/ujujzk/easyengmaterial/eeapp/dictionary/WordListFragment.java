package com.ujujzk.easyengmaterial.eeapp.dictionary;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.ujujzk.easyengmaterial.eeapp.Application;
import com.ujujzk.easyengmaterial.eeapp.R;
import com.ujujzk.easyengmaterial.eeapp.model.Word;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class WordListFragment extends Fragment implements
        WordListAdapter.WordViewHolder.ClickListener,
        android.support.v7.widget.SearchView.OnQueryTextListener{

    @SuppressWarnings("unused")
    private static final String TAG = WordListFragment.class.getSimpleName();

    android.support.v7.widget.SearchView searchView;
    private CircularProgressView progressBar;
    private RecyclerView wordList;
    private WordListAdapter wordListAdapter;
    private LinearLayoutManager wordListManager;
    private Context context;
    private OnWordSelectedListener wordSelectedListener;


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

        progressBar = (CircularProgressView) v.findViewById(R.id.word_list_fr_progress_bar);

        wordList = (RecyclerView) v.findViewById(R.id.word_list_fr_list);
        wordList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).build());

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        wordListManager = new LinearLayoutManager(getActivity());
        wordList.setLayoutManager(wordListManager);
        wordListAdapter = new WordListAdapter(new ArrayList<Word>(), this);
        wordList.setAdapter(wordListAdapter);
        wordList.setItemAnimator(new DefaultItemAnimator());
        updateWordList();

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
        //TODO go to WordArticleFragment

        Word word = wordListAdapter.getWord(position);

        wordSelectedListener.OnWordSelected(word.getLocalId());

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
        int position = wordListAdapter.getPositionOf(query);
        if (position > 0 && position < wordListAdapter.getItemCount()) {
            wordListManager.scrollToPositionWithOffset(
                    position,
                    2
            );
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        //TODO check if there is this word and
        //TODO go to WordArticleFragment
        return true;
    }


    public void updateWordList () {

        new AsyncTask<Void, Void, List<Word>>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                if(progressBar != null && wordList != null) {
                    progressBar.setVisibility(View.VISIBLE);
                    wordList.setVisibility(View.GONE);
                }
            }

            @Override
            protected List<Word> doInBackground(Void... params) {
                return Application.localStore.readAll(Word.class);
            }

            @Override
            protected void onPostExecute(List<Word> words) {
                if(progressBar != null && wordList != null) {
                    wordListAdapter.setWords(words);
                    progressBar.setVisibility(View.GONE);
                    wordList.setVisibility(View.VISIBLE);
                }
            }
        }.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }


}
