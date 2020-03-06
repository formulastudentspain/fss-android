package es.formulastudent.app.mvp.view.activity.teammemberdetail;

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
import es.formulastudent.app.mvp.view.activity.teammember.TeamMemberGeneralPresenter;


public class TeamMemberDetailPresenter implements TeamMemberGeneralPresenter {

    //Dependencies
    private View view;
    private TeamMemberBO teamMemberBO;
    private ImageBO imageBO;
    private TeamBO teamBO;

    public TeamMemberDetailPresenter(TeamMemberDetailPresenter.View view, TeamMemberBO teamMemberBO, ImageBO imageBO, TeamBO teamBO) {
        this.view = view;
        this.teamMemberBO = teamMemberBO;
        this.imageBO = imageBO;
        this.teamBO = teamBO;
    }

    void onNFCTagDetected(final TeamMember teamMember, final String tagNFC){

        //Show loading
        view.showLoading();

        //Check if the NFC tag is already assigned
        teamMemberBO.retrieveTeamMemberByNFCTag(tagNFC, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {

                //The NFC tag is not assigned
                if(responseDTO.getData() == null) {

                    //Update the user
                    teamMember.setNFCTag(tagNFC);
                    teamMemberBO.updateTeamMember(teamMember, new BusinessCallback() {
                        @Override
                        public void onSuccess(ResponseDTO responseDTO) {
                            view.createMessage(responseDTO.getInfo());
                            view.updateNFCInformation(tagNFC);
                            view.hideLoadingIcon();
                        }

                        @Override
                        public void onFailure(ResponseDTO responseDTO) {
                            view.createMessage(responseDTO.getError());
                            view.hideLoadingIcon();
                        }
                    });

                //The NFC tag is already assigned
                } else {
                    TeamMember teamMember = (TeamMember) responseDTO.getData();
                    view.createMessage(R.string.team_member_error_tag_already_used, teamMember.getName());
                    view.hideLoadingIcon();
                }
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                view.createMessage(responseDTO.getError());
                view.hideLoadingIcon();
            }
        });
    }

    public void checkDocuments(TeamMember teamMember){

        teamMember.setCertifiedASR(true);
        teamMember.setCertifiedDriver(true);
        teamMember.setCertifiedESO(true);

        view.showLoading();
        teamMemberBO.updateTeamMember(teamMember, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                view.updateTeamMemberInfo(teamMember);
                view.hideLoadingIcon();
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                view.hideLoadingIcon();
            }
        });

    }


    public void checkMaxNumDrivers(){

        teamMemberBO.getRegisteredTeamMemberByTeamId(view.getSelectedUser().getTeamID(), new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                List<TeamMember> teamMembers = (List<TeamMember>) responseDTO.getData();

                boolean updatingUserNFC = false;
                for(TeamMember teamMember : teamMembers){
                    if(teamMember.getID().equals(view.getSelectedUser().getID())){
                        updatingUserNFC = true;
                        break;
                    }
                }

                if(!updatingUserNFC && teamMembers.size() >= 6){
                    view.createMessage(R.string.team_member_error_max_6_drivers);
                }else{
                    view.openNFCReader();
                }
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                view.createMessage(responseDTO.getError());
            }
        });
    }


    void uploadProfilePicture(final Bitmap bitmap, final TeamMember teamMember){

        //Show loading
        view.showLoading();

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

                        //Update the image in view
                        view.updateProfilePicture(bitmap);
                        view.hideLoadingIcon();
                        view.createMessage(responseDTO.getInfo());
                    }

                    @Override
                    public void onFailure(ResponseDTO responseDTO) {
                        view.createMessage(responseDTO.getError());
                        view.hideLoadingIcon();
                    }
                });
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                view.createMessage(responseDTO.getError());
                view.hideLoadingIcon();
            }
        });

    }

    public void updateOrCreateTeamMember(TeamMember teamMember){
        teamMemberBO.updateTeamMember(teamMember, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                view.updateTeamMemberInfo(teamMember);
                view.createMessage(responseDTO.getInfo());
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                view.createMessage(responseDTO.getError());
            }
        });
    }

    public void openEditTeamMemberDialog(){

        //Show loading
        view.showLoading();

        //Call business to retrieve teams
        teamBO.retrieveTeams(null, null, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {

                //Hide loading
                view.hideLoadingIcon();

                List<Team> teams = (List<Team>) responseDTO.getData();
                view.showEditTeamMemberDialog(teams);
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                view.createMessage(R.string.team_member_get_teams_error);
            }
        });
    }

    public interface View {

        /**
         * Show message to teamMember
         * @param message
         */
        void createMessage(Integer message, Object...args);

        /**
         * Finish current activity
         */
        void finishView();

        /**
         * Show loading icon
         */
        void showLoading();

        /**
         * Hide loading icon
         */
        void hideLoadingIcon();

        /**
         * Update teamMember NFC infomation
         */
        void updateNFCInformation(String TAG);

        /**
         * Update the teamMember profile imageView
         * @param imageBitmap
         */
        void updateProfilePicture(Bitmap imageBitmap);

        /**
         * Get selected TeamMember
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
         * @param teams
         */
        void showEditTeamMemberDialog(List<Team> teams);
    }

}
