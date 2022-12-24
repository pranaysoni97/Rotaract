package com.example.harshad.rotaract;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harshad.rotaract.Adapter.CustomAdapter;
import com.squareup.picasso.Picasso;

public class EventDescription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_description);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView textView=findViewById(R.id.textViewEvent);
        textView.setText(getIntent().getStringExtra("event_name"));
        TextView eventDescription=findViewById(R.id.eventDescription);
        String eventImage=getIntent().getStringExtra("eventImage");
        ImageView imageEvent=findViewById(R.id.eventImage);
        Picasso.with(EventDescription.this).load(eventImage).placeholder(R.drawable.ic_launcher_background).into(imageEvent);
        eventDescription.setText(getIntent().getStringExtra("event_description"));
        // ImageView imageView=findViewById(R.id.eventImage);
        // Picasso.with(EventDescription.this).load(getIntent().getStringExtra("event_image")).into(imageView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if( id == android.R.id.home){
            Intent intent=new Intent(EventDescription.this,NewsActivity.class);
            startActivity(intent);
        }
        return true;
    }
}
