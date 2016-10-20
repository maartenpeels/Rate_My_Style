package com.ratemystyle.rate_my_style.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.ratemystyle.rate_my_style.FeedListAdapter;
import com.ratemystyle.rate_my_style.Models.Post;
import com.ratemystyle.rate_my_style.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Jean Paul on 29-9-2016.
 */

public class MainFragment extends Fragment {
    private ListView listView;
    private FeedListAdapter listAdapter;
    private List<Post> posts;

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

        posts = new ArrayList<Post>();



        DatabaseReference mFeedReference = FirebaseDatabase.getInstance().getReference().child("posts");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null)
                    return;

                GenericTypeIndicator<Map<String, Post>> t = new GenericTypeIndicator<Map<String, Post>>() {
                };
                Map<String, Post> userMap = dataSnapshot.getValue(t);
                for (Post post : userMap.values()) {
                    posts.add(post);
                }

                listAdapter = new FeedListAdapter(getActivity(), posts);
                listView.setAdapter(listAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mFeedReference.addValueEventListener(postListener);

    }
}

