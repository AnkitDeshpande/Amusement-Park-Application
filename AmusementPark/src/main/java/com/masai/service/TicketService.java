package com.masai.service;

import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.TicketNotFoundException;
import com.masai.exception.UserNotFoundException;
import com.masai.model.Ticket;

import java.util.Set;

public interface TicketService {

	public Ticket getTicket(Integer userId, Integer ticketId) throws TicketNotFoundException, UserNotFoundException;

	public Set<Ticket> getTickets(Integer userId) throws TicketNotFoundException, UserNotFoundException;

	public Ticket createTicket(Integer userId, Integer parkId, Ticket ticket) throws SomethingWentWrongException, UserNotFoundException;

	public String updateTicket(Integer userId, Integer ticketId, Ticket ticket)
			throws TicketNotFoundException, SomethingWentWrongException, UserNotFoundException;

	public String deleteTicket(Integer userId, Integer ticketId) throws TicketNotFoundException, UserNotFoundException;
}
