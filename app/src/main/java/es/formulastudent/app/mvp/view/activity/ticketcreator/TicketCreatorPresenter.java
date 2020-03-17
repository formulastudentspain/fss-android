package es.formulastudent.app.mvp.view.activity.ticketcreator;

import android.app.Activity;
import android.content.Context;

import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.List;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.ticket.TicketBO;
import es.formulastudent.app.mvp.data.model.Ticket;
import es.formulastudent.app.mvp.data.model.TicketArea;
import es.formulastudent.app.mvp.data.model.TicketCurrentTurns;
import es.formulastudent.app.mvp.data.model.User;


public class TicketCreatorPresenter {


    //Dependencies
    private View view;
    private Context context;
    private TicketBO ticketBO;


    //Data
    List<Ticket> ticketList = new ArrayList<>();
    ListenerRegistration registrationTicketList;
    ListenerRegistration registrationCurrentStatus;
    TicketCurrentTurns currentTurns;



    public TicketCreatorPresenter(TicketCreatorPresenter.View view, Context context, TicketBO ticketBO) {
        this.view = view;
        this.context = context;
        this.ticketBO = ticketBO;
    }


    public ListenerRegistration retrieveTicketList() {

        //We need to prevent multiple listeners if user filters multiple times
        if(registrationTicketList != null){
            registrationTicketList.remove();
        }

        //Show loading
        view.showLoading();

        //Retrieve tickets in real-time
        registrationTicketList = ticketBO.getTicketsByTeamInRealTime(view.getLoggedUser().getTeam(),  new BusinessCallback() {

            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                List<Ticket> results = (List<Ticket>) responseDTO.getData();
                updateTicketList(results==null ? new ArrayList<>() : results, false);
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                view.createMessage(responseDTO.getError());
            }
        });

        return registrationTicketList;
    }


    public ListenerRegistration retrieveCurrentTurns() {

        //We need to prevent multiple listeners if user filters multiple times
        if(registrationCurrentStatus != null){
            registrationCurrentStatus.remove();
        }

        //Show loading
        view.showLoading();

        //Retrieve current turns in real-time
        registrationCurrentStatus = ticketBO.getCurrentTicketTurnsInRealTime(new BusinessCallback() {

            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                TicketCurrentTurns result = (TicketCurrentTurns) responseDTO.getData();
                updateTicketsWithCurrentTurns(result);
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                view.createMessage(responseDTO.getError());
            }
        });

        return registrationCurrentStatus;
    }

    private void updateTicketsWithCurrentTurns(TicketCurrentTurns currentTurns) {
        this.currentTurns = currentTurns;
        updateTicketList(this.ticketList, true);
    }

    public void updateTicketList(List<Ticket> items, boolean fromCurrentTurns){

        //Set the current turn for each area
        for(Ticket ticket: items){
            if(TicketArea.PRE_SCRUTINEERING.equals(ticket.getArea())){
                ticket.setCurrentTurn(currentTurns.getPreScrutinering());

            }else if(TicketArea.ACCUMULATION_INSPECTION.equals(ticket.getArea())){
                ticket.setCurrentTurn(currentTurns.getAccumulationInspection());

            }else if(TicketArea.ELECTRICAL_INSPECTION.equals(ticket.getArea())){
                ticket.setCurrentTurn(currentTurns.getElectricalInspection());

            }else if(TicketArea.MECHANICAL_INSPECTION.equals(ticket.getArea())){
                ticket.setCurrentTurn(currentTurns.getMechanicalInspection());

            }else if(TicketArea.TILT_TABLE_TEST.equals(ticket.getArea())){
                ticket.setCurrentTurn(currentTurns.getTiltTableTest());

            }else if(TicketArea.NOISE_TEST.equals(ticket.getArea())){
                ticket.setCurrentTurn(currentTurns.getNoiseTest());

            }else if(TicketArea.RAIN_TEST.equals(ticket.getArea())){
                ticket.setCurrentTurn(currentTurns.getRainTest());

            }else if(TicketArea.BRAKE_TEST.equals(ticket.getArea())){
                ticket.setCurrentTurn(currentTurns.getBrakeTest());
            }
        }

        if(!fromCurrentTurns){
            this.ticketList.clear();
            this.ticketList.addAll(items);
        }
        this.view.refreshEventRegisterItems();
    }


    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void createTicket() {

        ticketBO.createTicket(TicketArea.PRE_SCRUTINEERING, view.getLoggedUser().getTeam(), new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {

            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {

            }
        });

    }

    public interface View {

        Activity getActivity();

        /**
         * Show message to user
         * @param message
         */
        void createMessage(Integer message, Object... args);

        /**
         * Refresh list
         */
        void refreshEventRegisterItems();

        /**
         * Show loading icon
         */
        void showLoading();

        /**
         * Hide loading icon
         */
        void hideLoading();

        /**
         * Get loggedUser
         * @return
         */
        User getLoggedUser();
    }
}
