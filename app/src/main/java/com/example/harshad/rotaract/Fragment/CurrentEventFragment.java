package com.example.harshad.rotaract.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.harshad.rotaract.Adapter.CustomAdapter;
import com.example.harshad.rotaract.EventAdd;
import com.example.harshad.rotaract.EventDescription;
import com.example.harshad.rotaract.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import org.w3c.dom.Text;

/**
 * Created by Harshad on 1/15/2018.
 */

public class CurrentEventFragment extends Fragment {

  private RecyclerView mRecyclerView;
  private DatabaseReference mDatabase;
  private ProgressBar progressBar;

  @Nullable
  @Override
  public View onCreateView(
    @NonNull LayoutInflater inflater,
    @Nullable ViewGroup container,
    @Nullable Bundle savedInstanceState
  ) {
    View view = inflater.inflate(R.layout.currentevent, container, false);

    mDatabase =
      FirebaseDatabase.getInstance().getReference().child("EventRecords");
    mRecyclerView = view.findViewById(R.id.post_display);
    progressBar = view.findViewById(R.id.progressbar);

    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    layoutManager.setReverseLayout(true);
    layoutManager.setStackFromEnd(true);

    mRecyclerView.setHasFixedSize(true);
    mRecyclerView.setLayoutManager(layoutManager);

    return view;
  }

  @Override
  public void onStart() {
    super.onStart();
    progressBar.setVisibility(View.VISIBLE);
    mDatabase.addListenerForSingleValueEvent(
      new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
          if (dataSnapshot.exists()) {
            FirebaseRecyclerAdapter<CustomAdapter, BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<CustomAdapter, BlogViewHolder>(
              CustomAdapter.class,
              R.layout.current_event,
              BlogViewHolder.class,
              mDatabase
            ) {
              @Override
              protected void populateViewHolder(
                BlogViewHolder viewHolder,
                final CustomAdapter model,
                final int position
              ) {
                viewHolder.setTitle(model.getEventName());
                viewHolder.setDescription(model.getEventDescription());
                viewHolder.setImage(getActivity(), model.getImageUri());

                viewHolder.upload_user.setOnClickListener(
                  new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      Intent intent = new Intent(
                        getActivity(),
                        EventDescription.class
                      );
                      intent.putExtra("event_name", model.getEventName());
                      intent.putExtra("eventImage", model.getImageUri());
                      intent.putExtra(
                        "event_description",
                        model.getEventDescription()
                      );
                      startActivity(intent);
                    }
                  }
                );
              }
            };
            mRecyclerView.setAdapter(firebaseRecyclerAdapter);
            progressBar.setVisibility(View.GONE);
          } else {
            Toast
              .makeText(
                getActivity(),
                "The data is not available",
                Toast.LENGTH_SHORT
              )
              .show();
            progressBar.setVisibility(View.GONE);
          }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
          Toast
            .makeText(
              getActivity(),
              "The data is not available",
              Toast.LENGTH_SHORT
            )
            .show();
          progressBar.setVisibility(View.GONE);
        }
      }
    );
  }

  public static class BlogViewHolder extends RecyclerView.ViewHolder {

    View mView;
    TextView upload_user;

    public BlogViewHolder(View itemView) {
      super(itemView);
      mView = itemView;
    }

    public void setTitle(String EventName) {
      upload_user = (TextView) mView.findViewById(R.id.image_uploader);
      upload_user.setText(EventName);
    }

    public void setDescription(String EventDescription) {
      TextView upload_desc = (TextView) mView.findViewById(R.id.description);
      upload_desc.setText(EventDescription);
    }

    public void setImage(Context ctx, String ImageUri) {
      ImageView imageView = (ImageView) mView.findViewById(R.id.event_image);
      Picasso
        .with(ctx)
        .load(ImageUri)
        .placeholder(R.drawable.ic_launcher_background)
        .into(imageView);
    }
  }
}
