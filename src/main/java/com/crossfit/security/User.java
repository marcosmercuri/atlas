package com.crossfit.security;

/**
 * Very basic, almost dummy, logged in user class.
 */
class User {
    private String name;

    public User() {
    }

    User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
