package com.kowalczyk.iwill.model;

public class ConstanceNr {

    // STATUS
    public static int STATUS_VISIT = 1;
    public static int STATUS_RESERVATION = 2;
    public static int STATUS_PHONE = 3;
    public static int STATUS_EMAIL = 4;
    public static int STATUS_ADDRESS = 5;
    public static int STATUS_SERVICE_TYPE = 6;
    public static int STATUS_CANCELLED = 7;
    public static int STATUS_INACTIVE = 8;

    // NUMERATOR
    public static int NUMERATOR_VISIT = 1;
    public static int NUMERATOR_CLIENT = 2;

    // PROMOTION
    public static int PROMOTION_MIN_VALUE = 0;
    public static int PROMOTION_MAX_VALUE = 100;

    //
    public static int MAX_CLIENT_LIST_SIZE_5 = 5;
    public static int MAX_CLIENT_LIST_SIZE_10 = 10;
    public static int MAX_SERVICE_TYPE_LIST_SIZE_5 = 5;
    public static int MAX_SERVICE_TYPE_LIST_SIZE_10 = 10;

    // FLAGS
    public final static String FLAG_IS_MANAGER_VIEW = "isManagerView";
    public final static String FLAG_IS_EDIT_VIEW = "isEditView";
    public final static String FLAG_IS_DELETE_ACTION = "isDeleteAction";


}
