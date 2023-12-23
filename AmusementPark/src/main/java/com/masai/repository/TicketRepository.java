package com.masai.repository;

import com.masai.model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    Set<Ticket> findTicketsByUser_UserId(Integer userId);

    @Query("SELECT t FROM Ticket t WHERE t.user.userId = :userId")
    Page<Ticket> findTicketsByUser_UserId(Integer userId, Pageable pageable);

    @Query("SELECT t FROM Ticket t WHERE t.user.userId = :userId ORDER BY t.purchaseDate ASC")
    Page<Ticket> findTicketsByUser_UserIdOrderByPurchaseDateAsc(Integer userId, Pageable pageable);

    @Query("SELECT t FROM Ticket t WHERE t.user.userId = :userId ORDER BY t.purchaseDate DESC")
    Page<Ticket> findTicketsByUser_UserIdOrderByPurchaseDateDesc(Integer userId, Pageable pageable);
}
