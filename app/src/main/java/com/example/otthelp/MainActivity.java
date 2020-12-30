package com.example.otthelp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.otthelp.unused.PlatformsActivity;
import com.example.otthelp.unused.TitlesActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        MainViewPagerAdapter adapter;
        adapter = new MainViewPagerAdapter(getSupportFragmentManager(), Arrays.asList(
                "Titles",
                "Platforms"
        ), Arrays.asList(
                new TitlesFragment(),
                new PlatformsFragment()
        ));
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

    }

    public void showTitles(View view) {
        Intent intent = new Intent(this, TitlesActivity.class);
        startActivity(intent);
    }

    public void showPlatforms(View view) {
        Intent intent = new Intent(this, PlatformsActivity.class);
        startActivity(intent);
    }

    public void admin() {
        Intent intent = new Intent(this, AdminActivity.class);
        startActivity(intent);
    }

    class MainViewPagerAdapter extends FragmentStatePagerAdapter {
        List<Fragment> fragments;
        List<String> fragmentTitles;

        public MainViewPagerAdapter(@NonNull FragmentManager fm, List<String> fragmentTitles, List<Fragment> fragments) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.fragments = fragments;
            this.fragmentTitles = fragmentTitles;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitles.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.search:
                Toast.makeText(this,"Search not yet implemented",Toast.LENGTH_SHORT).show();
                break;
            case R.id.add:
                admin();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}