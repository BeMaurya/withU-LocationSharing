package com.anand.withu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class NavBarCurrentLoc extends AppCompatActivity {

    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_bar_current_loc);
            // setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            drawer = findViewById(R.id.draw_lay);
        NavigationView nav = findViewById(R.id.nav_view);


        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.home:

                        getSupportFragmentManager().beginTransaction().replace(R.id.frg_cont,new FragHome()).commit();

                        break;

                    case R.id.currloc:
                        //getSupportFragmentManager().beginTransaction().replace().commit();
                        Intent intent = new Intent(NavBarCurrentLoc.this,Location.class);
                        startActivity(intent);
                        break;

                    case R.id.jngrp:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frg_cont,new FragJoinGroup()).commit();
                        break;

                    case R.id.crtgrp:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frg_cont,new FragCreate()).commit();
                        break;

                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });





        toggle= new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();




    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}