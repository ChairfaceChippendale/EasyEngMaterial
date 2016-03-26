package com.ujujzk.easyengmaterial.eeapp.dictionary;


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

public class WordArticleFragment extends Fragment{

    @SuppressWarnings("unused")
    private static final String TAG = WordArticleFragment.class.getSimpleName();

    private RecyclerView articleList;
    private ArticleListAdapter articleListAdapter;
    private CircularProgressView progressBar;

    private String selectedWordName = "";

    public WordArticleFragment() {
    }

    public void setSelectedWord(Long wordId){

        //TODO create articles - CHECK
        final Word selectedWord = Application.localStore.read(wordId, Word.class);
        if (selectedWord != null) {
            selectedWordName = selectedWord.getWordName();
            updateArticleList(selectedWordName);
        }
    }

    String getSelectedWordName(){
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
        articleListAdapter = new ArticleListAdapter(new ArrayList<Article>());
        articleList.setAdapter(articleListAdapter);
        articleList.setItemAnimator(new DefaultItemAnimator());
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
                return Application.localStore.readBy(Article.class, new KeyValue("wordName",params[0]));
            }

            @Override
            protected void onPostExecute(List<Article> articles) {

                articleListAdapter.setArticles(articles);
                progressBar.setVisibility(View.GONE);
                articleList.setVisibility(View.VISIBLE);
            }
        }.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, wordName);
    }
}
