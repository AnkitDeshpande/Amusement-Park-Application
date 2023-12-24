package com.masai.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.masai.exception.ParkNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.model.Park;
import com.masai.repository.ActivityRepository;
import com.masai.repository.ParkRepository;

@Service
public class IParkService implements ParkService {

    @Autowired
    private ParkRepository repo;

    @Autowired
    private ActivityRepository activityRepository;

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
            repo.save(existingPark);

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

    /**
     * Retrieves a paginated list of all parks.
     *
     * @param pageable Pageable object containing pagination and sorting information.
     * @return Page object containing a list of all available parks.
     * @throws SomethingWentWrongException If an unexpected issue occurs while retrieving parks.
     */
    @Override
    public Page<Park> getAllParks(Pageable pageable) throws SomethingWentWrongException {
        return repo.findAll(pageable);
    }

    /**
     * Retrieves a paginated and sorted list of all parks in ascending order.
     *
     * @param pageable Pageable object containing pagination and sorting information.
     * @return Page object containing a list of all available parks sorted in ascending order.
     * @throws SomethingWentWrongException If an unexpected issue occurs while retrieving parks.
     */
    @Override
    public Page<Park> getAllParksSortedAsc(Pageable pageable) throws SomethingWentWrongException {
        return repo.findAllParksOrderByNameAsc(pageable);
    }

    /**
     * Retrieves a paginated and sorted list of all parks in descending order.
     *
     * @param pageable Pageable object containing pagination and sorting information.
     * @return Page object containing a list of all available parks sorted in descending order.
     * @throws SomethingWentWrongException If an unexpected issue occurs while retrieving parks.
     */
    @Override
    public Page<Park> getAllParksSortedDesc(Pageable pageable) throws SomethingWentWrongException {
        return repo.findAllParksOrderByNameDesc(pageable);
    }

    /**
     * Adds a list of parks in bulk.
     *
     * @param parks List of Park objects to be added in bulk.
     * @return List of added Park objects.
     * @throws SomethingWentWrongException If an unexpected issue occurs during bulk park addition.
     */
    @Override
    public List<Park> addParksInBulk(List<Park> parks) throws SomethingWentWrongException {
        return repo.saveAll(parks);
    }
}
