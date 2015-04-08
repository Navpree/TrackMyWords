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
public class InvalidAdminUpdateQueryTests {

    private int code;
    private String message, query, method, params;

    public InvalidAdminUpdateQueryTests(String query, String params, int code, String message, String method){
        this.code = code;
        this.message = message;
        this.query = query;
        this.method = method;
        this.params = params;
    }

    @Parameters
    public static List<Object[]> data(){
        Object[][] data = new Object[][]{{"/", "", 400, Constants.AdminUpdateErrors.NO_VALID_ID_PARAM, "GET"},
                {"/1", "", 400, Constants.AdminUpdateErrors.ONLY_POST_ACCEPTED, "GET"},
                {"/1", "", 400, Constants.AdminUpdateErrors.NO_VALID_RELEASE_DATE, "POST"},
                {"/1", "releaseDate=11-08-2013", 400, Constants.AdminUpdateErrors.NO_VALID_LYRICS_OR_TITLE, "POST"}};
        return Arrays.asList(data);
    }

    @Test
    public void updateQueryTest(){
        System.out.println(String.format("---Starting Test with Query: %s '%s'---", method, params));
        try{
            HttpURLConnection con = TestHelper.createConnection(Constants.BASE_ADMIN_URL + "update" + query, method);
            if(!params.equals("")){
                TestHelper.writePostParameters(con, params);
            }
            int code = con.getResponseCode();
            Assert.assertEquals(this.code, code);
            String response = TestHelper.getResponseString(con);
            Assert.assertEquals(message, response);
        }catch(IOException e){
            e.printStackTrace();
            Assert.fail("An error occurred during query test.");
        }
        System.out.println("---Finished Test---");
    }

}
