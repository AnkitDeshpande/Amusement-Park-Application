package com.masai.controller;

import java.util.List;
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

import com.masai.model.Address;
import com.masai.service.AddressService;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

	@Autowired
	private AddressService addressService;

	@PostMapping("/users/{userId}")
	public ResponseEntity<Address> createAddress(@PathVariable Integer userId, @RequestBody Address address) {
		Address createdAddress = addressService.createAddress(userId, address);
		return new ResponseEntity<>(createdAddress, HttpStatus.CREATED);
	}

	@PostMapping("/bulk/users/{userId}")
	public ResponseEntity<List<Address>> createAddresses(@PathVariable Integer userId,
			@RequestBody Set<Address> addresses) {
		List<Address> createdAddresses = addressService.addAddressesInBulk(addresses);
		return new ResponseEntity<>(createdAddresses, HttpStatus.CREATED);
	}

	@GetMapping("/users/{userId}")
	public ResponseEntity<Set<Address>> getAddressesByUser(@PathVariable Integer userId) {
		Set<Address> address = addressService.getAddressesByUser(userId);
		return new ResponseEntity<>(address, HttpStatus.OK);
	}

	@PutMapping("/users/{userId}/update-addresses")
	public ResponseEntity<String> updateAddressesByUser(@PathVariable Integer userId,
			@RequestBody Set<Address> addresses) {
		String message = addressService.updateAddressesByUser(userId, addresses);
		return new ResponseEntity<>(message, HttpStatus.OK);
	}

	@DeleteMapping("/users/{userId}/delete-addresses/{addressId}")
	public ResponseEntity<String> deleteAddressesByUser(@PathVariable Integer userId, @PathVariable Integer addressId) {
		String deletedAddress = addressService.deleteAddressesByUser(userId, addressId);
		return new ResponseEntity<>(deletedAddress, HttpStatus.OK);
	}
}
