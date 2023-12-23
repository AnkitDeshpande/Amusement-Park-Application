package com.masai.service;

import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.UserNotFoundException;
import com.masai.model.Address;

import java.util.Set;

/**
 * Service interface for managing user addresses.
 */
public interface AddressService {

    /**
     * Creates a new address for a user.
     *
     * @param userId  ID of the user for whom the address is to be created.
     * @param address Details of the address to be created.
     * @return Created Address object.
     * @throws SomethingWentWrongException If an unexpected issue occurs during address creation.
     */
    Address createAddress(Integer userId, Address address) throws SomethingWentWrongException;

    /**
     * Fetches all addresses associated with a specific user.
     *
     * @param userId ID of the user for which addresses are requested.
     * @return Set of addresses associated with the user.
     * @throws UserNotFoundException       If the specified user is not found.
     * @throws SomethingWentWrongException If an unexpected issue occurs while retrieving addresses.
     */
    Set<Address> getAddressesByUser(Integer userId) throws UserNotFoundException, SomethingWentWrongException;

    /**
     * Updates multiple addresses for a user.
     *
     * @param userId    ID of the user whose addresses are to be updated.
     * @param addresses Set of updated addresses for the user.
     * @return Message indicating the status of the update operation.
     * @throws UserNotFoundException       If the specified user is not found.
     * @throws SomethingWentWrongException If an unexpected issue occurs during the update process.
     */
    String updateAddressesByUser(Integer userId, Set<Address> addresses)
            throws UserNotFoundException, SomethingWentWrongException;

    /**
     * Deletes a specific address belonging to a user.
     *
     * @param userId    ID of the user from whom the address will be removed.
     * @param addressId ID of the address to be deleted.
     * @return Message indicating the status of the deletion operation.
     * @throws UserNotFoundException       If the specified user is not found.
     * @throws SomethingWentWrongException If an unexpected issue occurs during the deletion.
     */
    String deleteAddressesByUser(Integer userId, Integer addressId)
            throws UserNotFoundException, SomethingWentWrongException;
}
