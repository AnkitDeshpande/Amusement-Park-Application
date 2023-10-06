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

import com.masai.exception.ActivityNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.model.Activity;
import com.masai.service.ActivityService;

@RestController
@RequestMapping("/activities")
public class ActivityController {

	@Autowired
	private ActivityService activityService;

	@GetMapping("/{activityId}")
	public ResponseEntity<Activity> getActivity(@PathVariable Integer activityId) {
		try {
			Activity activity = activityService.getActivity(activityId);
			return new ResponseEntity<>(activity, HttpStatus.OK);
		} catch (ActivityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	public ResponseEntity<Activity> createActivity(@RequestBody Activity activity) {
		try {
			Activity createdActivity = activityService.createActivity(activity.getPark().getParkId(), activity);
			return new ResponseEntity<>(createdActivity, HttpStatus.CREATED);
		} catch (SomethingWentWrongException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping
	public ResponseEntity<String> updateActivity(@RequestBody Activity activity) {
		try {
			String message = activityService.updateActivity(activity);
			return new ResponseEntity<>(message, HttpStatus.OK);
		} catch (ActivityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (SomethingWentWrongException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{activityId}")
	public ResponseEntity<String> deleteActivity(@PathVariable Integer activityId) {
		try {
			String message = activityService.deleteActivity(activityId);
			return new ResponseEntity<>(message, HttpStatus.OK);
		} catch (ActivityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
