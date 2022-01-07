package com.example.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;

public class RegistrationFragment extends Fragment {
    View mView;
    Button btnRegistration;
    LoginActivity mLoginActivity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_registration,container,false);

        btnRegistration = mView.findViewById(R.id.btnRegistration);
        mLoginActivity = (LoginActivity) getActivity();

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginActivity.replaceFragment(new LoginFragment());
            }
        });

        return mView;
    }
}
