package com.ajal.arsocialmessaging;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;

import com.ajal.arsocialmessaging.R;
import com.ajal.arsocialmessaging.util.TouchViewPagerImageView;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

// REFERENCE: https://www.geeksforgeeks.org/image-slider-in-android-using-viewpager/ 03/02/2022 01:33

public class ViewPagerAdapter extends PagerAdapter {

    private AppCompatActivity activity;
    private Context context;
    private List<String> images;

    // Layout Inflater
    LayoutInflater mLayoutInflater;


    // Viewpager Constructor
    public ViewPagerAdapter(AppCompatActivity activity, Context context, List<String> images) {
        this.activity = activity;
        this.context = context;
        this.images = images;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int pos) {
        // inflating the item.xml
        View itemView = mLayoutInflater.inflate(R.layout.viewpager_image, container, false);

        // referencing the image view from the item.xml file
        TouchViewPagerImageView imageView = (TouchViewPagerImageView) itemView.findViewById(R.id.imageViewMain);

        // setting the image in the imageView
        Picasso.get()
                .load(images.get(pos))
                .fit()
                .centerCrop()
                .into(imageView);

        // Adding the View
        Objects.requireNonNull(container).addView(itemView);

        // Set up the action bar title
        if (this.activity.getSupportActionBar().getTitle().equals("SkyWrite")) {
            this.activity.getSupportActionBar().setTitle((pos+1)+"/"+images.size());
        }

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}

