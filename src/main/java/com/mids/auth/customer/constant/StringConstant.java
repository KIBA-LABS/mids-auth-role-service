package com.mids.auth.customer.constant;

public class StringConstant {

    private StringConstant() {
        //private constructor for constant class
    }
    //Exception message
    public static final String ROLE_BY_ID_NOT_FOUND = "Role with id {%s} is not found.";
    
    public static final String CUSTOMER_BY_ID_NOT_FOUND = "Customer with id {%s} is not found.";

    //Success message
    public static final String USER_REGISTER_SUCCESFULLY = "Customer registered successfully!";
    
    public static final String USER_NAME_ALREADY_TAKEN = "Error: Username is already taken!";
    
    public static final String EMAIL_ALREADY_EXIST = "Error: Email is already in use!";
    
    public static final String ROLE_IS_DELETED = "Role is deleted.";
    
    public static final String CUSTOMER_IS_DELETED="Customer is deleted.";
}
