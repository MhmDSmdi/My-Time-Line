package com.blucode.mhmd.timeline.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.blucode.mhmd.timeline.R;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemReselectedListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_content);
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            System.out.println("asdasd");
            toolbar.setTitleTextColor(getColor(R.color.white));
        }
        bottomNavigationView = findViewById(R.id.bottomNavigation_main);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setOnNavigationItemReselectedListener(this);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content, new HomeFragment());
        setTitle("Home");
        transaction.commit();
    }

    @Override
    public void onNavigationItemReselected(@NonNull MenuItem menuItem) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (menuItem.getItemId()) {
            case R.id.icon_bottomnav_home:
                setTitle("Home");
                transaction.replace(R.id.main_content, new HomeFragment());
                transaction.commit();
                return true;
            case R.id.icon_bottomnav_Info:
                setTitle("Info");
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_content, new InfoFragment());
                transaction.commit();
                return true;

            case R.id.icon_bottomnav_Setting:
                setTitle("Setting");
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_content, new SettingFragment());
                transaction.commit();
                return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_app_bar_capture_image_camera:

                return true;
            case R.id.menu_app_bar_capture_image_gallery:

                return true;

            case R.id.menu_app_bar_search:

                return true;
        }
        return false;
    }
}
