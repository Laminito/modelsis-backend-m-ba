package sn.modelsisbackendmba.util;

public class Constants {

    //Generator Password
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 8;

    //Statut

    public static final int STATUS_VALUE_OK = 200;
    public static final int STATUS_VALUE_CREATED = 201;
    public static final int STATUS_VALUE_BAD_REQUEST = 400;
    public static final int STATUS_VALUE_UNAUTHORIZED = 401;
    public static final int STATUS_VALUE_NOT_FOUND = 404;
    public static final int STATUS_VALUE_INTERNAL_SERVER_ERROR = 500;
    public static final int STATUS_VALUE_CONFLICT = 409;

    //Message

    public static final String STATUS_MESSAGE_SUCCESS_BODY = "Success";
    public static final String STATUS_MESSAGE_NOT_FOUND_BODY = "Not Found";
    public static final String STATUS_MESSAGE_UNAUTHORIZED_BODY = "Unauthorized";
    public static final String STATUS_MESSAGE_USER_CREATED_BODY = "Created successfully";
    public static final String STATUS_MESSAGE_BAD_REQUEST_BODY = "Invalid input data";
    public static final String STATUS_MESSAGE_SERVER_ERROR_BODY = "Internal server error";
    public static final String STATUS_MESSAGE_CONFLICT_BODY = "Conflict";

    //Attributes
    public static String CODE = "";
    public static String MESSAGE = "";
    public static String BODY = "";

    public static final String SECRET_KEY = "hsvdfzgtfczqhdjqsbdzhegJHQGJYF465ftzaydszhgdvsqjsibuUUHJZQBDJSQBJHSDVJHCSVjhdfvhejsgckjsejhdschsdv";
}
