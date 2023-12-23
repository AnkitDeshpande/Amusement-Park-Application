package com.masai.service;

import com.masai.exception.ActivityNotFoundException;
import com.masai.exception.ParkNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.model.Activity;
import com.masai.model.Park;
import com.masai.repository.ActivityRepository;
import com.masai.repository.ParkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    /**
     * Retrieves paginated and sorted activities associated with a specific park.
     *
     * @param parkId   ID of the park for which activities are requested.
     * @param pageable Pageable object containing pagination and sorting information.
     * @return Page object containing a list of activities associated with the specified park.
     * @throws ActivityNotFoundException   If no activities are found for the specified park.
     * @throws SomethingWentWrongException If an unexpected issue occurs while fetching activities.
     */
    @Override
    public Page<Activity> getActivitiesByPark(Integer parkId, Pageable pageable) throws ActivityNotFoundException, SomethingWentWrongException {
        Optional<Park> parkOptional = parkRepository.findById(parkId);
        if (parkOptional.isPresent() && !parkOptional.get().isDeleted()) {
            return activityRepository.findByPark(parkOptional.get(), pageable);
        } else {
            throw new ActivityNotFoundException("No activities found for the specified park.");
        }
    }

    /**
     * Retrieves a paginated and sorted list of activities related to a particular park in ascending order.
     *
     * @param parkId   ID of the park for which activities are requested.
     * @param pageable Pageable object containing pagination and sorting information.
     * @return Page object containing a list of activities related to the specified park sorted in ascending order.
     * @throws ParkNotFoundException       If the specified park is not found.
     * @throws SomethingWentWrongException If an unexpected issue occurs while retrieving activities.
     */
    @Override
    public Page<Activity> getActivitiesByParkSortedAsc(Integer parkId, Pageable pageable) throws ParkNotFoundException, SomethingWentWrongException {
        Optional<Park> parkOptional = parkRepository.findById(parkId);
        if (parkOptional.isPresent() && !parkOptional.get().isDeleted()) {
            return activityRepository.findByParkOrderByNameAsc(parkOptional.get(), pageable);
            // Replace 'SomeField' with the field you want to sort by in ascending order
        } else {
            throw new ParkNotFoundException("Park not found with ID: " + parkId);
        }
    }

    /**
     * Retrieves a paginated and sorted list of activities related to a particular park in descending order.
     *
     * @param parkId   ID of the park for which activities are requested.
     * @param pageable Pageable object containing pagination and sorting information.
     * @return Page object containing a list of activities related to the specified park sorted in descending order.
     * @throws ParkNotFoundException       If the specified park is not found.
     * @throws SomethingWentWrongException If an unexpected issue occurs while retrieving activities.
     */
    @Override
    public Page<Activity> getActivitiesByParkSortedDesc(Integer parkId, Pageable pageable) throws ParkNotFoundException, SomethingWentWrongException {
        Optional<Park> parkOptional = parkRepository.findById(parkId);
        if (parkOptional.isPresent() && !parkOptional.get().isDeleted()) {
            return activityRepository.findByParkOrderByNameDesc(parkOptional.get(), pageable);
            // Replace 'SomeField' with the field you want to sort by in descending order
        } else {
            throw new ParkNotFoundException("Park not found with ID: " + parkId);
        }
    }

    /**
     * Adds a list of activities in bulk.
     *
     * @param activities List of Activity objects to be added in bulk.
     * @return List of added Activity objects.
     * @throws SomethingWentWrongException If an unexpected issue occurs during bulk activity addition.
     */
    @Override
    public List<Activity> addActivitiesInBulk(List<Activity> activities) throws SomethingWentWrongException {
        return activityRepository.saveAll(activities);
    }
}
