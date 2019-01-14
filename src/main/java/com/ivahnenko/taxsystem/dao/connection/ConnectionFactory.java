package com.ivahnenko.taxsystem.dao.connection;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.ivahnenko.taxsystem.dao.exception.DAOConfigurationException;

/**
 * {@code ConnectionFactory} is an abstract class that describes behavior of concrete {@code ConnectionFactory} object.
 */
public abstract class ConnectionFactory {
    private static final String DB_PROPERTIES = "/database.properties";
    private static final String DB_CLASS = "connectionFactory";

    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class);

    private static ConnectionFactory instance;

    /**
     * The method creates concrete {@code ConnectionFactory} object using parameters from database properties.
     *
     * @return {@code ConnectionFactory} object.
     */
    public static ConnectionFactory getInstance() {
        if (instance == null)
            synchronized (ConnectionFactory.class) {
                if (instance == null) {
                    try {
                        InputStream stream = ConnectionFactory.class.getResourceAsStream(DB_PROPERTIES);
                        Properties properties = new Properties();
                        properties.load(stream);
                        instance = (ConnectionFactory) Class.forName(properties.getProperty(DB_CLASS)).newInstance();
                    } catch (IOException | IllegalAccessException | ClassNotFoundException | InstantiationException e) {
                        String exception = "Error in reading database properties ";
                        LOGGER.error(exception, e);
                        throw new DAOConfigurationException(exception, e);
                    }
                }
            }
        return instance;
    }

    /**
     * The method creates {@code DAOConnection} object.
     *
     * @return created {@code DAOConnection} object.
     */
    public abstract DAOConnection getConnection();
}
