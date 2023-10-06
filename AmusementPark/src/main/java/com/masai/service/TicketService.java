package com.masai.service;

import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.TicketNotFoundException;
import com.masai.model.Ticket;

public interface TicketService {

	public Ticket getTicket(Integer ticketId) throws TicketNotFoundException;

	public Ticket createTicket(Ticket ticket) throws SomethingWentWrongException;

	public String updateTicket(Ticket ticket) throws TicketNotFoundException, SomethingWentWrongException;

	public String deleteTicket(Integer ticketId) throws TicketNotFoundException;

}
