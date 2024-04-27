package code.formulastudentspain.app.mvp.view.screen.teammemberdetail;

import android.graphics.Bitmap;

import java.util.List;

import code.formulastudentspain.app.R;
import code.formulastudentspain.app.mvp.data.business.DataConsumer;
import code.formulastudentspain.app.mvp.data.business.imageuploader.ImageBO;
import code.formulastudentspain.app.mvp.data.business.team.TeamBO;
import code.formulastudentspain.app.mvp.data.business.teammember.TeamMemberBO;
import code.formulastudentspain.app.mvp.data.model.Team;
import code.formulastudentspain.app.mvp.data.model.TeamMember;
import code.formulastudentspain.app.mvp.view.screen.teammember.TeamMemberGeneralPresenter;
import code.formulastudentspain.app.mvp.view.utils.messages.Message;


public class TeamMemberDetailPresenter extends DataConsumer implements TeamMemberGeneralPresenter {

    //Dependencies
    private View view;
    private TeamMemberBO teamMemberBO;
    private ImageBO imageBO;
    private TeamBO teamBO;

    public TeamMemberDetailPresenter(TeamMemberDetailPresenter.View view, TeamMemberBO teamMemberBO,
                                     ImageBO imageBO, TeamBO teamBO) {
        super(teamMemberBO, imageBO, teamBO);
        this.view = view;
        this.teamMemberBO = teamMemberBO;
        this.imageBO = imageBO;
        this.teamBO = teamBO;
    }

    void onNFCTagDetected(final TeamMember teamMember, final String tagNFC) {
        //Check if the NFC tag is already assigned
        teamMemberBO.retrieveTeamMemberByNFCTag(tagNFC,
                retrievedTeamMember -> {
                    if (retrievedTeamMember == null) {
                        teamMember.setNFCTag(tagNFC);
                        teamMemberBO.updateTeamMember(teamMember,
                                onSuccess -> view.updateNFCInformation(tagNFC),
                                this::setErrorToDisplay);
                    } else {//The NFC tag is already assigned
                        setErrorToDisplay(new Message(R.string.team_member_error_tag_already_used,
                                retrievedTeamMember.getName()));
                    }
                },
                this::setErrorToDisplay
        );
    }

    void checkDocuments(TeamMember teamMember) {
        teamMember.setCertifiedASR(true);
        teamMember.setCertifiedDriver(true);
        teamMember.setCertifiedESO(true);

        teamMemberBO.updateTeamMember(teamMember,
                response -> view.updateTeamMemberInfo(teamMember),
                this::setErrorToDisplay);
    }


    void checkMaxNumDrivers() {
        teamMemberBO.getRegisteredTeamMemberByTeamId(view.getSelectedUser().getTeamID(),
                teamMembers -> {
                    boolean updatingUserNFC = false;
                    for (TeamMember teamMember : teamMembers) {
                        if (teamMember.getID().equals(view.getSelectedUser().getID())) {
                            updatingUserNFC = true;
                            break;
                        }
                    }
                    if (!updatingUserNFC && teamMembers.size() >= 6) {
                        setErrorToDisplay(new Message(R.string.team_member_error_max_6_drivers));
                    } else {
                        view.openNFCReader();
                    }
                },
                this::setErrorToDisplay);
    }


    void uploadProfilePicture(final Bitmap bitmap, final TeamMember teamMember) {
        //Upload image and get the URL
        imageBO.uploadImage(bitmap, teamMember.getID(),
                path -> {
                    teamMember.setPhotoUrl(path.toString());

                    //Update de team member with the URL
                    teamMemberBO.updateTeamMember(teamMember,
                            onSuccess -> view.updateProfilePicture(bitmap),
                            this::setErrorToDisplay);
                },
                this::setErrorToDisplay);
    }

    public void updateOrCreateTeamMember(TeamMember teamMember) {
        teamMemberBO.updateTeamMember(teamMember,
                onSuccess -> view.updateTeamMemberInfo(teamMember),
                this::setErrorToDisplay);
    }

    void openEditTeamMemberDialog() {
        //Call business to retrieve teams
        teamBO.retrieveTeams(null, null,
                teams -> view.showEditTeamMemberDialog(teams),
                this::setErrorToDisplay);
    }

    public interface View {

        /**
         * Update teamMember NFC infomation
         */
        void updateNFCInformation(String TAG);

        /**
         * Update the teamMember profile imageView
         *
         * @param imageBitmap
         */
        void updateProfilePicture(Bitmap imageBitmap);

        /**
         * Get selected TeamMember
         *
         * @return
         */
        TeamMember getSelectedUser();

        /**
         * Open NFC Reader Activity
         */
        void openNFCReader();

        /**
         * Update team member info
         */
        void updateTeamMemberInfo(TeamMember teamMember);

        /**
         * Show edit team member dialog
         *
         * @param teams
         */
        void showEditTeamMemberDialog(List<Team> teams);
    }

}
