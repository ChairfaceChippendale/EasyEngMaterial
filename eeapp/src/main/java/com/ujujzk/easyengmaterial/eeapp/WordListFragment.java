package com.ujujzk.easyengmaterial.eeapp;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ujujzk.easyengmaterial.eeapp.model.Word;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WordListFragment extends Fragment implements
        WordListAdapter.WordViewHolder.ClickListener,
        android.support.v7.widget.SearchView.OnQueryTextListener{

    @SuppressWarnings("unused")
    private static final String TAG = WordListFragment.class.getSimpleName();

    android.support.v7.widget.SearchView searchView;
    private RecyclerView wordList;
    private WordListAdapter wordListAdapter;
    private LinearLayoutManager wordListManager;
    private List<Word> wordListContent;
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

        wordList = (RecyclerView) v.findViewById(R.id.word_list_fr_list);
        wordList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).build());

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        wordListManager = new LinearLayoutManager(getActivity());
        wordList.setLayoutManager(wordListManager);

        //--MOC--
        wordListContent = new ArrayList<Word>();
        String[] s = {"abandoned", "able", "absolute", "adorable", "adventurous", "academic", "acceptable", "acclaimed", "accomplished", "accurate", "aching", "acidic",
                "acrobatic", "active", "actual", "adept", "admirable", "admired", "adolescent", "adorable", "adored", "advanced", "afraid", "affectionate", "aged", "aggravating"};
        for (int i = 0; i < s.length; i++) {
            wordListContent.add(new Word(s[i]));
        }
        //------------------

        wordListAdapter = new WordListAdapter(wordListContent, this);
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
        //TODO go to WordArticleFragment

        Word word = wordListAdapter.getWord(position);

        wordSelectedListener.OnWordSelected(word.getLocalId());

        if (context != null) {
            TabLayout tabhost = (TabLayout) getActivity().findViewById(R.id.dict_act_tabs);
            int wordArticleTabPosition = 1;
            if (wordArticleTabPosition < tabhost.getTabCount()) {
                tabhost.getTabAt(wordArticleTabPosition).select();
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
}
