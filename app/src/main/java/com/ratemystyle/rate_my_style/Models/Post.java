package com.ratemystyle.rate_my_style.Models;

import java.util.List;

/**
 * Created by Maarten Peels on 10/20/2016.
 */

public class Post implements Comparable<Post> {
    public String uid;
    public List<String> images;
    public String status;
    public String timeStamp;
    public String url;

    public Post() {
    }

    public Post(String uid, List<String> images, String status, String timeStamp, String url) {
        this.uid = uid;
        this.images = images;
        this.status = status;
        this.timeStamp = timeStamp;
        this.url = url;
    }

    @Override
    public int compareTo(Post o) {
        if (Long.parseLong(this.timeStamp) > Long.parseLong(o.timeStamp))//This is newer
        {
            return -1;
        } else if (Long.parseLong(this.timeStamp) < Long.parseLong(timeStamp))//This is older
        {
            return 1;
        } else {
            return 0;
        }
    }
}
