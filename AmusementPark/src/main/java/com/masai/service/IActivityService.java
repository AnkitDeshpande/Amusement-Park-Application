package com.masai.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.ActivityNotFoundException;
import com.masai.exception.ParkNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.model.Activity;
import com.masai.repository.ActivityRepository;

@Service
public class IActivityService implements ActivityService {

	@Autowired
	private ActivityRepository repo;

	@Override
	public Activity getActivity(Integer activityId) throws ActivityNotFoundException, SomethingWentWrongException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Activity> getActivitiesByPark(Integer parkId)
			throws ParkNotFoundException, SomethingWentWrongException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Activity createActivity(Integer parkId, Activity activity)
			throws ParkNotFoundException, SomethingWentWrongException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateActivity(Activity activity) throws ActivityNotFoundException, SomethingWentWrongException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteActivity(Integer activityId) throws ActivityNotFoundException, SomethingWentWrongException {
		// TODO Auto-generated method stub
		return null;
	}

}
