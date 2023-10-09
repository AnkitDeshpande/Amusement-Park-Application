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

import com.masai.exception.ActivityNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.model.Activity;
import com.masai.service.ActivityService;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {

	@Autowired
	private ActivityService activityService;

	@GetMapping("/{parkId}")
	public ResponseEntity<Set<Activity>> getActivity(@PathVariable Integer parkId) throws ActivityNotFoundException {
		Set<Activity> activity = activityService.getActivity(parkId);
		return new ResponseEntity<>(activity, HttpStatus.OK);
	}

	@PostMapping("/park/{parkId}")
	public ResponseEntity<Activity> createActivity(@RequestBody Activity activity, @PathVariable Integer parkId)
			throws SomethingWentWrongException {
		Activity createdActivity = activityService.createActivity(parkId, activity);
		return new ResponseEntity<>(createdActivity, HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<String> updateActivity(@RequestBody Activity activity)
			throws ActivityNotFoundException, SomethingWentWrongException {
		String message = activityService.updateActivity(activity);
		return new ResponseEntity<>(message, HttpStatus.OK);
	}

	@DeleteMapping("/park/{parkId}/activity/{activityId}")
	public ResponseEntity<String> deleteActivity(@PathVariable Integer activityId, @PathVariable Integer parkId)
			throws ActivityNotFoundException {
		String message = activityService.deleteActivity(activityId, parkId);
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
}
