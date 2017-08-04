package com.minhaj.minhajlib.heatstroke;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.minhaj.minhajlib.heatstroke.adapter.PagerAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class Symptoms extends AppCompatActivity {

    /*** declaring components ***/
    //views
    TabLayout tabLayout;
    ViewPager viewPager;
    Toolbar toolbar;
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);

        /*** initializing components ***/
        //views
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        adView = (AdView) findViewById(R.id.ad_container);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setUpTabIcon();

        //loading ad
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    //set icons to tabs
    private void setUpTabIcon() {
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_personal_video_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_image_black_24dp);
    }

    //setting tabs to work with view pager
    private void setUpViewPager(ViewPager viewPager) {
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentSymptomsVideo(), "Video");
        adapter.addFragment(new FragmentSymptomsImage(), "Image");
        viewPager.setAdapter(adapter);
    }
}
