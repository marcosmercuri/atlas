package com.crossfit.controller;

class ParametrizedTestData {
    private String invalidRequest;
    private String expectedErrorMessage;

    ParametrizedTestData(String invalidRequest, String expectedErrorMessage) {
        this.invalidRequest = invalidRequest;
        this.expectedErrorMessage = expectedErrorMessage;
    }

    public String getInvalidRequest() {
        return invalidRequest;
    }

    public String getExpectedErrorMessage() {
        return expectedErrorMessage;
    }
}
