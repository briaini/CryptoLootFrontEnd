package com.universityoflimerick.cryptolootfrontend.Model.User;

public class ProfilePage {
    private String name;

    public ProfilePage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
