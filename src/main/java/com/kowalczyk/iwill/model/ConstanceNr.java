package com.kowalczyk.iwill.model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

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

    // CONTACT ADDRESS
    public final static String PHONE_AGREEMENT = "phoneAgreement";
    public final static String EMAIL_AGREEMENT = "emailAgreement";
    public final static String PHONE_VALUE = "phoneValue";
    public final static String EMAIL_VALUE = "emailValue";

    // CLIENT
    public final static String DEFAULT_CLIENT_NAME = "Dane zostały usunięte";
    public final static String DEFAULT_CLIENT_LASTNAME = "Dane zostały usunięte";
    public final static String DEFAULT_CLIENT_COMMENT = "Dane zostały usunięte";
    public final static String DEFAULT_CLIENT_CODE = "Dane zostały usunięte";
    public final static Boolean ACTIVE_CLIENT = true;
    public final static Boolean INACTIVE_CLIENT = false;

    // ATTRIBUTE
    public final static String LAST_VISIT_DATE = "lastVisitDate";
    public final static String MESSAGE_5_YEARS_BEFORE_DELETE = "Dane powinny być przechowywane przez 5 lat.";
    public final static String ALERT = "alert";
    public final static LocalDate VISIT_SEARCH_START_DATE = LocalDate.parse("2022-01-01");
    public final static String ATTRIBUTE_SERVICE_TYPE_LIST = "serviceTypeList";
    public final static String ATTRIBUTE_FIELDS_LIST = "fieldsList";


    // SEARCH
    public final static String DEFAULT_FIELD_TITLE = "Tytuł";
    public final static String DEFAULT_FIELD_DESC = "Opis";
    public final static String DEFAULT_FIELD_CODE = "Kod";
    public final static List<String> SEARCH_DEFAULT_FIELDS_LIST = Arrays.asList(DEFAULT_FIELD_TITLE, DEFAULT_FIELD_DESC, DEFAULT_FIELD_CODE);


}
