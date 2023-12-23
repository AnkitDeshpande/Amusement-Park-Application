package com.masai.service;

import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.UserNotFoundException;
import com.masai.model.User;

import java.util.List;

/**
 * Service interface for managing users.
 */
public interface UserService {

    /**
     * Retrieves a user by ID.
     *
     * @param userId ID of the user to retrieve.
     * @return User object corresponding to the specified ID.
     * @throws UserNotFoundException If the specified user is not found.
     */
    User getUser(Integer userId) throws UserNotFoundException;

    /**
     * Creates a new user.
     *
     * @param user User object containing details of the user to be created.
     * @return The created User object.
     * @throws SomethingWentWrongException If an unexpected issue occurs during user creation.
     */
    User createUser(User user) throws SomethingWentWrongException;

    /**
     * Updates an existing user.
     *
     * @param user User object containing updated information.
     * @return Message indicating the status of the update operation.
     * @throws UserNotFoundException       If the specified user is not found.
     * @throws SomethingWentWrongException If an unexpected issue occurs during the update process.
     */
    String updateUser(User user) throws UserNotFoundException, SomethingWentWrongException;

    /**
     * Deletes a specific user.
     *
     * @param userId ID of the user to be deleted.
     * @return Message indicating the status of the deletion operation.
     * @throws UserNotFoundException If the specified user is not found.
     */
    String deleteUser(Integer userId) throws UserNotFoundException;

    /**
     * Retrieves a list of all users.
     *
     * @return List of all available users.
     * @throws SomethingWentWrongException If an unexpected issue occurs while retrieving users.
     */
    List<User> getAllUsers() throws SomethingWentWrongException;

    /**
     * Retrieves a user by email.
     *
     * @param email Email of the user to retrieve.
     * @return User object corresponding to the specified email.
     * @throws UserNotFoundException If the user with the specified email is not found.
     */
    User findByEmail(String email) throws UserNotFoundException;
}
