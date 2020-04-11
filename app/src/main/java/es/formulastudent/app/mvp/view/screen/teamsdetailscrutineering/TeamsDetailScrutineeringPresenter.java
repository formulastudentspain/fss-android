package es.formulastudent.app.mvp.view.screen.teamsdetailscrutineering;

import android.app.Activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.dynamicevent.DynamicEventBO;
import es.formulastudent.app.mvp.data.business.egress.EgressBO;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.business.teammember.TeamMemberBO;
import es.formulastudent.app.mvp.data.model.EventRegister;
import es.formulastudent.app.mvp.data.model.EventType;
import es.formulastudent.app.mvp.data.model.PreScrutineeringRegister;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.data.model.TeamMember;
import es.formulastudent.app.mvp.view.screen.teamsdetailscrutineering.tabs.TeamsDetailFragment;


public class TeamsDetailScrutineeringPresenter {

    //Dependencies
    private View view;
    private TeamBO teamBO;
    private DynamicEventBO dynamicEventBO;
    private TeamMemberBO teamMemberBO;
    private EgressBO egressBO;

    //Data
    List<EventRegister> eventRegisterList = new ArrayList<>();


    public TeamsDetailScrutineeringPresenter(TeamsDetailScrutineeringPresenter.View view, TeamBO teamBO,
                                             DynamicEventBO dynamicEventBO, EgressBO egressBO, TeamMemberBO teamMemberBO) {
        this.view = view;
        this.teamBO = teamBO;
        this.dynamicEventBO = dynamicEventBO;
        this.teamMemberBO = teamMemberBO;
        this.egressBO = egressBO;
    }

    public void updateTeam(final Team mofidiedTeam, final Team originalTeam) {

        //Update
        teamBO.updateTeam(mofidiedTeam, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {

                //Get fragments and update fields with the new values
                List<Fragment> fragmentList = view.getViewFragmentManager().getFragments();
                for(Fragment fragment: fragmentList){
                    if(fragment instanceof TeamsDetailFragment){
                        ((TeamsDetailFragment)fragment).updateView(mofidiedTeam);
                    }
                }
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {

                //Get fragments and update fields with the old values
                List<Fragment> fragmentList = view.getViewFragmentManager().getFragments();
                for(Fragment fragment: fragmentList){
                    if(fragment instanceof TeamsDetailFragment) {
                        ((TeamsDetailFragment) fragment).updateView(originalTeam);
                    }
                }
            }
        });
    }

    public void retrieveEgressRegisterList() {

        //Call Event business
        dynamicEventBO.retrieveRegisters(null, null, view.getCurrentTeam().getID(), null, EventType.PRE_SCRUTINEERING, new BusinessCallback() {

            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                //Refresh the records
                List<EventRegister> results = (List<EventRegister>) responseDTO.getData();
                updateEventRegisters(results==null ? new ArrayList<EventRegister>() : results);
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
            }
        });
    }


    public void updateEventRegisters(List<EventRegister> items){
        //Update register-list
        this.eventRegisterList.clear();
        this.eventRegisterList.addAll(items);
        this.view.refreshRegisterItems();
    }

    public List<EventRegister> getEventRegisterList(){
        return eventRegisterList;
    }


    /**
     * Retrieve user by NFC tag after read
     * @param tag
     */
    public void onNFCTagDetected(String tag){

        //Retrieve user by the NFC tag
        teamMemberBO.retrieveTeamMemberByNFCTag(tag, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                TeamMember teamMember = (TeamMember)responseDTO.getData();

                //The driver can run, create the register
                dynamicEventBO.createRegister(teamMember, teamMember.getCarNumber(), null, EventType.PRE_SCRUTINEERING, new BusinessCallback() {
                    @Override
                    public void onSuccess(ResponseDTO responseDTO) {

                        PreScrutineeringRegister register = (PreScrutineeringRegister) responseDTO.getData();
                        createEgressRegister(register);

                    }
                    @Override
                    public void onFailure(ResponseDTO responseDTO) {
                    }
                });
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
            }
        });
    }


    /**
     * Call business to create the Egress register
     * @param register
     */
    private void createEgressRegister(PreScrutineeringRegister register){

        egressBO.createRegister(register.getID(), new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                //Refresh the list
                retrieveEgressRegisterList();
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
            }
        });
    }



    /**
     * It is time to update the chrono time
     * @param milliseconds
     * @param registerID
     */
    public void onChronoTimeRegistered(Long milliseconds, String registerID) {

        //We have the time, we need to update the time
        dynamicEventBO.updatePreScrutineeringRegister(registerID, milliseconds, new BusinessCallback() {

            @Override
            public void onSuccess(ResponseDTO responseDTO) {

                //Update results
                retrieveEgressRegisterList();
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
            }
        });
    }



    public interface View {

        Activity getActivity();

        /**
         * Get fragment manager
         * @return
         */
        FragmentManager getViewFragmentManager();

        /**
         * Get current team
         * @return
         */
        Team getCurrentTeam();

        /**
         * Refresh the list items
         */
        void refreshRegisterItems();
    }
}
