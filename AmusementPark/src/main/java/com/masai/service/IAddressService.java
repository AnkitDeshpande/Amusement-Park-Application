package com.masai.service;

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
	private AddressRepository repo;

	@Autowired
	private UserRepository urepo;

	@Override
	public Address createAddress(Integer userId, Address address) throws SomethingWentWrongException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Address> getAddressesByUser(Integer userId) throws UserNotFoundException {
		User user = urepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
		return user.getAddresses();
	}

	@Override
	public String updateAddressesByUser(Integer userId) throws UserNotFoundException, SomethingWentWrongException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Address deleteAddressesByUser(Integer userId) throws UserNotFoundException, SomethingWentWrongException {
		// TODO Auto-generated method stub
		return null;
	}

}
