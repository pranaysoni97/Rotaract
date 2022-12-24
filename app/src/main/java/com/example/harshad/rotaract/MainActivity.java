package com.example.harshad.rotaract;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {


    public TextView btnRegister, btnSignIn;
    public EditText mLoginEmail, mLoginPassword;
    String loginEmail, loginPassword;
    public Button mRegister;
    public LinearLayout rootLayout;
    private AlertDialog.Builder dialog;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase db;
    DatabaseReference users;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Before setCotntentView
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/font_rounded.otf")
                .setFontAttrId(R.attr.fontPath)
                .build());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            Intent intent = new Intent(MainActivity.this, NewsActivity.class);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.activity_main);

        init();

      /*  firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");
*/
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isInternetPresent(MainActivity.this)){
                    buildDialog(MainActivity.this).show();
                }else {
                    //get data from database
                    loginEmail = mLoginEmail.getText().toString().trim();
                    loginPassword = mLoginPassword.getText().toString().trim();

                    firebaseAuth.signInWithEmailAndPassword(loginEmail, loginPassword).addOnSuccessListener(MainActivity.this, new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                            startActivity(intent);
                            Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(MainActivity.this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "Data invalid", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

/*
                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            User userValue = data.getValue(User.class);

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
*/

            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    void init() {
       /* btnRegister = findViewById(R.id.btnRegister);*/
        btnSignIn = findViewById(R.id.signin);
        // rootLayout = findViewById(R.id.rootLayout);
        mLoginEmail = findViewById(R.id.loginEmail);
        mLoginPassword = findViewById(R.id.loginPassword);
        btnRegister = findViewById(R.id.LoginText);
    }
/*

    public void showRegisterDialog() {

        dialog = new AlertDialog.Builder(this);

        dialog.setTitle("Register");
        dialog.setMessage("Please Sign up Here");

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View register_layout = layoutInflater.inflate(R.layout.register, null);


        dialog.setView(register_layout);

        dialog.setPositiveButton("Register", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialog, int which) {





            }
        });

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
*/

/*
    public void showLoginDialog() {

       *//* AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Login");

        LayoutInflater inflater = LayoutInflater.from(this);
        View login_layout = inflater.inflate(R.layout.layout_login, null);

        mLoginEmail = login_layout.findViewById(R.id.loginEmail);
        mLoginPassword = login_layout.findViewById(R.id.loginPassword);*//*
     *//*   dialog.setView(login_layout);*//*
        dialog.setPositiveButton("Login", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {

                dialog.dismiss();

                //get data from database
                loginEmail = mLoginEmail.getText().toString().trim();
                loginPassword = mLoginPassword.getText().toString().trim();

                firebaseAuth.signInWithEmailAndPassword(loginEmail, loginPassword).addOnSuccessListener(MainActivity.this, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(MainActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Data invalid", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }*/

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            Intent intent = new Intent(MainActivity.this, NewsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

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
