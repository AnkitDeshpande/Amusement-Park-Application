package com.masai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masai.model.Park;

@Repository
public interface ParkRepository extends JpaRepository<Park, Integer> {

}
