package com.ivahnenko.taxsystem.dao;

import java.util.List;
import java.util.Optional;

import com.ivahnenko.taxsystem.model.User;

public interface UserDAO extends GenericDAO<User> {

    /**
     * Getting user from database by username.
     */
    Optional<User> getByUsername(String username);

    /**
     * Getting all users from database by type.
     */
    List<User> getAllByType(String type);

    /**
     * Getting amount of existing users from database.
     */
    int getAmount();

    /**
     * Changing user type database.
     */
    int changeType(User user);

    /**
     * Changing deleted status of user in database.
     */
    int changeDeleted(User user);

    /**
     * Checking if email already exists in database.
     */
    boolean isEmailExists(String email);

    /**
     * Checking if username already exists in database.
     */
    boolean isUsernameExists(String username);

    /**
     * Checking if username already has a taxpayer.
     */
    boolean isUserHasTaxpayer(User user);

    /**
     * Checking if username already has a taxpayer.
     */
    Optional<String> getPassword(String username);
}
