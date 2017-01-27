package controllers;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import dao.HangmanUserDao;
import models.HangmanUser;
import ninja.NinjaTest;
import org.junit.Before;
import org.junit.Test;
import services.AuthenticationService;

import java.util.Map;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by dillon on 2017/01/26.
 */
public class AuthenticationControllerTest extends NinjaTest {
    @Before
    public void setupTest() {
        // Register user
        Map<String, String> headers = Maps.newHashMap();

        Map<String, String> formParameters = Maps.newHashMap();
        formParameters.put("username", "Jim");
        formParameters.put("password", "123");

        ninjaTestBrowser.makePostRequestWithFormParameters(getServerAddress()
                + "register", headers, formParameters);
    }

    @Test
    public void testLogin() {
        Map<String, String> headers = Maps.newHashMap();

        // Login
        Map<String, String> formParameters = Maps.newHashMap();
        formParameters.put("username", "Jim");
        formParameters.put("password", "123");

        String result = ninjaTestBrowser.makePostRequestWithFormParameters(getServerAddress()
                + "login", headers, formParameters);

        // Logout
        ninjaTestBrowser.makeRequest(getServerAddress() + "logout", headers);

        assertTrue(result.contains("Login successful."));
    }
}
