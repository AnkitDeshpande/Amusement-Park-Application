package com.masai.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.ParkNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.model.Park;
import com.masai.repository.ActivityRepository;
import com.masai.repository.ParkRepository;
import com.masai.repository.ReviewRepository;

@Service
public class IParkService implements ParkService {

	@Autowired
	private ParkRepository repo;

	@Autowired
	private ActivityRepository activityRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Override
	public Park getPark(Integer parkId) throws ParkNotFoundException, SomethingWentWrongException {
		return repo.findById(parkId)
				.orElseThrow(() -> new ParkNotFoundException("Couldn't find park with id: " + parkId));
	}

	@Override
	public List<Park> getAllParks() throws SomethingWentWrongException {
		try {
			return repo.findAll();
		} catch (Exception e) {
			throw new SomethingWentWrongException();
		}
	}

	@Override
	public Park createPark(Park park) throws SomethingWentWrongException {
		try {
			// Set the park reference for each activity
			if (park.getActivities() != null) {
				park.getActivities().forEach(activity -> activity.setPark(park));
			}

			// Save the park entity first
			Park savedPark = repo.save(park);

			// Save the activity entities
			if (park.getActivities() != null) {
				park.getActivities().forEach(activityRepository::save);
			}

			return savedPark;
		} catch (Exception e) {
			e.printStackTrace();
			throw new SomethingWentWrongException();
		}
	}

	@Override
	public String updatePark(Park park) throws ParkNotFoundException, SomethingWentWrongException {
		Park existingPark = repo.findById(park.getParkId())
				.orElseThrow(() -> new ParkNotFoundException("Couldn't find park with id: " + park.getParkId()));
		try {
			// Update park properties here
			existingPark.setName(park.getName());
			existingPark.setLocation(park.getLocation());
			existingPark.setDescription(park.getDescription());
			existingPark.setOpeningHours(park.getOpeningHours());
			existingPark.setClosingHours(park.getClosingHours());

			// Set the park reference for each activity
			if (park.getActivities() != null) {
				park.getActivities().forEach(activity -> activity.setPark(existingPark));
			}

			// Save the updated park
			Park updatedPark = repo.save(existingPark);

			// Save the updated activity entities
			if (park.getActivities() != null) {
				park.getActivities().forEach(activityRepository::save);
			}

			return "Park updated successfully.";
		} catch (Exception e) {
			e.printStackTrace();
			throw new SomethingWentWrongException();
		}
	}

	@Override
	public String deletePark(Integer parkId) throws ParkNotFoundException {
		Park park = repo.findById(parkId)
				.orElseThrow(() -> new ParkNotFoundException("Couldn't find park with id: " + parkId));

		// Instead of deleting the park, mark it as deleted
		park.setDeleted(true);
		repo.save(park);
		return "Park deleted successfully.";
	}
}
