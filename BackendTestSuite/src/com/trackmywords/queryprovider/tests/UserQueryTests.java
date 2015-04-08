package com.trackmywords.queryprovider.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackmywords.testdata.Constants;
import com.trackmywords.testdata.SongSet;
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
public class UserQueryTests {

    private String query;

    public UserQueryTests(String query) {
        this.query = query;
    }

    @Parameters
    public static List<Object[]> data(){
        Object[][] data = new Object[][]{ {"monkey"}, {"baby"} };
        return Arrays.asList(data);
    }

    @Test
    public void querySongWithTermTest() {
        System.out.println(String.format("---Starting Query with \"%s\"---", query));
        try {
            HttpURLConnection con = TestHelper.createConnection(Constants.BASE_USER_URL + "?type=song&query=" + query);
            int code = con.getResponseCode();
            Assert.assertEquals(200, code);
            String response = TestHelper.getResponseString(con);

            ObjectMapper mapper = new ObjectMapper();
            SongSet set = mapper.readValue(response, SongSet.class);
            Assert.assertNotNull(set);
            Assert.assertNotNull(set.getSongs());
            Assert.assertNotEquals(0, set.getSongs().size());
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("");
        }
        System.out.println("---Finished Test---");
    }

}
