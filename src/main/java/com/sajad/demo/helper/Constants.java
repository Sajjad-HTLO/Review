package com.sajad.demo.helper;

/**
 * Contains constants used across the project.
 */
public class Constants {

    public static class UrlMappings {
        public static final String API_PREFIX = "/api";

        public static final String PRODUCT_API = API_PREFIX + "/products";

        public static final String COMMENTS_API = API_PREFIX + "/comments";

        public static final String RATES_API = API_PREFIX + "/rates";
    }

    public static class ErrorMessages {
        public static final String COMMENT_NOT_ALLOWED_MSG = "You can't comment on this product";

        public static final String RATE_NOT_ALLOWED_MSG = "You can't rate to this product";

        public static final String ENTITY_NOT_FOUND_MSG = "Entity not found";
    }

    public static class KafkaTopics {
        public static final String KAFKA_NEW_COMMENT_TOPIC = "-comments";
    }
}
