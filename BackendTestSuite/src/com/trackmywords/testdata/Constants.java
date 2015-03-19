package com.trackmywords.testdata;

public class Constants {

    public static final String BASE_USER_URL = "http://backend-andrewsstuff.rhcloud.com/query";
    public static final String BASE_ADMIN_URL = "http://backend-andrewsstuff.rhcloud.com/admin/";

    public static final String NO_TYPE_PARAMETER_RESPONSE = "{error: \"A non blank or whitespace query parameter |type| expected.\"}";
    public static final String NO_QUERY_PARAMETER_RESPONSE = "{error: \"A non blank or whitespace query parameter |query| expected.\"}";

    public static final String NO_VALID_SEARCH_TITLE = "{error: \"No valid title parameter was provided in request.\"}";
    public static final String NO_VALID_PAGE_PARAM = "{error: \"No valid path param was provided in request.\"}";
    public static final String NO_VALID_SORT_PARAM = "{error: \"No valid sort parameter was provided in request.\"}";

}
