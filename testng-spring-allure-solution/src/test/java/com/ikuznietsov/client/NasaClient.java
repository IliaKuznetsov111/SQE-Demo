package com.ikuznietsov.client;

import com.google.common.collect.ImmutableMap;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.ikuznietsov.enums.properties.NasaProperties.NASA_API_KEY;
import static com.ikuznietsov.enums.properties.NasaProperties.NASA_URL;
import static com.ikuznietsov.enums.query.parameters.NasaQueryParameters.API_KEY;

@Service
public class NasaClient {

    @Step("Request sounds from space")
    public Response getSoundsFromSpace(Map<String, ?> queryParams, boolean includeDefaultApiKey) {
        ImmutableMap.Builder params = ImmutableMap.builder()
                .putAll(queryParams);
        if (includeDefaultApiKey) {
            params.put(API_KEY.getParameter(), NASA_API_KEY.getValue());
        }
        return RestAssured.given()
                .baseUri(NASA_URL.getValue())
                .queryParams(params.build())
                .log().uri()
                .when().get("planetary/sounds")
                .then().extract().response();
    }
}