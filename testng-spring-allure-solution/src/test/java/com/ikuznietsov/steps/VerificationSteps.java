package com.ikuznietsov.steps;

import io.qameta.allure.Step;
import io.restassured.response.ResponseBody;
import org.springframework.stereotype.Service;

import static org.assertj.core.api.Assertions.assertThat;

@Service
public class VerificationSteps {

    private static final String ERROR_MESSAGE_TEMPLATE = "Incorrect status code, expected %s, but was %s";

    @Step("Verify that response status code equals to {expectedStatusCode}")
    public void responseStatusCodeVerification(int expectedStatusCode, int actualStatusCode) {
        assertThat(actualStatusCode)
                .as(ERROR_MESSAGE_TEMPLATE, expectedStatusCode, actualStatusCode)
                .isEqualTo(expectedStatusCode);
    }

    @Step("Verify that response body contains error code: {errorCode}")
    public void responseBodyErrorCodeVerification(String errorCode, ResponseBody responseBody) {
        assertThat(responseBody.jsonPath().getString("error.code"))
                .isEqualTo(errorCode);
    }
}