package com.ujujzk.easyengmaterial.eeapp;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.clans.fab.FloatingActionButton;
import com.ujujzk.easyengmaterial.eeapp.model.MOC;
import com.ujujzk.easyengmaterial.eeapp.model.Pack;
import com.ujujzk.easyengmaterial.eeapp.util.ActivityUtil;


public class EditPackActivity extends AppCompatActivity {

    private Toolbar toolBar;
    private FloatingActionButton addCardFab;
    private TextView packTitle;
    private Pack packToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityUtil.setTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pack);

        packToEdit = MOC.getPack();

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
                String oldTitle = tv.getText().toString();

                new MaterialDialog.Builder(EditPackActivity.this)
                        .title("New title")
                        //.content("Fill")
                        .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL)
                        .input("Enter title", tv.getText().toString(), new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                // Do something
                                if (input.length() > 0) {
                                    tv.setText(input.toString());
                                }
                            }
                        }).show();

            }
        });

        addCardFab = (FloatingActionButton) findViewById(R.id.edit_pack_act_fab);
        addCardFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Add Card Clicked",
                        Toast.LENGTH_SHORT).show();

                //TODO add card
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            packToEdit.setTitle(packTitle.getText().toString());
            //TODO save all cards
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
