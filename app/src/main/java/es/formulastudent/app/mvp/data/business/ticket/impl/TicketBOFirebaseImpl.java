package es.formulastudent.app.mvp.data.business.ticket.impl;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ConfigConstants;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.parameter.ParameterBO;
import es.formulastudent.app.mvp.data.business.ticket.TicketBO;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.data.model.Ticket;
import es.formulastudent.app.mvp.data.model.TicketArea;
import es.formulastudent.app.mvp.data.model.TicketCurrentTurns;

import static es.formulastudent.app.mvp.data.business.ConfigConstants.FIREBASE_TABLE_TICKET;

public class TicketBOFirebaseImpl implements TicketBO {

    private FirebaseFirestore firebaseFirestore;
    private ParameterBO parameterBO;
    private Context context;

    public TicketBOFirebaseImpl(FirebaseFirestore firebaseFirestore, ParameterBO parameterBO, Context context) {
        this.firebaseFirestore = firebaseFirestore;
        this.parameterBO = parameterBO;
        this.context = context;
    }

    @Override
    public ListenerRegistration getTicketsByTeamInRealTime(Team team, BusinessCallback callback) {

        Query query = firebaseFirestore.collection(FIREBASE_TABLE_TICKET)
            .whereEqualTo(Ticket.CAR_NUMBER, team.getCar().getNumber())
            .orderBy(Ticket.WAITING_DATE, Query.Direction.DESCENDING);

        return this.getTicketsInRealTime(query, callback);
    }

    @Override
    public ListenerRegistration getTicketsByAreaInRealTime(TicketArea area, BusinessCallback callback) {

        Query query = firebaseFirestore.collection(FIREBASE_TABLE_TICKET)
            .whereEqualTo(Ticket.AREA, area.getName())
            .whereEqualTo(Ticket.IS_DELETED, false)
            .whereEqualTo(Ticket.IS_CLOSED, false)
            .orderBy(Ticket.NUMBER, Query.Direction.ASCENDING);

        return this.getTicketsInRealTime(query, callback);
    }

    private ListenerRegistration getTicketsInRealTime(Query query, BusinessCallback callback) {

        ListenerRegistration registration = query.addSnapshotListener((value, e) -> {

            //Response object
            ResponseDTO responseDTO = new ResponseDTO();

            if (e != null) {
                responseDTO.setError(R.string.ticket_activity_error_retrieving_tickets);
                callback.onFailure(responseDTO);
                return;
            }

            List<Ticket> result = new ArrayList<>();
            for (QueryDocumentSnapshot doc : value) {
                result.add(new Ticket(doc));
            }

            responseDTO.setData(result);
            callback.onSuccess(responseDTO);
        });

        return registration;
    }

    private void getTickets(Query query, BusinessCallback callback) {
        final ResponseDTO responseDTO = new ResponseDTO();

        query.get().addOnSuccessListener(queryDocumentSnapshots -> {

            List<Ticket> result = new ArrayList<>();
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                Ticket ticket = new Ticket(document);
                result.add(ticket);
            }
            responseDTO.setData(result);
            //TODO
            callback.onSuccess(responseDTO);

        }).addOnFailureListener(e -> {
            //TODO
            callback.onFailure(responseDTO);
        });
    }


    @Override
    public ListenerRegistration getCurrentTicketTurnsInRealTime(BusinessCallback callback) {
        return parameterBO.getItemByKeyInRealTime(TicketCurrentTurns.KEY, callback);
    }

    @Override
    public void createTicket(TicketArea area, Team team, BusinessCallback callback) {

        final ResponseDTO responseDTO = new ResponseDTO();

        //Get all tickets by area
        Query query = firebaseFirestore.collection(FIREBASE_TABLE_TICKET)
            .whereEqualTo(Ticket.AREA, area.getName())
            .orderBy(Ticket.NUMBER, Query.Direction.ASCENDING);

        getTickets(query, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO dto) {

                List<Ticket> tickets = (List<Ticket>) dto.getData();
                Long ticketNumber = (long) (tickets.size() + 1);

                //Create ticket
                Ticket ticket = new Ticket();
                ticket.setArea(area);
                ticket.setCarNumber(team.getCar().getNumber());
                ticket.setNumber(ticketNumber);

                firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_TICKET)
                    .document(ticket.getId())
                    .set(ticket.toDocumentData())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {

                        @Override
                        public void onSuccess(Void aVoid) {
                            responseDTO.setInfo(R.string.ticket_activity_info_create);
                            callback.onSuccess(responseDTO);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            responseDTO.setError(R.string.ticket_activity_error_create);
                            callback.onFailure(responseDTO);
                        }
                    });
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                responseDTO.setError(R.string.ticket_activity_error_create);
                callback.onFailure(responseDTO);
            }
        });
    }


    @Override
    public void deleteTicket(String ticketId, BusinessCallback callback) {

    }

    @Override
    public void updateTicket(Ticket ticket, BusinessCallback callback) {
        //Si el ticket pasa a IN PROGRESS actualizar el current al número de este ticket
    }
}