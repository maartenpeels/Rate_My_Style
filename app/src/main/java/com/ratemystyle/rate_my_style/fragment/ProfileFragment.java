package com.ratemystyle.rate_my_style.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ratemystyle.rate_my_style.CreateProfileActivity;
import com.ratemystyle.rate_my_style.Database;
import com.ratemystyle.rate_my_style.Models.Profile;
import com.ratemystyle.rate_my_style.R;

/**
 * Created by Jean Paul on 13-10-2016.
 */

public class ProfileFragment extends Fragment {
    static final int USER_CREATED_PROFILE = 101;
    private static final String TAG = "ProfileFragment";
    private TextView fName;
    private TextView lName;
    private TextView age;
    private ImageView imageView;

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

        checkProfile();

        return rootView;
    }

    void checkProfile() {
        Database.getDatabaseReference().child("profiles").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    startActivityForResult(new Intent(getView().getContext(), CreateProfileActivity.class), USER_CREATED_PROFILE);
                } else {
                    startListening();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == USER_CREATED_PROFILE && resultCode == Activity.RESULT_OK) {
            startListening();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void startListening() {
        ///Testing realtime db
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        ((TextView) getView().findViewById(R.id.tb_uid)).setText(uid);
        ((TextView) getView().findViewById(R.id.tb_email)).setText(email);

        fName = (TextView) getView().findViewById(R.id.tb_fname);
        lName = (TextView) getView().findViewById(R.id.tb_lname);
        age = (TextView) getView().findViewById(R.id.tb_age);

        imageView = (ImageView) getView().findViewById(R.id.img_pf);

        DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference().child("profiles").child(uid);
        StorageReference profilePicRef = FirebaseStorage.getInstance().getReference().child("profilePics");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Profile profile = dataSnapshot.getValue(Profile.class);

                fName.setText(profile.firstName);
                lName.setText(profile.lastName);
                age.setText(profile.age + "");
                Toast.makeText(getContext(), "User change: " + profile.firstName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadProfile:onCancelled", databaseError.toException());
            }
        };

        profilePicRef.child(uid + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                imageView.setImageURI(uri);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.w(TAG, "loadImage:onFailure", exception);
            }
        });

        mPostReference.addValueEventListener(postListener);
        //end testing realtime db
    }
}
