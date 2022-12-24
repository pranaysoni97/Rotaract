package com.example.harshad.rotaract.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.harshad.rotaract.Adapter.CustomAdapter;
import com.example.harshad.rotaract.R;
import com.example.harshad.rotaract.RecruitmentApply;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import javax.microedition.khronos.opengles.GL;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Harshad on 1/9/2018.
 */

public class Recruitment extends Fragment {

    private RecyclerView mRecyclerView;
    private DatabaseReference mDatabase;
    private GridLayoutManager mGridLayoutManager;
    private ProgressBar Dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.recruitment, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("RecruitmentRecords");

        Dialog= view.findViewById(R.id.progressbar);

        mRecyclerView = view.findViewById(R.id.sponsors_list);

        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        //mGridLayoutManager.setReverseLayout(true);
        //mGridLayoutManager.setStackFromEnd(true);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDataFromFirebase();
    }

    public void getDataFromFirebase(){

        Dialog.setVisibility(View.VISIBLE);

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    FirebaseRecyclerAdapter<CustomAdapter, Recruitment.BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<CustomAdapter, Recruitment.BlogViewHolder>(CustomAdapter.class, R.layout.recruitment_recyclerview, Recruitment.BlogViewHolder.class, mDatabase) {
                        @Override
                        protected void populateViewHolder(BlogViewHolder viewHolder, CustomAdapter model, int position) {
                            viewHolder.setTitle(model.getRecruitmentName());
                            viewHolder.setImage(getActivity(), model.getRecruitmentImageUri());
                            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getActivity(), RecruitmentApply.class);
                                    startActivity(intent);
                                }
                            });
                        }
                    };
                    mRecyclerView.setAdapter(firebaseRecyclerAdapter);
                    Dialog.setVisibility(View.GONE);
                }else{
                    Toast.makeText(getActivity(),"The data is not exist",Toast.LENGTH_SHORT).show();
                    Dialog.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                    Dialog.setVisibility(View.GONE);
            }
        });

    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public BlogViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setTitle(String SponsorsName) {
            TextView upload_user = (TextView) mView.findViewById(R.id.tv_sponsors_name);
            upload_user.setText(SponsorsName);
        }

        public void setImage(Context ctx, String SponsorsLogoImageUri) {
            CircleImageView imageView = (CircleImageView) mView.findViewById(R.id.iv_sponsors);
            Picasso.with(ctx).load(SponsorsLogoImageUri).placeholder(R.drawable.ic_launcher_background).into(imageView);
        }
    }
}