package com.trackmywords.testdata;

public class Constants {

    public static final String BASE_USER_URL = "http://backend-andrewsstuff.rhcloud.com/query";
    public static final String BASE_ADMIN_URL = "http://backend-andrewsstuff.rhcloud.com/admin/";

    public static class UserErrors {
        public static final String NO_TYPE_PARAMETER_RESPONSE = "{\"error\": \"A non blank or whitespace query parameter |type| expected.\"}";
        public static final String NO_QUERY_PARAMETER_RESPONSE = "{\"error\": \"A non blank or whitespace query parameter |query| expected.\"}";
    }

    public static class AdminViewErrors {
        public static final String NO_VALID_PAGE_PARAM = "{\"error\": \"Please provide a page number as a path parameter.\"}";
        public static final String NO_VALID_SORT_PARAM = "{\"error\": \"No valid sort parameter was provided in request.\"}";
    }

    public static class AdminUpdateErrors {
        public static final String NO_VALID_ID_PARAM = "{\"error\": \"Please provide a song id as a path parameter.\"}";
        public static final String ONLY_POST_ACCEPTED = "{\"error\": \"Only post requests are allowed for updating a song.\"}";
        public static final String NO_VALID_TITLE = "{\"error\":\"The provided title or lyric value is not a valid string value.\"}";
    }

    public static class AdminInsertErrors {
        public static final String ONLY_POST_ACCEPTED = "{\"error\": \"Only post requests are allowed at this point.\"}";
        public static final String TITLE_KEY_NOT_FOUND = "{\"error\": \"Required key not found: title\"}";
        public static final String LYRICS_KEY_NOT_FOUND = "{\"error\": \"Required key not found: lyrics\"}";
        public static final String INVALID_DATE_FORMAT = "{\"error\": \"The value 'release_date' is not valid for the value 'test'.\"}";
        public static final String ALBUM_KEY_NOT_FOUND = "{\"error\": \"Required key not found: album\"}";
    }

    public static class AdminDeleteErrors {
        public static final String INVALID_ID_FORMAT = "{\"error\": \"The provided id is not a valid id value.\"}";
        public static final String ONLY_GET_ACCEPTED = "{\"error\": \"Only get requests are allowed at this point.\"}";
        public static final String NO_ID_PROVIDED = "{\"error\": \"No id was provided as a path parameter in request.\"}";
    }
}
