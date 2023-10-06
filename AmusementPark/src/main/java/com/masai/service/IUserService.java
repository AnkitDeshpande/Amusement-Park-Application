package com.masai.service;

import java.util.List;
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
	private AddressRepository arepo;

	@Override
	public User getUser(Integer userId) throws UserNotFoundException {
		User user = repo.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("Couldn't find user with id: " + userId));
		
		return user;
	}

	@Override
	public User createUser(User user) throws SomethingWentWrongException {

		User user1;
		try {

			Set<Address> set = user.getAddresses();
			for (Address add : set) {
				arepo.save(add);
			}
			user1 = repo.save(user);

		} catch (Exception e) {
			throw new SomethingWentWrongException();
		}

		return user1;
	}

	@Override
	public String updateUser(User user) throws UserNotFoundException, SomethingWentWrongException {

		return null;
	}

	@Override
	public String deleteUser(Integer userId) throws UserNotFoundException {

		return null;
	}

}
