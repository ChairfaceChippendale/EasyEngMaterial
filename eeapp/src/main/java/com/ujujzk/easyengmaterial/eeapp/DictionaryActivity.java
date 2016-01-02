package com.ujujzk.easyengmaterial.eeapp;

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
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.ujujzk.easyengmaterial.eeapp.util.ActivityUtil;

import java.util.ArrayList;
import java.util.List;

//http://geektimes.ru/post/120161/
//http://web.archive.org/web/20110720142541/http://basharkokash.com/post/2010/04/19/Bing-Translator-for-developers.aspx

//http://mymemory.translated.net/doc/spec.php

public class DictionaryActivity extends AppCompatActivity implements OnWordSelectedListener{

    @SuppressWarnings("unused")
    private static final String TAG = DictionaryActivity.class.getSimpleName();

    private Toolbar toolBar;
    private Drawer navigationDrawer = null;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityUtil.setTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        toolBar = (Toolbar) findViewById(R.id.dict_act_app_bar);
        ActivityUtil.setToolbarColor(this, toolBar.getId());
        setSupportActionBar(toolBar);
        navigationDrawer = new DrawerBuilder()
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
                                //TODO
                                break;
                            case Application.IDENTIFIER_FEEDBACK:
                                //TODO
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
        navigationDrawer.setSelection(Application.IDENTIFIER_DICTIONARY);

        viewPager = (ViewPager) findViewById(R.id.dict_act_viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.dict_act_tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new WordListFragment(), getResources().getString(R.string.word_list_fragment_title));
        adapter.addFragment(new WordArticleFragment(), getResources().getString(R.string.word_article_fragment_title));
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
                //TODO
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void OnWordSelected(long wordId) {
        //TODO send wordId to WordAtricleFragment
        //((ViewPagerAdapter) viewPager.getAdapter()).getItem(1);
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
}
