package com.example.myapplication.fragment;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.MainActivity;
import com.example.myapplication.Module.TruyenChu;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DocTruyenChuFragment extends Fragment {
    TextView tvText,tvTenChap,tvTenTruyen;
    TruyenChu truyenChu;
    int idChap;
    Button btnBackChap,btnNextChap,btnBackChapTop,btnbtnNextChapTop;
    MainActivity mMainActivity;
    View mView;

    public static DocTruyenChuFragment getInstance(TruyenChu truyenChu, int i){
        DocTruyenChuFragment docTruyenFragment = new DocTruyenChuFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_id_chap",i);
        bundle.putSerializable("object_truyen_tranh",truyenChu);
        docTruyenFragment.setArguments(bundle);

        return docTruyenFragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_doc_truyen_chu,container,false);

        mMainActivity = (MainActivity) getActivity();

        tvText = mView.findViewById(R.id.tvText);
        tvTenChap = mView.findViewById(R.id.tvTenChap);
        tvTenTruyen = mView.findViewById(R.id.tvTenTruyen);
        btnBackChap = mView.findViewById(R.id.btnBackChap);
        btnNextChap = mView.findViewById(R.id.btnNextChap);
        btnBackChapTop = mView.findViewById(R.id.btnBackChapTop);
        btnbtnNextChapTop = mView.findViewById(R.id.btnbtnNextChapTop);


        Bundle bundle = getArguments();
        if(bundle != null){
            idChap = (int) bundle.get("object_id_chap");
            truyenChu = (TruyenChu) bundle.get("object_truyen_tranh");
            tvTenTruyen.setText(truyenChu.getTenTruyen());
            getTenChap(truyenChu.getId(),idChap);
            getText(truyenChu.getId(),idChap);
        }

        btnBackChapTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBackChap();
            }
        });

        btnbtnNextChapTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNextChap();
            }
        });

        btnBackChap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBackChap();
            }
        });

        btnNextChap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNextChap();
            }
        });


        return mView;
    }
    private void getText(int i,int j){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference();

        myRef.child("list_truyen_chu/"+i+"/chapTruyen/"+j+"/contentHtml").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                tvText.setText(Html.fromHtml(value));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(),"Fail",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getTenChap(int i,int j){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference();

        myRef.child("list_truyen_chu/"+i+"/chapTruyen/"+j+"/tenChap").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                tvTenChap.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(),"Fail",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void onClickBackChap(){
        if(idChap == 1){
            Toast.makeText(getActivity(),"This is the first chapter",Toast.LENGTH_SHORT).show();
            return;
        }
        mMainActivity.goToDocTruyenChuFragment(truyenChu,idChap-1);
    }
    private void onClickNextChap(){
        if(idChap == truyenChu.getChapTruyen().size()-1){
            Toast.makeText(getActivity(),"This is the last chapter",Toast.LENGTH_SHORT).show();
            return;
        }
        mMainActivity.goToDocTruyenChuFragment(truyenChu,idChap+1);
    }

}
