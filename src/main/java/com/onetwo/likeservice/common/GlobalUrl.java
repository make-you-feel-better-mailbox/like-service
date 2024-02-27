package com.onetwo.likeservice.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GlobalUrl {

    public static final String ROOT_URI = "/like-service";
    public static final String UNDER_ROUTE = "/*";
    public static final String EVERY_UNDER_ROUTE = "/**";
    public static final String LIKE_ROOT = ROOT_URI + "/likes";
    public static final String LIKE_COUNT = LIKE_ROOT + "/counts";
    public static final String LIKE_FILTER = LIKE_ROOT + "/filter";

    public static final String PATH_VARIABLE_CATEGORY = "category";
    public static final String PATH_VARIABLE_CATEGORY_WITH_BRACE = "/{" + PATH_VARIABLE_CATEGORY + "}";

    public static final String PATH_VARIABLE_TARGET_ID = "target-id";
    public static final String PATH_VARIABLE_TARGET_ID_WITH_BRACE = "/{" + PATH_VARIABLE_TARGET_ID + "}";
}
