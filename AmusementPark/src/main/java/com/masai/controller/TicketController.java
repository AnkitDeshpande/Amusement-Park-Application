package com.masai.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.TicketNotFoundException;
import com.masai.model.Ticket;
import com.masai.service.TicketService;

@RestController
@RequestMapping("/api/user/{userId}/tickets")
public class TicketController {

	@Autowired
	private TicketService ticketService;

	@GetMapping("/{ticketId}")
	public ResponseEntity<Ticket> getTicket(@PathVariable Integer userId, @PathVariable Integer ticketId)
			throws TicketNotFoundException {
		Ticket tickets = ticketService.getTicket(userId, ticketId);
		return new ResponseEntity<>(tickets, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<Set<Ticket>> getTickets(@PathVariable Integer userId) throws TicketNotFoundException {
		Set<Ticket> tickets = ticketService.getTickets(userId);
		return new ResponseEntity<>(tickets, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Ticket> createTicket(@PathVariable Integer userId, Integer parkId, @RequestBody Ticket ticket)
			throws SomethingWentWrongException {
		Ticket createdTicket = ticketService.createTicket(userId, parkId, ticket);
		return new ResponseEntity<>(createdTicket, HttpStatus.CREATED);
	}

	@DeleteMapping("/{ticketId}")
	public ResponseEntity<String> deleteTicket(@PathVariable Integer userId, @PathVariable Integer ticketId)
			throws TicketNotFoundException {
		String message = ticketService.deleteTicket(userId, ticketId);
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
}
