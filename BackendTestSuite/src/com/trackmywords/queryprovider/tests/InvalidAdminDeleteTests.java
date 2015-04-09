package com.trackmywords.queryprovider.tests;

import com.trackmywords.testdata.Constants;
import com.trackmywords.testdata.TestHelper;
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

    public InvalidAdminDeleteTests(String id, String method, String response){
        this.id = id;
        this.method = method;
        this.response = response;
    }

    @Parameters
    public List<Object[]> data() {
        Object[][] data = new Object[][]{{""}};
        return Arrays.asList(data);
    }

    @Test
    public void queryTest() throws IOException {
        HttpURLConnection con = TestHelper.createConnection(Constants.BASE_ADMIN_URL + "delete");

    }

}
