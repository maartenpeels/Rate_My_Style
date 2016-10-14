package com.ratemystyle.rate_my_style.Models;

/**
 * Created by Maarten Peels on 10/14/2016.
 */

public class Profile {
    public String uid;
    public String firstName;
    public String lastName;
    public int age;

    public Profile() {
    }

    public Profile(String uid, String firstName, String lastName, int age) {
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }


}
