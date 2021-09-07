package com.DailyNeeds.dailyneeds.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.DailyNeeds.dailyneeds.NetworkCall.ConstantsUrl;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.HomeResponse.Category_images;
import com.DailyNeeds.dailyneeds.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;



public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageAdapterViewHolder> {
    Category_images[] category_images;
    Context context;
    public ImageAdapter(Category_images[] category_images, Context context) {

        this.category_images = category_images;
        this.context = context;
    }

    @NonNull
    @Override
    public ImageAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.single_ads_recyclerview, viewGroup, false);
        return new ImageAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapterViewHolder ViewHolder, int i) {

        //ad_RecyclerView_AdapterViewHolder.ad_imageview.setImageResource(category_images[0]);

        String url= ConstantsUrl.BASE_URL+category_images[i].getImg_url();

        Glide.with(context).load(url).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).centerInside()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(context).load(R.drawable.noimage).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).fitCenter()).into(ViewHolder.ad_imageview);
                    }});
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                return false;
            }
        }).into(ViewHolder.ad_imageview);

        }


    @Override
    public int getItemCount() {
        return category_images.length;
    }
    public class ImageAdapterViewHolder  extends RecyclerView.ViewHolder{

        ImageView ad_imageview;
        public ImageAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ad_imageview=itemView.findViewById(R.id.imageView_ad);

        }
    }
}
