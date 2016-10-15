package com.ratemystyle.rate_my_style.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
    private TextView name;
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
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        ((TextView) getView().findViewById(R.id.tb_email)).setText(email);

        name = (TextView) getView().findViewById(R.id.tb_name);
        imageView = (ImageView) getView().findViewById(R.id.profilePic);

        DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference().child("profiles").child(uid);
        StorageReference profilePicRef = FirebaseStorage.getInstance().getReference().child("profilePics");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Profile profile = dataSnapshot.getValue(Profile.class);

                String nameS = profile.firstName + " " + profile.lastName + " - " + profile.age;
                name.setText(nameS);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadProfile:onCancelled", databaseError.toException());
            }
        };

        profilePicRef.child(uid + ".jpg").getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                imageView.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
            }
        });

        mPostReference.addValueEventListener(postListener);
    }
}
