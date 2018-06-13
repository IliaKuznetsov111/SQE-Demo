package com.ikuznietsov.steps;

import io.restassured.response.ResponseBody;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;

import static com.ikuznietsov.enums.response.keys.NasaResponseKeys.NASA_RESPONSE_BODY;
import static com.ikuznietsov.enums.response.keys.NasaResponseKeys.NASA_RESPONSE_STATUS_CODE;
import static org.assertj.core.api.Assertions.assertThat;

public class VerificationSteps {

    private static final String ERROR_MESSAGE_TEMPLATE = "Incorrect status code, expected %s, but was %s";

    @Step("Verify that response status code equals to {0}")
    public void responseStatusCodeVerification(int statusCode) {
        int actualStatusCode = Serenity.sessionVariableCalled(NASA_RESPONSE_STATUS_CODE);
        assertThat(actualStatusCode)
                .as(ERROR_MESSAGE_TEMPLATE, statusCode, actualStatusCode)
                .isEqualTo(statusCode);
    }

    @Step("Verify that response body contains error code: {0}")
    public void responseBodyErrorCodeVerification(String errorCode) {
        ResponseBody responseBody = Serenity.sessionVariableCalled(NASA_RESPONSE_BODY);
        assertThat(responseBody.jsonPath().getString("error.code"))
                .isEqualTo(errorCode);
    }
}