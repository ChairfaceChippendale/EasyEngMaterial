package com.ujujzk.easyengmaterial.eeapp.dictionary;


import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ujujzk.easyengmaterial.eeapp.Application;
import com.ujujzk.easyengmaterial.eeapp.R;
import com.ujujzk.easyengmaterial.eeapp.model.Article;
import com.ujujzk.easyengmaterial.eeapp.model.Dictionary;
import com.ujujzk.easyengmaterial.eeapp.model.Word;

import java.util.ArrayList;
import java.util.List;

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder>{

    private List<Article> mArticles;

    public ArticleListAdapter(List<Article> articles) {
        super();

        mArticles = new ArrayList<Article>(articles);

    }

    public Article getArticle(int position) {
        if (position < mArticles.size()) {
            return mArticles.get(position);
        }
        return null;
    }

    public void setArticles(List<Article> newWords){
        mArticles = new ArrayList<Article>(newWords);
        notifyDataSetChanged();
    }

    @Override
    public ArticleListAdapter.ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_list_item, parent, false);
        ArticleViewHolder holder = new ArticleViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        final Article article = mArticles.get(position);

        holder.dictionaryName.setText(article.getDictionaryName());
        holder.wordName.setText(article.getWordName());
        holder.wordArticle.setText( Html.fromHtml(article.getArticleHTMLStyle()) );
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {

        TextView wordName;
        TextView dictionaryName;
        TextView wordArticle;



        public ArticleViewHolder(View v) {
            super(v);

            dictionaryName = (TextView) v.findViewById(R.id.article_list_item_dictionary_name);
            wordName = (TextView) v.findViewById(R.id.article_list_item_word_name);
            wordArticle = (TextView) v.findViewById(R.id.article_list_item_word_article);
        }
    }

}
