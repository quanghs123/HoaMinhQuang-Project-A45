package com.example.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.LoginActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Module.User;
import com.example.myapplication.R;

public class LoginFragment extends Fragment {
    View mView;
    EditText edEmail,edPassword;
    TextView tvMessage;
    Button btnLogin,btnRegistration;
    LoginActivity mMainActivity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_login,container,false);

        edEmail = mView.findViewById(R.id.edEmail);
        edPassword = mView.findViewById(R.id.edPassword);
        tvMessage = mView.findViewById(R.id.tvMessage);
        btnLogin = mView.findViewById(R.id.btnLogin);
        btnRegistration = mView.findViewById(R.id.btnRegistration);
        mMainActivity = (LoginActivity) getActivity();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickLogin();;
            }
        });

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainActivity.replaceFragment(new RegistrationFragment());
            }
        });


        return mView;
    }

    private void clickLogin() {
        String strEmail = edEmail.getText().toString().trim();
        String strPassword = edPassword.getText().toString().trim();

        User user = new User(strEmail,strPassword,"Quang");

        tvMessage.setVisibility(View.VISIBLE);
        if(user.isValidEmail()&&user.isValidPassword()){
            tvMessage.setText("Login Success");
            tvMessage.setTextColor(getResources().getColor(R.color.teal_200));
            mMainActivity.sendData();
        }else{
            tvMessage.setText("Email or Password invalid");
            tvMessage.setTextColor(getResources().getColor(R.color.design_default_color_error));
        }
    }

}
