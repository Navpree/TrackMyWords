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
public class InvalidAdminInsertQueryTests {

    private String type, response, query;
    private int code;

    public InvalidAdminInsertQueryTests(String type, int code, String response, String query){
        this.type = type;
        this.response = response;
        this.query = query;
        this.code = code;
    }

    @Parameters
    public static List<Object[]> data(){
        Object[][] data = new Object[][]{{"GET", 400, Constants.AdminInsertErrors.ONLY_POST_ACCEPTED, ""},
                {"POST", 400, Constants.AdminInsertErrors.TITLE_KEY_NOT_FOUND, "test=test"},
                {"POST", 415, "", ""},
                {"POST", 400, Constants.AdminInsertErrors.LYRICS_KEY_NOT_FOUND, "title=test"},
                {"POST", 400, Constants.AdminInsertErrors.INVALID_DATE_FORMAT, "title=test&lyrics=test&release_date=test"},
                {"POST", 400, Constants.AdminInsertErrors.ALBUM_KEY_NOT_FOUND, "title=test&lyrics=test&release_date=12-12-12"}};
        return Arrays.asList(data);
    }

    @Test
    public void queryTest(){
        System.out.println(String.format("---Starting Test %s, '%s'---", type, query));
        try {
            HttpURLConnection con = TestHelper.createConnection(Constants.BASE_ADMIN_URL + "insert", type);
            if(!query.equals("")){
                TestHelper.writePostParameters(con, query);
            }
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
