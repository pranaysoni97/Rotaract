package com.example.harshad.rotaract;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.harshad.rotaract.model.add;
import com.example.harshad.rotaract.model.addevent;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EventNameAdd extends AppCompatActivity {

    EditText mEventName,mEventPrize;
    Button mAddEvent;

    DatabaseReference mDataBaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_name_add);
        mEventName = findViewById(R.id.eventAdd);
        mEventPrize = findViewById(R.id.eventPrize);
        mAddEvent = findViewById(R.id.mAdd);

        mDataBaseReference = FirebaseDatabase.getInstance().getReference("Events");

        mAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEventName.getText().toString().trim();
                String eventName=mEventPrize.getText().toString().trim();
                String id = mDataBaseReference.push().getKey();
                add  mAddData= new add(id,name,eventName);
                mDataBaseReference.child(id).setValue(mAddData);
                Toast.makeText(EventNameAdd.this,"Event Added Successfully",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EventNameAdd.this,EventAdd.class));
            }
        });
    }
}
