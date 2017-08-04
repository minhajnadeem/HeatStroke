package com.minhaj.minhajlib.heatstroke;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.minhaj.minhajlib.heatstroke.config.Config;
import com.minhaj.minhajlib.heatstroke.constants.MyConstants;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class MainActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener,View.OnClickListener {


    //declaring variables
    private static final int REQUEST_CODE = 111;
    //views
    TextView tvWhatIs,tvWhoAtRisk,tvSymptoms,tvPrevention,tvWhatToDo,tvNews;
    Button btnShare;
    AdView adView;
    //youtube
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer mYouTubePlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //configuring ad mob
        MobileAds.initialize(this, MyConstants.AD_MOB_ID);

        //initializing youtube components
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player_view);

        //initializing views
        tvWhatIs = (TextView) findViewById(R.id.tv_what_is);
        tvSymptoms = (TextView) findViewById(R.id.tv_symptoms);
        tvPrevention = (TextView) findViewById(R.id.tv_prevention);
        tvWhoAtRisk = (TextView) findViewById(R.id.tv_who_at_risk);
        tvNews = (TextView) findViewById(R.id.tv_news);
        tvWhatToDo = (TextView) findViewById(R.id.tv_to_do);
        btnShare = (Button) findViewById(R.id.btn_share);
        adView = (AdView) findViewById(R.id.ad_container);

        //loading ad
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

    }

    //attaching click listener
    @Override
    protected void onStart() {
        super.onStart();
        youTubePlayerView.initialize(Config.DEVELOPER_API_KEY,this);
        tvWhatIs.setOnClickListener(this);
        tvWhatToDo.setOnClickListener(this);
        tvNews.setOnClickListener(this);
        tvWhoAtRisk.setOnClickListener(this);
        tvPrevention.setOnClickListener(this);
        tvSymptoms.setOnClickListener(this);
        btnShare.setOnClickListener(this);
    }

    //removing click listener
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

        mYouTubePlayer = youTubePlayer;
        if(!b) {
            youTubePlayer.cueVideo(Config.HEAT_STROKE_PUBLIC_SERVICE_MESSAGE_VIDEO);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if(youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this,REQUEST_CODE).show();
        }else {
            String message = String.format(getString(R.string.player_error),youTubeInitializationResult.toString());
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE) {
            youTubePlayerView.initialize(Config.DEVELOPER_API_KEY,this);
        }
    }

    //method for handling click
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tv_what_is:
                //show what is activity
                startActivity(new Intent(this,WhatIs.class));
                break;
            case R.id.tv_news:
                //show news activity
                startActivity(new Intent(this,News.class));
                break;
            case R.id.tv_prevention:
                //start prevention activity
                startActivity(new Intent(this,Prevention.class));
                break;
            case R.id.tv_symptoms:
                //start symptoms activity
                startActivity(new Intent(this,Symptoms.class));
                break;
            case R.id.tv_to_do:
                //start what to do activity
                startActivity(new Intent(this,WhatToDo.class));
                break;
            case R.id.tv_who_at_risk:
                //start who at_risk activity
                startActivity(new Intent(this,WhoAtRisk.class));
                break;
            case R.id.btn_share:
                mShareThisApp();
        }
    }

    private void mShareThisApp() {
        //start share intent
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,getString(R.string.share_msg));
        startActivity(Intent.createChooser(shareIntent,getString(R.string.chooser_text)));
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mYouTubePlayer!=null){
            Log.d("xyz","releasing youtube resources");
            mYouTubePlayer.release();
        }
        tvWhatIs.setOnClickListener(null);
        tvWhatToDo.setOnClickListener(null);
        tvNews.setOnClickListener(null);
        tvWhoAtRisk.setOnClickListener(null);
        tvPrevention.setOnClickListener(null);
        tvSymptoms.setOnClickListener(null);
        btnShare.setOnClickListener(null);
    }
}