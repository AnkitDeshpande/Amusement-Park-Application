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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class IUserService implements UserService, UserDetailsService {

	@Autowired
	private UserRepository repo;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

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
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setRole("USER");
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

	/**
	 * Creates a new Admin user.
	 *
	 * @param user User object containing details of the user to be created.
	 * @return The created User object.
	 * @throws SomethingWentWrongException If an unexpected issue occurs during user
	 *                                     creation.
	 */
	@Override
	public User createAdmin(User user) throws SomethingWentWrongException {
		try {
			// Save the user first to generate a user_id
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setRole("ADMIN");
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

	/**
	 * Retrieves a page of users based on the provided pagination criteria.
	 *
	 * @param pageable Pagination information such as page number, size, sorting,
	 *                 etc.
	 * @return A Page object containing the users based on the pagination criteria.
	 * @throws SomethingWentWrongException If an error occurs while fetching the
	 *                                     users.
	 */
	@Override
	public Page<User> getAllUsers(Pageable pageable) throws SomethingWentWrongException {
		return repo.findAll(pageable);
	}

	/**
	 * Locates the user based on the username. In the actual implementation, the
	 * search may possibly be case sensitive, or case insensitive depending on how
	 * the implementation instance is configured. In this case, the
	 * <code>UserDetails</code> object that comes back may have a username that is
	 * of a different case than what was actually requested..
	 *
	 * @param username the username identifying the user whose data is required.
	 * @return a fully populated user record (never <code>null</code>)
	 * @throws UsernameNotFoundException if the user could not be found or the user
	 *                                   has no GrantedAuthority
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repo.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not Found !! "));
		return user;
	}
}
