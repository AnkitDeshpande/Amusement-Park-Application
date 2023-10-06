package com.masai.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.UserNotFoundException;
import com.masai.model.Address;
import com.masai.service.AddressService;

@RestController
@RequestMapping("/addresses")
public class AddressController {

	@Autowired
	private AddressService addressService;

	@PostMapping("/users/{userId}")
	public ResponseEntity<Address> createAddress(@PathVariable Integer userId, @RequestBody Address address) {
		try {
			Address createdAddress = addressService.createAddress(userId, address);
			return new ResponseEntity<>(createdAddress, HttpStatus.CREATED);
		} catch (SomethingWentWrongException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/users/{userId}")
	public ResponseEntity<Set<Address>> getAddressesByUser(@PathVariable Integer userId) {
		try {
			Set<Address> address = addressService.getAddressesByUser(userId);
			return new ResponseEntity<>(address, HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (SomethingWentWrongException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/users/{userId}")
	public ResponseEntity<String> updateAddressesByUser(@PathVariable Integer userId) {
		try {
			String message = addressService.updateAddressesByUser(userId);
			return new ResponseEntity<>(message, HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (SomethingWentWrongException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/users/{userId}")
	public ResponseEntity<Address> deleteAddressesByUser(@PathVariable Integer userId) {
		try {
			Address deletedAddress = addressService.deleteAddressesByUser(userId);
			return new ResponseEntity<>(deletedAddress, HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (SomethingWentWrongException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
