package com.ivahnenko.taxsystem.controller.commands.taxpayer;

import java.io.IOException;
import java.util.Arrays;
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
import com.ivahnenko.taxsystem.model.Taxpayer;
import com.ivahnenko.taxsystem.model.Taxpayer.OwnershipType;
import com.ivahnenko.taxsystem.model.User;
import com.ivahnenko.taxsystem.service.TaxpayerService;
import com.ivahnenko.taxsystem.service.UserService;
import com.ivahnenko.taxsystem.service.impl.ServiceFactoryImpl;

/**
 * The class is an implementation of {@code Command} interface.
 * It executes creation of a new taxpayer.
 */
public class CreateTaxpayer implements Command {
    private static final String NAME = "name";
    private static final String REGISTRATION_NUMBER = "registrationNumber";
    private static final String EMAIL = "email";
    private static final String POSTCODE = "postcode";
    private static final String OWNERSHIP_TYPE = "ownershipType";
    private static final String ENTITY_NAME = "taxpayer";

    private static final String EXCEPTION_ALREADY_EXISTS_REGISTRATION_NUMBER = "exception.createTaxpayer.registrationNumberExists";
    private static final String EXCEPTION_ALREADY_EXISTS_EMAIL = "exception.createTaxpayer.emailExists";
    private static final String EXCEPTION_USER_ALREADY_ASSIGNED = "exception.createTaxpayer.userIsTaxpayer";
    private static final String EXCEPTION_IN_READING_DATA = "exception.dataReading";

    private static final String EXCEPTION_LABEL = "Exception";
    private static final String USER_INPUT_LABEL = "Provided";
    private static final String REFRESHING_URL_DISPLAYED_LABEL = "redirect";

    private static final String SUCCESSFUL_PAGE = "/WEB-INF/pages/home.jsp";
    private static final String VALIDATION_EXCEPTION_PAGE = "/WEB-INF/pages/taxpayer/createTaxpayer.jsp";
    private static final String EXCEPTION_PAGE = "/WEB-INF/pages/exceptionPage.jsp";

    private static final Logger LOGGER = Logger.getLogger(CreateTaxpayer.class);

    private static Map<String, String> inputExceptionList = new HashMap<>();

    static {
        inputExceptionList.put(NAME, "exception.createTaxpayer.name");
        inputExceptionList.put(REGISTRATION_NUMBER, "exception.createTaxpayer.registrationNumber");
        inputExceptionList.put(EMAIL, "exception.createTaxpayer.email");
        inputExceptionList.put(POSTCODE, "exception.createTaxpayer.postcode");
        inputExceptionList.put(OWNERSHIP_TYPE, "exception.createTaxpayer.ownershipType");
    }

    private TaxpayerService taxpayerService;
    private UserService userService;

    public CreateTaxpayer() {
        this.taxpayerService = ServiceFactoryImpl.getInstance().getTaxpayerService();
        this.userService = ServiceFactoryImpl.getInstance().getUserService();
    }

    public CreateTaxpayer(TaxpayerService taxpayerService, UserService userService) {
        this.taxpayerService = taxpayerService;
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        LOGGER.info(String.format("User, id: %d requested CreateTaxpayer", user.getId()));

        try {
            String name = request.getParameter(NAME);
            String registrationNumber = request.getParameter(REGISTRATION_NUMBER);
            String email = request.getParameter(EMAIL);
            String postcode = request.getParameter(POSTCODE);
            String ownershipType = request.getParameter(OWNERSHIP_TYPE);

            Map<String, String> taxpayerParams = new HashMap<>();
            taxpayerParams.put(NAME, name);
            taxpayerParams.put(OWNERSHIP_TYPE, ownershipType);
            taxpayerParams.put(REGISTRATION_NUMBER, registrationNumber);
            taxpayerParams.put(EMAIL, email);
            taxpayerParams.put(POSTCODE, postcode);

            List<String> validationExceptionList = UtilCommands.getValidationExceptionList(taxpayerParams, ENTITY_NAME);
            List<OwnershipType> ownershipTypeList = Arrays.asList(OwnershipType.values()); 
            boolean validationError = false;

            if (userService.isUserHasTaxpayer(user)) {
                LOGGER.info(EXCEPTION_USER_ALREADY_ASSIGNED);
                request.setAttribute("exception", EXCEPTION_USER_ALREADY_ASSIGNED);
                return EXCEPTION_PAGE;

            } else if (!validationExceptionList.isEmpty()) {
                taxpayerParams.keySet()
                        .stream()
                        .filter(prm -> !validationExceptionList.contains(prm))
                        .forEach(prm -> request.setAttribute(prm + USER_INPUT_LABEL, taxpayerParams.get(prm)));

                validationExceptionList.forEach(excp -> request.setAttribute(excp + EXCEPTION_LABEL, inputExceptionList.get(excp)));
                request.setAttribute("ownershipTypeList", ownershipTypeList);
                LOGGER.info("Input validation exception");
                validationError = true;

            } else if (taxpayerService.isRegNumberExists(registrationNumber)) {
                LOGGER.info(EXCEPTION_ALREADY_EXISTS_REGISTRATION_NUMBER);
                request.setAttribute(REGISTRATION_NUMBER + EXCEPTION_LABEL, EXCEPTION_ALREADY_EXISTS_REGISTRATION_NUMBER);
                validationError = true;

            } else if (taxpayerService.isEmailExists(email)) {
                LOGGER.info(EXCEPTION_ALREADY_EXISTS_EMAIL);
                request.setAttribute(EMAIL + EXCEPTION_LABEL, EXCEPTION_ALREADY_EXISTS_EMAIL);
                validationError = true;

            } else if (!ownershipTypeList.stream().anyMatch((o) -> o.name().equals(ownershipType))) {
                LOGGER.error(inputExceptionList.get(OWNERSHIP_TYPE));
                request.setAttribute(OWNERSHIP_TYPE + EXCEPTION_LABEL, inputExceptionList.get(OWNERSHIP_TYPE));
                validationError = true;
            }

            if (validationError) {

                taxpayerParams.keySet().stream()
                        .filter(prm -> !validationExceptionList.contains(prm))
                        .forEach(prm -> request.setAttribute(prm + USER_INPUT_LABEL, taxpayerParams.get(prm)));

                validationExceptionList.forEach(excp -> request.setAttribute(excp + EXCEPTION_LABEL, inputExceptionList.get(excp)));
                return VALIDATION_EXCEPTION_PAGE;
            }

            Taxpayer taxpayer = new Taxpayer.Builder()
                    .setName(name)
                    .setRegistrationNumber(registrationNumber)
                    .setEmail(email)
                    .setPostcode(postcode)
                    .setOwnershipType(OwnershipType.valueOf(ownershipType))
                    .setEmployee(user)
                    .build();

            taxpayerService.save(taxpayer);
            request.setAttribute(REFRESHING_URL_DISPLAYED_LABEL, REFRESHING_URL_DISPLAYED_LABEL);
            return SUCCESSFUL_PAGE;

        } catch (NumberFormatException | NullPointerException e) {
            LOGGER.error(EXCEPTION_IN_READING_DATA, e);
            request.setAttribute("exception", EXCEPTION_IN_READING_DATA);
            return EXCEPTION_PAGE;
        }
    }
}
