package com.masai.service;

import com.masai.exception.ParkNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.model.Park;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for managing parks.
 */
public interface ParkService {

    /**
     * Retrieves a park by its ID.
     *
     * @param parkId ID of the park to retrieve.
     * @return Park object corresponding to the specified ID.
     * @throws ParkNotFoundException       If the specified park is not found.
     * @throws SomethingWentWrongException If an unexpected issue occurs while retrieving the park.
     */
    Park getPark(Integer parkId) throws ParkNotFoundException, SomethingWentWrongException;

    /**
     * Retrieves a list of all parks.
     *
     * @return List of all available parks.
     * @throws SomethingWentWrongException If an unexpected issue occurs while retrieving parks.
     */
    List<Park> getAllParks() throws SomethingWentWrongException;

    /**
     * Creates a new park.
     *
     * @param park Park object containing details of the park to be created.
     * @return The created Park object.
     * @throws SomethingWentWrongException If an unexpected issue occurs during park creation.
     */
    Park createPark(Park park) throws SomethingWentWrongException;

    /**
     * Updates an existing park.
     *
     * @param park Park object containing updated information.
     * @return Message indicating the status of the update operation.
     * @throws ParkNotFoundException       If the specified park is not found.
     * @throws SomethingWentWrongException If an unexpected issue occurs during the update process.
     */
    String updatePark(Park park) throws ParkNotFoundException, SomethingWentWrongException;

    /**
     * Deletes a specific park.
     *
     * @param parkId ID of the park to be deleted.
     * @return Message indicating the status of the deletion operation.
     * @throws ParkNotFoundException       If the specified park is not found.
     * @throws SomethingWentWrongException If an unexpected issue occurs during the deletion.
     */
    String deletePark(Integer parkId) throws ParkNotFoundException, SomethingWentWrongException;

    /**
     * Retrieves a paginated list of all parks.
     *
     * @param pageable Pageable object containing pagination and sorting information.
     * @return Page object containing a list of all available parks.
     * @throws SomethingWentWrongException If an unexpected issue occurs while retrieving parks.
     */
    Page<Park> getAllParks(Pageable pageable) throws SomethingWentWrongException;

    /**
     * Retrieves a paginated and sorted list of all parks in ascending order.
     *
     * @param pageable Pageable object containing pagination and sorting information.
     * @return Page object containing a list of all available parks sorted in ascending order.
     * @throws SomethingWentWrongException If an unexpected issue occurs while retrieving parks.
     */
    Page<Park> getAllParksSortedAsc(Pageable pageable) throws SomethingWentWrongException;

    /**
     * Retrieves a paginated and sorted list of all parks in descending order.
     *
     * @param pageable Pageable object containing pagination and sorting information.
     * @return Page object containing a list of all available parks sorted in descending order.
     * @throws SomethingWentWrongException If an unexpected issue occurs while retrieving parks.
     */
    Page<Park> getAllParksSortedDesc(Pageable pageable) throws SomethingWentWrongException;

    /**
     * Adds a list of parks in bulk.
     *
     * @param parks List of Park objects to be added in bulk.
     * @return List of added Park objects.
     * @throws SomethingWentWrongException If an unexpected issue occurs during bulk park addition.
     */
    List<Park> addParksInBulk(List<Park> parks) throws SomethingWentWrongException;
}
