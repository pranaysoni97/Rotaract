package com.example.harshad.rotaract;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.Date;

public class PostNews extends AppCompatActivity {

    private ImageButton imageButton;
    public static final int GALLERY_INTENT = 1;
    //private EditText upload_sponsors;
    String EventName;
    private Button mSend;
    private DatabaseReference mDatabaseReference;
    private StorageReference mStorageReference;
    private Uri ImageUri= null;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_news);

        imageButton = (ImageButton) findViewById(R.id.imageButton);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("News");
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

       // upload_sponsors = (EditText) findViewById(R.id.upload_sponsors);

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

        //EventName = upload_sponsors.getText().toString().trim();

        //final Date currentTime = Calendar.getInstance().getTime();
         if (ImageUri.equals("")) {
            Toast.makeText(PostNews.this, "Please Select Image", Toast.LENGTH_SHORT).show();
        }else if(!ImageUri.equals("")){
            mProgress.setMessage("News Adding");
            mProgress.show();
            StorageReference filepath=mStorageReference.child("News").child(ImageUri.getLastPathSegment());
            filepath.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUri=taskSnapshot.getDownloadUrl();
                    DatabaseReference data=mDatabaseReference.push();
                    data.child("IvPath").setValue(downloadUri.toString());
                    Toast.makeText(PostNews.this,"News Uploaded sucessfully",Toast.LENGTH_SHORT).show();
                    mProgress.dismiss();
                    Intent intent=new Intent(PostNews.this,EventAdd.class);
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(PostNews.this,"Error:uploading image..",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
