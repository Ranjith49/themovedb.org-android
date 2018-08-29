package com.ran.themoviedb.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.ran.themoviedb.R;
import com.ran.themoviedb.TheMovieDbAppController;
import com.ran.themoviedb.adapters.LanguageBaseAdapter;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.utils.Navigator;

/**
 * Activity responsible for showing the languages supported by the APp [Movies and TV shows ]
 */
public class LanguageSelectionActivity extends AppCompatActivity implements
        AdapterView.OnItemClickListener {

    private ListView langListView;
    private LanguageBaseAdapter languageBaseAdapter;
    private String langCodes[];
    private long mBackPressedTime;
    private Button mLanguageContinue;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_selection);
        this.context = this;

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.language_selection_custom_actiobar);

        langListView = findViewById(R.id.language_screen_listView);
        langCodes = getResources().getStringArray(R.array.language_display_codes);
        languageBaseAdapter = new LanguageBaseAdapter(this, langCodes);
        mLanguageContinue = findViewById(R.id.language_screen_continue);
        mLanguageContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TheMovieDbAppController.getAppInstance().appSharedPreferences.disableAppFirstLaunch();
                Navigator.navigateToAppHome(context);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        langListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        langListView.setAdapter(languageBaseAdapter);
        langListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TheMovieDbAppController.getAppInstance().appSharedPreferences.setAppLanguageData(langCodes[position]);
        languageBaseAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        //Do n't Handle Case of Not Task Root
        if (!isTaskRoot()) {
            super.onBackPressed();
            return;
        }

        if (mBackPressedTime + TheMovieDbConstants.TIME_INTERVAL_APP_EXIT >
                System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, getResources().getString(R.string.app_back_pressed_toast),
                    Toast.LENGTH_SHORT).show();
        }
        mBackPressedTime = System.currentTimeMillis();
    }
}
