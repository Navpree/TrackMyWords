package com.trackmywords.testdata;

public class Constants {

    public static final String BASE_USER_URL = "http://backend-andrewsstuff.rhcloud.com/query";
    public static final String BASE_ADMIN_URL = "http://backend-andrewsstuff.rhcloud.com/admin/";

    public static class UserErrors {
        public static final String NO_TYPE_PARAMETER_RESPONSE = "{error: \"A non blank or whitespace query parameter |type| expected.\"}";
        public static final String NO_QUERY_PARAMETER_RESPONSE = "{error: \"A non blank or whitespace query parameter |query| expected.\"}";
    }

    public static class AdminViewErrors {
        public static final String NO_VALID_SEARCH_TITLE = "{error: \"No valid title parameter was provided in request.\"}";
        public static final String NO_VALID_PAGE_PARAM = "{error: \"Please provide a page number as a path parameter.\"}";
        public static final String NO_VALID_SORT_PARAM = "{error: \"No valid sort parameter was provided in request.\"}";
        public static final String EXCEPTION_AT_PAGE_0 = "{error: \"You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near '-25, 25' at line 1\"}";
    }

    public static class AdminUpdateErrors{
        public static final String NO_VALID_ID_PARAM = "{error: \"Please provide a song id as a path parameter.\"}";
        public static final String ONLY_POST_ACCEPTED = "{error: \"Only post requests are allowed for updating a song.\"}";
        public static final String NO_VALID_RELEASE_DATE = "{error: \"The provided releaseDate value is not a valid date for the format dd-MM-yyyy.\"}";
        public static final String NO_VALID_LYRICS_OR_TITLE = "{error:\"The provided title or lyric value is not a valid string value.\"}";
    }
}
