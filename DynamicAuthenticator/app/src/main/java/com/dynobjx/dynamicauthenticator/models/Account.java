package com.dynobjx.dynamicauthenticator.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by madeveloper on 10/13/15.
 */
public class Account extends RealmObject {

    @PrimaryKey
    private String email;

    public Account() {}

    public Account(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
