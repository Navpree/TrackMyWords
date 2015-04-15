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
public class InvalidAdminViewQueryTests {

    private String query, message;
    private int code;

    public InvalidAdminViewQueryTests(String query, int code, String message) {
        this.query = query;
        this.code = code;
        this.message = message;
    }

    @Parameters
    public static List<Object[]> data() {
        Object[][] data = new Object[][]{{"/", 400, Constants.AdminViewErrors.NO_VALID_PAGE_PARAM},
                {"/1?title=monkey", 400, Constants.AdminViewErrors.NO_VALID_SORT_PARAM},
                {"/1", 400, Constants.AdminViewErrors.NO_VALID_SORT_PARAM}};
        return Arrays.asList(data);
    }

    @Test
    public void adminQueryTest() {
        System.out.println(String.format("---Starting Query with \"%s\"---", query));
        try {
            HttpURLConnection con = TestHelper.createConnection(Constants.BASE_ADMIN_URL + "view" + query);
            int code = con.getResponseCode();
            Assert.assertEquals(this.code, code);
            String response = TestHelper.getResponseString(con);
            Assert.assertEquals(message, response);
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("");
        }
        System.out.println("---Finished Test---");
    }
}
