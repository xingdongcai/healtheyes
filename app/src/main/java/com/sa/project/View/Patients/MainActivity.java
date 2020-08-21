package com.sa.project.View.Patients;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.sa.project.View.OptionMenu.CholesterolDetailBarView;
import com.sa.project.View.OptionMenu.SystolicDetailLineView;
import com.sa.project.R;
import com.sa.project.View.OptionMenu.SystolicDetailTextView;

/**
 * the main activity to held two fragment and have the shareViewModel to let two fragment to connect
 */
public class MainActivity extends AppCompatActivity {
    private ShareViewModel shareViewModel;
    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;
    Toolbar toolbar;

    /**
     * the function to set up the fragment and the tool bar with option menu
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);

        viewPager.setAdapter(sectionsPagerAdapter);
        shareViewModel = ViewModelProviders.of(this).get(ShareViewModel.class);
        shareViewModel.init();
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * inflate the option menu
     * @param menu: the option menu to show different selection for three view: bar , line and text chart
     * @return: the boolean the indicate success or not
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar, menu);
        return true;
    }

    /**
     * set the option menu item selected function
     * @param item: the item can be clicked in the option menu
     * @return: the boolean the indicate success or not
     */
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.BarChart:
                Context contextBar = getApplicationContext();
                Intent intentToBar = new Intent(contextBar, CholesterolDetailBarView.class);
                intentToBar.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                contextBar.startActivity(intentToBar);

                return true;
            case R.id.LineChart:
                Context contextLine = getApplicationContext();
                Intent intentToLine = new Intent(contextLine, SystolicDetailLineView.class);
                intentToLine.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                contextLine.startActivity(intentToLine);

                return true;
            case R.id.TextChart:
                Context contextText = getApplicationContext();
                Intent intentToText = new Intent(contextText, SystolicDetailTextView.class);
                intentToText.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                contextText.startActivity(intentToText);


            default:
                return super.onOptionsItemSelected(item);
        }


    }






}