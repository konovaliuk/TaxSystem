package com.ivahnenko.taxsystem.controller.commands.taxreportform;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ivahnenko.taxsystem.controller.commands.Command;
import com.ivahnenko.taxsystem.controller.util.UtilCommands;
import com.ivahnenko.taxsystem.model.Taxpayer;
import com.ivahnenko.taxsystem.model.User;
import com.ivahnenko.taxsystem.model.form.Form.Status;
import com.ivahnenko.taxsystem.model.form.TaxReportForm;
import com.ivahnenko.taxsystem.service.TaxReportFormService;
import com.ivahnenko.taxsystem.service.impl.ServiceFactoryImpl;

/**
 * The class is an implementation of {@code Command} interface.
 * It executes method for creating tax report form.
 */
public class CreateTaxReportForm implements Command {
    private static final String ENTITY_NAME = "taxReportForm";
    private static final String USER = "user";
    private static final String TAXPAYER = "taxpayer";
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
    private static final String EXCEPTION_TAXPAYER_NOT_FOUND = "exception.taxpayer.notFound";

    private static final String EXCEPTION_LABEL = "Exception";
    private static final String USER_INPUT_LABEL = "Provided";
    private static final String REFRESHING_URL_DISPLAYED_LABEL = "redirect";

    private static final String SUCCESSFUL_PAGE = "/WEB-INF/pages/home.jsp";
    private static final String EXCEPTION_PAGE = "/WEB-INF/pages/exceptionPage.jsp";
    private static final String VALIDATION_EXCEPTION_PAGE = "/WEB-INF/pages/taxreportform/createTaxReportForm.jsp";

    private static final Logger LOGGER = Logger.getLogger(CreateTaxReportForm.class);

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

    private TaxReportFormService taxReportFormService;

    public CreateTaxReportForm() {
        this.taxReportFormService = ServiceFactoryImpl.getInstance().getTaxReportFormService();
    }

    public CreateTaxReportForm(TaxReportFormService taxReportFormService) {
        this.taxReportFormService = taxReportFormService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        LOGGER.info(String.format("User, id: %d requested CreateTaxReportForm", user.getId()));

        try {
            String quarterStr = request.getParameter(QUARTER);
            String yearStr = request.getParameter(YEAR);
            String mainActivityIncomeStr = request.getParameter(MAIN_ACTIVITY_INCOME);
            String investmentIncomeStr = request.getParameter(INVESTMENT_INCOME);
            String propertyIncomeStr = request.getParameter(PROPERTY_INCOME);
            String mainActivityExpensesStr = request.getParameter(MAIN_ACTIVITY_EXPENSES);
            String investmentExpensesStr = request.getParameter(INVESTMENT_EXPENSES);
            String propertyExpensesStr = request.getParameter(PROPERTY_EXPENSES);

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
                LOGGER.info("Input validation exception");
                return VALIDATION_EXCEPTION_PAGE;
            }

            byte quarter = Byte.parseByte(quarterStr.replaceAll(",", "."));
            short year = Short.parseShort(yearStr);
            double mainActivityIncome = Double.parseDouble(mainActivityIncomeStr.replaceAll(",", "."));
            double investmentIncome = Double.parseDouble(investmentIncomeStr.replaceAll(",", "."));
            double propertyIncome = Double.parseDouble(propertyIncomeStr.replaceAll(",", "."));
            double mainActivityExpenses = Double.parseDouble(mainActivityExpensesStr.replaceAll(",", "."));
            double investmentExpenses = Double.parseDouble(investmentExpensesStr.replaceAll(",", "."));
            double propertyExpenses = Double.parseDouble(propertyExpensesStr.replaceAll(",", "."));

            Taxpayer taxpayer = (Taxpayer) session.getAttribute(TAXPAYER);

            if (Objects.isNull(taxpayer)) {
                LOGGER.info(String.format("Taxpayer, with user, id: %d not found ", user.getId()));
                request.setAttribute("exception", EXCEPTION_TAXPAYER_NOT_FOUND);
                return EXCEPTION_PAGE;
            }

            TaxReportForm taxReportForm = new TaxReportForm.Builder()
                    .setQuarter(quarter)
                    .setYear(year)
                    .setTaxpayer(taxpayer)
                    .setInitiator(user)
                    .setMainActivityIncome(mainActivityIncome)
                    .setInvestmentIncome(investmentIncome)
                    .setPropertyIncome(propertyIncome)
                    .setMainActivityExpenses(mainActivityExpenses)
                    .setInvestmentExpenses(investmentExpenses)
                    .setPropertyExpenses(propertyExpenses)
                    .setCreationDate(LocalDate.now())
                    .setLastModifiedDate(LocalDate.now())
                    .setStatus(Status.TODO)
                    .build();

            taxReportFormService.save(taxReportForm);
            request.setAttribute(REFRESHING_URL_DISPLAYED_LABEL, REFRESHING_URL_DISPLAYED_LABEL);
            return SUCCESSFUL_PAGE;

        } catch (NumberFormatException | NullPointerException e) {
            LOGGER.error(EXCEPTION_IN_READING_DATA, e);
            request.setAttribute("exception", EXCEPTION_IN_READING_DATA);
            return EXCEPTION_PAGE;
        }
    }
}
