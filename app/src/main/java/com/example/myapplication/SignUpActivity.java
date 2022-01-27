package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Module.TruyenChu;
import com.example.myapplication.Module.TruyenTranh;
import com.example.myapplication.Module.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {

    EditText edEmail,edPassword,edConfirmPassword;
    Button btnSignUp;
    ProgressDialog progressDialog;
    TextView tvMessage;
    List<TruyenTranh> truyenTranhList;
    List<TruyenTranh> listFavorite;
    List<TruyenTranh> listHistory;
    List<TruyenChu> truyenChuList;
    List<TruyenChu> listTruyenChuFavorite;
    List<TruyenChu> listTruyenChuHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initUi();
        initListener();

    }
    private void initUi(){
        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        edConfirmPassword = findViewById(R.id.edConfirmPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvMessage = findViewById(R.id.tvMessage);
        progressDialog = new ProgressDialog(this);

        truyenTranhList = new ArrayList<>();
        listFavorite = new ArrayList<>();
        listHistory = new ArrayList<>();
        truyenChuList = new ArrayList<>();
        listTruyenChuFavorite = new ArrayList<>();
        listTruyenChuHistory = new ArrayList<>();
    }
    private void initListener(){
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSignUp();
            }
        });
    }

    private void onClickSignUp() {
        String strEmail = edEmail.getText().toString().trim();
        String strPassword = edPassword.getText().toString().trim();
        String strConfirmPassword = edConfirmPassword.getText().toString().trim();
        if(strPassword.compareTo(strConfirmPassword)!=0){
            tvMessage.setVisibility(View.VISIBLE);
            tvMessage.setText("Password are not matching!");
            tvMessage.setTextColor(getResources().getColor(R.color.design_default_color_error));
        }else{
            tvMessage.setVisibility(View.GONE);
            FirebaseAuth auth = FirebaseAuth.getInstance();
            progressDialog.show();
            auth.createUserWithEmailAndPassword(strEmail, strPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                User user = new User(auth.getUid(),strEmail,strPassword,truyenTranhList,listFavorite,listHistory,truyenChuList,listTruyenChuFavorite,listTruyenChuHistory);
                                addUserToRealtimeDatabase(auth.getUid(),user);
                                // Sign in success, update UI with the signed-in user's information
                                Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                                startActivity(intent);
                                finishAffinity();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
    private void addUserToRealtimeDatabase(String strUid, User user){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("list_user");

        myRef.child(strUid).setValue(user);
    }
}