package com.ratemystyle.rate_my_style.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ratemystyle.rate_my_style.FeedListAdapter;
import com.ratemystyle.rate_my_style.Models.Post;
import com.ratemystyle.rate_my_style.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Jean Paul on 29-9-2016.
 */

public class MainFragment extends Fragment {
    private ListView listView;
    private FeedListAdapter listAdapter;
    private HashSet<Post> posts;

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

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_main, container, false);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        listView = (ListView) getView().findViewById(R.id.listFeed);

        posts = new HashSet<>();

        final DatabaseReference mFeedReference = FirebaseDatabase.getInstance().getReference().child("posts");
        ChildEventListener childListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Post p = dataSnapshot.getValue(Post.class);

                posts.add(p);
                updateList();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mFeedReference.addChildEventListener(childListener);
    }

    private void updateList() {
        List<Post> p = new ArrayList<>(posts);
        Collections.sort(p);

        listAdapter = new FeedListAdapter(getActivity(), p);
        listView.setAdapter(listAdapter);
    }
}

