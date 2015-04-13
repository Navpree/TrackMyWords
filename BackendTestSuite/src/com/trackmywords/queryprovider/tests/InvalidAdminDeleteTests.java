package com.trackmywords.queryprovider.tests;

import com.trackmywords.testdata.Constants;
import com.trackmywords.testdata.TestHelper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
public class InvalidAdminDeleteTests {

    private String id, method, response;
    private int code;

    public InvalidAdminDeleteTests(String id, String method, String response, int code){
        this.id = id;
        this.method = method;
        this.response = response;
        this.code = code;
    }

    @Parameters
    public static List<Object[]> data() {
        Object[][] data = new Object[][]{
                {"a", "GET", Constants.AdminDeleteErrors.INVALID_ID_FORMAT, 400},
                {"a", "POST", Constants.AdminDeleteErrors.ONLY_GET_ACCEPTED, 400},
                {"", "GET", Constants.AdminDeleteErrors.NO_ID_PROVIDED, 400}
        };
        return Arrays.asList(data);
    }

    @Test
    public void queryTest() throws IOException {
        System.out.println(String.format("---Starting Test %s, '%s'---", method, id));
        try {
            HttpURLConnection con = TestHelper.createConnection(Constants.BASE_ADMIN_URL + "delete/" + id, method);
            int code = con.getResponseCode();
            Assert.assertEquals(this.code, code);
            String response = TestHelper.getResponseString(con);
            Assert.assertEquals(this.response, response);
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("An error occurred during test.");
        }
        System.out.println("---Finished Test---");
    }

}
