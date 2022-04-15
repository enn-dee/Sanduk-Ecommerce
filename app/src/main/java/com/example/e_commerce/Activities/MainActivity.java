package com.example.e_commerce.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import com.example.e_commerce.R;
import com.example.e_commerce.fragments.HomeFragment;
import com.example.e_commerce.fragments.MobileFragment;
import com.google.firebase.auth.FirebaseAuth;



import androidx.core.view.GravityCompat;

import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private long backPressedTime;
    private Toast backToast;

    Fragment homeFragment, mobileFragment;
    Toolbar toolbar;
    FirebaseAuth auth;


    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        mobileFragment = new MobileFragment();
        homeFragment = new HomeFragment();


         toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.navmenu);
        navigationView.setNavigationItemSelectedListener(this);

         drawer = findViewById(R.id.drawer);
         toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();




    //   getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);
        auth = FirebaseAuth.getInstance();


        loadFragment(homeFragment);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbarmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_LogOut) {
            auth.signOut();
            startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
           // finish();
            return true;
        } else if (id == R.id.menu_cart) {
            startActivity(new Intent(MainActivity.this, CartActivity.class));
            return true;

        }

        return super.onOptionsItemSelected(item);

    }


    private void loadFragment(Fragment homeFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_container, homeFragment);
        transaction.commit();

    }
    private void loadMobileFragment(Fragment mobileFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_container, mobileFragment);
        transaction.commit();

    }
//    @Override
//    public void onBackPressed()
//    {
//        DrawerLayout drawer = findViewById(R.id.drawer);
//        if (drawer.isDrawerOpen(GravityCompat.START))
//        {
//            drawer.closeDrawer(GravityCompat.START);
//        } else
//        {
//            super.onBackPressed();
//        }
//    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {


            case R.id.redmiPro:
              loadMobileFragment(mobileFragment);
                break;
            case R.id.mobHome:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                break;

        }



        DrawerLayout drawer = findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }


    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }


}





