package com.ikuznietsov.tests;

import com.ikuznietsov.client.NasaClient;
import com.ikuznietsov.steps.VerificationSteps;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static com.ikuznietsov.enums.query.parameters.NasaQueryParameters.API_KEY;
import static com.ikuznietsov.enums.query.parameters.NasaQueryParameters.FILTER;
import static com.ikuznietsov.enums.query.parameters.NasaQueryParameters.LIMIT;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;

@Test
@ContextConfiguration(locations = { "classpath:spring-test-config.xml" })
public class SoundsApiTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private NasaClient nasaClient;

    @Autowired
    private VerificationSteps verificationSteps;


    @DataProvider(name = "positive test data for sound request verification")
    public static Object[][] positiveTestData() {
        return new Object[][] {
                { new HashMap(){{put(FILTER.getParameter(), "apolo");}}},
                { new HashMap(){{put(LIMIT.getParameter(), 5);}}},
                { new HashMap()},
                { new HashMap(){{put(FILTER.getParameter(), "apolo"); put(LIMIT.getParameter(), 5);}}},
                { new HashMap(){{put(LIMIT.getParameter(), -10);}}},
                { new HashMap(){{put(FILTER.getParameter(), "?#%!@&*");}}},
                { new HashMap(){{put(FILTER.getParameter(), "ApoLo");}}},
                { new HashMap(){{put(FILTER.getParameter(), "test123");}}},
                { new HashMap(){{put(FILTER.getParameter(), "null"); put(LIMIT.getParameter(), "null");}}}
        };
    }

    @DataProvider(name = "negative test data for sound request verification")
    public static Object[][] negativeTestData() {
        return new Object[][] {
                { new HashMap<>(), "API_KEY_MISSING", false },
                { new HashMap(){{put(API_KEY.getParameter(), "test");}}, "API_KEY_INVALID", false}
        };
    }

    @Test(dataProvider = "positive test data for sound request verification")
    public void requestSoundsSuccessScenarios(Map queryParams) {
        Response response = nasaClient.getSoundsFromSpace(queryParams, true);
        verificationSteps.responseStatusCodeVerification(SC_OK, response.getStatusCode());
    }

    @Test(dataProvider = "negative test data for sound request verification")
    public void requestSoundsNegativeScenarios(Map queryParams, String exptectedBodyErrorCode, boolean includeDefaultApiKey) {
        Response response = nasaClient.getSoundsFromSpace(queryParams, includeDefaultApiKey);
        verificationSteps.responseStatusCodeVerification(SC_FORBIDDEN, response.getStatusCode());
        verificationSteps.responseBodyErrorCodeVerification(exptectedBodyErrorCode, response);
    }
}