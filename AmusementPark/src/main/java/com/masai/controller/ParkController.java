package com.masai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.masai.exception.ParkNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.model.Park;
import com.masai.service.ParkService;

@RestController
@RequestMapping("/api/parks")
public class ParkController {

	@Autowired
	private ParkService parkService;

	@GetMapping("/{parkId}")
	public ResponseEntity<Park> getPark(@PathVariable Integer parkId) throws ParkNotFoundException {
		Park park = parkService.getPark(parkId);
		return new ResponseEntity<>(park, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Park> createPark(@RequestBody Park park) throws SomethingWentWrongException {
		Park createdPark = parkService.createPark(park);
		return new ResponseEntity<>(createdPark, HttpStatus.CREATED);
	}

	@PutMapping("/{parkId}")
	public ResponseEntity<String> updatePark(@RequestBody Park park, @PathVariable Integer parkId)
			throws ParkNotFoundException, SomethingWentWrongException {
		park.setParkId(parkId);
		String message = parkService.updatePark(park);
		return new ResponseEntity<>(message, HttpStatus.OK);
	}

	@DeleteMapping("/{parkId}")
	public ResponseEntity<String> deletePark(@PathVariable Integer parkId) throws ParkNotFoundException {
		String message = parkService.deletePark(parkId);
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
}
