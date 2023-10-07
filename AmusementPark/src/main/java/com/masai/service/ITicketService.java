package com.masai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.TicketNotFoundException;
import com.masai.model.Ticket;
import com.masai.repository.TicketRepository;

@Service
public class ITicketService implements TicketService {

	@Autowired
	private TicketRepository repo;

	@Override
	public Ticket getTicket(Integer ticketId) throws TicketNotFoundException {
		return repo.findById(ticketId)
				.orElseThrow(() -> new TicketNotFoundException("Couldn't find ticket with id: " + ticketId));
	}

	@Override
	public Ticket createTicket(Ticket ticket) throws SomethingWentWrongException {
		try {
			return repo.save(ticket);
		} catch (Exception e) {
			throw new SomethingWentWrongException();
		}
	}

	@Override
	public String updateTicket(Ticket ticket) throws TicketNotFoundException, SomethingWentWrongException {
		Ticket existingTicket = repo.findById(ticket.getId())
				.orElseThrow(() -> new TicketNotFoundException("Couldn't find ticket with id: " + ticket.getId()));
		try {
			// Update ticket properties here
			existingTicket.setUser(ticket.getUser());
			existingTicket.setPark(ticket.getPark());
			existingTicket.setPurchaseDate(ticket.getPurchaseDate());
			existingTicket.setPrice(ticket.getPrice());

			// Save the updated ticket
			repo.save(existingTicket);
			return "Ticket updated successfully.";
		} catch (Exception e) {
			throw new SomethingWentWrongException();
		}
	}

	@Override
	public String deleteTicket(Integer ticketId) throws TicketNotFoundException {
		Ticket ticket = repo.findById(ticketId)
				.orElseThrow(() -> new TicketNotFoundException("Couldn't find ticket with id: " + ticketId));

		// Instead of deleting the ticket, mark it as cancelled
		ticket.setCancelled(true);
		repo.save(ticket);
		return "Ticket deleted successfully.";
	}
}
