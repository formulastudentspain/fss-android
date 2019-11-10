package es.formulastudent.app.mvp.view.activity.racecontrol;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.FragmentManager;

import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.Arrays;
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
import es.formulastudent.app.mvp.view.activity.general.actionlisteners.RecyclerViewClickListener;
import es.formulastudent.app.mvp.view.activity.general.actionlisteners.RecyclerViewLongClickListener;
import es.formulastudent.app.mvp.view.activity.racecontrol.dialog.CreateRegisterDialog;
import es.formulastudent.app.mvp.view.activity.racecontrol.dialog.FilteringRegistersDialog;
import es.formulastudent.app.mvp.view.activity.racecontrol.dialog.RaceControlTeamDTO;
import es.formulastudent.app.mvp.view.activity.racecontrol.dialog.UpdatingRegistersDialog;


public class RaceControlPresenter implements RecyclerViewClickListener, RecyclerViewLongClickListener {

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
    ListenerRegistration registration = null;

    //Filtering values
    private String selectedArea;
    private Long selectedCarNumber;


    public RaceControlPresenter(RaceControlPresenter.View view, Context context, RaceControlEvent rcEventType, String raceType, RaceControlBO raceControlBO) {
        this.view = view;
        this.context = context;
        this.rcEventType = rcEventType;
        this.raceControlBO = raceControlBO;
        this.raceType = raceType;
        this.selectedArea = context.getString(R.string.rc_area_all);
    }


    /**
     * Retrieve Event registers
     */
     public ListenerRegistration retrieveRegisterList() {

         //We need to prevent multiple listeners if user filters multiple times
         if(registration != null){
             registration.remove();
         }

        //Show loading
        view.showLoading();

        //Filters
        Map<String, Object> filters = new HashMap<>();

        //Select states fot the selected area
        List<String> states = new ArrayList<>();
        if(context.getString(R.string.rc_area_waiting_area).equals(selectedArea)){
            states.addAll(Arrays.asList(
                    RaceControlState.NOT_AVAILABLE.getAcronym()));

        }else if(context.getString(R.string.rc_area_scrutineering).equals(selectedArea)){
            states.addAll(Arrays.asList(
                    RaceControlState.WAITING_AREA.getAcronym(),
                    RaceControlState.FIXING.getAcronym(),
                    RaceControlState.SCRUTINEERING.getAcronym()));

        }else if(context.getString(R.string.rc_area_racing1).equals(selectedArea)){
            states.addAll(Arrays.asList(
                    RaceControlState.SCRUTINEERING.getAcronym(),
                    RaceControlState.READY_TO_RACE_1D.getAcronym()));

        }else if(context.getString(R.string.rc_area_racing2).equals(selectedArea)){
            states.addAll(Arrays.asList(
                    RaceControlState.RACING_1D.getAcronym(),
                    RaceControlState.READY_TO_RACE_2D.getAcronym(),
                    RaceControlState.RACING_2D.getAcronym()));

        }else if(selectedArea == null || context.getString(R.string.rc_area_all).equals(selectedArea)) {
            states.addAll(Arrays.asList(
                    RaceControlState.NOT_AVAILABLE.getAcronym(),
                    RaceControlState.WAITING_AREA.getAcronym(),
                    RaceControlState.FIXING.getAcronym(),
                    RaceControlState.SCRUTINEERING.getAcronym(),
                    RaceControlState.READY_TO_RACE_1D.getAcronym(),
                    RaceControlState.RACING_1D.getAcronym(),
                    RaceControlState.READY_TO_RACE_2D.getAcronym(),
                    RaceControlState.RACING_2D.getAcronym(),
                    RaceControlState.FINISHED.getAcronym(),
                    RaceControlState.RUN_LATER.getAcronym(),
                    RaceControlState.DNF.getAcronym()));
        }

         filters.put("states",states);
         filters.put("raceType", raceType);
         filters.put("eventType", rcEventType);
         filters.put("carNumber", selectedCarNumber);


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

         this.registration = registration;

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
    public void updateRegister(final RaceControlRegister register, final RaceControlEvent event, final RaceControlState newState){

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

        //Opening filtering dialog
        FragmentManager fm = ((RaceControlActivity)view.getActivity()).getSupportFragmentManager();
        FilteringRegistersDialog createFilteringDialog = FilteringRegistersDialog
                .newInstance(RaceControlPresenter.this, selectedCarNumber, selectedArea);
        createFilteringDialog.show(fm, "rc_filtering_dialog");

    }

    public void setFilteringValues(String selectedArea, Long selectedCarNumber){
        this.selectedArea = selectedArea;
        this.selectedCarNumber = selectedCarNumber;

        view.filtersActivated((selectedArea != null && !selectedArea.equals(context.getString(R.string.rc_area_all)))|| selectedCarNumber != null);
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

    @Override
    public void recyclerViewLongListClicked(android.view.View v, int position) {
        RaceControlRegister register = filteredRaceControlRegisterList.get(position);

        //Opening officials raceControl dialog
        FragmentManager fm = ((RaceControlActivity)view.getActivity()).getSupportFragmentManager();
        UpdatingRegistersDialog createUpdatingDialog = UpdatingRegistersDialog
                .newInstance(RaceControlPresenter.this, register, rcEventType);
        createUpdatingDialog.show(fm, "rc_updating_dialog");

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
