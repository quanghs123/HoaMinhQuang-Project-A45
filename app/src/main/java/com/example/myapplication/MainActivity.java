package com.example.myapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.Module.ChapTruyen;
import com.example.myapplication.Module.TruyenChu;
import com.example.myapplication.Module.TruyenTranh;
import com.example.myapplication.fragment.ChangePasswordFragment;
import com.example.myapplication.fragment.DocTruyenChuFragment;
import com.example.myapplication.fragment.DocTruyenFragment;
import com.example.myapplication.fragment.FavoriteFragment;
import com.example.myapplication.fragment.HistoryFragment;
import com.example.myapplication.fragment.HomeFragment;
import com.example.myapplication.fragment.MyProfileFragment;
import com.example.myapplication.fragment.SearchFragment;
import com.example.myapplication.fragment.TruyenChuFragment;
import com.example.myapplication.fragment.TruyenTranhFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static final int MY_REQUEST_CODE = 10;
    public static boolean CHECK = true;

    static final int FRAGMENT_HOME=0;
    static final int FRAGMENT_FAVORITE=1;
    static final int FRAGMENT_HISTORY=2;
    static final int FRAGMENT_MY_PROFILE=3;
    static final int FRAGMENT_CHANGE_PASSWORD=4;

    int mCurrentFragment = FRAGMENT_HOME;
    SearchView searchView;
    
    List<TruyenTranh> list1;
    List<TruyenChu> list2;
    ImageView btnChangeTruyenTranh;
    ImageView btnChangeTruyenChu;

    Long backPressedTime = System.currentTimeMillis();
    Toast mToast;
    ImageView imgAvatar;
    TextView tvName,tvEmail;
    final private MyProfileFragment mMyProfileFragment = new MyProfileFragment();
    Toolbar toolbar;
    ActionBar actionBar;

    NavigationView mNavigationView;
    DrawerLayout mDrawerLayout;
    final private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode()==RESULT_OK){
                Intent intent = result.getData();
                if(intent == null){
                    return;
                }
                Uri uri = intent.getData();
                mMyProfileFragment.setmUri(uri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                    mMyProfileFragment.setBitmapImageView(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.tootbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();

        initUi();
        showUserInformation();

        mDrawerLayout = findViewById(R.id.drawre_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new HomeFragment());
        mNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);

        list1 = new ArrayList<>();
        list2 = new ArrayList<>();

        btnChangeTruyenTranh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickChangeImg();
                btnChangeTruyenTranh.setVisibility(View.GONE);
                btnChangeTruyenChu.setVisibility(View.VISIBLE);
            }
        });
        btnChangeTruyenChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickChangeImg();
                btnChangeTruyenTranh.setVisibility(View.VISIBLE);
                btnChangeTruyenChu.setVisibility(View.GONE);
            }
        });
    }

    private void onClickChangeImg() {
        CHECK = !CHECK;
        replaceFragment(new HomeFragment());
    }

    private void initUi(){
        mNavigationView = findViewById(R.id.navigation_view);
        imgAvatar = mNavigationView.getHeaderView(0).findViewById(R.id.imgAvatar);
        tvName = mNavigationView.getHeaderView(0).findViewById(R.id.tvName);
        tvEmail = mNavigationView.getHeaderView(0).findViewById(R.id.tvEmail);
        btnChangeTruyenTranh = mNavigationView.getHeaderView(0).findViewById(R.id.btnChangeTruyenTranh);
        btnChangeTruyenChu = mNavigationView.getHeaderView(0).findViewById(R.id.btnChangeTruyenChu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id =  item.getItemId();
        if(id == R.id.nav_home){
            if(mCurrentFragment != FRAGMENT_HOME){
                replaceFragment(new HomeFragment());
                mCurrentFragment = FRAGMENT_HOME;
            }
        }
        if(id == R.id.nav_favorite){
            if(mCurrentFragment != FRAGMENT_FAVORITE){
                replaceFragment(new FavoriteFragment());
                mCurrentFragment = FRAGMENT_FAVORITE;
            }
        }
        if(id == R.id.nav_history){
            if(mCurrentFragment != FRAGMENT_HISTORY){
                replaceFragment(new HistoryFragment());
                mCurrentFragment = FRAGMENT_HISTORY;
            }
        }else if(id == R.id.nav_sign_out){
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this,SignInActivity.class);
            startActivity(intent);
            finish();
        }
        else if(id == R.id.nav_my_profile){
            if(mCurrentFragment != FRAGMENT_MY_PROFILE){
                replaceFragment(mMyProfileFragment);
                mCurrentFragment = FRAGMENT_MY_PROFILE;
            }
        }else if(id == R.id.nav_change_password){
            if(mCurrentFragment != FRAGMENT_CHANGE_PASSWORD){
                replaceFragment(new ChangePasswordFragment());
                mCurrentFragment = FRAGMENT_CHANGE_PASSWORD;
            }
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }else if(!searchView.isIconified()){
            searchView.setIconified(true);
        }
        else{
            super.onBackPressed();
        }
//        else if(backPressedTime + 2000 > System.currentTimeMillis()){
//            mToast.cancel();
//            super.onBackPressed();
//        }else{
//            mToast = Toast.makeText(MainActivity.this, "Press back again to exit the application", Toast.LENGTH_SHORT);
//            mToast.show();
//        }
//        backPressedTime = System.currentTimeMillis();

    }

    private void replaceFragment(Fragment fragment){
        actionBar.setTitle(R.string.app_name);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame,fragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(CHECK){
                    SearchFragment searchFragment = new SearchFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("list_truyen", (Serializable) timKiem(list1,query));
                    searchFragment.setArguments(bundle);
                    actionBar.setTitle(R.string.app_name);
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content_frame, searchFragment);
                    fragmentTransaction.addToBackStack(SearchFragment.TAG);
                    fragmentTransaction.commit();
                }
                else{
                    SearchFragment searchFragment = new SearchFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("list_truyen_chu", (Serializable) timKiemTruyenChu(list2,query));
                    searchFragment.setArguments(bundle);
                    actionBar.setTitle(R.string.app_name);
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content_frame, searchFragment);
                    fragmentTransaction.addToBackStack(SearchFragment.TAG);
                    fragmentTransaction.commit();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(CHECK){
                    SearchFragment searchFragment = new SearchFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("list_truyen", (Serializable) timKiem(list1,newText));
                    searchFragment.setArguments(bundle);
                    actionBar.setTitle(R.string.app_name);
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content_frame, searchFragment);
                    fragmentTransaction.addToBackStack(SearchFragment.TAG);
                    fragmentTransaction.commit();
                }
                else{
                    SearchFragment searchFragment = new SearchFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("list_truyen_chu", (Serializable) timKiemTruyenChu(list2,newText));
                    searchFragment.setArguments(bundle);
                    actionBar.setTitle(R.string.app_name);
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content_frame, searchFragment);
                    fragmentTransaction.addToBackStack(SearchFragment.TAG);
                    fragmentTransaction.commit();
                }
                return false;
            }
        });
        return true;
    }

    public void setList1(List<TruyenTranh> list1) {
        this.list1 = list1;
    }

    public void setList2(List<TruyenChu> list2) {
        this.list2 = list2;
    }

    private List<TruyenTranh> timKiem(List<TruyenTranh> ls, String strSearch){
        List<TruyenTranh> tranhs = new ArrayList<>();
        for(TruyenTranh truyenTranh : ls){
            if(truyenTranh.getTenTruyen().toLowerCase().contains(strSearch.toLowerCase())){
                tranhs.add(truyenTranh);
            }
        }
        return tranhs;
    }
    private List<TruyenChu> timKiemTruyenChu(List<TruyenChu> ls, String strSearch){
        List<TruyenChu> truyenChus = new ArrayList<>();
        for(TruyenChu truyenChu : ls){
            if(truyenChu.getTenTruyen().toLowerCase().contains(strSearch.toLowerCase())){
                truyenChus.add(truyenChu);
            }
        }
        return truyenChus;
    }
    public void goToTruyenTranhFragment(TruyenTranh truyenTranh){

        actionBar.setTitle(truyenTranh.getTenTruyen());

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        TruyenTranhFragment truyenTranhFragment = new TruyenTranhFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_manga",truyenTranh);
        truyenTranhFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.content_frame,truyenTranhFragment);
        fragmentTransaction.addToBackStack(TruyenTranhFragment.TAG);
        fragmentTransaction.commit();
    }
    public void goToTruyenChuFragment(TruyenChu truyenChu){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        TruyenChuFragment truyenChuFragment = new TruyenChuFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_manga",truyenChu);
        truyenChuFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.content_frame,truyenChuFragment);
        fragmentTransaction.addToBackStack(TruyenChuFragment.TAG);
        fragmentTransaction.commit();
    }
    public void showUserInformation(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            return;
        }
        String name = user.getDisplayName();
        String email = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();


        if(name == null){
            tvName.setVisibility(View.GONE);
        }else{
            tvName.setVisibility(View.VISIBLE);
            tvName.setText(name);
        }
        tvEmail.setText(email);
        Glide.with(this).load(photoUrl).error(R.drawable.ic_avatar_default).into(imgAvatar);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == MY_REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openGallery();
            }else{
                Toast.makeText(MainActivity.this,"Vui l??ng cho ph??p truy c???p",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void openGallery(){
        Intent intent = new Intent();
        intent.setType("imgage/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent,"Select Picture"));
    }
    public void goToDocTruyenFragment(TruyenTranh truyenTranh,int i){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame,DocTruyenFragment.getInstance(truyenTranh,i));
        fragmentTransaction.addToBackStack(TruyenTranhFragment.TAG);
        fragmentTransaction.commit();
    }
    public void goToDocTruyenChuFragment(TruyenChu truyenChu,int i){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame,DocTruyenChuFragment.getInstance(truyenChu,i));
        fragmentTransaction.addToBackStack(TruyenChuFragment.TAG);
        fragmentTransaction.commit();
    }
}