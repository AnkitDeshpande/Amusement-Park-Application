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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ticket createTicket(Ticket ticket) throws SomethingWentWrongException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateTicket(Ticket ticket) throws TicketNotFoundException, SomethingWentWrongException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteTicket(Integer ticketId) throws TicketNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
