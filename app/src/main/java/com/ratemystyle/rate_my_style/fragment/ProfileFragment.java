package com.ratemystyle.rate_my_style.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ratemystyle.rate_my_style.R;

/**
 * Created by Jean Paul on 13-10-2016.
 */

public class ProfileFragment extends Fragment {
    public static ProfileFragment newInstance(String text) {

        ProfileFragment f = new ProfileFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("Profile fragment call");
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_profile, container, false);

        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_profile,
                container, false);


        return rootView;
    }
}
