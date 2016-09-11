package com.crossfit.security;

import com.crossfit.model.User;

@FunctionalInterface
public interface LoggedInUserGetter {
    User getLoggedInUser();
}
