package com.masai.service;

import java.util.Set;

import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.UserNotFoundException;
import com.masai.model.Address;

public interface AddressService {
	public Address createAddress(Integer userId, Address address) throws SomethingWentWrongException;

	public Set<Address> getAddressesByUser(Integer userId) throws UserNotFoundException, SomethingWentWrongException;

	public String updateAddressesByUser(Integer userId, Set<Address> addresses)
			throws UserNotFoundException, SomethingWentWrongException;

	public String deleteAddressesByUser(Integer userId, Integer addressId)
			throws UserNotFoundException, SomethingWentWrongException;

}
