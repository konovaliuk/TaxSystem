package com.ivahnenko.taxsystem.controller.commands.taxreportform;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.google.gson.JsonSyntaxException;
import com.ivahnenko.taxsystem.controller.commands.Command;
import com.ivahnenko.taxsystem.controller.util.UtilCommands;
import com.ivahnenko.taxsystem.model.User;

/**
 * The class is an implementation of {@code Command} interface.
 * It executes method for parsing and displaying json input of tax report form.
 */
public class DisplayJsonTaxReportFormInput implements Command {
    private static final String ENTITY_NAME = "taxReportForm";
    private static final String USER = "user";
    private static final String QUARTER = "quarter";
    private static final String YEAR = "year";
    private static final String MAIN_ACTIVITY_INCOME = "mainActivityIncome";
    private static final String INVESTMENT_INCOME = "investmentIncome";
    private static final String PROPERTY_INCOME = "propertyIncome";
    private static final String MAIN_ACTIVITY_EXPENSES = "mainActivityExpenses";
    private static final String INVESTMENT_EXPENSES = "investmentExpenses";
    private static final String PROPERTY_EXPENSES = "propertyExpenses";

    private static final String EXCEPTION_MONEY_FORMAT = "exception.taxReportForm.moneyFormat";
    private static final String EXCEPTION_IN_READING_DATA = "exception.dataReading";
    private static final String EXCEPTION_LABEL = "Exception";
    private static final String USER_INPUT_LABEL = "Provided";

    private static final String CREATE_TAX_REPORT_FORM_PAGE = "/WEB-INF/pages/taxreportform/createTaxReportForm.jsp";
    private static final String EXCEPTION_PAGE = "/WEB-INF/pages/exceptionPage.jsp";

    private static final Logger LOGGER = Logger.getLogger(DisplayJsonTaxReportFormInput.class);

    private static Map<String, String> inputExceptionList = new HashMap<>();

    static {
        inputExceptionList.put(QUARTER, "exception.taxReportForm.quarter");
        inputExceptionList.put(YEAR, "exception.taxReportForm.year");
        inputExceptionList.put(MAIN_ACTIVITY_INCOME, EXCEPTION_MONEY_FORMAT);
        inputExceptionList.put(INVESTMENT_INCOME, EXCEPTION_MONEY_FORMAT);
        inputExceptionList.put(PROPERTY_INCOME, EXCEPTION_MONEY_FORMAT);
        inputExceptionList.put(MAIN_ACTIVITY_EXPENSES, EXCEPTION_MONEY_FORMAT);
        inputExceptionList.put(INVESTMENT_EXPENSES, EXCEPTION_MONEY_FORMAT);
        inputExceptionList.put(PROPERTY_EXPENSES, EXCEPTION_MONEY_FORMAT);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        LOGGER.info(String.format("User, id: %d requested DisplayJsonTaxReportFormInput", user.getId()));

        try {
            List<String> taxReportFormParams = new ArrayList<>();
            taxReportFormParams.add(QUARTER);
            taxReportFormParams.add(YEAR);
            taxReportFormParams.add(MAIN_ACTIVITY_INCOME);
            taxReportFormParams.add(INVESTMENT_INCOME);
            taxReportFormParams.add(PROPERTY_INCOME);
            taxReportFormParams.add(MAIN_ACTIVITY_EXPENSES);
            taxReportFormParams.add(INVESTMENT_EXPENSES);
            taxReportFormParams.add(PROPERTY_EXPENSES);

            Map<String, String> taxReportFormParamsValues = UtilCommands.getParamValuesFromJsonInput(request, taxReportFormParams);

            List<String> validationExceptionList = UtilCommands.getValidationExceptionList(taxReportFormParamsValues, ENTITY_NAME);

            taxReportFormParamsValues.keySet().stream()
                    .filter(prm -> !validationExceptionList.contains(prm))
                    .forEach(prm -> request.setAttribute(prm + USER_INPUT_LABEL, taxReportFormParamsValues.get(prm)));

            validationExceptionList.forEach(excp -> request.setAttribute(excp + EXCEPTION_LABEL, inputExceptionList.get(excp)));

            return CREATE_TAX_REPORT_FORM_PAGE;

        } catch (NumberFormatException | NullPointerException | IOException | JsonSyntaxException | IllegalStateException e) {
            LOGGER.error(EXCEPTION_IN_READING_DATA, e);
            request.setAttribute("exception", EXCEPTION_IN_READING_DATA);
            return EXCEPTION_PAGE;
        }
    }
}
