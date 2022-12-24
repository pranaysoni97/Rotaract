package com.example.harshad.rotaract;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;

public class EventAdd extends AppCompatActivity{

    CardView addeventname,addevent,addsponsors,recruitment,postNews;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_add);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        addeventname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EventAdd.this,EventNameAdd.class);
                startActivity(intent);
            }
        });

        addevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EventAdd.this,UploadEvent.class);
                startActivity(intent);
            }
        });
        addsponsors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EventAdd.this,AddSponsors.class);
                startActivity(intent);
            }
        });
        recruitment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventAdd.this,AddRecruitment.class);
                startActivity(intent);
            }
        });
        postNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EventAdd.this,PostNews.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id == android.R.id.home){
            Intent intent=new Intent(EventAdd.this,NewsActivity.class);
            startActivity(intent);
        }
        return true;
    }

    void init(){
        addeventname = findViewById(R.id.addeventname);
        addsponsors = findViewById(R.id.addsponsors);
        addevent = findViewById(R.id.addevent);
        recruitment = findViewById(R.id.recruitment);
        postNews = findViewById(R.id.postNews);
    }
}
