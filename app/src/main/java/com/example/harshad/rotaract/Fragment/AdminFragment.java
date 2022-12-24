package com.example.harshad.rotaract.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.harshad.rotaract.EventAdd;
import com.example.harshad.rotaract.R;

/**
 * Created by Harshad on 1/16/2018.
 */

public class AdminFragment extends Fragment {

    EditText admin_name, admin_password;
    Button mLogin;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.admin, container, false);

        admin_name = view.findViewById(R.id.admin_name);
        admin_password = view.findViewById(R.id.admin_password);

        mLogin = view.findViewById(R.id.mAdd);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=admin_name.getText().toString().trim();
                String password=admin_password.getText().toString().trim();

                if(name.isEmpty()){
                    Toast.makeText(getActivity(),"Please Enter Name",Toast.LENGTH_SHORT).show();
                }else if(password.isEmpty()){
                    Toast.makeText(getActivity(),"Please Enter Password",Toast.LENGTH_SHORT).show();
                }else if(name.equals("Akshat") && password.equals("Akshat@123")){
                    Intent intent=new Intent(getActivity(),EventAdd.class);
                    startActivity(intent);
                }
            }
        });
        return view;
    }
}
