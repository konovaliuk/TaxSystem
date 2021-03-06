package com.ivahnenko.taxsystem.controller.commands.taxreportform;

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
 * It executes method for displaying page for creating tax report form.
 */
public class DisplayCreateTaxReportForm implements Command {
    private static final String SUCCESSFUL_PAGE = "/WEB-INF/pages/taxreportform/createTaxReportForm.jsp";
    private static final String NEW_TAXPAYER_PAGE_IF_NOT_FOUND = "/WEB-INF/pages/taxpayer/createTaxpayer.jsp";

    private static final Logger LOGGER = Logger.getLogger(DisplayCreateTaxReportForm.class);

    private TaxpayerService taxpayerService;

    public DisplayCreateTaxReportForm() {
        this.taxpayerService = ServiceFactoryImpl.getInstance().getTaxpayerService();
    }

    public DisplayCreateTaxReportForm(TaxpayerService taxpayerService) {
        this.taxpayerService = taxpayerService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        LOGGER.info(String.format("User, id: %d requested DisplayCreateTaxReportForm", user.getId()));

        Optional<Taxpayer> optionalTaxpayer = taxpayerService.getByEmployeeId(user.getId());

        if (!optionalTaxpayer.isPresent()) {
            LOGGER.info(String.format("Taxpayer, with user, id: %d not found ", user.getId()));
            return NEW_TAXPAYER_PAGE_IF_NOT_FOUND;

        }

        session.setAttribute("taxpayer", optionalTaxpayer.get());
        return SUCCESSFUL_PAGE;
    }
}
