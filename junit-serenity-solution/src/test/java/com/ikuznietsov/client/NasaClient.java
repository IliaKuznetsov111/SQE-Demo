package com.ikuznietsov.client;

import com.google.common.collect.ImmutableMap;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.Map;

import static com.ikuznietsov.enums.properties.NasaProperties.NASA_API_KEY;
import static com.ikuznietsov.enums.properties.NasaProperties.NASA_URL;
import static com.ikuznietsov.enums.query.parameters.NasaQueryParameters.API_KEY;
import static com.ikuznietsov.enums.response.keys.NasaResponseKeys.NASA_RESPONSE_BODY;
import static com.ikuznietsov.enums.response.keys.NasaResponseKeys.NASA_RESPONSE_STATUS_CODE;

public class NasaClient {

    @Step("Request sounds from space")
    public void getSoundsFromSpace(Map<String, ?> queryParams, boolean includeDefaultApiKey) {
        ImmutableMap.Builder params = ImmutableMap.builder()
                .putAll(queryParams);
        if (includeDefaultApiKey) {
            params.put(API_KEY.getParameter(), NASA_API_KEY.getValue());
        }
        Response response = SerenityRest.given()
                .baseUri(NASA_URL.getValue())
                .queryParams(params.build())
                .log().uri()
                .when().get("planetary/sounds")
                .then().extract().response();
        Serenity.setSessionVariable(NASA_RESPONSE_STATUS_CODE).to(response.getStatusCode());
        Serenity.setSessionVariable(NASA_RESPONSE_BODY).to(response.getBody());
    }
}