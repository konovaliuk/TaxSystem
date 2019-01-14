package com.ivahnenko.taxsystem.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.ivahnenko.taxsystem.dao.connection.DAOConnection;
import com.ivahnenko.taxsystem.dao.exception.DAOConfigurationException;

/**
 * {@code DAOFactory} is an abstract class that describes behavior of concrete {@code DAOFactory} object.
 */
public abstract class DAOFactory {
    private static final Logger LOGGER = Logger.getLogger(DAOFactory.class);

    private static final String DB_PROPERTIES = "/database.properties";
    private static final String DB_CLASS = "daoFactory";
    private static DAOFactory instance;

    /**
     * The method creates concrete {@code DAOFactory} object using parameters from database properties.
     *
     * @return {@code DAOFactory} object.
     */
    public static DAOFactory getInstance() {
        if (instance == null)
            synchronized (DAOFactory.class) {
                if (instance == null) {
                    try {
                        InputStream stream = DAOFactory.class.getResourceAsStream(DB_PROPERTIES);
                        Properties properties = new Properties();
                        properties.load(stream);
                        instance = (DAOFactory) Class.forName(properties.getProperty(DB_CLASS)).newInstance();
                    } catch (IOException | IllegalAccessException | ClassNotFoundException | InstantiationException e) {
                        String exception = "Error in reading daoFactory properties ";
                        LOGGER.error(exception, e);
                        throw new DAOConfigurationException(exception, e);
                    }
                }
            }
        return instance;
    }

    /**
     * The method creates {@code UserDAO} object.
     *
     * @param connection {@code DaoConnection} object.
     * @return created {@code UserDAO} object.
     */
    public abstract UserDAO getUserDAO(DAOConnection connection);

    /**
     * The method creates {@code TaxpayerDAO} object.
     *
     * @param connection {@code DaoConnection} object.
     * @return created {@code TaxpayerDAO} object.
     */
    public abstract TaxpayerDAO getTaxpayerDAO(DAOConnection connection);

    /**
     * The method creates {@code TaxReportFormDAO} object.
     *
     * @param connection {@code DaoConnection} object.
     * @return created {@code TaxReportFormDAO} object.
     */
    public abstract TaxReportFormDAO getTaxReportFormDAO(DAOConnection connection);

    /**
     * The method creates {@code FeedbackFormDAO} object.
     *
     * @param connection {@code DaoConnection} object.
     * @return created {@code FeedbackFormDAO} object.
     */
    public abstract FeedbackFormDAO getFeedbackFormDAO(DAOConnection connection);
}
