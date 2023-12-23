package com.masai.service;

import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.UserNotFoundException;
import com.masai.model.Address;
import com.masai.model.User;
import com.masai.repository.AddressRepository;
import com.masai.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class IAddressService implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Address createAddress(Integer userId, Address address) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            address.setUser(user);
            return addressRepository.save(address);
        } else {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }
    }

    @Override
    public Set<Address> getAddressesByUser(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getAddresses();
        } else {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }
    }

    @Override
    public String updateAddressesByUser(Integer userId, Set<Address> addresses) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            addresses.forEach(add -> add.setUser(user));
            user.setAddresses(addresses);
            userRepository.save(user);
            addresses.forEach(add -> addressRepository.save(add));

            return "Addresses updated successfully.";
        } else {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }
    }

    @Override
    public String deleteAddressesByUser(Integer userId, Integer addressId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Set<Address> addresses = user.getAddresses();
            Address addressToDelete = addresses.stream().filter(a -> a.getAddressId().equals(addressId)).findFirst()
                    .orElseThrow(() -> new SomethingWentWrongException("Address not found with ID: " + addressId));
            addressToDelete.setRemoved(true);
            userRepository.save(user);
            return "Address removed successfully.";
        } else {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }
    }

    /**
     * Retrieves a paginated list of addresses associated with a specific user.
     *
     * @param userId   ID of the user for which addresses are requested.
     * @param pageable Pageable object containing pagination and sorting information.
     * @return Page object containing a list of addresses associated with the user.
     * @throws UserNotFoundException       If the specified user is not found.
     * @throws SomethingWentWrongException If an unexpected issue occurs while retrieving addresses.
     */
    @Override
    public Page<Address> getAddressesByUser(Integer userId, Pageable pageable) throws UserNotFoundException, SomethingWentWrongException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return addressRepository.findAdressesByUser(user, pageable);
        } else {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }
    }

    /**
     * Retrieves a paginated and sorted list of addresses associated with a specific user in ascending order.
     *
     * @param userId   ID of the user for which addresses are requested.
     * @param pageable Pageable object containing pagination and sorting information.
     * @return Page object containing a list of addresses associated with the user sorted in ascending order.
     * @throws UserNotFoundException       If the specified user is not found.
     * @throws SomethingWentWrongException If an unexpected issue occurs while retrieving addresses.
     */
    @Override
    public Page<Address> getAddressesByUserSortedAsc(Integer userId, Pageable pageable) throws UserNotFoundException, SomethingWentWrongException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return addressRepository.findAdressesByUserOrderByCityAsc(user, pageable);
        } else {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }
    }

    /**
     * Retrieves a paginated and sorted list of addresses associated with a specific user in descending order.
     *
     * @param userId   ID of the user for which addresses are requested.
     * @param pageable Pageable object containing pagination and sorting information.
     * @return Page object containing a list of addresses associated with the user sorted in descending order.
     * @throws UserNotFoundException       If the specified user is not found.
     * @throws SomethingWentWrongException If an unexpected issue occurs while retrieving addresses.
     */
    @Override
    public Page<Address> getAddressesByUserSortedDesc(Integer userId, Pageable pageable) throws UserNotFoundException, SomethingWentWrongException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return addressRepository.findAdressesByUserOrderByCityDesc(user, pageable);
        } else {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }
    }

    /**
     * Adds a list of addresses in bulk.
     *
     * @param addresses List of Address objects to be added in bulk.
     * @return List of added Address objects.
     * @throws SomethingWentWrongException If an unexpected issue occurs during bulk address addition.
     */
    @Override
    public List<Address> addAddressesInBulk(List<Address> addresses) throws SomethingWentWrongException {
        return addressRepository.saveAll(addresses);
    }
}
