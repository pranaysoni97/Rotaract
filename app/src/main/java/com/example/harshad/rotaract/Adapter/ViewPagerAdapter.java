package com.example.harshad.rotaract.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.harshad.rotaract.EventDescription;
import com.example.harshad.rotaract.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Harshad on 1/11/2018.
 */

public class ViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private Integer[] images={R.drawable.rotaract_1,R.drawable.rotaract_2,R.drawable.rotaract_3,R.drawable.rotaract_5,R.drawable.rotaract_5};

    public ViewPagerAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        mLayoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mLayoutInflater.inflate(R.layout.customlayout,null);
        ImageView imageView =(ImageView)view.findViewById(R.id.imageViewAdapter);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position == 0){
                    Intent intent=new Intent(mContext,EventDescription.class);
                    //intent.putExtra("event_id","1");
                    intent.putExtra("event_name","first_image");
                    mContext.startActivity(intent);
                }else if(position == 1){
                    Intent intent=new Intent(mContext,EventDescription.class);
                   // intent.putExtra("event_id","2");
                    intent.putExtra("event_name","second_image");
                   // intent.putExtra("event_image",images[position]);
                    mContext.startActivity(intent);
                }else if(position == 2){
                    Intent intent=new Intent(mContext,EventDescription.class);
                    //intent.putExtra("event_id","3");
                    intent.putExtra("event_name","third_image");
                    //intent.putExtra("event_image",images[position]);
                    mContext.startActivity(intent);
                }else if(position == 3){
                    Intent intent=new Intent(mContext,EventDescription.class);
                  //  intent.putExtra("event_id","4");
                    intent.putExtra("event_name","fourth_image");
                   // intent.putExtra("event_image",images[position]);
                    mContext.startActivity(intent);
                }else if(position == 4){
                    Intent intent=new Intent(mContext,EventDescription.class);
                    //intent.putExtra("event_id","5");
                    intent.putExtra("event_name","fifth_image");
                    //intent.putExtra("event_image",images[position]);
                    mContext.startActivity(intent);
                }
            }
        });
        imageView.setImageResource(images[position]);
        ViewPager vp=(ViewPager) container;
        vp.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager vp= (ViewPager) container;
        View view=(View)object;
        vp.removeView(view);
    }
}
