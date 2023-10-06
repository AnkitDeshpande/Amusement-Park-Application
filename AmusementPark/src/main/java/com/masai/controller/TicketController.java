package com.masai.controller;

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
@RequestMapping("/tickets")
public class TicketController {

	@Autowired
	private TicketService ticketService;

	@GetMapping("/{ticketId}")
	public ResponseEntity<Ticket> getTicket(@PathVariable Integer ticketId) {
		try {
			Ticket ticket = ticketService.getTicket(ticketId);
			return new ResponseEntity<>(ticket, HttpStatus.OK);
		} catch (TicketNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
		try {
			Ticket createdTicket = ticketService.createTicket(ticket);
			return new ResponseEntity<>(createdTicket, HttpStatus.CREATED);
		} catch (SomethingWentWrongException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping
	public ResponseEntity<String> updateTicket(@RequestBody Ticket ticket) {
		try {
			String message = ticketService.updateTicket(ticket);
			return new ResponseEntity<>(message, HttpStatus.OK);
		} catch (TicketNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (SomethingWentWrongException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{ticketId}")
	public ResponseEntity<String> deleteTicket(@PathVariable Integer ticketId) {
		try {
			String message = ticketService.deleteTicket(ticketId);
			return new ResponseEntity<>(message, HttpStatus.OK);
		} catch (TicketNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
