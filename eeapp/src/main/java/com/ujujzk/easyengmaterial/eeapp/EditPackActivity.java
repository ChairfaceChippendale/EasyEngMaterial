package com.ujujzk.easyengmaterial.eeapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.clans.fab.FloatingActionButton;
import com.ujujzk.easyengmaterial.eeapp.model.Card;
import com.ujujzk.easyengmaterial.eeapp.model.MOC;
import com.ujujzk.easyengmaterial.eeapp.model.Pack;
import com.ujujzk.easyengmaterial.eeapp.util.ActivityUtil;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;


public class EditPackActivity extends AppCompatActivity implements CardListAdapter.CardViewHolder.ClickListener {

    private Toolbar toolBar;
    private RecyclerView cardList;
    private CardListAdapter cardListAdapter;
    private FloatingActionButton addCardFab;
    private TextView packTitle;
    private String packToEditId;
    private Pack packToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityUtil.setTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pack);

        Intent intent = getIntent();
        packToEditId = intent.getStringExtra(VocabularyActivity.SELECTED_PACK_ID);
        packToEdit = Application.packLocalCrudDao.read(packToEditId);

        toolBar = (Toolbar) findViewById(R.id.edit_pack_act_app_bar);
        ActivityUtil.setToolbarColor(this, toolBar.getId());
        setSupportActionBar(toolBar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        packTitle = (TextView) findViewById(R.id.edit_pack_act_pack_title);
        packTitle.setText(packToEdit.getTitle().toString());
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
        cardListAdapter = new CardListAdapter(packToEdit.getAllCards(), this);
        cardList.setAdapter(cardListAdapter);
        cardList.setItemAnimator(new DefaultItemAnimator());

        addCardFab = (FloatingActionButton) findViewById(R.id.edit_pack_act_fab);
        addCardFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cardListAdapter.addCard();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {

            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {

        packToEdit.setTitle(packTitle.getText().toString());
        packToEdit.removeCards();
        packToEdit.addCards((ArrayList<Card>)cardListAdapter.getCards());
        Application.packLocalCrudDao.deleteWithRelations(packToEditId);
        Application.packLocalCrudDao.createWithRelations(packToEdit);

        super.onBackPressed();
    }


    @Override
    public void onItemClicked(final int position) {

        final int cardPosition = position;

        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .customView(R.layout.dialog_edit_card, true)
                .positiveText(R.string.ok)
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
                }).build();

        EditText frontInput = (EditText) dialog.getCustomView().findViewById(R.id.dialog_edit_card_et_front_side);
        frontInput.setText(cardListAdapter.getCard(position).getFront().toString());
        EditText backInput = (EditText) dialog.getCustomView().findViewById(R.id.dialog_edit_card_et_back_side);
        backInput.setText(cardListAdapter.getCard(position).getBack().toString());

        dialog.show();

        //Toast.makeText(getApplicationContext(), ""+position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onItemLongClicked(int position) {
        //TODO
        return true;
    }

}
