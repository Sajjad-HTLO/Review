package com.sajad.demo.utility;

public class Constants {

    public static class UrlMappings {
        public static final String API_PREFIX = "/api";

        public static final String PRODUCT_API = API_PREFIX + "/products";

        public static final String COMMENTS_API = API_PREFIX + "/comments";

        public static final String VOTES_API = API_PREFIX + "/votes";
    }

    public static class ErrorMessages {
        public static final String COMMENT_NOT_ALLOWED_MSG = "You can't comment on this product";

        public static final String VOTE_NOT_ALLOWED_MSG = "You can't vote to this product";
    }
}
