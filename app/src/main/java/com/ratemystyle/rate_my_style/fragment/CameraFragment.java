package com.ratemystyle.rate_my_style.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.ratemystyle.rate_my_style.R;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Jean Paul on 13-10-2016.
 */

public class CameraFragment extends Fragment {
    private static final int CAMERA_REQUEST = 1888;
    String path;
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
                File mediaFile = new File(Environment.getDataDirectory(), "DYLMS");
                mediaFile.mkdirs();
                File file = new File(mediaFile, "tempImg.png");
                path = file.getPath();
                file.mkdirs();

                Uri outputFileUri = Uri.fromFile(file);
                System.out.println(outputFileUri);
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);


            }
        });

        return v;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            try {
                System.out.println("Test");
                Bitmap photo = (Bitmap) data.getExtras().get("data");


                //String destFolder = getCacheDir().getAbsolutePath();

                FileOutputStream out = null;

                out = new FileOutputStream(path);

                // photo.compress(Bitmap.CompressFormat.PNG, 100, out);
                imageView.setImageBitmap(photo);

                //Delete the image from standard image libary
                //File image = new File(path);
                //image.delete();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Gets the last image id from the media store
     * @return
     */
//        private int getLastImageId(){
//            final String[] imageColumns = { MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA };
//            final String imageOrderBy = MediaStore.Images.Media._ID+" DESC";
//            Cursor imageCursor = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageColumns, null, null, imageOrderBy);
//            if(imageCursor.moveToFirst()){
//                int id = imageCursor.getInt(imageCursor.getColumnIndex(MediaStore.Images.Media._ID));
//                String fullPath = imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA));
//                //Log.d(TAG, "getLastImageId::id " + id);
//              //  Log.d(TAG, "getLastImageId::path " + fullPath);
//                imageCursor.close();
//                return id;
//            }else{
//                return 0;
//            }
//        }
//        private void removeImage(int id) {
//            ContentResolver cr = getContentResolver();
//        cr.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, MediaStore.Images.Media._ID + "=?", new String[]{ Long.toString(id) } );
//    }

}
