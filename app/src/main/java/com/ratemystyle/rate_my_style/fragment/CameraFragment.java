package com.ratemystyle.rate_my_style.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.ratemystyle.rate_my_style.R;

/**
 * Created by Jean Paul on 13-10-2016.
 */

public class CameraFragment extends Fragment {
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;

    public static CameraFragment newInstance(String text) {

        CameraFragment f = new CameraFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("Camera fragment call");

        View v = inflater.inflate(R.layout.fragment_camera, container, false);
        imageView = (ImageView) v.findViewById(R.id.cameraResult);
        Button button1 = (Button) v.findViewById(R.id.startCamera);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("New picture button click");
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
    }
}
