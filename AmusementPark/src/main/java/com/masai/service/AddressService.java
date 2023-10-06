package com.masai.service;

import java.util.Set;

import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.UserNotFoundException;
import com.masai.model.Address;

public interface AddressService {
	public Address createAddress(Integer userId, Address address) throws SomethingWentWrongException;

	public Set<Address> getAddressesByUser(Integer userId) throws UserNotFoundException, SomethingWentWrongException;

	public String updateAddressesByUser(Integer userId) throws UserNotFoundException, SomethingWentWrongException;

	public Address deleteAddressesByUser(Integer userId) throws UserNotFoundException, SomethingWentWrongException;

}
