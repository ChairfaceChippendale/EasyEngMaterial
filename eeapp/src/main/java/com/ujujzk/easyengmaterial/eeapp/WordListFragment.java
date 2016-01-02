package com.ujujzk.easyengmaterial.eeapp;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import com.ujujzk.easyengmaterial.eeapp.model.Word;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class WordListFragment extends Fragment implements WordListAdapter.WordViewHolder.ClickListener{

    SearchView searchView;
    private RecyclerView wordList;
    private WordListAdapter wordListAdapter;
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
        View v = inflater.inflate(R.layout.fragment_word_list, container, false);

        searchView = (SearchView) v.findViewById(R.id.word_list_fr_search);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });

        wordList = (RecyclerView) v.findViewById(R.id.word_list_fr_list);
        wordList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).build());
        wordList.setLayoutManager(new LinearLayoutManager(getActivity()));


        //--MOC--
        List<Word> wordListContent = new ArrayList<Word>();
        for (int i = 0; i < 9000; i++){
            wordListContent.add(new Word("Hello"+i));
        }
        //------------------


        wordListAdapter = new WordListAdapter(wordListContent, this);
        wordList.setAdapter(wordListAdapter);
        wordList.setItemAnimator(new DefaultItemAnimator());

        return v;
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
}
