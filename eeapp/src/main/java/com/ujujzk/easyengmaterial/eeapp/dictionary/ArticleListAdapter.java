package com.ujujzk.easyengmaterial.eeapp.dictionary;


import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ujujzk.easyengmaterial.eeapp.R;
import com.ujujzk.ee.domain.usecase.dic.model.Article;

import java.util.ArrayList;
import java.util.List;

class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder>{

    @SuppressWarnings("unused")
    private static final String TAG = ArticleListAdapter.class.getSimpleName();

    private List<Article> mArticles;
    private WordLinkClickListener mWordLinkClickListener;

    ArticleListAdapter(List<Article> articles, WordLinkClickListener cl) {
        super();

        mArticles = new ArrayList<Article>(articles);
        mWordLinkClickListener = cl;

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

        setTextViewHTML(holder.wordArticle, article.getArticleHTMLStyle());
        holder.wordArticle.setMovementMethod(LinkMovementMethod.getInstance());
//        holder.wordArticle.setText( Html.fromHtml(article.getArticleHTMLStyle()) );
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

    private void makeLinkClickable(SpannableStringBuilder strBuilder, final URLSpan span)
    {
        int start = strBuilder.getSpanStart(span);
        int end = strBuilder.getSpanEnd(span);
        int flags = strBuilder.getSpanFlags(span);
        ClickableSpan clickable = new ClickableSpan() {
            public void onClick(View view) {
                // Do something with span.getURL() to handle the link click...
                Log.d(TAG, span.getURL());
                mWordLinkClickListener.onWordLinkClicked(span.getURL());
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                //set an appearance of a link
                ds.setUnderlineText(false);
                ds.setColor(Color.parseColor("#303F9F"));
            }
        };
        strBuilder.setSpan(clickable, start, end, flags);
        strBuilder.removeSpan(span);
    }

    private void setTextViewHTML(TextView text, String html)
    {
        CharSequence sequence = Html.fromHtml(html);
        SpannableStringBuilder strBuilder = new SpannableStringBuilder(sequence);
        URLSpan[] urls = strBuilder.getSpans(0, sequence.length(), URLSpan.class);
        for(URLSpan span : urls) {
            makeLinkClickable(strBuilder, span);
        }
        text.setText(strBuilder);
    }

}
