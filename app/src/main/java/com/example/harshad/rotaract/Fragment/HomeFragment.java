package com.example.harshad.rotaract.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harshad.rotaract.Adapter.CustomAdapter;
import com.example.harshad.rotaract.Adapter.ListDataAdapter;
import com.example.harshad.rotaract.Adapter.ViewPagerAdapter;
import com.example.harshad.rotaract.EventDescription;
import com.example.harshad.rotaract.R;
import com.example.harshad.rotaract.RegistrationForm;
import com.example.harshad.rotaract.model.add;
import com.example.harshad.rotaract.model.addevent;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Harshad on 1/9/2018.
 **/

public class HomeFragment extends Fragment {

    ViewPager mViewPager = null;
    ListView listView;
    DatabaseReference data;
    ArrayList<add> arrayList=new ArrayList<>();

     //private LinearLayout sliderDotsPanel;
    // ArrayList<String> sliderImages;
    // private int dotsCount;
    // private ImageView[] dots;
    StorageReference storageReference;
    private DatabaseReference mDatabase,eventListData;
    RecyclerView news_recyclerview,event_recyclerview;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false);
        news_recyclerview = view.findViewById(R.id.news_recyclerview);
        event_recyclerview = view.findViewById(R.id.recyclerview_news);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("News");
        eventListData=FirebaseDatabase.getInstance().getReference().child("NewsDescriptions");
        LinearLayoutManager eventList=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        event_recyclerview.setLayoutManager(eventList);
        news_recyclerview.setLayoutManager(layoutManager);
        event_recyclerview.setNestedScrollingEnabled(false);
        event_recyclerview.smoothScrollToPosition(0);
        ViewCompat.setNestedScrollingEnabled(event_recyclerview,false);
        news_recyclerview.setHasFixedSize(true);
        progressBar=view.findViewById(R.id.progress);

/*
        mViewPager = view.findViewById(R.id.slider_page);

        ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter(getActivity());
        sliderDotsPanel=view.findViewById(R.id.Sliderdots);w
        mViewPager.setAdapter(mViewPagerAdapter);
        dotsCount = mViewPagerAdapter.getCount();
        dots= new ImageView[dotsCount];

        for(int i=0;i<dotsCount;i++){
            dots[i]=new ImageView(getActivity());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.nonactive_dot));
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8,16,8,16);
            sliderDotsPanel.addView(dots[i],params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.active_dot));

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i=0;i<dotsCount;i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.nonactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
*/


        /*listView = view.findViewById(R.id.EventlistView);
        arrayList = new ArrayList<>();
        data = FirebaseDatabase.getInstance().getReference("Events");
*/
/*
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimeTask(), 2000, 4000);
        return view;
*/
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        progressBar.setVisibility(View.VISIBLE);

        FirebaseRecyclerAdapter<CustomAdapter, HomeFragment.eventViewHolder> eventRecyclerview = new FirebaseRecyclerAdapter<CustomAdapter, HomeFragment.eventViewHolder>(CustomAdapter.class, R.layout.list_layout, HomeFragment.eventViewHolder.class, eventListData) {
            @Override
            protected void populateViewHolder(eventViewHolder viewHolder, final CustomAdapter model, int position) {
                viewHolder.setEventName(model.getEventName());
                viewHolder.setEventDescription(model.getEventDescription());
                viewHolder.setEventImage(getActivity(),model.getImageUri());
                viewHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(),EventDescription.class);
                        intent.putExtra("event_name",model.getEventName());
                        intent.putExtra("eventImage",model.getImageUri());
                        intent.putExtra("event_description",model.getEventDescription());
                        startActivity(intent);
                    }
                });
            }
        };
        event_recyclerview.setAdapter(eventRecyclerview);

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    FirebaseRecyclerAdapter<CustomAdapter, HomeFragment.BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<CustomAdapter, HomeFragment.BlogViewHolder>(CustomAdapter.class, R.layout.news_recyclerview, HomeFragment.BlogViewHolder.class, mDatabase) {
                        @Override
                        protected void populateViewHolder(BlogViewHolder viewHolder, final CustomAdapter model, int position) {

                            viewHolder.setImage(getActivity(), model.getIvPath());

                            viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getActivity(), EventDescription.class);
                                    intent.putExtra("eventImage", model.getIvPath());
                                    startActivity(intent);
                                }
                            });
                        }
                    };
                    news_recyclerview.setAdapter(firebaseRecyclerAdapter);
                    progressBar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(getActivity(), "The data is not available", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "The data is not available", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });

//        arrayList.clear();

    /*    data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for( DataSnapshot eventData : dataSnapshot.getChildren()){
                    add event=eventData.getValue(add.class);
                    arrayList.add(event);
                }

                ListDataAdapter listDataAdapter=new ListDataAdapter(getActivity(),arrayList);
                listView.setAdapter(listDataAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
*/


        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),RegistrationForm.class);
                intent.putExtra("event_name",arrayList.get(position).getEventName());
                startActivity(intent);
            }
        });
*/
    }



 /*   public class MyTimeTask extends TimerTask {
        @Override
        public void run() {
            if (getActivity() == null) {
                return;
            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mViewPager.getCurrentItem() == 0) {
                            mViewPager.setCurrentItem(1);
                        } else if (mViewPager.getCurrentItem() == 1) {
                            mViewPager.setCurrentItem(2);
                        } else if (mViewPager.getCurrentItem() == 2) {
                            mViewPager.setCurrentItem(3);
                        } else if (mViewPager.getCurrentItem() == 3) {
                            mViewPager.setCurrentItem(4);
                        } else if (mViewPager.getCurrentItem() == 4) {
                            mViewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        }
    }*/

    public static class BlogViewHolder extends RecyclerView.ViewHolder {

        View mView;
        ImageView imageView;

        public BlogViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setImage(Context ctx, String IvPath) {
            imageView = (ImageView) mView.findViewById(R.id.ivNews);
            Picasso.with(ctx).load(IvPath).placeholder(R.drawable.ic_launcher_background).into(imageView);
        }
    }

    public static class eventViewHolder extends RecyclerView.ViewHolder{

        View view;
        TextView title,description;
        ImageView imageView;

        public eventViewHolder(View itemView) {
            super(itemView);
            view=itemView;
        }

        public void setEventImage(Context ctx,String imageUri){
            imageView = view.findViewById(R.id.news_imageView);
            Picasso.with(ctx).load(imageUri).placeholder(R.drawable.ic_launcher_background).into(imageView);
        }

        public void setEventName(String eventName){
            title = view.findViewById(R.id.news_heading);
            title.setText(eventName);
        }

        public void setEventDescription(String eventDescription){
            description = view.findViewById(R.id.news_description);
            description.setText(eventDescription);
        }
    }

}
