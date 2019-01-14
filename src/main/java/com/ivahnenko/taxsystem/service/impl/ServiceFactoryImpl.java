package com.ivahnenko.taxsystem.service.impl;

import org.apache.log4j.Logger;

import com.ivahnenko.taxsystem.service.FeedbackFormService;
import com.ivahnenko.taxsystem.service.ServiceFactory;
import com.ivahnenko.taxsystem.service.TaxReportFormService;
import com.ivahnenko.taxsystem.service.TaxpayerService;
import com.ivahnenko.taxsystem.service.UserService;

public class ServiceFactoryImpl implements ServiceFactory {
    private static final Logger LOGGER = Logger.getLogger(ServiceFactoryImpl.class);

    private static ServiceFactoryImpl instance;

    private final UserService userService = new UserServiceImpl();
    private final TaxpayerService taxpayerService = new TaxpayerServiceImpl();
    private final TaxReportFormService taxReportFormService = new TaxReportFormServiceImpl();
    private final FeedbackFormService feedbackFormService = new FeedbackFormServiceImpl();

    /**
     * The method creates concrete {@code ServiceFactory} object using parameters from database properties.
     *
     * @return {@code ServiceFactory} object.
     */
    public static ServiceFactory getInstance() {
        if (instance == null) {
            synchronized (ServiceFactory.class) {
                if (instance == null) {
                    instance = new ServiceFactoryImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public UserService getUserService() {
        return userService;
    }

    @Override
    public TaxpayerService getTaxpayerService() {
        return taxpayerService;
    }

    @Override
    public TaxReportFormService getTaxReportFormService() {
        return taxReportFormService;
    }

    @Override
    public FeedbackFormService getFeedbackFormService() {
        return feedbackFormService;
    }
}
