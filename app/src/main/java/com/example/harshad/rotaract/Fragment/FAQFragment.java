package com.example.harshad.rotaract.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harshad.rotaract.Adapter.CustomAdapter;
import com.example.harshad.rotaract.EventAdd;
import com.example.harshad.rotaract.EventDescription;
import com.example.harshad.rotaract.EventNameAdd;
import com.example.harshad.rotaract.R;
import com.example.harshad.rotaract.model.add;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * Created by Harshad on 4/4/2018.
 */

public class FAQFragment extends Fragment {

    View view;

    EditText eventRequest;
    Button submit;
    RecyclerView eventsRecyclerView;
    //ProgressBar progressBar;
    private DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.faqfragment, container, false);
        eventRequest = view.findViewById(R.id.event_request);
        eventsRecyclerView = view.findViewById(R.id.request_recyclerview);
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        eventsRecyclerView.setHasFixedSize(true);
        submit = view.findViewById(R.id.btnSubmit);
        // progressBar=view.findViewById(R.id.progress);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("RequestedEvents");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String request_event = eventRequest.getText().toString();
                add dataAdd = new add("false",request_event);
                mDatabase.push().setValue(dataAdd);
                Toast.makeText(getActivity(), "Event Request sended Successfully", Toast.LENGTH_SHORT).show();
                eventRequest.getText().clear();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //  progressBar.setVisibility(View.VISIBLE);
        // getNewsData();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    FirebaseRecyclerAdapter<add, FAQFragment.BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<add, FAQFragment.BlogViewHolder>(add.class, R.layout.event_request_recyclerview, FAQFragment.BlogViewHolder.class, mDatabase) {
                        @Override
                        protected void populateViewHolder(FAQFragment.BlogViewHolder viewHolder, final add model, int position) {
                            if(model.getEventApprovalRequest().equals("true")) {
                                viewHolder.setEventRequest(getActivity(), model.getEventRequest());
                            }

                            viewHolder.interested.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(getActivity(),"Your Data inserted successfully for "+model.getEventRequest(),Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    };
                    eventsRecyclerView.setAdapter(firebaseRecyclerAdapter);
  //                  progressBar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(getActivity(), "The data is not available", Toast.LENGTH_SHORT).show();
//                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "The data is not available", Toast.LENGTH_SHORT).show();
    //            progressBar.setVisibility(View.GONE);
            }
        });
    }



    public static class BlogViewHolder extends RecyclerView.ViewHolder {

        View mView;
        TextView textView;
        Button interested;

        public BlogViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            interested = mView.findViewById(R.id.interested);
        }

        public void setEventRequest(Context ctx, String eventRequest) {
            textView = mView.findViewById(R.id.event_approved_admin);
            textView.setText(eventRequest);
        }
    }

}
