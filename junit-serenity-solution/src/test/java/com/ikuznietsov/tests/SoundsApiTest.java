package com.ikuznietsov.tests;

import com.ikuznietsov.client.NasaClient;
import com.ikuznietsov.steps.VerificationSteps;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

import static com.ikuznietsov.enums.query.parameters.NasaQueryParameters.API_KEY;
import static com.ikuznietsov.enums.query.parameters.NasaQueryParameters.FILTER;
import static com.ikuznietsov.enums.query.parameters.NasaQueryParameters.LIMIT;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;

@RunWith(SerenityRunner.class)
public class SoundsApiTest {

    @Steps
    private NasaClient nasaClient;

    @Steps
    private VerificationSteps verificationSteps;

    @Test
    @Title("Request sounds without query params")
    public void requestSoundsWithoutQueryParam() {
        nasaClient.getSoundsFromSpace(new HashMap<>(), false);
        verificationSteps.responseStatusCodeVerification(SC_FORBIDDEN);
        verificationSteps.responseBodyErrorCodeVerification("API_KEY_MISSING");
    }

    @Test
    @Title("Request sounds with filer and api key query params")
    public void requestSoundsWithFilerQueryParam() {
        Map queryParams = new HashMap(){{
            put(FILTER.getParameter(), "apolo");
        }};
        nasaClient.getSoundsFromSpace(queryParams, true);
        verificationSteps.responseStatusCodeVerification(SC_OK);
    }

    @Test
    @Title("Request sounds with limit and api key query params")
    public void requestSoundsWithLimitQueryParam() {
        Map queryParams = new HashMap(){{
            put(LIMIT.getParameter(), 5);
        }};
        nasaClient.getSoundsFromSpace(queryParams, true);
        verificationSteps.responseStatusCodeVerification(SC_OK);
    }

    @Test
    @Title("Request sounds with api key query param")
    public void requestSoundsWithApiKeyQueryParam() {
        nasaClient.getSoundsFromSpace(new HashMap<>(), true);
        verificationSteps.responseStatusCodeVerification(SC_OK);
    }

    @Test
    @Title("Request sounds with all available query params")
    public void requestSoundsWithAllAvailableQueryParams() {
        Map queryParams = new HashMap(){{
            put(FILTER.getParameter(), "apolo");
            put(LIMIT.getParameter(), 5);
        }};
        nasaClient.getSoundsFromSpace(queryParams, true);
        verificationSteps.responseStatusCodeVerification(SC_OK);
    }

    @Test
    @Title("Request sounds with negative limit query param")
    public void requestSoundsWithNegativeLimitQueryParam() {
        Map queryParams = new HashMap(){{
            put(LIMIT.getParameter(), -10);
        }};
        nasaClient.getSoundsFromSpace(queryParams, true);
        verificationSteps.responseStatusCodeVerification(SC_OK);
    }

    @Test
    @Title("Request sounds incorrect api key query param")
    public void requestSoundsIncorrectApiKeyQueryParam() {
        Map queryParams = new HashMap(){{
            put(API_KEY.getParameter(), "test");
        }};
        nasaClient.getSoundsFromSpace(queryParams, false);
        verificationSteps.responseStatusCodeVerification(SC_FORBIDDEN);
        verificationSteps.responseBodyErrorCodeVerification("API_KEY_INVALID");
    }

    @Test
    @Title("Request sounds with special symbols in filer query param")
    public void requestSoundsWithSpecialSymbolsInFilerQueryParam() {
        Map queryParams = new HashMap(){{
            put(FILTER.getParameter(), "?#%!@&*");
        }};
        nasaClient.getSoundsFromSpace(queryParams, true);
        verificationSteps.responseStatusCodeVerification(SC_OK);
    }

    @Test
    @Title("Request sounds with uppercase symbols in filer query param")
    public void requestSoundsWithUppercaseSymbolsInFilerQueryParam() {
        Map queryParams = new HashMap(){{
            put(FILTER.getParameter(), "ApoLo");
        }};
        nasaClient.getSoundsFromSpace(queryParams, true);
        verificationSteps.responseStatusCodeVerification(SC_OK);
    }

    @Test
    @Title("Request sounds with non existence filer query param")
    public void requestSoundsWithNonExistenceFilerQueryParam() {
        Map queryParams = new HashMap(){{
            put(FILTER.getParameter(), "test123");
        }};
        nasaClient.getSoundsFromSpace(queryParams, true);
        verificationSteps.responseStatusCodeVerification(SC_OK);
    }

    @Test
    @Title("Request sounds with null values in query params")
    public void requestSoundsWithNullQueryParams() {
        Map queryParams = new HashMap(){{
            put(FILTER.getParameter(), "null");
            put(LIMIT.getParameter(), "null");
        }};
        nasaClient.getSoundsFromSpace(queryParams, true);
        verificationSteps.responseStatusCodeVerification(SC_OK);
    }
}