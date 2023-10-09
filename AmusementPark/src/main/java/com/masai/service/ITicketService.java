package com.masai.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.ParkNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.TicketNotFoundException;
import com.masai.exception.UserNotFoundException;
import com.masai.model.Park;
import com.masai.model.Ticket;
import com.masai.model.User;
import com.masai.repository.ParkRepository;
import com.masai.repository.TicketRepository;
import com.masai.repository.UserRepository;

@Service
public class ITicketService implements TicketService {

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ParkRepository parkRepository;

	@Override
	public Ticket getTicket(Integer userId, Integer ticketId) throws TicketNotFoundException, UserNotFoundException {
		// Check if the user exists
		userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));

		// Try to find the ticket
		Ticket ticket = ticketRepository.findById(ticketId)
				.orElseThrow(() -> new TicketNotFoundException("Ticket with ID " + ticketId + " not found"));

		if (ticket.isCancelled()) {
			throw new TicketNotFoundException("Ticket with ID " + ticketId + " not found");
		}

		return ticket;
	}

	@Override
	public Set<Ticket> getTickets(Integer userId) throws UserNotFoundException {
		// Check if the user exists
		userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));

		// Fetch all tickets associated with the user
		Set<Ticket> tickets = ticketRepository.findTicketsByUser_UserId(userId);

		if (tickets.isEmpty()) {
			throw new TicketNotFoundException("No tickets found for User with ID " + userId);
		}

		return tickets;
	}

	@Override
	public Ticket createTicket(Integer userId, Integer parkId, Ticket ticket)
			throws SomethingWentWrongException, UserNotFoundException {
		// Check if the user exists
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));
		Park park = parkRepository.findById(parkId)
				.orElseThrow(() -> new ParkNotFoundException("Park with ID " + parkId + " not found"));

		// Set the user ID for the ticket
		ticket.setUser(user);
		ticket.setPark(park);

		// Save the ticket
		return ticketRepository.save(ticket);
	}

	@Override
	public String updateTicket(Integer userId, Integer ticketId, Ticket ticket)
			throws TicketNotFoundException, SomethingWentWrongException, UserNotFoundException {
		// Check if the user exists
		userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));

		// Try to find the ticket
		Ticket existingTicket = ticketRepository.findById(ticketId)
				.orElseThrow(() -> new TicketNotFoundException("Ticket with ID " + ticketId + " not found"));

		// Ensure the ticket belongs to the user
		if (!existingTicket.getUser().equals(userId)) {
			throw new TicketNotFoundException(
					"Ticket with ID " + ticketId + " does not belong to User with ID " + userId);
		}

		if (existingTicket.isCancelled()) {
			throw new TicketNotFoundException("Cancelled Ticket with ID " + ticketId + " cannot be updated.");
		}

		// Update ticket properties
		existingTicket.setPark(ticket.getPark());
		existingTicket.setPurchaseDate(ticket.getPurchaseDate());

		// Save the updated ticket
		ticketRepository.save(existingTicket);

		return "Ticket updated successfully.";
	}

	@Override
	public String deleteTicket(Integer userId, Integer ticketId) throws TicketNotFoundException, UserNotFoundException {
		// Check if the user exists
		userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));

		// Try to find the ticket
		Ticket existingTicket = ticketRepository.findById(ticketId)
				.orElseThrow(() -> new TicketNotFoundException("Ticket with ID " + ticketId + " not found"));

		// Ensure the ticket belongs to the user
		if (!existingTicket.getUser().getUserId().equals(userId)) {
			throw new TicketNotFoundException(
					"Ticket with ID " + ticketId + " does not belong to User with ID " + userId);
		}

		// Instead of deleting, mark the ticket as cancelled
		existingTicket.setCancelled(true);
		ticketRepository.save(existingTicket);

		return "Ticket cancelled successfully.";
	}
}
