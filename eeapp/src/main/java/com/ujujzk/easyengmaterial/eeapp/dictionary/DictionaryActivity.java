package com.ujujzk.easyengmaterial.eeapp.dictionary;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.ujujzk.easyengmaterial.eeapp.*;
import com.ujujzk.easyengmaterial.eeapp.grammar.GrammarActivity;
import com.ujujzk.easyengmaterial.eeapp.util.ActivityUtil;
import com.ujujzk.easyengmaterial.eeapp.vocabulary.VocabularyActivity;

import java.util.ArrayList;
import java.util.List;


public class DictionaryActivity extends AppCompatActivity implements OnWordSelectedListener {

    @SuppressWarnings("unused")
    private static final String TAG = DictionaryActivity.class.getSimpleName();

    private Toolbar toolBar;
    private Drawer navigationDrawer = null;
    private TabLayout tabLayout;
    protected ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityUtil.setTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        toolBar = (Toolbar) findViewById(R.id.dict_act_app_bar);
        ActivityUtil.setToolbarColor(this, toolBar.getId());
        setSupportActionBar(toolBar);
        navigationDrawer = makeNavigationDrawer();
        navigationDrawer.setSelection(Application.IDENTIFIER_DICTIONARY);

        viewPager = (ViewPager) findViewById(R.id.dict_act_viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.dict_act_tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    public int getTabPositionByTitle (String title){
        return ((ViewPagerAdapter)viewPager.getAdapter()).getFragmentPositionByTitle(title);
    }

    private Drawer makeNavigationDrawer () {
        return new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolBar)
                .withTranslucentStatusBar(true)
                .withAccountHeader(
                        new AccountHeaderBuilder()
                                .withActivity(this)
                                .withHeaderBackground(R.drawable.img_dict)
                                .build()
                )
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withName(R.string.title_activity_dictionary)
                                .withIcon(GoogleMaterial.Icon.gmd_chrome_reader_mode)
                                .withIdentifier(Application.IDENTIFIER_DICTIONARY),
                        new PrimaryDrawerItem()
                                .withName(R.string.title_activity_vocabulary)
                                .withIcon(GoogleMaterial.Icon.gmd_style)
                                .withIdentifier(Application.IDENTIFIER_VOCABULARY),
                        new PrimaryDrawerItem()
                                .withName(R.string.title_activity_grammar)
                                .withIcon(GoogleMaterial.Icon.gmd_class)
                                .withIdentifier(Application.IDENTIFIER_GRAMMAR),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem()
                                .withName(R.string.title_activity_about)
                                .withIcon(GoogleMaterial.Icon.gmd_info)
                                .withIdentifier(Application.IDENTIFIER_ABOUT),
                        new SecondaryDrawerItem()
                                .withName(R.string.title_share)
                                .withIcon(GoogleMaterial.Icon.gmd_share)
                                .withIdentifier(Application.IDENTIFIER_SHARE),
                        new SecondaryDrawerItem()
                                .withName(R.string.title_feedback)
                                .withIcon(GoogleMaterial.Icon.gmd_feedback)
                                .withIdentifier(Application.IDENTIFIER_FEEDBACK),
                        new SecondaryDrawerItem()
                                .withName(R.string.title_activity_settings)
                                .withIcon(GoogleMaterial.Icon.gmd_settings)
                                .withIdentifier(Application.IDENTIFIER_SETTING)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch(drawerItem.getIdentifier()){
                            case Application.IDENTIFIER_DICTIONARY:
                                break;
                            case Application.IDENTIFIER_VOCABULARY:
                                startActivity(new Intent(DictionaryActivity.this, VocabularyActivity.class));
                                finish();
                                break;
                            case Application.IDENTIFIER_GRAMMAR:
                                startActivity(new Intent(DictionaryActivity.this, GrammarActivity.class));
                                finish();
                                break;
                            case Application.IDENTIFIER_ABOUT:
                                startActivity(new Intent(DictionaryActivity.this, AboutActivity.class));
                                break;
                            case Application.IDENTIFIER_SHARE:
                                sendSharingMassage(getResources().getString(R.string.sharing_massage));
                                break;
                            case Application.IDENTIFIER_FEEDBACK:
                                sendFeedBack("I like this app");
                                break;
                            case Application.IDENTIFIER_SETTING:
                                startActivity(new Intent(DictionaryActivity.this, SettingsActivity.class));
                                break;
                            default:
                                break;
                        }
                        navigationDrawer.closeDrawer();
                        return true;
                    }
                })
                .build();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new WordListFragment(), getResources().getString(R.string.word_list_fragment_title));
        adapter.addFragment(new WordArticleFragment(), getResources().getString(R.string.word_article_fragment_title));
        adapter.addFragment(new WordHistoryFragment(), getResources().getString(R.string.word_history_fragment_title));
        viewPager.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dictionary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case R.id.dict_act_action_manager:
                startActivityForResult(new Intent(DictionaryActivity.this, DictManagerActivity.class), 1);
                overridePendingTransition(R.animator.activity_appear_from_right, R.animator.activity_disappear_alpha); //custom activity transition animation




                final int wordListFragmentPosition = ((ViewPagerAdapter) viewPager.getAdapter()).getFragmentPositionByTitle(getResources().getString(R.string.word_list_fragment_title));
                ((WordListFragment)((ViewPagerAdapter) viewPager.getAdapter()).getItem(wordListFragmentPosition)).updateWordList();




                return true;

            case R.id.dict_act_action_to_vocabulary:

                //TODO through dialog
                makeSendToVocabularyDialog("Hello");

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        boolean isChanged = true;
        if (data != null) {
            isChanged = data.getBooleanExtra(DictManagerActivity.DATA_CHANGED, true);
        }
        if (isChanged) {
            final int wordListFragmentPosition = ((ViewPagerAdapter) viewPager.getAdapter()).getFragmentPositionByTitle(getResources().getString(R.string.word_list_fragment_title));
            ((WordListFragment) ((ViewPagerAdapter) viewPager.getAdapter()).getItem(wordListFragmentPosition)).updateWordList();
        }
    }

    @Override
    public void OnWordSelected(long wordId) {

        final int wordArticleFragmentPosition = ((ViewPagerAdapter) viewPager.getAdapter()).getFragmentPositionByTitle(getResources().getString(R.string.word_article_fragment_title));
        ((WordArticleFragment)((ViewPagerAdapter) viewPager.getAdapter()).getItem(wordArticleFragmentPosition)).setSelectedWord(wordId);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<Fragment>();
        private final List<String> mFragmentTitleList = new ArrayList<String>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        public int getFragmentPositionByTitle(String title){
            for (int i = 0; i < mFragmentTitleList.size(); i++) {
                if (mFragmentTitleList.get(i).equals(title)){
                    return i;
                }
            }
            return 0;
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private MaterialDialog makeSendToVocabularyDialog (String word) {
        //TODO
        return null;
    }

    private void sendSharingMassage(String massage){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, massage);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    private void sendFeedBack(String massage){
        Intent email = new Intent(Intent.ACTION_SEND);
        email.setType("text/email");
        email.putExtra(Intent.EXTRA_EMAIL, new String[] { getResources().getString(R.string.feed_back_email) });
        email.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.feed_back_subject));
        email.putExtra(Intent.EXTRA_TEXT, massage);
        startActivity(Intent.createChooser(email, getResources().getString(R.string.feed_back_title)));
    }
}
