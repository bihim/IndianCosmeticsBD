package com.indiancosmeticsbd.app.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.indiancosmeticsbd.app.R;

public class MainActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();
        setNavDrawer();
        setBottomNavigationView();
    }


    private void setBottomNavigationView(){
        
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.bottom_nav_home){
                    Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                }
                else if(item.getItemId() == R.id.bottom_nav_wishlist){
                    Toast.makeText(MainActivity.this, "wish", Toast.LENGTH_SHORT).show();
                }
                else if(item.getItemId() == R.id.bottom_nav_cart){
                    Toast.makeText(MainActivity.this, "cart", Toast.LENGTH_SHORT).show();
                }
                else if(item.getItemId() == R.id.bottom_nav_account){
                    Toast.makeText(MainActivity.this, "account", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

    private void setNavDrawer(){
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }
    private void findViewById(){
        toolbar = findViewById(R.id.toolbar_main);
        drawerLayout = findViewById(R.id.nav_drawer);
        navigationView = findViewById(R.id.navigation_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }
}