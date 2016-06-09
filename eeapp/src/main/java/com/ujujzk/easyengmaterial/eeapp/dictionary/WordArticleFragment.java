package com.ujujzk.easyengmaterial.eeapp.dictionary;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.aleksandrsavosh.simplestore.KeyValue;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.ujujzk.easyengmaterial.eeapp.Application;
import com.ujujzk.easyengmaterial.eeapp.R;
import com.ujujzk.easyengmaterial.eeapp.model.Article;
import com.ujujzk.easyengmaterial.eeapp.model.Word;

import java.util.ArrayList;
import java.util.List;

public class WordArticleFragment extends Fragment implements WordLinkClickListener {

    @SuppressWarnings("unused")
    private static final String TAG = WordArticleFragment.class.getSimpleName();

    private RecyclerView articleList;
    private ArticleListAdapter articleListAdapter;
    private CircularProgressView progressBar;

    private String selectedWordName = "";

    public WordArticleFragment() {
    }

    void setSelectedWord(String wordName) {
        selectedWordName = wordName;
        updateArticleList(wordName);
    }

    String getSelectedWordName() {
        return selectedWordName;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_word_article, container, false);
        progressBar = (CircularProgressView) v.findViewById(R.id.word_article_fr_progress_bar);
        articleList = (RecyclerView) v.findViewById(R.id.word_article_fr_rv_article_list);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        articleList.setLayoutManager(new LinearLayoutManager(getActivity()));
        articleListAdapter = new ArticleListAdapter(new ArrayList<Article>(), this);
        articleList.setAdapter(articleListAdapter);
        articleList.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onWordLinkClicked(String wordName) {
        selectedWordName = wordName;
        Activity parentActivity = getActivity();
        if (parentActivity instanceof DictionaryActivity) {
            ((DictionaryActivity) parentActivity).addWordToHistory(wordName);
        }
        updateArticleList(selectedWordName);
    }

    private void updateArticleList(String wordName) {
        new AsyncTask<String, Void, List<Article>>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar.setVisibility(View.VISIBLE);
                articleList.setVisibility(View.GONE);
            }

            @Override
            protected List<Article> doInBackground(String... params) {
                return Application.localStore.readBy(Article.class, new KeyValue("wordName", params[0]));
            }

            @Override
            protected void onPostExecute(List<Article> articles) {

                articleListAdapter.setArticles(articles);
                progressBar.setVisibility(View.GONE);
                articleList.setVisibility(View.VISIBLE);
                articleList.scrollToPosition(0);
            }
        }.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, wordName);
    }
}
