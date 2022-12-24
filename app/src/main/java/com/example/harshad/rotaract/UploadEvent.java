package com.example.harshad.rotaract;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UploadEvent extends AppCompatActivity {

    private ImageButton imageButton;
    public static final int GALLERY_INTENT = 1;
    private EditText upload_event, upload_location, upload_description;
    String EventName, EventLocation, EventDescription;
    private Button mSend;
    private DatabaseReference mDatabaseReference;
    private StorageReference mStorageReference;
    private Uri ImageUri= null;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_event);

        imageButton = (ImageButton) findViewById(R.id.imageButton);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("EventRecords");
        mStorageReference = FirebaseStorage.getInstance().getReference();

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });

        mProgress = new ProgressDialog(this);

        mSend = (Button) findViewById(R.id.post_send);

        upload_event = (EditText) findViewById(R.id.upload_event);
        upload_location = (EditText) findViewById(R.id.upload_location);
        upload_description = (EditText) findViewById(R.id.upload_description);

        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddEvent();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_INTENT) {
            if (resultCode == RESULT_OK) {
                ImageUri = data.getData();
                imageButton.setImageURI(ImageUri);
            }
        }
    }

    private void AddEvent() {
        EventName = upload_event.getText().toString().trim();
        EventDescription = upload_description.getText().toString().trim();
        EventLocation = upload_location.getText().toString().trim();

        final Date currentTime = Calendar.getInstance().getTime();

        if (EventName.isEmpty()) {
            Toast.makeText(UploadEvent.this, "Please Enter Event Name", Toast.LENGTH_SHORT).show();
        } else if (EventDescription.isEmpty()) {
            Toast.makeText(UploadEvent.this, "Please Enter Event Description", Toast.LENGTH_SHORT).show();
        } else if (EventLocation.isEmpty()) {
            Toast.makeText(UploadEvent.this, "Please Enter Event Location", Toast.LENGTH_SHORT).show();
        } else if (ImageUri.equals("")) {
            Toast.makeText(UploadEvent.this, "Please Select Image", Toast.LENGTH_SHORT).show();
        }else if(!EventName.isEmpty() && !EventDescription.isEmpty() && !EventLocation.isEmpty() && !ImageUri.equals("")){
            mProgress.setMessage("Event Adding");
            mProgress.show();
            StorageReference filepath=mStorageReference.child("Events").child(ImageUri.getLastPathSegment());
            filepath.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUri=taskSnapshot.getDownloadUrl();
                    DatabaseReference data=mDatabaseReference.push();
                    data.child("EventName").setValue(EventName);
                    data.child("EventLocation").setValue(EventLocation);
                    data.child("EventDescription").setValue(EventDescription);
                    data.child("ImageUri").setValue(downloadUri.toString());
                    data.child("Date").setValue(currentTime.toString());
                    Toast.makeText(UploadEvent.this,"Uploaded sucessfully",Toast.LENGTH_SHORT).show();
                    mProgress.dismiss();
                    Intent intent=new Intent(UploadEvent.this,EventAdd.class);
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UploadEvent.this,"Error:uploading image..",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
