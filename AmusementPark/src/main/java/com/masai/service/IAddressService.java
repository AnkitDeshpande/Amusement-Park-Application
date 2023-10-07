package com.masai.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.UserNotFoundException;
import com.masai.model.Address;
import com.masai.model.User;
import com.masai.repository.AddressRepository;
import com.masai.repository.UserRepository;

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
}
