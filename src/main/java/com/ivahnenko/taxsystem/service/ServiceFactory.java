package com.ivahnenko.taxsystem.service;

/**
 * {@code ServiceFactory} is an abstract class that describes behavior of concrete {@code ServiceFactory} object.
 */
public interface ServiceFactory {

    /**
     * The method creates {@code UserService} object.
     *
     * @return created {@code UserService} object.
     */
    UserService getUserService();

    /**
     * The method returns {@code TaxpayerService} object.
     *
     * @return created {@code TaxpayerService} object.
     */
    TaxpayerService getTaxpayerService();

    /**
     * The method returns {@code TaxReportForm} object.
     *
     * @return created {@code TaxReportForm} object.
     */
    TaxReportFormService getTaxReportFormService();

    /**
     * The method returns {@code FeedbackForm} object.
     *
     * @return created {@code FeedbackForm} object.
     */
    FeedbackFormService getFeedbackFormService();
}
