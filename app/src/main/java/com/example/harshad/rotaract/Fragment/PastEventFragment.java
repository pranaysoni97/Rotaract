package com.example.harshad.rotaract.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.harshad.rotaract.Adapter.CustomAdapter;
import com.example.harshad.rotaract.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

/**
 * Created by Harshad on 1/15/2018.
 */

public class PastEventFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.pastevent,container,false);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("EventRecords");
        mRecyclerView = view.findViewById(R.id.post_display);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<CustomAdapter,CurrentEventFragment.BlogViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<CustomAdapter, CurrentEventFragment.BlogViewHolder>(CustomAdapter.class, R.layout.recycler_view, CurrentEventFragment.BlogViewHolder.class, mDatabase) {
            @Override
            protected void populateViewHolder(CurrentEventFragment.BlogViewHolder viewHolder, CustomAdapter model, int position) {
                viewHolder.setTitle(model.getEventName());
                /*viewHolder.setLocation(model.getEventLocation());
                viewHolder.setDescription(model.getEventDescription());
                viewHolder.setImage(getActivity(),model.getImageUri());*/
            }
        };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public BlogViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setTitle(String EventName){
            TextView upload_user=(TextView)mView.findViewById(R.id.image_uploader);
            upload_user.setText(EventName);
        }

/*
        public void setLocation(String EventLocation){
            TextView upload_location=(TextView)mView.findViewById(R.id.image_location);
            upload_location.setText(EventLocation);
        }

        public void setDescription(String EventDescription){
            TextView upload_desc=(TextView)mView.findViewById(R.id.image_description);
            upload_desc.setText(EventDescription);
        }

        public void setImage(Context ctx, String ImageUri){
            ImageView imageView=(ImageView)mView.findViewById(R.id.image_show);
            Picasso.with(ctx).load(ImageUri).into(imageView);
        }*/
    }
}
