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
import es.formulastudent.app.mvp.view.screen.teammember.TeamMemberGeneralPresenter;
import es.formulastudent.app.mvp.view.utils.LoadingDialog;
import es.formulastudent.app.mvp.view.utils.Messages;


public class TeamMemberDetailPresenter implements TeamMemberGeneralPresenter {

    //Dependencies
    private View view;
    private TeamMemberBO teamMemberBO;
    private ImageBO imageBO;
    private TeamBO teamBO;
    private LoadingDialog loadingDialog;
    private Messages messages;

    public TeamMemberDetailPresenter(TeamMemberDetailPresenter.View view, TeamMemberBO teamMemberBO,
                                     ImageBO imageBO, TeamBO teamBO, LoadingDialog loadingDialog,
                                     Messages messages) {
        this.view = view;
        this.teamMemberBO = teamMemberBO;
        this.imageBO = imageBO;
        this.teamBO = teamBO;
        this.loadingDialog = loadingDialog;
        this.messages = messages;
    }

    void onNFCTagDetected(final TeamMember teamMember, final String tagNFC) {

        //Check if the NFC tag is already assigned
        loadingDialog.show();
        teamMemberBO.retrieveTeamMemberByNFCTag(tagNFC, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                loadingDialog.hide();

                if (responseDTO.getData() == null) {
                    teamMember.setNFCTag(tagNFC);
                    teamMemberBO.updateTeamMember(teamMember, new BusinessCallback() {
                        @Override
                        public void onSuccess(ResponseDTO responseDTO) {
                            loadingDialog.hide();
                            messages.showInfo(responseDTO.getInfo());
                            view.updateNFCInformation(tagNFC);
                        }

                        @Override
                        public void onFailure(ResponseDTO responseDTO) {
                            loadingDialog.hide();
                            messages.showError(responseDTO.getError());
                        }
                    });

                    //The NFC tag is already assigned
                } else {
                    TeamMember teamMember = (TeamMember) responseDTO.getData();
                    messages.showError(R.string.team_member_error_tag_already_used, teamMember.getName());
                    loadingDialog.hide();
                }
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                loadingDialog.hide();
                messages.showError(responseDTO.getError());
            }
        });
    }

    void checkDocuments(TeamMember teamMember) {

        teamMember.setCertifiedASR(true);
        teamMember.setCertifiedDriver(true);
        teamMember.setCertifiedESO(true);

        loadingDialog.show();
        teamMemberBO.updateTeamMember(teamMember, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                loadingDialog.hide();
                view.updateTeamMemberInfo(teamMember);
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                loadingDialog.hide();
            }
        });
    }


    void checkMaxNumDrivers() {
        loadingDialog.show();
        teamMemberBO.getRegisteredTeamMemberByTeamId(view.getSelectedUser().getTeamID(), new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                loadingDialog.hide();
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
                loadingDialog.hide();
                messages.showError(responseDTO.getError());
            }
        });
    }


    void uploadProfilePicture(final Bitmap bitmap, final TeamMember teamMember) {
        //Upload image and get the URL
        loadingDialog.show();
        imageBO.uploadImage(bitmap, teamMember.getID(), new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                Uri path = (Uri) responseDTO.getData();
                teamMember.setPhotoUrl(path.toString());

                //Update de team member with the URL
                teamMemberBO.updateTeamMember(teamMember, new BusinessCallback() {
                    @Override
                    public void onSuccess(ResponseDTO responseDTO) {
                        loadingDialog.hide();
                        view.updateProfilePicture(bitmap);
                        messages.showInfo(responseDTO.getInfo());
                    }

                    @Override
                    public void onFailure(ResponseDTO responseDTO) {
                        loadingDialog.hide();
                        messages.showError(responseDTO.getError());
                    }
                });
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                loadingDialog.hide();
                messages.showError(responseDTO.getError());
            }
        });

    }

    public void updateOrCreateTeamMember(TeamMember teamMember) {
        loadingDialog.show();
        teamMemberBO.updateTeamMember(teamMember, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                loadingDialog.hide();
                view.updateTeamMemberInfo(teamMember);
                messages.showInfo(responseDTO.getInfo());
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                loadingDialog.hide();
                messages.showError(responseDTO.getError());
            }
        });
    }

    void openEditTeamMemberDialog() {
        //Call business to retrieve teams
        loadingDialog.show();
        teamBO.retrieveTeams(null, null, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                loadingDialog.hide();
                List<Team> teams = (List<Team>) responseDTO.getData();
                view.showEditTeamMemberDialog(teams);
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                loadingDialog.hide();
                messages.showError(responseDTO.getError());
            }
        });
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
