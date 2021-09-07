package com.DailyNeeds.dailyneeds.FirebaseDB;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UserModel {



    public String name;
    public String receive;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public UserModel() {
    }

    public UserModel(String name, String receive) {
        this.name = name;
        this.receive = receive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReceive() {
        return receive;
    }

    public void setReceive(String receive) {
        this.receive = receive;
    }
}
