package com.masai.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.ActivityNotFoundException;
import com.masai.exception.ParkNotFoundException;
import com.masai.model.Activity;
import com.masai.model.Park;
import com.masai.repository.ActivityRepository;
import com.masai.repository.ParkRepository;

@Service
public class IActivityService implements ActivityService {

	@Autowired
	private ActivityRepository activityRepository;

	@Autowired
	private ParkRepository parkRepository;

	@Override
	public Set<Activity> getActivity(Integer parkId) {
		Optional<Park> parkOptional = parkRepository.findById(parkId);
		if (parkOptional.isPresent() && !parkOptional.get().isDeleted()) {
			return parkOptional.get().getActivities();
		} else {
			throw new ActivityNotFoundException("Park not found with ID: " + parkId);
		}
	}

	@Override
	public List<Activity> getActivitiesByPark(Integer parkId) {
		Optional<Park> parkOptional = parkRepository.findById(parkId);
		if (parkOptional.isPresent()) {
			Park park = parkOptional.get();
			return activityRepository.findByPark(park);
		} else {
			throw new ParkNotFoundException("Park not found with ID: " + parkId);
		}
	}

	@Override
	public Activity createActivity(Integer parkId, Activity activity) {
		Optional<Park> parkOptional = parkRepository.findById(parkId);
		if (parkOptional.isPresent()) {
			Park park = parkOptional.get();
			activity.setPark(park);
			return activityRepository.save(activity);
		} else {
			throw new ParkNotFoundException("Park not found with ID: " + parkId);
		}
	}

	@Override
	public String updateActivity(Activity activity) {
		Optional<Activity> existingActivityOptional = activityRepository.findById(activity.getId());
		if (existingActivityOptional.isPresent()) {
			Activity existingActivity = existingActivityOptional.get();
			existingActivity.setName(activity.getName());
			existingActivity.setDescription(activity.getDescription());
			existingActivity.setPrice(activity.getPrice());
			activityRepository.save(existingActivity);
			return "Activity updated successfully.";
		} else {
			throw new ActivityNotFoundException("Activity not found with ID: " + activity.getId());
		}
	}

	@Override
	public String deleteActivity(Integer parkId, Integer activityId) {
		Optional<Park> parkOptional = parkRepository.findById(parkId);
		if (parkOptional.isPresent() && !parkOptional.get().isDeleted()) {
			Optional<Activity> activityOptional = activityRepository.findById(activityId);
			if (activityOptional.isPresent() && !activityOptional.get().isDeleted()) {
				Activity activity = activityOptional.get();
				activity.setDeleted(true); // Mark activity as deleted instead of removing it
				activityRepository.save(activity);
				return "Activity deleted successfully.";
			} else {
				throw new ActivityNotFoundException("Activity not found with ID: " + activityId);
			}
		} else {
			throw new ActivityNotFoundException("Park not found with ID: " + parkId);
		}
	}
}
