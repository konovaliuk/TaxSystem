package com.ivahnenko.taxsystem.controller.commands.taxpayer;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ivahnenko.taxsystem.controller.commands.Command;
import com.ivahnenko.taxsystem.model.Taxpayer;
import com.ivahnenko.taxsystem.model.User;
import com.ivahnenko.taxsystem.service.TaxpayerService;
import com.ivahnenko.taxsystem.service.impl.ServiceFactoryImpl;

/**
 * The class is an implementation of {@code Command} interface.
 * It executes method for displaying taxpayer's detailed information.
 */
public class DisplayTaxpayerDetails implements Command {
    private static final String EXCEPTION_IN_READING_DATA = "exception.dataReading";

    private static final String SUCCESSFUL_PAGE = "/WEB-INF/pages/taxpayer/taxpayerDetails.jsp";
    private static final String NEW_TAXPAYER_PAGE_IF_NOT_FOUND = "/main/displayCreateTaxpayer";
    private static final String EXCEPTION_PAGE = "/WEB-INF/pages/exceptionPage.jsp";

    private static final Logger LOGGER = Logger.getLogger(DisplayTaxpayerDetails.class);

    private TaxpayerService taxpayerService;

    public DisplayTaxpayerDetails() {
        this.taxpayerService = ServiceFactoryImpl.getInstance().getTaxpayerService();
    }

    public DisplayTaxpayerDetails(TaxpayerService taxpayerService) {
        this.taxpayerService = taxpayerService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        LOGGER.info(String.format("User, id: %d requested DisplayTaxpayerDetails", user.getId()));

        try {
            Optional<Taxpayer> optionalTaxpayer = taxpayerService.getByEmployeeId(user.getId());

            if (!optionalTaxpayer.isPresent()) {
                LOGGER.info(String.format("Taxpayer, with user, id: %s not found ", user.getId()));
                return NEW_TAXPAYER_PAGE_IF_NOT_FOUND;

            } else {
                Taxpayer taxpayer = optionalTaxpayer.get();
                request.setAttribute("taxpayer", taxpayer);
                return SUCCESSFUL_PAGE;
            }

        } catch (NumberFormatException | NullPointerException e) {
            LOGGER.error(EXCEPTION_IN_READING_DATA, e);
            request.setAttribute("exception", EXCEPTION_IN_READING_DATA);
            return EXCEPTION_PAGE;
        }
    }
}
