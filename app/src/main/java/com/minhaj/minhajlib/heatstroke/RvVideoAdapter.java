package com.minhaj.minhajlib.heatstroke;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.minhaj.minhajlib.heatstroke.modal.VideoModal;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Minhaj lib on 4/25/2017.
 */

public class RvVideoAdapter extends RecyclerView.Adapter<RvVideoAdapter.MyViewHolder> {

    Context context;
    ArrayList<VideoModal> arrListVideos;

    public RvVideoAdapter(Context context,ArrayList<VideoModal> arrListVideos){
        this.context = context;
        this.arrListVideos = arrListVideos;
    }

    @Override
    public RvVideoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custome_video,parent,false);
        return new RvVideoAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RvVideoAdapter.MyViewHolder holder, int position) {
        VideoModal modal = arrListVideos.get(position);

        holder.tvVideoDesc.setText(modal.getVideoDesc());
        final String strUrl = modal.getThumbUrl();
        Picasso.with(context)
                .load(strUrl)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(holder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d("xyz","image is downloaded picasson video.");}
                    @Override
                    public void onError() {
                        Log.d("xyz","inside picasso onError video");
                        Picasso.with(context)
                                .load(strUrl)
                                .placeholder(R.drawable.videoplaceholder)
                                .error(R.drawable.videoplaceholder)
                                .into(holder.imageView);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return arrListVideos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;
        public TextView tvVideoDesc;

        public MyViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.iv_video_thumbnail);
            tvVideoDesc = (TextView) itemView.findViewById(R.id.tv_video_description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            VideoModal modal = arrListVideos.get(getAdapterPosition());
            Intent intent = new Intent(context,YoutubeVideo.class);
            intent.putExtra("video_url",modal.getVideoUrl());
            intent.putExtra("start_at",modal.getStartAt());
            intent.putExtra("end_at",modal.getEndAt());
            context.startActivity(intent);
        }
    }
}
