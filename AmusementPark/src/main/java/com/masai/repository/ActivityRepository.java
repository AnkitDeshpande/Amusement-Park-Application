package com.masai.repository;

import com.masai.model.Activity;
import com.masai.model.Park;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {

    List<Activity> findByPark(Park park);

    Page<Activity> findByPark(Park park, Pageable page);


    @Query("FROM Park p ORDER BY p.name ASC")
    Page<Activity> findByParkOrderByNameAsc(Park park, Pageable pageable);

    @Query("FROM Park p ORDER BY p.name DESC")
    Page<Activity> findByParkOrderByNameDesc(Park park, Pageable pageable);
}
