package com.example.myapplication.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangePasswordFragment extends Fragment {
    public static final String TAG = ChangePasswordFragment.class.getName();
    EditText edNewPassword,edConfirmPassword;
    Button btnChangePassword;
    ProgressDialog progressDialog;
    TextView tvMessage;

    View mView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_change_password,container,false);

        initUi();
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickChangePassword();
            }
        });

        return mView;
    }
    private void initUi(){
        edNewPassword = mView.findViewById(R.id.edNewPassword);
        edConfirmPassword = mView.findViewById(R.id.edConfirmPassword);
        btnChangePassword = mView.findViewById(R.id.btnChangePassword);
        tvMessage = mView.findViewById(R.id.tvMessage);
        progressDialog = new ProgressDialog(getContext());

    }
    private void onClickChangePassword(){
        String strNewPassword = edNewPassword.getText().toString().trim();
        String strConfirmPassword = edConfirmPassword.getText().toString().trim();
        if(strNewPassword.compareTo(strConfirmPassword)!=0){
            tvMessage.setVisibility(View.VISIBLE);
            tvMessage.setText("Password are not matching!");
            tvMessage.setTextColor(getResources().getColor(R.color.design_default_color_error));
        }else{
            tvMessage.setVisibility(View.GONE);
            progressDialog.show();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            user.updatePassword(strNewPassword)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(),"User password updated.",Toast.LENGTH_SHORT).show();

                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("list_user/"+user.getUid());
                                myRef.child("password").setValue(strNewPassword);
                            }
                        }
                    });
        }
    }
}
