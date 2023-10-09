package com.masai.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masai.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
	Set<Ticket> findTicketsByUser_UserId(Integer userId);
}
