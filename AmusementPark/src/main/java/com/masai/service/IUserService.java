package com.masai.service;

import java.util.List;
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
public class IUserService implements UserService {

	@Autowired
	private UserRepository repo;

	@Autowired
	private AddressRepository addressRepository;

	@Override
	public User getUser(Integer userId) throws UserNotFoundException {
		// Try to find the user by ID
		User user = repo.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
		System.out.println(user.toString());
		return user;
	}

	@Override
	public User createUser(User user) throws SomethingWentWrongException {
		try {
			// Save the user first to generate a user_id
			User savedUser = repo.save(user);

			// Update the addresses with the saved user reference
			Set<Address> addresses = user.getAddresses();
			for (Address address : addresses) {
				address.setUser(savedUser); // Set the user reference
				addressRepository.save(address); // Save the address
			}

			return savedUser;
		} catch (Exception e) {
			throw new SomethingWentWrongException();
		}
	}

	@Override
	public String updateUser(User user) throws UserNotFoundException, SomethingWentWrongException {
		User existingUser = repo.findById(user.getUserId())
				.orElseThrow(() -> new UserNotFoundException("Couldn't find user with id: " + user.getUserId()));

		// Update user properties here
		existingUser.setFullName(user.getFullName());
		existingUser.setUsername(user.getUsername());
		existingUser.setPassword(user.getPassword());
		existingUser.setPhone(user.getPhone());
		existingUser.setEmail(user.getEmail());
		Set<Address> addresses = user.getAddresses();
		for (Address address : addresses) {
			// Check if an address with the same attributes already exists for the user
			boolean addressExists = existingUser.getAddresses().stream()
					.anyMatch(existingAddress -> existingAddress.getCity().equals(address.getCity())
							&& existingAddress.getState().equals(address.getState())
							&& existingAddress.getPincode().equals(address.getPincode()));

			if (!addressExists) {
				address.setUser(existingUser); // Set the user reference
				existingUser.getAddresses().add(address); // Add the address if it doesn't exist
			}
		}

		// Save the updated user
		repo.save(existingUser);
		return "User updated successfully.";
	}

	@Override
	public String deleteUser(Integer userId) throws UserNotFoundException {
		User user = repo.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("Couldn't find user with id: " + userId));

		// Instead of deleting the user, mark it as deleted
		user.setDeleted(true);
		repo.save(user);
		return "User deleted successfully.";
	}

	@Override
	public List<User> getAllUsers() throws SomethingWentWrongException {
		return repo.findAll();
	}

	@Override
	public User findByEmail(String email) throws UserNotFoundException {
		Optional<User> user = repo.findByEmail(email);
		if (user.isEmpty())
			throw new UserNotFoundException("No user found");
		return user.get();
	}
}
