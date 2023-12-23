package com.masai.service;

import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.TicketNotFoundException;
import com.masai.exception.UserNotFoundException;
import com.masai.model.Ticket;

import java.util.Set;

/**
 * Service interface for managing tickets.
 */
public interface TicketService {

    /**
     * Retrieves a specific ticket for a user.
     *
     * @param userId   ID of the user for whom the ticket is requested.
     * @param ticketId ID of the ticket to retrieve.
     * @return Ticket object corresponding to the specified user and ticket ID.
     * @throws TicketNotFoundException If the specified ticket is not found.
     * @throws UserNotFoundException   If the specified user is not found.
     */
    Ticket getTicket(Integer userId, Integer ticketId) throws TicketNotFoundException, UserNotFoundException;

    /**
     * Retrieves all tickets for a specific user.
     *
     * @param userId ID of the user for whom tickets are requested.
     * @return Set of tickets associated with the specified user.
     * @throws TicketNotFoundException If no tickets are found for the specified user.
     * @throws UserNotFoundException   If the specified user is not found.
     */
    Set<Ticket> getTickets(Integer userId) throws TicketNotFoundException, UserNotFoundException;

    /**
     * Creates a new ticket for a user in a specific park.
     *
     * @param userId ID of the user for whom the ticket is created.
     * @param parkId ID of the park for which the ticket is created.
     * @param ticket Ticket object containing details of the ticket to be created.
     * @return The created Ticket object.
     * @throws SomethingWentWrongException If an unexpected issue occurs during ticket creation.
     * @throws UserNotFoundException       If the specified user is not found.
     */
    Ticket createTicket(Integer userId, Integer parkId, Ticket ticket)
            throws SomethingWentWrongException, UserNotFoundException;

    /**
     * Updates an existing ticket for a user.
     *
     * @param userId   ID of the user who owns the ticket.
     * @param ticketId ID of the ticket to be updated.
     * @param ticket   Updated Ticket object.
     * @return Message indicating the status of the update operation.
     * @throws TicketNotFoundException     If the specified ticket is not found.
     * @throws SomethingWentWrongException If an unexpected issue occurs during the update process.
     * @throws UserNotFoundException       If the specified user is not found.
     */
    String updateTicket(Integer userId, Integer ticketId, Ticket ticket)
            throws TicketNotFoundException, SomethingWentWrongException, UserNotFoundException;

    /**
     * Deletes a specific ticket for a user.
     *
     * @param userId   ID of the user who owns the ticket.
     * @param ticketId ID of the ticket to be deleted.
     * @return Message indicating the status of the deletion operation.
     * @throws TicketNotFoundException If the specified ticket is not found.
     * @throws UserNotFoundException   If the specified user is not found.
     */
    String deleteTicket(Integer userId, Integer ticketId) throws TicketNotFoundException, UserNotFoundException;
}
