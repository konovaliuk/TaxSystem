package com.ivahnenko.taxsystem.dao.exception;

public class DAOConfigurationException extends DAOException {

    /**
     * This class defines exceptions in reading DAO configuration or properties files.
     */
    private static final long serialVersionUID = 1L;

    public DAOConfigurationException(String message) {
        super(message);
    }

    public DAOConfigurationException(Throwable cause) {
        super(cause);
    }

    public DAOConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

}
