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
public class InvalidUserQueryTests {

    private String query, message;
    private int status;

    public InvalidUserQueryTests(String query, int status, String message) {
        this.query = query;
        this.status = status;
        this.message = message;
    }

    @Parameters
    public static List<Object[]> data() {
        Object[][] data = new Object[][]{{"", 400, Constants.UserErrors.NO_TYPE_PARAMETER_RESPONSE},
                {"?type=song", 400, Constants.UserErrors.NO_QUERY_PARAMETER_RESPONSE},
                {"?query=monkey", 400, Constants.UserErrors.NO_TYPE_PARAMETER_RESPONSE}};
        return Arrays.asList(data);
    }

    @Test
    public void parameterTest() {
        System.out.println(String.format("---Running Test with Parameters \"%s\"---", query));
        try {
            HttpURLConnection con = TestHelper.createConnection(Constants.BASE_USER_URL + query);
            int status = con.getResponseCode();
            Assert.assertEquals(this.status, status);
            String response = TestHelper.getResponseString(con);
            Assert.assertEquals(message, response);
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("Exception was thrown during test.");
        }
        System.out.println("---Finished Test---");
    }

}
