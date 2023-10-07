package com.masai.service;

import java.util.List;
import java.util.Set;

import com.masai.exception.ActivityNotFoundException;
import com.masai.exception.ParkNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.model.Activity;

public interface ActivityService {

	Set<Activity> getActivity(Integer parkId) throws ActivityNotFoundException, SomethingWentWrongException;

	List<Activity> getActivitiesByPark(Integer parkId) throws ParkNotFoundException, SomethingWentWrongException;

	Activity createActivity(Integer parkId, Activity activity)
			throws ParkNotFoundException, SomethingWentWrongException;

	String updateActivity(Activity activity) throws ActivityNotFoundException, SomethingWentWrongException;

	String deleteActivity(Integer activityId, Integer parkId) throws ActivityNotFoundException, SomethingWentWrongException;
}