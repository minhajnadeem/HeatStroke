package com.minhaj.minhajlib.heatstroke.modal;

/**
 * Created by Minhaj lib on 4/25/2017.
 */

public class VideoModal {

    private String videoDesc, videoUrl, thumbUrl;
    private int startAt,endAt;

    public VideoModal(String videoDesc, String videoUrl, String thumbUrl,int startAt,int endAt) {
        setVideoDesc(videoDesc);
        setVideoUrl(videoUrl);
        setThumbUrl(thumbUrl);
        setStartAt(startAt);
        setEndAt(endAt);
    }

    public String getVideoDesc() {
        return videoDesc;
    }

    public void setVideoDesc(String videoDesc) {
        this.videoDesc = videoDesc;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public int getStartAt() {
        return startAt;
    }

    public void setStartAt(int startAt) {
        this.startAt = startAt;
    }

    public int getEndAt() {
        return endAt;
    }

    public void setEndAt(int endAt) {
        this.endAt = endAt;
    }
}
