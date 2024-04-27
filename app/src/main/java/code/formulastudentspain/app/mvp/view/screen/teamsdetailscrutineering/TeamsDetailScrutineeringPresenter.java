package code.formulastudentspain.app.mvp.view.screen.teamsdetailscrutineering;

import android.app.Activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

import code.formulastudentspain.app.R;
import code.formulastudentspain.app.mvp.data.business.BusinessCallback;
import code.formulastudentspain.app.mvp.data.business.DataConsumer;
import code.formulastudentspain.app.mvp.data.business.ResponseDTO;
import code.formulastudentspain.app.mvp.data.business.egress.EgressBO;
import code.formulastudentspain.app.mvp.data.business.raceaccess.RaceAccessBO;
import code.formulastudentspain.app.mvp.data.business.team.TeamBO;
import code.formulastudentspain.app.mvp.data.business.teammember.TeamMemberBO;
import code.formulastudentspain.app.mvp.data.model.EventRegister;
import code.formulastudentspain.app.mvp.data.model.EventType;
import code.formulastudentspain.app.mvp.data.model.PreScrutineeringRegister;
import code.formulastudentspain.app.mvp.data.model.Team;
import code.formulastudentspain.app.mvp.view.screen.teamsdetailscrutineering.tabs.TeamsDetailFragment;
import code.formulastudentspain.app.mvp.view.utils.messages.Message;
import code.formulastudentspain.app.mvp.view.utils.messages.Messages;


public class TeamsDetailScrutineeringPresenter extends DataConsumer {

    //Dependencies
    private View view;
    private TeamBO teamBO;
    private RaceAccessBO raceAccessBO;
    private TeamMemberBO teamMemberBO;
    private EgressBO egressBO;
    private Messages messages;

    //Data
    private List<EventRegister> eventRegisterList = new ArrayList<>();


    public TeamsDetailScrutineeringPresenter(TeamsDetailScrutineeringPresenter.View view, TeamBO teamBO,
                                             RaceAccessBO raceAccessBO, EgressBO egressBO,
                                             TeamMemberBO teamMemberBO, Messages messages) {
        super(teamBO, raceAccessBO, teamMemberBO, egressBO);
        this.view = view;
        this.teamBO = teamBO;
        this.raceAccessBO = raceAccessBO;
        this.teamMemberBO = teamMemberBO;
        this.egressBO = egressBO;
        this.messages = messages;
    }

    public void updateTeam(final Team modifiedTeam, final Team originalTeam) {
        teamBO.updateTeam(modifiedTeam,
                response -> {
                    //Get fragments and update fields with the new values
                    List<Fragment> fragmentList = view.getViewFragmentManager().getFragments();
                    for (Fragment fragment : fragmentList) {
                        if (fragment instanceof TeamsDetailFragment) {
                            ((TeamsDetailFragment) fragment).updateView(modifiedTeam);
                        }
                    }
                },
                errorMessage -> {
                    //Get fragments and update fields with the old values
                    List<Fragment> fragmentList = view.getViewFragmentManager().getFragments();
                    for (Fragment fragment : fragmentList) {
                        if (fragment instanceof TeamsDetailFragment) {
                            ((TeamsDetailFragment) fragment).updateView(originalTeam);
                        }
                    }
                    setErrorToDisplay(errorMessage);
                });
    }

    public void retrieveEgressRegisterList() {
        raceAccessBO.retrieveRegisters(null, null, view.getCurrentTeam().getID(),
                null, EventType.PRE_SCRUTINEERING,
                this::updateEventRegisters,
                this::setErrorToDisplay);
    }


    private void updateEventRegisters(List<EventRegister> items){
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
    public void onNFCTagDetected(String tag, Team team) {
        teamMemberBO.retrieveTeamMemberByNFCTag(tag,
                teamMember -> {
                    if(teamMember.getTeam().contains(team.getName())){
                        //The driver can run, create the register
                        raceAccessBO.createRegister(teamMember, teamMember.getCarNumber(), null, EventType.PRE_SCRUTINEERING,
                                register -> createEgressRegister((PreScrutineeringRegister)register),
                                this::setErrorToDisplay);
                    } else {
                     this.setErrorToDisplay(new Message(R.string.prescruti_error_egress_wrong_team));
                    }
                }, this::setErrorToDisplay);
    }


    /**
     * Call business to create the Egress register
     * @param register
     */
    private void createEgressRegister(PreScrutineeringRegister register){
        egressBO.createRegister(register.getID(), new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                retrieveEgressRegisterList();
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                messages.showError(responseDTO.getError());
            }
        });
    }


    /**
     * It is time to update the chrono time
     * @param milliseconds
     * @param registerID
     */
    public void onChronoTimeRegistered(Long milliseconds, String registerID) {
        raceAccessBO.updatePreScrutineeringRegister(registerID, milliseconds,
                response -> retrieveEgressRegisterList(),
                this::setErrorToDisplay);
    }

    public Messages getMessages(){
        return messages;
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
