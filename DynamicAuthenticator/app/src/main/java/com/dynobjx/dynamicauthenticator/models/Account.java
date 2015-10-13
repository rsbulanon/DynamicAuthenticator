package com.dynobjx.dynamicauthenticator.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by madeveloper on 10/13/15.
 */
public class Account extends RealmObject {

    @PrimaryKey
    private String email;
    private long timeAdded;

    public Account() {}

    public long getTimeAdded() {
        return timeAdded;
    }

    public void setTimeAdded(long timeAdded) {
        this.timeAdded = timeAdded;
    }

    public Account(String email, long timeAdded) {
        this.email = email;
        this.timeAdded = timeAdded;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
