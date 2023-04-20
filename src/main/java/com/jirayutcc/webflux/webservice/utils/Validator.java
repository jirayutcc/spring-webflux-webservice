package com.jirayutcc.webflux.webservice.utils;

import java.util.List;

public class Validator {

    public static boolean isEmpty(String inputStr) {
        return null == inputStr
                || inputStr.trim().length() == 0
                || inputStr.equalsIgnoreCase("null");
    }

    public static boolean isNotEmpty(String inputStr) {
        return null != inputStr
                && inputStr.trim().length() > 0
                && !inputStr.equalsIgnoreCase("null");
    }

    public static boolean isEmpty(List<?> inputList) {
        try {
            return inputList.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isNotEmpty(List<?> inputList) {
        try {
            return !inputList.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isNull(Object object) {
        return null == object || object.equals("null");
    }

    public static boolean isNotNull(Object object) {
        return null != object && !object.equals("");
    }
}
