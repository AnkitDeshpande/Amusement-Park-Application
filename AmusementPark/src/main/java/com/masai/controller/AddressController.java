package com.masai.controller;

import com.masai.model.Address;
import com.masai.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

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
