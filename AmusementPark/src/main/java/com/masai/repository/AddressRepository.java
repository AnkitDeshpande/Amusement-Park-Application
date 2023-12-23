package com.masai.repository;

import com.masai.model.Address;
import com.masai.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer>{

    @Query("SELECT a FROM Address a WHERE a.user = :user")
    Page<Address> findAdressesByUser(User user, Pageable pageable);

    Page<Address> findAdressesByUserOrderByCityAsc(User user, Pageable pageable);

    Page<Address> findAdressesByUserOrderByCityDesc(User user, Pageable pageable);


}
