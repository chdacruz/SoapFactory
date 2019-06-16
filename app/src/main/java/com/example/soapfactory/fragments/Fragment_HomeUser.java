package com.example.soapfactory.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.soapfactory.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Fragment_HomeUser extends Fragment {

    TextView username;

    //Firebase
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_user, container, false);

        username = view.findViewById(R.id.txtName_User_Home);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        firebaseSetNavText(firebaseUser);

        return view;
    }

    public void firebaseSetNavText(FirebaseUser fUser) {

        if (fUser != null) {
            String pName = fUser.getDisplayName();

            username.setText(pName);
        }
    }
}
