package com.ujujzk.easyengmaterial.eeapp.vocabulary;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.clans.fab.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.ujujzk.easyengmaterial.eeapp.Application;
import com.ujujzk.easyengmaterial.eeapp.R;
import com.ujujzk.easyengmaterial.eeapp.model.Card;
import com.ujujzk.easyengmaterial.eeapp.model.GlosbeResponse;
import com.ujujzk.easyengmaterial.eeapp.model.Pack;
import com.ujujzk.easyengmaterial.eeapp.util.ActivityUtil;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class EditPackActivity extends AppCompatActivity implements CardListAdapter.CardViewHolder.ClickListener {

    @SuppressWarnings("unused")
    private static final String TAG = EditPackActivity.class.getSimpleName();

    private static final String GLOSBE_QUERY = "https://glosbe.com/gapi/translate?from=rus&dest=eng&format=json&pretty=true&tm=false&phrase=";
    private static final String CARDS_TO_EDIT_KEY = "cardsToEditKeyEasyEnglish";
    private static final String PACK_TITLE_KEY = "packTitleKeyEasyEnglish";

    private RecyclerView cardList;
    private CardListAdapter cardListAdapter;
    private FloatingActionButton addCardFab;
    private TextView packTitle;
    Pack packToEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityUtil.setTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pack);

        Intent intent = getIntent();
        Long packToEditId = intent.getLongExtra(VocabularyActivity.SELECTED_PACK_ID, 0L);
        packToEdit = Application.localStore.readWithRelations(packToEditId, Pack.class);

        Toolbar toolBar = (Toolbar) findViewById(R.id.edit_pack_act_app_bar);
        ActivityUtil.setToolbarColor(this, toolBar.getId());
        setSupportActionBar(toolBar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        packTitle = (TextView) findViewById(R.id.edit_pack_act_pack_title);
        if(savedInstanceState != null){
            packTitle.setText(savedInstanceState.getString(PACK_TITLE_KEY));
        } else {
            packTitle.setText(packToEdit.getTitle());
        }
        packTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final TextView tv = (TextView) v;

                MaterialDialog editTitleDialog = new MaterialDialog.Builder(EditPackActivity.this)
                        .customView(R.layout.dialog_edit_card_title, true)
                        .positiveText(R.string.ok)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog dialog, DialogAction which) {
                                String newPackTitle = ((EditText) dialog.getCustomView().findViewById(R.id.dialog_edit_card_et_title)).getText().toString();
                                if (newPackTitle.length() > 0) {
                                    packTitle.setText(newPackTitle);
                                }
                            }
                        }).build();

                EditText titleInput = (EditText) editTitleDialog.getCustomView().findViewById(R.id.dialog_edit_card_et_title);
                titleInput.setText(tv.getText().toString());

                editTitleDialog.show();
            }
        });

        cardList = (RecyclerView) findViewById(R.id.edit_pack_act_card_list);
        cardList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
        cardList.setLayoutManager(new LinearLayoutManager(this));
        if (savedInstanceState != null) {
            cardListAdapter = new CardListAdapter((List<Card>)savedInstanceState.getSerializable(CARDS_TO_EDIT_KEY), this);
        } else {
            cardListAdapter = new CardListAdapter(packToEdit.getAllCards(), this);
        }
        cardList.setAdapter(cardListAdapter);
        cardList.setItemAnimator(new DefaultItemAnimator());

        addCardFab = (FloatingActionButton) findViewById(R.id.edit_pack_act_fab);
        addCardFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final MaterialDialog md = new MaterialDialog.Builder(EditPackActivity.this)
                        .customView(R.layout.dialog_edit_card, true)
                        .positiveText(R.string.ok)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog dialog, DialogAction which) {
                                cardListAdapter.addCard(
                                        ((EditText) dialog.getCustomView().findViewById(R.id.dialog_edit_card_et_front_side)).getText().toString(),
                                        ((EditText) dialog.getCustomView().findViewById(R.id.dialog_edit_card_et_back_side)).getText().toString()
                                );
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                                materialDialog.dismiss();
                            }
                        })
                        .build();

                md.getCustomView().findViewById(R.id.dialog_edit_card_translate).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new AsyncTask<Void, Void, String>() {

                            @Override
                            protected void onPostExecute(String translatedWord) {
                                super.onPostExecute(translatedWord);
                                if (!translatedWord.isEmpty()) {
                                    ((EditText) md.getCustomView().findViewById(R.id.dialog_edit_card_et_back_side)).setText(translatedWord);
                                }
                            }

                            @Override
                            protected String doInBackground(Void... params) {

                                String wordToTranslate = ((EditText) md.getCustomView().findViewById(R.id.dialog_edit_card_et_front_side)).getText().toString();
                                wordToTranslate = wordToTranslate.replace(" ", "_").toLowerCase();
                                if (!wordToTranslate.isEmpty() && isNetworkConnected()) {
                                    URL url;
                                    HttpURLConnection urlConnection = null;
                                    String translatedWordJSON = "";
                                    try {
                                        url = new URL(GLOSBE_QUERY + wordToTranslate);

                                        urlConnection = (HttpURLConnection) url.openConnection();
                                        InputStream in = urlConnection.getInputStream();
                                        translatedWordJSON = readStream(in);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    } finally {
                                        if (urlConnection != null) {
                                            urlConnection.disconnect();
                                        }
                                    }

                                    GsonBuilder builder = new GsonBuilder();
                                    Gson gson = builder.create();
                                    GlosbeResponse glosbeResponse = gson.fromJson(translatedWordJSON, GlosbeResponse.class);
                                    if (glosbeResponse.tuc.isEmpty()) {
                                        return "";
                                    }
                                    return "" + glosbeResponse.tuc.get(0).phrase.text;
                                }
                                return "";

                            }
                        }.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);

                    }
                });
                md.show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {

            saveDataToLocalBase();

            onBackPressed();
            overridePendingTransition(R.animator.activity_appear_alpha, R.animator.activity_disappear_to_right); //custom activity transition animation
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putString(PACK_TITLE_KEY, packTitle.getText().toString());
        savedInstanceState.putSerializable(CARDS_TO_EDIT_KEY, (ArrayList<Card>)cardListAdapter.getCards());
    }

    @Override
    public void onItemClicked(final int position) {
        showCardEditDialog(position);
    }

    @Override
    public boolean onItemLongClicked(int position) {
        return true;
    }

    private void saveDataToLocalBase() {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                packToEdit.setTitle(packTitle.getText().toString());
                packToEdit.removeCards();
                packToEdit.addCards((ArrayList<Card>) cardListAdapter.getCards());
                Application.localStore.updateWithRelations(packToEdit);
                return null;
            }
        }.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }

    private void showCardEditDialog(final int position) {
        MaterialDialog cardEditDialog = new MaterialDialog.Builder(this)
                .customView(R.layout.dialog_edit_card, true)
                .positiveText(R.string.ok)
                .neutralText(R.string.card_list_menu_delete)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        cardListAdapter.editCard(position,
                                new Card(
                                        ((EditText) dialog.getCustomView().findViewById(R.id.dialog_edit_card_et_front_side)).getText().toString(),
                                        ((EditText) dialog.getCustomView().findViewById(R.id.dialog_edit_card_et_back_side)).getText().toString()
                                )
                        );
                    }
                })
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        cardListAdapter.removeCard(position);
                    }
                })
                .build();

        final EditText frontInput = (EditText) cardEditDialog.getCustomView().findViewById(R.id.dialog_edit_card_et_front_side);
        frontInput.setText(cardListAdapter.getCard(position).getFront());
        final EditText backInput = (EditText) cardEditDialog.getCustomView().findViewById(R.id.dialog_edit_card_et_back_side);
        backInput.setText(cardListAdapter.getCard(position).getBack());

        cardEditDialog.getCustomView().findViewById(R.id.dialog_edit_card_translate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AsyncTask<Void, Void, String>() {

                    @Override
                    protected void onPostExecute(String translatedWord) {
                        super.onPostExecute(translatedWord);
                        if (!translatedWord.isEmpty()) {
                            backInput.setText(translatedWord);
                        }
                    }

                    @Override
                    protected String doInBackground(Void... params) {

                        String wordToTranslate = frontInput.getText().toString();
                        if (!wordToTranslate.isEmpty() && isNetworkConnected()) {
                            URL url;
                            HttpURLConnection urlConnection = null;
                            String translatedWordJSON = "";
                            try {
                                url = new URL(GLOSBE_QUERY + wordToTranslate);

                                urlConnection = (HttpURLConnection) url.openConnection();
                                InputStream in = urlConnection.getInputStream();
                                translatedWordJSON = readStream(in);

                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                if (urlConnection != null) {
                                    urlConnection.disconnect();
                                }
                            }

                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            GlosbeResponse glosbeResponse = gson.fromJson(translatedWordJSON, GlosbeResponse.class);
                            if (glosbeResponse.tuc.isEmpty()) {
                                return "";
                            }
                            return "" + glosbeResponse.tuc.get(0).phrase.text;
                        }
                        return "";

                    }
                }.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);

            }
        });
        cardEditDialog.show();
    }

    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while (i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

}
