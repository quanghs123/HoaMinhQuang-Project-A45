package com.example.myapplication;

import androidx.annotation.NonNull;
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
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myapplication.Module.TruyenTranh;
import com.example.myapplication.fragment.FavoriteFragment;
import com.example.myapplication.fragment.HistoryFragment;
import com.example.myapplication.fragment.HomeFragment;
import com.example.myapplication.fragment.SearchFragment;
import com.example.myapplication.fragment.TruyenTranhFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    static final int FRAGMENT_HOME=0;
    static final int FRAGMENT_FAVORITE=1;
    static final int FRAGMENT_HISTORY=2;

    int mCurrentFragment = FRAGMENT_HOME;
    SearchView searchView;
    
    List<TruyenTranh> list1,list2;

    Long backPressedTime = System.currentTimeMillis();
    Toast mToast;

    DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.tootbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = findViewById(R.id.drawre_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view );
        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new HomeFragment());
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);

        list1 = new ArrayList<>();
        list2 = new ArrayList<>();

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
        }else{
            super.onBackPressed();
        }
//        if(backPressedTime + 2000 > System.currentTimeMillis()){
//            mToast.cancel();
//
//            return;
//        }else {
//            mToast = Toast.makeText(MainActivity.this, "Press back again to exit the application", Toast.LENGTH_SHORT);
//            mToast.show();
//
//        }
    }

    private void replaceFragment(Fragment fragment){
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
                replaceSearchFragment(timKiem(list1,query));

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                replaceSearchFragment(timKiem(list1,newText));
                return false;
            }
        });
        return true;
    }
    private void replaceSearchFragment(List<TruyenTranh> tranhs){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, SearchFragment.getInstance(tranhs));
        fragmentTransaction.addToBackStack(SearchFragment.TAG);
        fragmentTransaction.commit();
    }

    public void setList1(List<TruyenTranh> list1) {
        this.list1 = list1;
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

    public void goToTruyenTranhFragment(TruyenTranh truyenTranh){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        TruyenTranhFragment truyenTranhFragment = new TruyenTranhFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_manga",truyenTranh);
        truyenTranhFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.content_frame,truyenTranhFragment);
        fragmentTransaction.commit();
    }
    public void sendDataToFavoriteFragment(TruyenTranh truyenTranh){
        Bundle bundle = new Bundle();
        bundle.putSerializable("list_truyen",truyenTranh);
        FavoriteFragment favoriteFragment = new FavoriteFragment();
        favoriteFragment.setArguments(bundle);
    }
}