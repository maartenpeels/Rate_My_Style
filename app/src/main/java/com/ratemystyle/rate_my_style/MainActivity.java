package com.ratemystyle.rate_my_style;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.ratemystyle.rate_my_style.fragment.CameraFragment;
import com.ratemystyle.rate_my_style.fragment.MainFragment;
import com.ratemystyle.rate_my_style.fragment.ProfileFragment;

import static com.ratemystyle.rate_my_style.R.id.pager;

public class MainActivity extends FragmentActivity {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 3;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    // private PagerAdapter mPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate a ViewPager and a PagerAdapter.
        //https://developer.android.com/training/animation/screen-slide.html
        // http://stackoverflow.com/questions/18413309/how-to-implement-a-viewpager-with-different-fragments-layouts/18413437#18413437
        PagerAdapter mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(pager);
        mPager.setAdapter(mPagerAdapter);

    }

    @Override
    public void onBackPressed() {
        //TODO if previous is login block backPressed
//        if (mPager.getCurrentItem() == 0) {
//            // If the user is currently looking at the first step, allow the system to handle the
//            // Back button. This calls finish() on this activity and pops the back stack.
//            mPager.setCurrentItem(1);
//        } else {
//            // Otherwise, select the previous step.
//            super.onBackPressed();
//        }
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
                    return ProfileFragment.newInstance("ProfileFragment, Instance 1");

                default:
                    return MainFragment.newInstance("MainFragment, Default");
            }
        }


        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}

