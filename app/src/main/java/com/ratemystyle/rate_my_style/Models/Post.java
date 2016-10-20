package com.ratemystyle.rate_my_style.Models;

import java.util.List;

/**
 * Created by Maarten Peels on 10/20/2016.
 */

public class Post {
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
}
