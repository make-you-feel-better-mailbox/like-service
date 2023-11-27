package com.onetwo.likeservice.common;

public class GlobalUrl {

    public static final String ROOT_URI = "/";
    public static final String UNDER_ROUTE = "/*";
    public static final String EVERY_UNDER_ROUTE = "/**";
    public static final String LIKE_ROOT = "/likes";
    public static final String LIKE_FILTER = LIKE_ROOT + "/filter";

    public static final String PATH_VARIABLE_CATEGORY = "category";
    public static final String PATH_VARIABLE_CATEGORY_WITH_BRACE = "/{" + PATH_VARIABLE_CATEGORY + "}";

    public static final String PATH_VARIABLE_TARGET_ID = "target-id";
    public static final String PATH_VARIABLE_TARGET_ID_WITH_BRACE = "/{" + PATH_VARIABLE_TARGET_ID + "}";
}
