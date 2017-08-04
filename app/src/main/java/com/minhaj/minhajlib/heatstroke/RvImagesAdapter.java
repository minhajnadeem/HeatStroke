package com.minhaj.minhajlib.heatstroke;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Minhaj lib on 4/24/2017.
 */

public class RvImagesAdapter extends RecyclerView.Adapter<RvImagesAdapter.MyViewHolder> {

    Context context;
    ArrayList<String> arrListImages;

    public RvImagesAdapter(Context context, ArrayList<String> arrListImages) {
        this.context = context;
        this.arrListImages = arrListImages;
    }

    @Override
    public RvImagesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.custome_image, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RvImagesAdapter.MyViewHolder holder, int position) {
        final String strUrl = arrListImages.get(position);
        Log.d("xyz", "inside Rv adapter download url = " + strUrl);

        Picasso.with(context)
                .load(strUrl)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .placeholder(R.drawable.placeholder2)
                .error(R.drawable.place_holder)
                .into(holder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d("xyz", "image from memory.");
                    }

                    @Override
                    public void onError() {
                        Log.d("xyz", "image downloading");
                        Picasso.with(context)
                                .load(strUrl)
                                .placeholder(R.drawable.placeholder2)
                                .error(R.drawable.place_holder)
                                .into(holder.imageView);
                    }
                });

    }

    @Override
    public int getItemCount() {
        Log.d("xyz", "inside RV adapter, size:" + arrListImages.size());
        return arrListImages.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_images);
            textView = (TextView) itemView.findViewById(R.id.tv_empty);
        }
    }
}