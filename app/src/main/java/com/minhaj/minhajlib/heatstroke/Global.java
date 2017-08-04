package com.minhaj.minhajlib.heatstroke;

import android.app.Application;
import android.support.design.widget.TabLayout;
import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

/**
 * Created by Minhaj lib on 4/24/2017.
 */

public class Global extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("xyz","inside Application level");
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(this,Integer.MAX_VALUE));
        Picasso built = builder.build();
        built.setIndicatorsEnabled(false);
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        FirebaseDatabase.getInstance().getReference().keepSynced(true);
    }

    public static void setUpTabIcons(TabLayout tabLayout){
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_personal_video_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_image_black_24dp);
    }
}
