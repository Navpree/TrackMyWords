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
public class InvalidAdminQueryTests {

    private String query;
    private int code;

    public InvalidAdminQueryTests(String query, int code) {
        this.query = query;
        this.code = code;
    }

    @Parameters
    public static List<Object[]> data() {
        Object[][] data = new Object[][]{{"/0", 400},
                {"/0?title=monkey", 400},
                {"/0?sort=title", 400}};
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
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("");
        }
        System.out.println("---Finished Test---");
    }
}
