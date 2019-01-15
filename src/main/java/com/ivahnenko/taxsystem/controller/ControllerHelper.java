package com.ivahnenko.taxsystem.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ivahnenko.taxsystem.controller.commands.Command;
import com.ivahnenko.taxsystem.controller.commands.feedbackform.*;
import com.ivahnenko.taxsystem.controller.commands.taxpayer.*;
import com.ivahnenko.taxsystem.controller.commands.taxreportform.*;
import com.ivahnenko.taxsystem.controller.commands.user.*;

/**
 * {@code ControllerHelper} class describes commands generation.
 * It contains method for returning available commands.
 * Is used by security filters.
 */
class ControllerHelper {

    private static class ControllerHelperHolder {
        public static final ControllerHelper HOLDER_INSTANCE = new ControllerHelper();
    }

    static ControllerHelper getInstance() {
        return ControllerHelperHolder.HOLDER_INSTANCE;
    }

    private Map<String, Command> commands;
    private ControllerHelper() {
        init();
    }

    Command getCommand(HttpServletRequest request) {
        String action = request.getRequestURI().replaceAll(".*/", "");
        return commands.get(action);
    }

    private void init() {
        commands = new HashMap<>();
        commands.put("createUser", new CreateUser());
        commands.put("displayCreateUser", new DisplayCreateUser());
        commands.put("createTaxpayer", new CreateTaxpayer());
        commands.put("displayCreateTaxpayer", new DisplayCreateTaxpayer());
        commands.put("changeUserType", new ChangeUserType());
        commands.put("displayChangeUserType", new DisplayChangeUserType());
        commands.put("login", new Login());
        commands.put("displayLogin", new DisplayLogin());
        commands.put("logout", new Logout());
        commands.put("displayChangeTaxReportFormReviewer", new DisplayChangeTaxReportFormReviewer());
        commands.put("changeTaxReportFormReviewer", new ChangeTaxReportFormReviewer());
        commands.put("displayAllTaxReportFormByInitiator", new DisplayAllTaxReportFormByInitiator());
        commands.put("displayTaxReportFormDetails", new DisplayTaxReportFormDetails());
        commands.put("displayAllTaxReportFormByReviewer", new DisplayAllTaxReportFormByReviewer());
        commands.put("displayAllTaxReportFormTodo", new DisplayAllTaxReportFormTodo());
        commands.put("displayAllFeedbackFormByReviewer", new DisplayAllFeedbackFormByReviewer());
        commands.put("displayAllFeedbackFormByInitiator", new DisplayAllFeedbackFormByInitiator());
        commands.put("updateFeedbackFormByInitiator", new UpdateFeedbackFormByInitiator());
        commands.put("updateFeedbackFormByReviewer", new UpdateFeedbackFormByReviewer());
        commands.put("displayFeedbackFormDetails", new DisplayFeedbackFormDetails());
        commands.put("createFeedbackForm", new CreateFeedbackForm());
        commands.put("displayCreateFeedbackForm", new DisplayCreateFeedbackForm());
        commands.put("displayAllFeedbackFormTodo", new DisplayAllFeedbackFormTodo());
        commands.put("displayCreateTaxReportForm", new DisplayCreateTaxReportForm());
        commands.put("createTaxReportForm", new CreateTaxReportForm());
        commands.put("updateTaxReportFormByInitiator", new UpdateTaxReportFormByInitiator());
        commands.put("updateTaxReportFormByReviewer", new UpdateTaxReportFormByReviewer());
        commands.put("displayTaxReportFormDetailsByInitiator", new DisplayTaxReportFormDetailsByInitiator());
        commands.put("displayTaxReportFormDetailsByReviewer", new DisplayTaxReportFormDetailsByReviewer());
        commands.put("displayFeedbackFormDetailsByInitiator", new DisplayFeedbackFormDetailsByInitiator());
        commands.put("displayFeedbackFormDetailsByReviewer", new DisplayFeedbackFormDetailsByReviewer());
        commands.put("displayTaxpayerDetails", new DisplayTaxpayerDetails());
        commands.put("changeUserDeletionStatus", new ChangeUserDeletionStatus());
        commands.put("displayChangeUserDeletionStatus", new DisplayChangeUserDeletionStatus());
        commands.put("displayJsonTaxReportFormInput", new DisplayJsonTaxReportFormInput());
        commands.put("changeFeedbackFormReviewer", new ChangeFeedbackFormReviewer());
    }
}
