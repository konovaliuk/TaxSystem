package com.ivahnenko.taxsystem.controller.commands.taxreportform;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ivahnenko.taxsystem.controller.commands.Command;
import com.ivahnenko.taxsystem.controller.util.UtilCommands;
import com.ivahnenko.taxsystem.model.form.Form.Status;
import com.ivahnenko.taxsystem.model.User;
import com.ivahnenko.taxsystem.model.form.TaxReportForm;
import com.ivahnenko.taxsystem.service.TaxReportFormService;
import com.ivahnenko.taxsystem.service.impl.ServiceFactoryImpl;

/**
 * The class is an implementation of {@code Command} interface.
 * It executes method for updating tax report form details by initiator.
 */
public class UpdateTaxReportFormByInitiator implements Command {
    private static final String ENTITY_NAME = "taxReportForm";
    private static final String ALLOWED_STATUS_TO_MAKE_UPDATE = "RETURNED";

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
    private static final String EXCEPTION_NO_TAX_REPORT_FORM_FOR_UPDATE = "exception.forUpdateNotFound";

    private static final String EXCEPTION_LABEL = "Exception";
    private static final String USER_INPUT_LABEL = "Provided";
    private static final String REFRESHING_URL_DISPLAYED_LABEL = "redirect";

    private static final String SUCCESSFUL_PAGE = "/WEB-INF/pages/home.jsp";
    private static final String VALIDATION_EXCEPTION_PAGE = "/main/displayTaxReportFormDetailsByInitiator";
    private static final String EXCEPTION_PAGE = "/WEB-INF/pages/exceptionPage.jsp";

    private static final Logger LOGGER = Logger.getLogger(UpdateTaxReportFormByInitiator.class);

    private static Map<String, String> inputExceptionList = new HashMap<>();

    static {
        inputExceptionList.put(QUARTER, "exception.creteTaxpayer.quarter");
        inputExceptionList.put(YEAR, "exception.creteTaxpayer.year");
        inputExceptionList.put(MAIN_ACTIVITY_INCOME, EXCEPTION_MONEY_FORMAT);
        inputExceptionList.put(INVESTMENT_INCOME, EXCEPTION_MONEY_FORMAT);
        inputExceptionList.put(PROPERTY_INCOME, EXCEPTION_MONEY_FORMAT);
        inputExceptionList.put(MAIN_ACTIVITY_EXPENSES, EXCEPTION_MONEY_FORMAT);
        inputExceptionList.put(INVESTMENT_EXPENSES, EXCEPTION_MONEY_FORMAT);
        inputExceptionList.put(PROPERTY_EXPENSES, EXCEPTION_MONEY_FORMAT);
    }

    private TaxReportFormService taxReportFormService;

    public UpdateTaxReportFormByInitiator() {
        this.taxReportFormService = ServiceFactoryImpl.getInstance().getTaxReportFormService();
    }

    public UpdateTaxReportFormByInitiator(TaxReportFormService taxReportFormService) {
        this.taxReportFormService = taxReportFormService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        LOGGER.info(String.format("User, id: %d requested UpdateTaxReportFormByInitiator", user.getId()));

        try {
            String quarterStr = request.getParameter(QUARTER);
            String yearStr = request.getParameter(YEAR);
            String mainActivityIncomeStr = request.getParameter(MAIN_ACTIVITY_INCOME);
            String investmentIncomeStr = request.getParameter(INVESTMENT_INCOME);
            String propertyIncomeStr = request.getParameter(PROPERTY_INCOME);
            String mainActivityExpensesStr = request.getParameter(MAIN_ACTIVITY_EXPENSES);
            String investmentExpensesStr = request.getParameter(INVESTMENT_EXPENSES);
            String propertyExpensesStr = request.getParameter(PROPERTY_EXPENSES);
            String taxReportFormIdStr = request.getParameter(ENTITY_NAME);

            Map<String, String> taxReportFormParams = new HashMap<>();
            taxReportFormParams.put(QUARTER, quarterStr);
            taxReportFormParams.put(YEAR, yearStr);
            taxReportFormParams.put(MAIN_ACTIVITY_INCOME, mainActivityIncomeStr);
            taxReportFormParams.put(INVESTMENT_INCOME, investmentIncomeStr);
            taxReportFormParams.put(PROPERTY_INCOME, propertyIncomeStr);
            taxReportFormParams.put(MAIN_ACTIVITY_EXPENSES, mainActivityExpensesStr);
            taxReportFormParams.put(INVESTMENT_EXPENSES, investmentExpensesStr);
            taxReportFormParams.put(PROPERTY_EXPENSES, propertyExpensesStr);

            List<String> validationExceptionList = UtilCommands.getValidationExceptionList(taxReportFormParams, ENTITY_NAME);

            if (!validationExceptionList.isEmpty()) {
                taxReportFormParams.keySet().stream()
                        .filter(prm -> !validationExceptionList.contains(prm))
                        .forEach(prm -> request.setAttribute(prm + USER_INPUT_LABEL, taxReportFormParams.get(prm)));

                validationExceptionList.forEach(excp -> request.setAttribute(excp + EXCEPTION_LABEL, inputExceptionList.get(excp)));

                request.setAttribute(ENTITY_NAME, taxReportFormIdStr);
                LOGGER.info("Input validation exception");
                return VALIDATION_EXCEPTION_PAGE;
            }

            int taxReportFormId = Integer.parseInt(request.getParameter(ENTITY_NAME));
            byte quarter = Byte.parseByte(quarterStr.replaceAll(",", "."));
            short year = Short.parseShort(yearStr);
            double mainActivityIncome = Double.parseDouble(mainActivityIncomeStr.replaceAll(",", "."));
            double investmentIncome = Double.parseDouble(investmentIncomeStr.replaceAll(",", "."));
            double propertyIncome = Double.parseDouble(propertyIncomeStr.replaceAll(",", "."));
            double mainActivityExpenses = Double.parseDouble(mainActivityExpensesStr.replaceAll(",", "."));
            double investmentExpenses = Double.parseDouble(investmentExpensesStr.replaceAll(",", "."));
            double propertyExpenses = Double.parseDouble(propertyExpensesStr.replaceAll(",", "."));

            TaxReportForm taxReportForm = new TaxReportForm.Builder()
                    .setId(taxReportFormId)
                    .setInitiator(user)
                    .setQuarter(quarter)
                    .setYear(year)
                    .setMainActivityIncome(mainActivityIncome)
                    .setInvestmentIncome(investmentIncome)
                    .setPropertyIncome(propertyIncome)
                    .setMainActivityExpenses(mainActivityExpenses)
                    .setInvestmentExpenses(investmentExpenses)
                    .setPropertyExpenses(propertyExpenses)
                    .setLastModifiedDate(LocalDate.now())
                    .setStatus(Status.IN_PROGRESS)
                    .build();

            boolean actionSuccess = taxReportFormService.updateByInitiatorAndStatus(taxReportForm, ALLOWED_STATUS_TO_MAKE_UPDATE);

            if (!actionSuccess) {
                LOGGER.info(String.format("TaxReportForm for update with id: %d, user,id: %d, status: %s = not found"
                        , taxReportFormId, user.getId(), ALLOWED_STATUS_TO_MAKE_UPDATE));
                request.setAttribute("exception", EXCEPTION_NO_TAX_REPORT_FORM_FOR_UPDATE);
                return EXCEPTION_PAGE;
            }

            request.setAttribute(REFRESHING_URL_DISPLAYED_LABEL, REFRESHING_URL_DISPLAYED_LABEL);
            return SUCCESSFUL_PAGE;

        } catch (NumberFormatException | NullPointerException e) {
            LOGGER.error(EXCEPTION_IN_READING_DATA, e);
            request.setAttribute("exception", EXCEPTION_IN_READING_DATA);
            return EXCEPTION_PAGE;
        }
    }
}
