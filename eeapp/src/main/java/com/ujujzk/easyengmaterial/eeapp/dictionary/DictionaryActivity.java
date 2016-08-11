package com.ujujzk.easyengmaterial.eeapp.dictionary;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.*;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.ujujzk.easyengmaterial.eeapp.*;
import com.ujujzk.easyengmaterial.eeapp.grammar.GrammarActivity;
import com.ujujzk.easyengmaterial.eeapp.service.PronunciationService;
import com.ujujzk.easyengmaterial.eeapp.util.ActivityUtil;
import com.ujujzk.easyengmaterial.eeapp.vocabulary.VocabularyActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class DictionaryActivity extends AppCompatActivity implements OnWordSelectedListener {

    @SuppressWarnings("unused")
    private static final String TAG = DictionaryActivity.class.getSimpleName();

    private Toolbar toolBar;
    private Drawer navigationDrawer = null;
    private Drawer historyDrawer = null;
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
        navigationDrawer = makeNavigationDrawer();
        navigationDrawer.setSelection(Application.IDENTIFIER_DICTIONARY);
        historyDrawer = makeHistoryDrawer();

        if (isPortrait(this)) {
            viewPager = (ViewPager) findViewById(R.id.dict_act_viewpager);
            setupViewPager(viewPager);
            tabLayout = (TabLayout) findViewById(R.id.dict_act_tabs);
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    private void setupViewPager(ViewPager viewPager) {

        List<Fragment> fr = getSupportFragmentManager().getFragments();
        if (fr != null && !fr.isEmpty()) {
            for (Fragment f : fr) {
                if (f != null)
                    getSupportFragmentManager().beginTransaction().remove(f).commit();
            }
        }

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new WordListFragment(), getResources().getString(R.string.word_list_fragment_title));
        viewPagerAdapter.addFragment(new WordArticleFragment(), getResources().getString(R.string.word_article_fragment_title));
        //viewPagerAdapter.addFragment(new WordHistoryFragment(), getResources().getString(R.string.word_history_fragment_title)); //TODO
        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        startService(new Intent(this, PronunciationService.class));
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopService(new Intent(this, PronunciationService.class));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dictionary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.dict_act_action_manager:
                runDictManager();
                return true;

            case R.id.dict_act_action_pronunciation:
                runPronounce();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void runDictManager() {
        startActivity(new Intent(DictionaryActivity.this, DictManagerActivity.class));
        overridePendingTransition(R.animator.activity_appear_from_right, R.animator.activity_disappear_alpha); //custom activity transition animation
    }

    private void runPronounce() {
        if (isNetworkConnected()) {
            String wordToPronounce = "";
            if (isPortrait(this)) {
                final int wordArticleFragmentPosition = ((ViewPagerAdapter) viewPager.getAdapter()).getFragmentPositionByTitle(getResources().getString(R.string.word_article_fragment_title));
                if (wordArticleFragmentPosition > 0) {
                    try {
                        wordToPronounce = ((WordArticleFragment) ((ViewPagerAdapter) viewPager.getAdapter()).getItem(wordArticleFragmentPosition)).getSelectedWordName();
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            } else {
                wordToPronounce = ((WordArticleFragment) getSupportFragmentManager().findFragmentById(R.id.word_article_fragment)).getSelectedWordName();
            }
            if (wordToPronounce != null && !wordToPronounce.isEmpty()) {
                Intent intent = new Intent(PronunciationService.PRONUNCIATION_TASK);
                intent.putExtra(PronunciationService.WORD, wordToPronounce);
                sendBroadcast(intent);
            } else {
                Toast.makeText(this, getResources().getString(R.string.no_word_to_pronounce), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onWordSelected(String wordName) {
        if (isPortrait(this)) {
            final int wordArticleFragmentPosition = ((ViewPagerAdapter) viewPager.getAdapter()).getFragmentPositionByTitle(getResources().getString(R.string.word_article_fragment_title));
            ((WordArticleFragment) ((ViewPagerAdapter) viewPager.getAdapter()).getItem(wordArticleFragmentPosition)).setSelectedWord(wordName);
        } else {
            ((WordArticleFragment) getSupportFragmentManager().findFragmentById(R.id.word_article_fragment)).setSelectedWord(wordName);
        }
        addWordToHistory(wordName);
    }

    int getTabPositionByTitle(String title) {
        return ((ViewPagerAdapter) viewPager.getAdapter()).getFragmentPositionByTitle(title);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter implements Serializable {
        private final List<Fragment> mFragmentList = new ArrayList<Fragment>();
        private final List<String> mFragmentTitleList = new ArrayList<String>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        int getFragmentPositionByTitle(String title) {
            for (int i = 0; i < mFragmentTitleList.size(); i++) {
                if (mFragmentTitleList.get(i).equals(title)) {
                    return i;
                }
            }
            return 0;
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private Drawer makeNavigationDrawer() {
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
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        InputMethodManager inputMethodManager = (InputMethodManager) DictionaryActivity.this.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(DictionaryActivity.this.getCurrentFocus().getWindowToken(), 0);
                    }
                    @Override
                    public void onDrawerClosed(View drawerView) {}
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {}
                })
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
                                .withIdentifier(Application.IDENTIFIER_FEEDBACK)
//                        new SecondaryDrawerItem()
//                                .withName(R.string.title_activity_settings)
//                                .withIcon(GoogleMaterial.Icon.gmd_settings)
//                                .withIdentifier(Application.IDENTIFIER_SETTING)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch ((int)drawerItem.getIdentifier()) {
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

    private Drawer makeHistoryDrawer() {
        Drawer drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolBar)
                .withTranslucentStatusBar(true)
                .withAccountHeader(
                        new AccountHeaderBuilder()
                                .withActivity(this)
                                .withHeaderBackground(R.drawable.dict_history_header)
                                .withHeaderBackgroundScaleType(ImageView.ScaleType.CENTER_CROP)
                                .withCompactStyle(true)
                                .build()
                )
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        InputMethodManager inputMethodManager = (InputMethodManager) DictionaryActivity.this.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(DictionaryActivity.this.getCurrentFocus().getWindowToken(), 0);
                    }
                    @Override
                    public void onDrawerClosed(View drawerView) {}
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {}
                })
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem.getIdentifier() == Application.IDENTIFIER_CLEAR_HISTORY) {
                            historyDrawer.removeAllItems();
                        } else {
                            //TODO handle word selection
                            //Toast.makeText(getBaseContext(), ((PrimaryDrawerItem) drawerItem).getName().getText(), Toast.LENGTH_SHORT).show();
                            onWordSelected(((PrimaryDrawerItem) drawerItem).getName().toString());
                        }
                        historyDrawer.closeDrawer();
                        historyDrawer.setSelection(-1);

                        return true;
                    }
                })
                .withSelectedItem(-1)
                .withDrawerGravity(Gravity.END)
                .append(navigationDrawer);
        drawer.addStickyFooterItem(
                new SecondaryDrawerItem()
                        .withName("Clear history")
                        .withIcon(GoogleMaterial.Icon.gmd_delete)
                        .withIdentifier(Application.IDENTIFIER_CLEAR_HISTORY)
        );

        return drawer;

    }

    void addWordToHistory (String wordName) {
        historyDrawer.addItemAtPosition(new PrimaryDrawerItem().withName(wordName),0);
        historyDrawer.getRecyclerView().scrollToPosition(0);
    }

    @Override
    public void onBackPressed() {
        if(navigationDrawer.isDrawerOpen()){
            navigationDrawer.closeDrawer();
        } else if (historyDrawer.isDrawerOpen()) {
            historyDrawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    private void sendSharingMassage(String massage) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, massage);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    private void sendFeedBack(String massage) {
        Intent email = new Intent(Intent.ACTION_SEND);
        email.setType("text/email");
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{getResources().getString(R.string.feed_back_email)});
        email.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.feed_back_subject));
        email.putExtra(Intent.EXTRA_TEXT, massage);
        startActivity(Intent.createChooser(email, getResources().getString(R.string.feed_back_title)));
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    private static boolean isPortrait(Context context) {
        return (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
    }
}
