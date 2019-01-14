package com.ivahnenko.taxsystem.controller.util;

import java.util.HashMap;
import java.util.Map;

/**
 * {@code InputValidator} class responsible for validation of input.
 */
public class InputValidator {
    private static final String REGEX_MONEY = "^[0-9]{0,18}[.[,]]{0,1}[0-9]{0,2}";
    private static final String REGEX_TAXPAYER_NAME = "[A-Za-zА-Яа-я0-9\\'\\`\\-\\*\\+!.,:\"\\s\"\\`]{2,320}";
    private static final String REGEX_OWNERSHIP_TYPE = "[A-Z_\\s]{2,20}";
    private static final String REGEX_PERSON_NAME = "[A-Za-zА-Яа-я\\'\\`\\-\\s]{2,20}";
    private static final String REGEX_POSTCODE = "[0-9]{4,10}";
    private static final String REGEX_REGISTRATION_NUMBER = "^[a-zA-Z0-9]{6,6}[0-9]{1,6}";
    private static final String REGEX_EMAIL = "[^\\.](.{1,255})(@.{2,65})$";
    private static final String REGEX_DESCRIPTION = "(?m)[\\s\\S]{1,1000}";
    private static final String REGEX_USERNAME = "[a-z0-9_]{6,15}";
    private static final String REGEX_PASSWORD = "[a-zA-Z0-9]{6,20}";
    private static final String REGEX_QUARTER = "[1-4]{1}";
    private static final String REGEX_YEAR = "^[2]{1}[0]{1}[1-9]{1}[0-9]{1}";

   private static Map<String, Map<String, String>> consolidatedRegexMappingList = new HashMap<>(); 
   private static Map<String, String> taxpayerRegexMappingList = new HashMap<>();
   private static Map<String, String> taxReportFormRegexMappingList = new HashMap<>();
   private static Map<String, String> userRegexMappingList = new HashMap<>();
   private static Map<String, String> feedbackFormRegexMappingList = new HashMap<>();
   private static Map<String, String> ownershipTypeRegexMappingList = new HashMap<>();
   
   static {
	   userRegexMappingList.put("name", REGEX_PERSON_NAME);
       userRegexMappingList.put("surname", REGEX_PERSON_NAME);
       userRegexMappingList.put("patronymic", REGEX_PERSON_NAME);
       userRegexMappingList.put("email", REGEX_EMAIL);
       userRegexMappingList.put("username", REGEX_USERNAME);
       userRegexMappingList.put("password", REGEX_PASSWORD);

       taxpayerRegexMappingList.put("name", REGEX_TAXPAYER_NAME);
       taxpayerRegexMappingList.put("ownershipType", REGEX_OWNERSHIP_TYPE);
       taxpayerRegexMappingList.put("registrationNumber", REGEX_REGISTRATION_NUMBER);
       taxpayerRegexMappingList.put("email", REGEX_EMAIL);
       taxpayerRegexMappingList.put("postcode", REGEX_POSTCODE);
       
       taxReportFormRegexMappingList.put("quarter", REGEX_QUARTER);
       taxReportFormRegexMappingList.put("year", REGEX_YEAR);
       taxReportFormRegexMappingList.put("mainActivityIncome", REGEX_MONEY);
       taxReportFormRegexMappingList.put("investmentIncome", REGEX_MONEY);
       taxReportFormRegexMappingList.put("propertyIncome", REGEX_MONEY);
       taxReportFormRegexMappingList.put("mainActivityExpenses", REGEX_MONEY);
       taxReportFormRegexMappingList.put("investmentExpenses", REGEX_MONEY);
       taxReportFormRegexMappingList.put("propertyExpenses", REGEX_MONEY);
       taxReportFormRegexMappingList.put("reviewerComment", REGEX_DESCRIPTION);

       feedbackFormRegexMappingList.put("description", REGEX_DESCRIPTION);
       feedbackFormRegexMappingList.put("reviewerComment", REGEX_DESCRIPTION);
       
       ownershipTypeRegexMappingList.put("code", REGEX_OWNERSHIP_TYPE);

       consolidatedRegexMappingList.put("user", userRegexMappingList);
       consolidatedRegexMappingList.put("taxpayer", taxpayerRegexMappingList);
       consolidatedRegexMappingList.put("taxReportForm", taxReportFormRegexMappingList);
       consolidatedRegexMappingList.put("feedbackForm", feedbackFormRegexMappingList);
       consolidatedRegexMappingList.put("ownershipType", ownershipTypeRegexMappingList);
   }

/**
 * Method for checking if input meets the requirements of relevant regex expression.
 * @param inputName value to be associated with the regex expression in regex group.
 * @param inputValue value to be checked.
 * @param entityName value to be associated with regex group.
 * @return false if input is not valid or true otherwise.
 */
    public static boolean validate(String inputName, String inputValue, String entityName) {
    	return inputValue.matches(consolidatedRegexMappingList.get(entityName).get(inputName)); 	
    }
    
    private InputValidator() {
    }
}
