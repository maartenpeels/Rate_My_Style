package com.ratemystyle.rate_my_style.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ratemystyle.rate_my_style.R;

/**
 * Created by Jean Paul on 29-9-2016.
 */

public class MainFragment extends Fragment {
    ImageView imageView;
    private FirebaseAuth mAuth;
    private SurfaceView preview = null;
    private SurfaceHolder previewHolder = null;

    public static MainFragment newInstance(String text) {

        MainFragment f = new MainFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        System.out.println("Main fragment call");
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_main, container, false);

        final RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_main,
                container, false);
        this.imageView = (ImageView) layout.findViewById(R.id.cameraResult);

        return rootView;
    }

}

