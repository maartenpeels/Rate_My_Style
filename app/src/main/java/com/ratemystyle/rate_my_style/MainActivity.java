package com.ratemystyle.rate_my_style;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ratemystyle.rate_my_style.fragment.CameraFragment;
import com.ratemystyle.rate_my_style.fragment.MainFragment;
import com.ratemystyle.rate_my_style.fragment.ProfileFragment;

import static com.ratemystyle.rate_my_style.R.id.pager;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int NUM_PAGES = 3;
    private ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        PagerAdapter mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(pager);
        mPager.setOffscreenPageLimit(2);
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(1);

        FirebaseMessaging.getInstance().subscribeToTopic("main");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_settings:
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));

                return true;

            case R.id.btn_signout:
                LoginManager.getInstance().logOut();
                FirebaseAuth.getInstance().signOut();

                startActivity(new Intent(getApplicationContext(), LoginActivity.class));

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {
                case 0:
                    return CameraFragment.newInstance("CameraFragment, Instance 1");
                case 1:
                    return MainFragment.newInstance("MainFragment, Instance 1");
                default:
                    return ProfileFragment.newInstance("ProfileFragment, Default");
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Create";
                case 1:
                    return "Timeline";
                default:
                    return "Profile";
            }
        }


        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}

