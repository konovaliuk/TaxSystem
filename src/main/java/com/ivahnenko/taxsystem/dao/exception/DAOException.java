package com.ivahnenko.taxsystem.dao.exception;

public class DAOException extends RuntimeException {

    /**
     * This class defines generic exception that will wrap any exception on dao layer, such as SQLException.
     */
    private static final long serialVersionUID = 1L;

    public DAOException(String message) {
        super(message);
    }

    public DAOException(Throwable cause) {
        super(cause);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause, true, false);
    }

}
