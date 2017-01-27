package controllers;

import ninja.NinjaTest;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by dillon on 2017/01/26.
 */
public class ApplicationControllerTest extends NinjaTest {
    @Test
    public void testThatIndexPageWorks() {
        String result = ninjaTestBrowser.makeRequest(getServerAddress() + "/");

        assertTrue(result.contains("page"));
    }
}
