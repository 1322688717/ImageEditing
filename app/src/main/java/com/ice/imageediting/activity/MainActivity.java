package com.ice.imageediting.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ice.imageediting.R;
import com.ice.imageediting.fragment.BeautifyFragment;
import com.ice.imageediting.fragment.EditFragment;
import com.ice.imageediting.fragment.MyFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // 确保使用包含 BottomNavigationView 的布局

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);

        // 默认显示 EditFragment
        if (savedInstanceState == null) {
            loadFragment(new EditFragment());
        }
    }

    private boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;

        if (item.getItemId() == R.id.nav_edit) {
            selectedFragment = new EditFragment();
        } else if (item.getItemId() == R.id.nav_beautify) {
            selectedFragment = new BeautifyFragment();
        } else if (item.getItemId() == R.id.nav_my) {
            selectedFragment = new MyFragment();
        }

        return loadFragment(selectedFragment);
    }


    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contentLayout, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
