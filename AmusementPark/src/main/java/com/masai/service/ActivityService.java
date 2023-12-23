package com.masai.service;

import com.masai.exception.ActivityNotFoundException;
import com.masai.exception.ParkNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.model.Activity;

import java.util.List;
import java.util.Set;

/**
 * Service interface for managing activities.
 */
public interface ActivityService {

    /**
     * Retrieves activities associated with a specific park.
     *
     * @param parkId ID of the park for which activities are requested.
     * @return Set of activities associated with the specified park.
     * @throws ActivityNotFoundException   If no activities are found for the specified park.
     * @throws SomethingWentWrongException If an unexpected issue occurs while fetching activities.
     */
    Set<Activity> getActivity(Integer parkId) throws ActivityNotFoundException, SomethingWentWrongException;

    /**
     * Fetches a list of activities related to a particular park.
     *
     * @param parkId ID of the park for which activities are requested.
     * @return List of activities related to the specified park.
     * @throws ParkNotFoundException       If the specified park is not found.
     * @throws SomethingWentWrongException If an unexpected issue occurs while retrieving activities.
     */
    List<Activity> getActivitiesByPark(Integer parkId) throws ParkNotFoundException, SomethingWentWrongException;

    /**
     * Creates a new activity for a given park.
     *
     * @param parkId   ID of the park for which the activity is to be created.
     * @param activity Details of the activity to be created.
     * @return The created Activity object.
     * @throws ParkNotFoundException       If the specified park is not found.
     * @throws SomethingWentWrongException If an unexpected issue occurs during the activity creation.
     */
    Activity createActivity(Integer parkId, Activity activity)
            throws ParkNotFoundException, SomethingWentWrongException;

    /**
     * Updates an existing activity.
     *
     * @param activity Activity object containing updated information.
     * @return Message indicating the status of the update operation.
     * @throws ActivityNotFoundException   If the specified activity is not found.
     * @throws SomethingWentWrongException If an unexpected issue occurs during the update process.
     */
    String updateActivity(Activity activity) throws ActivityNotFoundException, SomethingWentWrongException;

    /**
     * Deletes a specific activity from a park.
     *
     * @param activityId ID of the activity to be deleted.
     * @param parkId     ID of the park from which the activity will be removed.
     * @return Message indicating the status of the deletion operation.
     * @throws ActivityNotFoundException   If the specified activity is not found.
     * @throws SomethingWentWrongException If an unexpected issue occurs during the deletion.
     */
    String deleteActivity(Integer activityId, Integer parkId)
            throws ActivityNotFoundException, SomethingWentWrongException;
}
