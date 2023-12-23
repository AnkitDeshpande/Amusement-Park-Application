package com.masai.repository;

import com.masai.model.Park;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkRepository extends JpaRepository<Park, Integer> {

    @Query("SELECT p FROM Park p ORDER BY p.name ASC")
    Page<Park> findAllParksOrderByNameAsc(Pageable pageable);

    @Query("SELECT p FROM Park p ORDER BY p.name DESC")
    Page<Park> findAllParksOrderByNameDesc(Pageable pageable);
}
