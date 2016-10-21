package com.ratemystyle.rate_my_style.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.ratemystyle.rate_my_style.Database;
import com.ratemystyle.rate_my_style.Models.Post;
import com.ratemystyle.rate_my_style.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.ratemystyle.rate_my_style.R.id.extraPhoto;

/**
 * Created by Jean Paul on 13-10-2016.
 */

public class CameraFragment extends Fragment {
    private static final int CAMERA_REQUEST = 1888;
    String path;
    List<String> imageUrls = new ArrayList<>();
    private ImageView imageView;
    private TextView textView;
    private Bitmap photo;
    private boolean pictureTaken = false;
    private EditText etStatus;
    private EditText etUrl;
    private ArrayList<Bitmap> pictures = new ArrayList<>();
    private int currentIndex = -1;
    private boolean replacePicture = false;
    private Button xtraPhoto;
    private Button nextPhoto;
    private Button prevPhoto;
    private TextView countText;

    public static CameraFragment newInstance(String text) {

        CameraFragment f = new CameraFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    public static void deleteFileFromMediaStore(final ContentResolver contentResolver, final File file) {
        String canonicalPath;
        try {
            canonicalPath = file.getCanonicalPath();
        } catch (IOException e) {
            canonicalPath = file.getAbsolutePath();
        }
        final Uri uri = MediaStore.Files.getContentUri("external");
        final int result = contentResolver.delete(uri,
                MediaStore.Files.FileColumns.DATA + "=?", new String[]{canonicalPath});
        if (result == 0) {
            final String absolutePath = file.getAbsolutePath();
            if (!absolutePath.equals(canonicalPath)) {
                contentResolver.delete(uri,
                        MediaStore.Files.FileColumns.DATA + "=?", new String[]{absolutePath});
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("Camera fragment call");

        final View layout = inflater.inflate(R.layout.fragment_camera, container, false);
        countText = (TextView) layout.findViewById(R.id.countText);
        etStatus = (EditText) layout.findViewById(R.id.addStatus);
        imageView = (ImageView) layout.findViewById(R.id.cameraResult);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), getResources().getString(R.string.hold_imageview), Toast.LENGTH_SHORT).show();
            }
        });
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                replacePicture = true;
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                return false;
            }
        });
        textView = (TextView) layout.findViewById(R.id.takePictureText);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
        xtraPhoto = (Button) layout.findViewById(extraPhoto);
        xtraPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
        prevPhoto = (Button) layout.findViewById(R.id.previousPhoto);
        prevPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (0 < currentIndex) {
                    currentIndex += -1;
                    countText.setText(currentIndex + 1 + "/" + pictures.size());
                    imageView.setImageBitmap(pictures.get(currentIndex));
                }
            }
        });
        nextPhoto = (Button) layout.findViewById(R.id.nextPhoto);
        nextPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pictures.size() - 1 > currentIndex) {
                    currentIndex++;
                    countText.setText(currentIndex + 1 + "/" + pictures.size());
                    imageView.setImageBitmap(pictures.get(currentIndex));
                }
            }
        });
        Button savePost = (Button) layout.findViewById(R.id.savePost);
        savePost.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (pictureTaken) {
                    if (etStatus.length() != 0) {
                        Database.getInstance().uploadMultipleImages(pictures, new Database.OnImagesSavedListener() {
                            @Override
                            public void onImagesSaved(List<String> urls) {
                                imageUrls = urls;
                                createPost(layout);
                            }
                        });
                    } else {
                        etStatus.setHintTextColor(Color.parseColor("#ff7f7f"));
                        Toast.makeText(getActivity(), "Please add a status",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.choose_image), Toast.LENGTH_SHORT).show();

                }
            }
        });
        return layout;
    }

    public void createPost(View layout) {
        etUrl = (EditText) layout.findViewById(R.id.addUrl);

        long unixTime = System.currentTimeMillis();
        Post post = new Post(FirebaseAuth.getInstance().getCurrentUser().getUid(), imageUrls, etStatus.getText().toString(), unixTime + "", etUrl.getText().toString());
        Database.getInstance().savePost(post);
        textView.setVisibility(View.VISIBLE);
        imageView.setImageDrawable(null);
        etStatus.setText("");
        etUrl.setText("");
        countText.setText("");
        nextPhoto.setVisibility(View.GONE);
        xtraPhoto.setVisibility(View.GONE);
        prevPhoto.setVisibility(View.GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {

            int permissionCheck = ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);

            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this.getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            }
            int permissionCheckAgain = ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);

            if (permissionCheckAgain == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(getActivity(), "Couldn't save image",
                        Toast.LENGTH_LONG).show();
                return;
            }
            try {
                photo = (Bitmap) data.getExtras().get("data");
                pictureTaken = true;
                textView.setVisibility(View.GONE);
                if (!replacePicture) {
                    pictures.add(photo);

                    xtraPhoto.setVisibility(View.VISIBLE);
                    nextPhoto.setVisibility(View.VISIBLE);
                    prevPhoto.setVisibility(View.VISIBLE);
                    countText.setText(pictures.size() + "/" + pictures.size());
                    currentIndex = pictures.size();
                    System.out.println(currentIndex);
                } else {
                    pictures.remove(currentIndex);
                    pictures.add(currentIndex, photo);
                    countText.setText(currentIndex + 1 + "/" + pictures.size());
                }


                imageView.setImageBitmap(photo);


                // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                //Uri tempUri = getImageUri(getApplicationContext(), photo);

                // CALL THIS METHOD TO GET THE ACTUAL PATH
                //File finalFile = new File(getRealPathFromURI(tempUri));
                //TODO Delete file from image libary
                //deleteFileFromMediaStore(getContext().getContentResolver(), finalFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
}

