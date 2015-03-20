package com.trackmywords.queryprovider.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackmywords.testdata.Constants;
import com.trackmywords.testdata.SongSetWithCount;
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
public class AdminQueryTests {

    private String query;
    private int count;

    public AdminQueryTests(String query, int count){
        this.query = query;
        this.count = count;
    }

    @Parameters
    public static List<Object[]> data(){
        Object[][] data = new Object[][]{{"/0?title=monkey&sort=title", 1}};
        return Arrays.asList(data);
    }

    @Test
    public void queryTest(){
        System.out.println(String.format("---Starting Query with \"%s\"---", query));
        try {
            HttpURLConnection con = TestHelper.createConnection(Constants.BASE_ADMIN_URL + "view" + query);
            int code = con.getResponseCode();
            Assert.assertEquals(200, code);
            String response = TestHelper.getResponseString(con);

            ObjectMapper mapper = new ObjectMapper();
            SongSetWithCount set = mapper.readValue(response, SongSetWithCount.class);
            Assert.assertNotNull(set);
            Assert.assertNotEquals(0, set.getSongs().size());
            Assert.assertEquals(count, set.getPages());
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("");
        }
        System.out.println("---Finished Test---");
    }

}
