package com.cognitive.face.recognition.api.data.model;

/**
 * Created by nellipc on 8/24/17.
 */

public class User {
    public String firstName;
    public String lastName;
    public String dob;
    public String email;

    public User(){

    }

    public User(String firstName, String lastName, String dob, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.email = email;

    }
}
