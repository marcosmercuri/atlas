package com.crossfit.util;

public class RequestErrorCodes {
    public static final int PROPOSED_WORKOUT_NOT_FOUND = 40401;
    public static final int RESULT_WORKOUT_NOT_FOUND = 40402;
    public static final int USER_NOT_FOUND = 40403;
    public static final int INVALID_FIELDS_IN_REQUEST_ERROR_CODE = 40001;
    public static final int CANNOT_CHANGE_FIELD = 40002;
    public static final int CANNOT_DELETE_PROPOSED_WORKOUT = 40003;
    public static final int DUPLICATED_USERNAME = 40004;
    public static final int NO_LOGGED_USER = 40005;

    private RequestErrorCodes() {};
}
