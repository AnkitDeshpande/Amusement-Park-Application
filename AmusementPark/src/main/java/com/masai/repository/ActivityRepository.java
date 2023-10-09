package com.masai.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masai.model.Activity;
import com.masai.model.Park;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {

	List<Activity> findByPark(Park park);

}
