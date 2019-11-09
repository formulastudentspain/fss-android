package es.formulastudent.app.mvp.view.activity.racecontrol;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.FragmentManager;

import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.racecontrol.RaceControlBO;
import es.formulastudent.app.mvp.data.model.RaceControlEvent;
import es.formulastudent.app.mvp.data.model.RaceControlRegister;
import es.formulastudent.app.mvp.data.model.RaceControlState;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.view.activity.general.actionlisteners.RecyclerViewClickListener;
import es.formulastudent.app.mvp.view.activity.racecontrol.dialog.CreateRegisterDialog;
import es.formulastudent.app.mvp.view.activity.racecontrol.dialog.RaceControlTeamDTO;


public class RaceControlPresenter implements RecyclerViewClickListener {

    //Race Control Event Type
    RaceControlEvent rcEventType;
    String raceType;

    //Dependencies
    private View view;
    private Context context;
    private RaceControlBO raceControlBO;

    //Data
    List<RaceControlRegister> allRaceControlRegisterList = new ArrayList<>();
    List<RaceControlRegister> filteredRaceControlRegisterList = new ArrayList<>();

    //Filtering values
    private List<Team> teams;
    private String selectedTeamID;
    private String selectedDay;
    private Long selectedCarNumber;


    public RaceControlPresenter(RaceControlPresenter.View view, Context context, RaceControlEvent rcEventType, String raceType, RaceControlBO raceControlBO) {
        this.view = view;
        this.context = context;
        this.rcEventType = rcEventType;
        this.raceControlBO = raceControlBO;
        this.raceType = raceType;
    }


    /**
     * Retrieve Event registers
     */
     public ListenerRegistration retrieveRegisterList() {

        //Show loading
        view.showLoading();

        //Filters
         Map<String, Object> filters = new HashMap<>();

         //States
         //TODO obtener estados para filtrar desde un dialog
         //TODO Marshall_1, leerá pulseras para ponerlo en WA
         //TODO Marshall_2, lo pondrá WA->SCR, SCR->FIX y FIX->SCR
         //TODO Marshall_3, lo pondrá SCR->RR1D y RR1D->R1D
         //TODO Marshall_4, lo pondrá R1D->RR2D (mediante lectura pulsera NFC), RR2D->R2D, R2D->FIN



         //TODO borrar añadir estados a manija
         List<String> states = new ArrayList<>();

         states.add("NA");
         states.add("WA");
         states.add("FIX");
         states.add("SCR");
         states.add("RR1D");
         states.add("R1D");
         states.add("RR2D");
         states.add("R2D");
         states.add("DNF");
         states.add("RL");

         filters.put("states",states);

         filters.put("raceType", raceType);
         filters.put("eventType", rcEventType);

        //Retrieve race control registers in real-time
         ListenerRegistration registration = raceControlBO.getRaceControlRegistersRealTime(filters,  new BusinessCallback() {

             @Override
             public void onSuccess(ResponseDTO responseDTO) {
                     List<RaceControlRegister> results = (List<RaceControlRegister>) responseDTO.getData();
                     updateEventRegisters(results==null ? new ArrayList<RaceControlRegister>() : results);
             }

             @Override
             public void onFailure(ResponseDTO responseDTO) {
                 //Show error message
                 view.createMessage(responseDTO.getError());
             }
         });

         return registration;
    }


    protected void openCreateRegisterDialog(){

        //Show loading
        view.showLoading();

        Map<String, Object> filters = new HashMap<>();
        filters.put("raceType", raceType);
        filters.put("eventType", rcEventType);

        //Call business to retrieve teams
        raceControlBO.getRaceControlTeams(filters, new BusinessCallback() {

            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                List<RaceControlTeamDTO> raceControlTeamsDTO = (List<RaceControlTeamDTO>)responseDTO.getData();

                //With all the information, we open the dialog
                FragmentManager fm = ((RaceControlActivity)view.getActivity()).getSupportFragmentManager();
                CreateRegisterDialog createUserDialog = CreateRegisterDialog
                        .newInstance(RaceControlPresenter.this, raceControlTeamsDTO, context);
                createUserDialog.show(fm, "rc_endurance_create_dialog");

                //Hide loading
                view.hideLoading();
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                //Show error message
                view.createMessage(responseDTO.getError());
            }

        });

    }


    public void updateEventRegisters(List<RaceControlRegister> items){
        //Update all-register-list
        this.allRaceControlRegisterList.clear();
        this.allRaceControlRegisterList.addAll(items);

        //Update and refresh filtered-register-list
        this.filteredRaceControlRegisterList.clear();
        this.filteredRaceControlRegisterList.addAll(items);
        this.view.refreshEventRegisterItems();
    }




    @Override
    public void recyclerViewListClicked(android.view.View v, int position) {

        RaceControlRegister register = filteredRaceControlRegisterList.get(position);
        RaceControlState newState = null;

        //State 1 clicked
        if(v.getId() == R.id.state1){
            newState = RaceControlState.getStateByAcronym(register.getCurrentState().getStates().get(0));

        //State 2 clicked
        }else if(v.getId() == R.id.state2){
            newState = RaceControlState.getStateByAcronym(register.getCurrentState().getStates().get(1));

        }

        //Update register state
        updateRegister(register, rcEventType, newState);
    }

    /**
     * Change the register state
     * @param register
     * @param event
     * @param newState
     */
    private void updateRegister(final RaceControlRegister register, final RaceControlEvent event, final RaceControlState newState){

         final String oldState = register.getCurrentState().getAcronym();

         raceControlBO.updateRaceControlState(register, event, newState, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {

                //Close the swiped row after updating
                view.closeUpdatedRow(register.getID());

                //Show success message
                view.createMessage(R.string.rc_update_state_success_message, oldState, newState.getAcronym());
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                //Show error message
                view.createMessage(responseDTO.getError());
            }
        });
    }


    void filterIconClicked(){

        //Go retrieve teams if we have not yet
        if(teams == null){
            //TODO retrieveTeams();
            //view.createMessage("TODO. Crear un dialog para filtrar.");

        }else{
           //TODO  openFilteringDialog(teams);
           // view.createMessage("TODO. Crear un dialog para filtrar.");
        }
    }

    public void setFilteringValues(Date selectedDateFrom, Date selectedDateTo, String selectedDay, String selectedTeamID, Long selectedCarNumber){
        this.selectedTeamID = selectedTeamID;
        this.selectedDay = selectedDay;
        this.selectedCarNumber = selectedCarNumber;

        view.filtersActivated(
                selectedDay!=null
                        || selectedCarNumber != null
                        || (selectedTeamID != null && !selectedTeamID.equals("-1"))
        );
    }


    /**
     * Create race control registers (as a transaction)
     * @param raceControlTeamDTOList
     * @param currentMaxIndex
     */
    public void createRaceControlRegisters(List<RaceControlTeamDTO> raceControlTeamDTOList, Long currentMaxIndex){

         raceControlBO.createRaceControlRegister(raceControlTeamDTOList, rcEventType, raceType, currentMaxIndex, new BusinessCallback() {
             @Override
             public void onSuccess(ResponseDTO responseDTO) {
                 //Show info message
                 view.createMessage(responseDTO.getInfo());
             }

             @Override
             public void onFailure(ResponseDTO responseDTO) {
                 //Show error message
                 view.createMessage(responseDTO.getError());
             }
         });

    }


    public List<RaceControlRegister> getEventRegisterList() {
        return filteredRaceControlRegisterList;
    }


    public interface View {

        Activity getActivity();

        /**
         * Show message to user
         * @param message
         */
        void createMessage(Integer message, Object...args);

        /**
         * Show loading icon
         */
        void showLoading();

        /**
         * Hide loading icon
         */
        void hideLoading();

        /**
         * Refresh items in list
         */
        void refreshEventRegisterItems();

        /**
         * Method to know if the filters are activated
         * @param activated
         */
        void filtersActivated(Boolean activated);

        /**
         * The row must be closed if it is updated (register state)
         * @param id
         */
        void closeUpdatedRow(String id);
    }

}
