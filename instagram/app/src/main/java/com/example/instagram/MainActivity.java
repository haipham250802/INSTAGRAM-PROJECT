package com.example.instagram;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.instagram.fragment.HomeFragment;
import com.example.instagram.fragment.notificationFragment;
import com.example.instagram.fragment.ProfilePragment;
import com.example.instagram.fragment.SearchFragment;
import com.example.instagram.fragment.postfragment;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.fragment.app.Fragment;
import android.view.MenuItem;

import kotlin.jvm.internal.Ref;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private Button LogOutButton;
    private TextView textView;

    private BottomNavigationView bottomNavigationView;
    private Fragment selectorFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ----------------- Variable --------------------
        mAuth = FirebaseAuth.getInstance();
        LogOutButton = findViewById(R.id.LogOutBtn);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        replaceFragment(new HomeFragment());
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        // --------------------- End -----------------------------
        mUser = mAuth.getCurrentUser();
        //--------------- Start Sign Out ---------------

        if (mUser == null) {
            Intent intent = new Intent(getApplicationContext(), activity_login.class);
            startActivity(intent);
            finish();
        }
        LogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), activity_login.class);
                startActivity(intent);
                finish();
            }
        });

        // ---------- End Sign Out --------------------------------------------------
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.nav_home:
                        Toast.makeText(MainActivity.this, "this nav home", Toast.LENGTH_SHORT).show();
                        replaceFragment(new HomeFragment());
                        break;
                    case R.id.nav_heart:
                        Toast.makeText(MainActivity.this, "this nav heart", Toast.LENGTH_SHORT).show();
                        replaceFragment(new notificationFragment());
                        break;
                    case R.id.nav_profile:
                        Toast.makeText(MainActivity.this, "this nav profile", Toast.LENGTH_SHORT).show();
                        replaceFragment(new ProfilePragment());
                        break;
                    case R.id.nav_search:
                        Toast.makeText(MainActivity.this, "this nav search", Toast.LENGTH_SHORT).show();
                        replaceFragment(new SearchFragment());
                        break;
                    case R.id.nav_add:
                        Toast.makeText(MainActivity.this, "this nav Pos", Toast.LENGTH_SHORT).show();
                        replaceFragment(new postfragment());
                        break;
                }
                return true;
            }
        });
    }
    private  void replaceFragment(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
    }
}

