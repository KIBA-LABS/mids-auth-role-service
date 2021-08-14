package com.mids.auth.role.constant;

public class StringConstant {

    private StringConstant() {
        //private constructor for constant class
    }
    //Exception message
    public static final String ROLE_BY_NAME_NOT_FOUND = "Role with name {%s} is not found.";
    public static final String PERMISSION_BY_ID_NOT_FOUND = "Permission with id {%s} is not found.";
    public static final String ROLE_BY_APPLICATIONID_NOT_FOUND="Role with application Id {%s} is not found.";
    public static final String PERMISSION_BY_ID_DELETED = "Permission with id {%s} was deleted.";
    public static final String ROLE_BY_ID_NOT_FOUND = "Role with name {%s} is not found.";
    public static final String ROLE_BY_APPLICATION_ID_NOT_FOUND="Role with application id {%s} is not found.";
    public static final String PERMISSION_BY_APPLICATION_ID_NOT_FOUND="Permission with application id {%s} is not found.";
    public static final String ROLE_IS_CREATED = "Role is created.";
    public static final String PERMISSION_IS_CREATED = "Permission is created.";
    public static final String ROLE_IS_DELETED = "Role is deleted.";
    public static final String PERMISSION_IS_DELETED = "Permission is deleted.";
    public static final String ROLE_IS_ADDED_TO_USER="Role is succesfully added to user.";
}
