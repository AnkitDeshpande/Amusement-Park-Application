package com.masai.repository;

import com.masai.model.Park;
import com.masai.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query("SELECT r FROM Review r WHERE r.park = :park")
    Page<Review> findByPark(Park park, Pageable pageable);

    @Query("SELECT r FROM Review r WHERE r.park = :park ORDER BY r.id ASC")
    Page<Review> findByParkOrderByIdAsc(Park park, Pageable pageable);

    @Query("SELECT r FROM Review r WHERE r.park = :park ORDER BY r.id DESC")
    Page<Review> findByParkOrderByIdDesc(Park park, Pageable pageable);
}
