package com.masai.controller;

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

import com.masai.exception.ParkNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.model.Park;
import com.masai.service.ParkService;

@RestController
@RequestMapping("/parks")
public class ParkController {

	@Autowired
	private ParkService parkService;

	@GetMapping("/{parkId}")
	public ResponseEntity<Park> getPark(@PathVariable Integer parkId) {
		try {
			Park park = parkService.getPark(parkId);
			return new ResponseEntity<>(park, HttpStatus.OK);
		} catch (ParkNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	public ResponseEntity<Park> createPark(@RequestBody Park park) {
		try {
			Park createdPark = parkService.createPark(park);
			return new ResponseEntity<>(createdPark, HttpStatus.CREATED);
		} catch (SomethingWentWrongException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping
	public ResponseEntity<String> updatePark(@RequestBody Park park) {
		try {
			String message = parkService.updatePark(park);
			return new ResponseEntity<>(message, HttpStatus.OK);
		} catch (ParkNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (SomethingWentWrongException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{parkId}")
	public ResponseEntity<String> deletePark(@PathVariable Long parkId) {
		try {
			String message = parkService.deletePark(parkId);
			return new ResponseEntity<>(message, HttpStatus.OK);
		} catch (ParkNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
