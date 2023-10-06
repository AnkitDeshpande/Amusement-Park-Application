package com.masai.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.ParkNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.model.Park;
import com.masai.repository.ParkRepository;

@Service
public class IParkService implements ParkService {

	@Autowired
	private ParkRepository repo;

	@Override
	public Park getPark(Integer parkId) throws ParkNotFoundException, SomethingWentWrongException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Park> getAllParks() throws SomethingWentWrongException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Park createPark(Park park) throws SomethingWentWrongException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updatePark(Park park) throws ParkNotFoundException, SomethingWentWrongException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deletePark(Long parkId) throws ParkNotFoundException, SomethingWentWrongException {
		// TODO Auto-generated method stub
		return null;
	}

}
