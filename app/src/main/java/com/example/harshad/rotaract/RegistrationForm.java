package com.example.harshad.rotaract;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RegistrationForm extends AppCompatActivity {

    private TextView Event_Name;
    private EditText Username,Mobile,Email,Enrollment,Branch,Semester;
    DatabaseReference mDatabase;
    private String email, name, mobile, enrollment,branch,semester;
    ArrayList<User> usersArrayList;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Caligraphyconfig
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Helmet-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form);
        init();

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        usersArrayList = new ArrayList<>();

        if (firebaseUser != null) {
            // Name,Email
            name = firebaseUser.getDisplayName();
            email = firebaseUser.getEmail();
            mobile = firebaseUser.getPhoneNumber();
        }

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userData : dataSnapshot.getChildren()) {
                    User user = userData.getValue(User.class);
                    if (user.getEmail().equals(firebaseUser.getEmail())) {
                        name = user.getName();
                        Username.setText(name);
                        mobile = user.getMobile();
                        Mobile.setText(mobile);
                        enrollment = user.getEnrollmentno();
                        Enrollment.setText(enrollment);
                        semester = user.getSemester();
                        Semester.setText(semester);
                        branch = user.getBranch();
                        Branch.setText(branch);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Event_Name.setText(getIntent().getStringExtra("event_name"));
        Email.setText(email);
    }

    void init() {
        Event_Name =(TextView) findViewById(R.id.event_name);
        Username = (EditText)findViewById(R.id.username);
        Mobile =(EditText) findViewById(R.id.mobile);
        Email = (EditText)findViewById(R.id.email);
        Enrollment =(EditText) findViewById(R.id.enrollment);
        Branch =(EditText) findViewById(R.id.branch);
        Semester = (EditText)findViewById(R.id.semester);
    }
}
