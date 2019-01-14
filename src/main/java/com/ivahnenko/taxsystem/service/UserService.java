package com.ivahnenko.taxsystem.service;

import java.util.List;
import java.util.Optional;

import com.ivahnenko.taxsystem.model.User;


public interface UserService {

    /**
     * Method for creation of a new user.
     */
    boolean save(User user);

    /**
     * Method for getting user by id.
     */
    Optional<User> getById(int id);

    /**
     * Method for getting user by username.
     */
    Optional<User> getByUsername(String username);

    /**
     * Method for user login
     */
    Optional<User> login(String username, String password);

    /**
     * Method for changing user privileges.
     */
    boolean changeType(User user);

    /**
     * Method for activation / deactivation of a user.
     */
    boolean changeDeletionStatus(User user);

    /**
     * Method for getting all users.
     */
    List<User> getAll();

    /**
     * Method for getting all users by type.
     */
    List<User> getAllByType(String type);

    /**
     * Method for getting amount of existing users.
     */
    int getAmount();

    /**
     * Method for checking if email already exists in database.
     */
    boolean isEmailExists(String email);

    /**
     * Method for checking if username already exists in  database.
     */
    boolean isUsernameExists(String username);

    /**
     * Method for checking if user is assigned to taxpayer.
     */
    boolean isUserHasTaxpayer(User user);
}
