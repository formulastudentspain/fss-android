package es.formulastudent.app.mvp.data.business.ticket;


import com.google.firebase.firestore.ListenerRegistration;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.data.model.Ticket;
import es.formulastudent.app.mvp.data.model.TicketArea;

public interface TicketBO {

    /**
     * Get tickets by team in Real-Time
     * @param team
     * @param callback
     */
    ListenerRegistration getTicketsByTeamInRealTime(Team team, BusinessCallback callback);

    /**
     * Get tickets by area in Real-Time
     * @param area
     * @param callback
     * @return
     */
    ListenerRegistration getTicketsByAreaInRealTime(TicketArea area, BusinessCallback callback);


    /**
     * Get current ticket turns in Real-Time
     * @param callback
     */
    ListenerRegistration getCurrentTicketTurnsInRealTime(BusinessCallback callback);


    /**
     * Create ticket
     * @param area
     * @param team
     * @param callback
     */
    void createTicket(TicketArea area, Team team, BusinessCallback callback);


    /**
     * Delete ticket
     * @param ticketId
     * @param callback
     */
    void deleteTicket(String ticketId, BusinessCallback callback);


    /**
     * Update ticket
     * @param ticket
     * @param callback
     */
    void updateTicket(Ticket ticket, BusinessCallback callback);

}