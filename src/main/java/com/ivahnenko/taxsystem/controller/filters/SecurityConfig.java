package com.ivahnenko.taxsystem.controller.filters;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * {@code SecurityConfig} class describes security configuration.
 * It contains method for returning available command names.
 */
class SecurityConfig {
    private static final String TYPE_GUEST = "GUEST";
    private static final String TYPE_CLIENT = "CLIENT";
    private static final String TYPE_INSPECTOR = "INSPECTOR";
    private static final String TYPE_SUPERVISOR = "SUPERVISOR";
    private static final String TYPE_ADMIN = "ADMIN";
    private static SecurityConfig instance;
    private Map<String, Set<String>> commandMapping;

    private SecurityConfig() {
        init();
    }

    static SecurityConfig getInstance() {
        if (instance == null) {
            synchronized (SecurityConfig.class) {
                if (instance == null) {
                    instance = new SecurityConfig();
                }
            }
        }
        return instance;
    }

    Set<String> getCommandsByType(String type) {
        return commandMapping.get(type);
    }

    boolean isCommandExist(String commandRequested) {
        boolean commandExists = false;
        for (Set<String> commands : commandMapping.values()) {
            if (commands.contains(commandRequested))
                commandExists = true;
        }
        return commandExists;
    }

    private void init() {
        Set<String> guestCommands;
        Set<String> clientCommands;
        Set<String> inspectorCommands;
        Set<String> supervisorCommands;
        Set<String> adminCommands;

        guestCommands = new HashSet<>();
        guestCommands.add("createUser");
        guestCommands.add("displayCreateUser");
        guestCommands.add("login");
        guestCommands.add("displayLogin");
        guestCommands.add("displayHome");

        clientCommands = new HashSet<>();
        clientCommands.add("displayHome");
        clientCommands.add("logout");
        clientCommands.add("displayTaxpayerDetails");
        clientCommands.add("displayCreateTaxpayer");
        clientCommands.add("createTaxpayer");
        clientCommands.add("displayAllTaxReportFormByInitiator");
        clientCommands.add("displayTaxReportFormDetailsByInitiator");
        clientCommands.add("displayJsonTaxReportFormInput");
        clientCommands.add("displayCreateTaxReportForm");
        clientCommands.add("createTaxReportForm");
        clientCommands.add("updateTaxReportFormByInitiator");
        clientCommands.add("displayFeedbackFormDetailsByInitiator");
        clientCommands.add("displayAllFeedbackFormByInitiator");
        clientCommands.add("updateFeedbackFormByInitiator");
        clientCommands.add("createFeedbackForm");
        clientCommands.add("displayCreateFeedbackForm");


        inspectorCommands = new HashSet<>();
        inspectorCommands.add("displayHome");
        inspectorCommands.add("logout");
        inspectorCommands.add("displayAllTaxReportFormByReviewer");
        inspectorCommands.add("displayTaxReportFormDetailsByReviewer");
        inspectorCommands.add("updateTaxReportFormByReviewer");
        inspectorCommands.add("displayAllTaxReportFormTodo");
        inspectorCommands.add("changeTaxReportFormReviewer");

        supervisorCommands = new HashSet<>();
        supervisorCommands.add("displayTaxReportFormDetails");
        supervisorCommands.add("displayAllFeedbackFormByReviewer");
        supervisorCommands.add("displayAllFeedbackFormTodo");
        supervisorCommands.add("displayFeedbackFormDetailsByReviewer");
        supervisorCommands.add("updateFeedbackFormByReviewer");
        supervisorCommands.add("displayHome");
        supervisorCommands.add("logout");
        supervisorCommands.add("displayChangeTaxReportFormReviewer");
        supervisorCommands.add("changeTaxReportFormReviewer");
        supervisorCommands.add("changeFeedbackFormReviewer");

        adminCommands = new HashSet<>();
        adminCommands.add("displayChangeUserType");
        adminCommands.add("changeUserType");
        adminCommands.add("displayHome");
        adminCommands.add("logout");
        adminCommands.add("changeUserDeletionStatus");
        adminCommands.add("displayChangeUserDeletionStatus");

        commandMapping = new HashMap<>();
        commandMapping.put(TYPE_GUEST, guestCommands);
        commandMapping.put(TYPE_CLIENT, clientCommands);
        commandMapping.put(TYPE_INSPECTOR, inspectorCommands);
        commandMapping.put(TYPE_SUPERVISOR, supervisorCommands);
        commandMapping.put(TYPE_ADMIN, adminCommands);
    }
}
