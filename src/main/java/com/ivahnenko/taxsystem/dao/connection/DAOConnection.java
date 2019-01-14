package com.ivahnenko.taxsystem.dao.connection;

import java.sql.Connection;

/**
 * {@code DAOConnection} describes behavior of concrete {@code DAOConnection} object.
 * It managing the connection pool and creating new connections.
 */
public interface DAOConnection extends AutoCloseable {

    /**
     * Method for beginning transactional operation.
     */
    void begin();

    /**
     * Method for rollback of transactional operation.
     */
    void rollback();

    /**
     * Method for committing of transactional operation.
     */
    void commit();

    /**
     * Method for returning connection to connection pool.
     */
    void close();

    /**
     * Method for getting connection from connection pool.
     */
    Connection getConnection();

}
