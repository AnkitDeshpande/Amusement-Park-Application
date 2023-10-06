package com.masai.service;

import java.util.List;

import com.masai.exception.ParkNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.model.Park;

public interface ParkService {

    Park getPark(Integer parkId) throws ParkNotFoundException, SomethingWentWrongException;

    List<Park> getAllParks() throws SomethingWentWrongException;

    Park createPark(Park park) throws SomethingWentWrongException;

    String updatePark(Park park) throws ParkNotFoundException, SomethingWentWrongException;

    String deletePark(Long parkId) throws ParkNotFoundException, SomethingWentWrongException;
}
