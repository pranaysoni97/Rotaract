package com.example.harshad.rotaract;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    public EditText mRegisterUsername, mRegisterEmail, mRegisterPassword, mRegisterMobile, mRegisterEnrollmentNo, mRegisterBranch, mRegisterSemester;
    public Button registrationBtn;
    String username, email, password, mobile, enrollmentno, branch, semester;
    public ProgressBar mProgressbar;
    FirebaseDatabase db;
    DatabaseReference users;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mProgressbar = findViewById(R.id.progressbarRegister);

        firebaseAuth = FirebaseAuth.getInstance();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //db = FirebaseDatabase.getInstance();
        users = FirebaseDatabase.getInstance().getReference("Users");

        mRegisterUsername = findViewById(R.id.registerUsername);
        mRegisterEmail = findViewById(R.id.registerEmail);
        mRegisterPassword = findViewById(R.id.registerPassword);
        mRegisterEnrollmentNo = findViewById(R.id.registerEnrollment);
        mRegisterMobile = findViewById(R.id.registerMobile);
        mRegisterBranch = findViewById(R.id.registerBranch);
        mRegisterSemester = findViewById(R.id.registerSemester);
        registrationBtn = findViewById(R.id.registerbtn);

        registrationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isInternetPresent(RegisterActivity.this)) {
                    buildDialog(RegisterActivity.this).show();
                }else {
                    username = mRegisterUsername.getText().toString();
                    email = mRegisterEmail.getText().toString();
                    password = mRegisterPassword.getText().toString();
                    enrollmentno = mRegisterEnrollmentNo.getText().toString();
                    mobile = mRegisterMobile.getText().toString();
                    branch = mRegisterBranch.getText().toString();
                    semester = mRegisterSemester.getText().toString();

                    if (username.isEmpty()) {
                        mRegisterUsername.setError("enter Username");
                    } else if (email.isEmpty()) {
                        mRegisterEmail.setError("enter Email properly");
                    } else if (password.isEmpty()) {
                        mRegisterPassword.setError("enter password");
                    } else if (enrollmentno.isEmpty()) {
                        mRegisterEnrollmentNo.setError("enter EnrollmentNo Proper");
                    } else if (mobile.isEmpty()) {
                        mRegisterMobile.setError("enter mobile number");
                    } else if (branch.isEmpty()) {
                        mRegisterBranch.setError("enter Branch Name");
                    } else if (semester.isEmpty()) {
                        mRegisterSemester.setError("Enter semester");
                    } else {
                        users.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot data : dataSnapshot.getChildren()) {

                                    User userValue = data.getValue(User.class);
                                    String enrollmentUser=userValue.getEnrollmentno();
                                    String emailUser=userValue.getEmail();
                                    String mobileUser=userValue.getMobile();

                                    if (enrollmentUser.equals(enrollmentno)) {
                                        mRegisterEnrollmentNo.setError("Enrollment Already available");
                                    } else if (emailUser.equals(email)) {
                                        mRegisterEmail.setError("Email Already registered");
                                    } else if (mobileUser.equals(mobile)) {
                                        mRegisterMobile.setError("Mobile Already registered");
                                    } else {
                                        //save data into the firebase
                                        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                User user = new User();
                                                user.setName(username);
                                                user.setEmail(email);
                                                user.setPassword(password);
                                                user.setMobile(mobile);
                                                user.setEnrollmentno(enrollmentno);
                                                user.setBranch(branch);
                                                user.setSemester(semester);

                                                mProgressbar.setVisibility(View.VISIBLE);

                                                users.child(user.getEnrollmentno()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        mProgressbar.setVisibility(View.GONE);
                                                        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                                                        startActivity(intent);
                                                        Toast.makeText(RegisterActivity.this, "Registration Successfullly", Toast.LENGTH_SHORT).show();
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(RegisterActivity.this, "Registration Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(RegisterActivity.this, "Registration Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }
        });
    }

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);

        builder.setTitle("No internet Connection");
        builder.setMessage("You need to have mobile or wifi to access this Application.Press ok to exit");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        return builder;
    }

    public boolean isInternetPresent(Context context) {
        ConnectivityManager con = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (con.getActiveNetworkInfo() != null && con.getActiveNetworkInfo().isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
            else
                return false;
        } else
            return false;
    }
}
