package com.ratemystyle.rate_my_style;

import android.graphics.Bitmap;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ratemystyle.rate_my_style.Models.Post;
import com.ratemystyle.rate_my_style.Models.Profile;

import java.io.ByteArrayOutputStream;

/**
 * Created by Maarten Peels on 10/14/2016.
 */

public class Database {
    private static DatabaseReference mDatabase;
    private static FirebaseStorage mStorage;

    private static Database db = new Database();
    private OnImageSavedListener onImageSavedListener;

    private Database() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance();
    }

    public static Database getInstance() {
        return db;
    }

    public static DatabaseReference getDatabaseReference() {
        return mDatabase;
    }

    public boolean saveProfile(String uid, String firstName, String lastName, int age, Bitmap image) {
        Profile profile = new Profile(uid, firstName, lastName, age);
        mDatabase.child("profiles").child(uid).setValue(profile);

        uploadImgFromView(image, uid);

        return true;
    }

    public boolean savePost(Post post) {
        mDatabase.child("posts").push().setValue(post);
        return true;
    }

    public void setOnScoreSavedListener(OnImageSavedListener listener) {
        onImageSavedListener = listener;
    }

    public void uploadImage(Bitmap image, final OnImageSavedListener listener) {
        StorageReference storageRef = mStorage.getReferenceFromUrl("gs://ratemystyle-99fce.appspot.com");
        StorageReference postPicRef = storageRef.child("images");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = postPicRef.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                listener.onImageSaved(taskSnapshot.getDownloadUrl().toString());
            }
        });
    }

    private void uploadImgFromView(Bitmap bitmap, String uid) {
        StorageReference storageRef = mStorage.getReferenceFromUrl("gs://ratemystyle-99fce.appspot.com");
        StorageReference profilePicRef = storageRef.child("profilePics/" + uid + ".jpg");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        profilePicRef.putBytes(data);
    }

    public interface OnImageSavedListener {
        void onImageSaved(String url);
    }
}
