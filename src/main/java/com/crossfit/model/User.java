package com.crossfit.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    private final String username;
    private final String providerId;
    private final String providerUserId;
    private final String name;
    private final String lastName;
    private final String imageUrl;

    private User() {
        this(null, null, null, null, null, null);
    }

    public User(String username, String providerId, String providerUserId, String name, String lastName, String imageUrl) {
        this.username = username;
        this.providerId = providerId;
        this.providerUserId = providerUserId;
        this.name = name;
        this.lastName = lastName;
        this.imageUrl = imageUrl;
    }

    /**
     * @return the provider of the social profile (ie, twitter, facebook, etc)
     */
    public String getProviderId() {
        return providerId;
    }

    /**
     * @return the id of the user in the provider's system.
     */
    public String getProviderUserId() {
        return providerUserId;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        User user = (User)o;

        if(username != null? !username.equals(user.username) : user.username != null) return false;
        if(providerId != null? !providerId.equals(user.providerId) : user.providerId != null) return false;
        if(providerUserId != null? !providerUserId.equals(user.providerUserId) : user.providerUserId != null)
            return false;
        if(name != null? !name.equals(user.name) : user.name != null) return false;
        if(lastName != null? !lastName.equals(user.lastName) : user.lastName != null) return false;
        return imageUrl != null? imageUrl.equals(user.imageUrl) : user.imageUrl == null;
    }

    @Override
    public int hashCode() {
        int result = username != null? username.hashCode() : 0;
        result = 31 * result + (providerId != null? providerId.hashCode() : 0);
        result = 31 * result + (providerUserId != null? providerUserId.hashCode() : 0);
        result = 31 * result + (name != null? name.hashCode() : 0);
        result = 31 * result + (lastName != null? lastName.hashCode() : 0);
        result = 31 * result + (imageUrl != null? imageUrl.hashCode() : 0);
        return result;
    }
}
