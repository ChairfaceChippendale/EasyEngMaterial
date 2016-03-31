package com.ujujzk.easyengmaterial.eeapp.dictionary;


import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.github.aleksandrsavosh.simplestore.KeyValue;
import com.ujujzk.easyengmaterial.eeapp.Application;
import com.ujujzk.easyengmaterial.eeapp.R;
import com.ujujzk.easyengmaterial.eeapp.model.Article;
import com.ujujzk.easyengmaterial.eeapp.model.Dictionary;
import com.ujujzk.easyengmaterial.eeapp.model.Word;

import java.util.ArrayList;
import java.util.List;

class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder>{

    private List<Article> mArticles;

    ArticleListAdapter(List<Article> articles) {
        super();

        mArticles = new ArrayList<Article>(articles);

    }

    public Article getArticle(int position) {
        if (position < mArticles.size()) {
            return mArticles.get(position);
        }
        return null;
    }

    void setArticles(List<Article> newWords){
        mArticles = new ArrayList<Article>(newWords);
        notifyDataSetChanged();
    }

    @Override
    public ArticleListAdapter.ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_list_item, parent, false);
        return new ArticleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        final Article article = mArticles.get(position);

        String dictName = Application.localStore.read(article.getDictionaryId(), Dictionary.class).getDictionaryName();

        holder.dictionaryName.setText(dictName);
        holder.wordName.setText(article.getWordName());
        holder.wordArticle.setText( Html.fromHtml(article.getArticleHTMLStyle()) );
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    static class ArticleViewHolder extends RecyclerView.ViewHolder {

        TextView wordName;
        TextView dictionaryName;
        TextView wordArticle;

        ArticleViewHolder(View v) {
            super(v);

            dictionaryName = (TextView) v.findViewById(R.id.article_list_item_dictionary_name);
            wordName = (TextView) v.findViewById(R.id.article_list_item_word_name);
            wordArticle = (TextView) v.findViewById(R.id.article_list_item_word_article);
        }
    }

}
