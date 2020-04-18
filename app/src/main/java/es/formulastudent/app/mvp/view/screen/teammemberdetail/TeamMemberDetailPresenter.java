package es.formulastudent.app.mvp.view.screen.teammemberdetail;

import android.graphics.Bitmap;
import android.net.Uri;

import java.util.List;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.imageuploader.ImageBO;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.business.teammember.TeamMemberBO;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.data.model.TeamMember;
import es.formulastudent.app.mvp.data.business.DataConsumer;
import es.formulastudent.app.mvp.view.screen.teammember.TeamMemberGeneralPresenter;
import es.formulastudent.app.mvp.view.utils.messages.Messages;


public class TeamMemberDetailPresenter extends DataConsumer implements TeamMemberGeneralPresenter {

    //Dependencies
    private View view;
    private TeamMemberBO teamMemberBO;
    private ImageBO imageBO;
    private TeamBO teamBO;
    private Messages messages;

    public TeamMemberDetailPresenter(TeamMemberDetailPresenter.View view, TeamMemberBO teamMemberBO,
                                     ImageBO imageBO, TeamBO teamBO, Messages messages) {
        super(teamMemberBO, imageBO, teamBO);
        this.view = view;
        this.teamMemberBO = teamMemberBO;
        this.imageBO = imageBO;
        this.teamBO = teamBO;
        this.messages = messages;
    }

    void onNFCTagDetected(final TeamMember teamMember, final String tagNFC) {
        //Check if the NFC tag is already assigned
        teamMemberBO.retrieveTeamMemberByNFCTag(tagNFC,
                retrievedTeamMember -> {
                    if (retrievedTeamMember == null) {
                        teamMember.setNFCTag(tagNFC);
                        teamMemberBO.updateTeamMember(teamMember, new BusinessCallback() {
                            @Override
                            public void onSuccess(ResponseDTO responseDTO) {
                                messages.showInfo(responseDTO.getInfo());
                                view.updateNFCInformation(tagNFC);
                            }

                            @Override
                            public void onFailure(ResponseDTO responseDTO) {
                                messages.showError(responseDTO.getError());
                            }
                        });

                        //The NFC tag is already assigned
                    } else {
                        messages.showError(R.string.team_member_error_tag_already_used, retrievedTeamMember.getName());
                    }
                },
                error -> {
                    messages.showError(1); //FIXME
                });
    }

    void checkDocuments(TeamMember teamMember) {

        teamMember.setCertifiedASR(true);
        teamMember.setCertifiedDriver(true);
        teamMember.setCertifiedESO(true);

        teamMemberBO.updateTeamMember(teamMember, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                view.updateTeamMemberInfo(teamMember);
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) { }
        });
    }


    void checkMaxNumDrivers() {
        teamMemberBO.getRegisteredTeamMemberByTeamId(view.getSelectedUser().getTeamID(), new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                List<TeamMember> teamMembers = (List<TeamMember>) responseDTO.getData();

                boolean updatingUserNFC = false;
                for (TeamMember teamMember : teamMembers) {
                    if (teamMember.getID().equals(view.getSelectedUser().getID())) {
                        updatingUserNFC = true;
                        break;
                    }
                }

                if (!updatingUserNFC && teamMembers.size() >= 6) {
                    messages.showError(R.string.team_member_error_max_6_drivers);
                } else {
                    view.openNFCReader();
                }
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                messages.showError(responseDTO.getError());
            }
        });
    }


    void uploadProfilePicture(final Bitmap bitmap, final TeamMember teamMember) {
        //Upload image and get the URL
        imageBO.uploadImage(bitmap, teamMember.getID(), new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                Uri path = (Uri) responseDTO.getData();
                teamMember.setPhotoUrl(path.toString());

                //Update de team member with the URL
                teamMemberBO.updateTeamMember(teamMember, new BusinessCallback() {
                    @Override
                    public void onSuccess(ResponseDTO responseDTO) {
                        view.updateProfilePicture(bitmap);
                        messages.showInfo(responseDTO.getInfo());
                    }

                    @Override
                    public void onFailure(ResponseDTO responseDTO) {
                        messages.showError(responseDTO.getError());
                    }
                });
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                messages.showError(responseDTO.getError());
            }
        });

    }

    public void updateOrCreateTeamMember(TeamMember teamMember) {
        teamMemberBO.updateTeamMember(teamMember, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                view.updateTeamMemberInfo(teamMember);
                messages.showInfo(responseDTO.getInfo());
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                messages.showError(responseDTO.getError());
            }
        });
    }

    void openEditTeamMemberDialog() {
        //Call business to retrieve teams
        teamBO.retrieveTeams(null, null,
                teams -> view.showEditTeamMemberDialog(teams),
                error -> messages.showError(1)); //FIXME
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
