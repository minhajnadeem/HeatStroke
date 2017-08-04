package com.minhaj.minhajlib.heatstroke;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.minhaj.minhajlib.heatstroke.config.Config;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubeVideo extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer youTubePlayer;
    private String videoUrl;
    private int startAt, endAt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_video);

        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player_view);
        youTubePlayerView.initialize(Config.DEVELOPER_API_KEY, this);
        videoUrl = getIntent().getStringExtra("video_url");
        startAt = getIntent().getIntExtra("start_at", -1);
        endAt = getIntent().getIntExtra("end_at",-1);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

        this.youTubePlayer = youTubePlayer;
        youTubePlayer.setPlaybackEventListener(playbackEventListener);
        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
        youTubePlayer.setFullscreen(true);
        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
        if (!b) {
            Log.d("xyz", "loading video");
            youTubePlayer.loadVideo(videoUrl);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
    }

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {
            Log.d("xyz", "youtube onLoading");
        }

        @Override
        public void onLoaded(String s) {

            Log.d("xyz", "youtube onLoaded");
            Log.d("xyz", "onLoaded: seek to " + startAt);
            if (startAt >= 0) {
                Log.d("xyz", "seek to " + startAt);
                youTubePlayer.seekToMillis(startAt);
                youTubePlayer.play();
            }
        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {
            Log.d("xyz", "youtube onVideoStarted");
        }

        @Override
        public void onVideoEnded() {

        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };

    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {

            /*Log.d("xyz","youtube onPlaying: endAt "+endAt);
            if (endAt != -1) {
                if (youTubePlayer.getCurrentTimeMillis() > endAt) {
                    youTubePlayer.pause();
                    Toast.makeText(YoutubeVideo.this, "this part is ended", Toast.LENGTH_SHORT).show();
                }
            }*/
        }

        @Override
        public void onPaused() {

        }

        @Override
        public void onStopped() {

        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {
            Log.d("xyz", "seek to " + i);

            if (startAt != -1 && i < startAt) {
                youTubePlayer.seekToMillis(startAt);
                Toast.makeText(YoutubeVideo.this, "you cannot seek here", Toast.LENGTH_SHORT).show();
            }/* else if (endAt !=-1 && i > endAt) {
                youTubePlayer.seekToMillis(endAt);
                Toast.makeText(YoutubeVideo.this, "you cannot seek here", Toast.LENGTH_SHORT).show();
            }*/
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        if(youTubePlayer!=null){
            youTubePlayer.release();
        }
    }
}