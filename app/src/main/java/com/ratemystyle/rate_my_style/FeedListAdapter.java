package com.ratemystyle.rate_my_style;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ratemystyle.rate_my_style.Models.Post;
import com.ratemystyle.rate_my_style.control.FeedImageView;

import java.util.List;

/**
 * Created by Maarten Peels on 10/20/2016.
 */

public class FeedListAdapter extends BaseAdapter {
    ImageLoader imageLoader = FeedController.getInstance().getImageLoader();
    private Activity activity;
    private LayoutInflater inflater;
    private List<Post> feedItems;

    public FeedListAdapter(Activity activity, List<Post> feedItems) {
        this.activity = activity;
        this.feedItems = feedItems;
    }

    @Override
    public int getCount() {
        return feedItems.size();
    }

    @Override
    public Object getItem(int location) {
        return feedItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.feed_item, null);

        if (imageLoader == null)
            imageLoader = FeedController.getInstance().getImageLoader();

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView timestamp = (TextView) convertView
                .findViewById(R.id.timestamp);
        TextView statusMsg = (TextView) convertView
                .findViewById(R.id.txtStatusMsg);
        TextView url = (TextView) convertView.findViewById(R.id.txtUrl);
        NetworkImageView profilePic = (NetworkImageView) convertView
                .findViewById(R.id.profilePic);
        FeedImageView feedImageView = (FeedImageView) convertView
                .findViewById(R.id.feedImage1);

        Post item = feedItems.get(position);

        String n = Database.getInstance().getLoggedInProfile().firstName + " " + Database.getInstance().getLoggedInProfile().lastName;
        name.setText(n);

        // Converting timestamp into x ago format
        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                Long.parseLong(item.timeStamp),
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        timestamp.setText(timeAgo);

        // Chcek for empty status message
        if (!TextUtils.isEmpty(item.status)) {
            statusMsg.setText(item.status);
            statusMsg.setVisibility(View.VISIBLE);
        } else {
            // status is empty, remove from view
            statusMsg.setVisibility(View.GONE);
        }

        // Checking for null feed url
        if (item != null) {
            url.setText(Html.fromHtml("<a href=\"" + item.url + "\">"
                    + item.url + "</a> "));

            // Making url clickable
            url.setMovementMethod(LinkMovementMethod.getInstance());
            url.setVisibility(View.VISIBLE);
        } else {
            // url is null, remove from the view
            url.setVisibility(View.GONE);
        }

        // user profile pic
        final String[] profilePicURI = {""};
        StorageReference profilePicRef = FirebaseStorage.getInstance().getReference().child("profilePics");
        profilePicRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid() + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                profilePicURI[0] = uri.toString();
            }
        });
        profilePic.setImageUrl(profilePicURI[0], imageLoader);

        // Feed image
        if (item.images.size() > 0) {
            feedImageView.setImageUrl(item.images.get(0), imageLoader);
            feedImageView.setVisibility(View.VISIBLE);
        } else {
            feedImageView.setVisibility(View.GONE);
        }

        return convertView;
    }
}
