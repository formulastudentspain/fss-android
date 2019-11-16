package es.formulastudent.app.mvp.view.activity.teammemberdetail;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ConfigConstants;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.teammember.TeamMemberBO;
import es.formulastudent.app.mvp.data.model.TeamMember;


public class TeamMemberDetailPresenter {

    //Dependencies
    private View view;
    private Context context;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private StorageReference mStorageRef;
    private TeamMemberBO teamMemberBO;

    public TeamMemberDetailPresenter(TeamMemberDetailPresenter.View view, Context context, TeamMemberBO teamMemberBO) {
        this.view = view;
        this.context = context;
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        this.teamMemberBO = teamMemberBO;
    }


    public void onNFCTagDetected(TeamMember teamMember, String tag){
        final String tagNFC = tag;
        final DocumentReference userRef = db.collection(ConfigConstants.FIREBASE_TABLE_TEAM_MEMBERS).document(teamMember.getID());
        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        teamMemberBO.retrieveTeamMemberByNFCTag(tagNFC, new BusinessCallback() {
                            @Override
                            public void onSuccess(ResponseDTO responseDTO) {
                                if(responseDTO.getData() == null) {
                                    userRef.update("tagNFC", tagNFC).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            view.createMessage(R.string.team_member_info_registered);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            view.createMessage(R.string.team_member_error_registering);
                                        }
                                    });
                                } else {
                                    TeamMember teamMember = (TeamMember) responseDTO.getData();
                                    view.createMessage(R.string.team_member_error_tag_already_used, teamMember.getName());
                                }
                            }

                            @Override
                            public void onFailure(ResponseDTO responseDTO) {
                                view.createMessage(R.string.team_member_error_registering);
                            }
                        });
                    } else {
                        Map<String, Object> docData = new HashMap<>();
                        docData.put("mail", mAuth.getCurrentUser().getEmail());
                        docData.put("name", mAuth.getCurrentUser().getDisplayName());
                        docData.put("preScrutineering", false);
                        docData.put("role", "default");
                        docData.put("tagNFC", tagNFC);

                        db.collection("UserInfo").document(mAuth.getUid())
                                .set(docData)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        view.updateNFCInformation(tagNFC);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        //fail
                                        view.createMessage(R.string.team_member_error_creating_user);
                                    }
                                });
                    }
                }
            }
        });
        view.updateNFCInformation(tag);
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

            }
        });
    }


    protected void uploadProfilePicture(Bitmap bitmap, TeamMember teamMember){
        final TeamMember actualTeamMember = teamMember;
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final Bitmap profileImage = bitmap;
        StorageReference storageReference = storage.getReference();
        final StorageReference userProfile = storageReference.child(teamMember.getID()+"_ProfilePhoto.jpg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();


        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = userProfile.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //on failure
                view.createMessage(R.string.team_member_error_updating_profile_picture);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //success
                taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            Uri path = task.getResult();
                            updatePhotoUrl(actualTeamMember, path);
                            view.updateProfilePicture(profileImage);
                        } else {
                            view.createMessage(R.string.team_member_error_updating_profile_picture);
                        }
                    }
                });
            }
        });
    }

    private void updatePhotoUrl(TeamMember actualTeamMember, Uri path) {
        db.collection(ConfigConstants.FIREBASE_TABLE_TEAM_MEMBERS).document(actualTeamMember.getID()).update(
                TeamMember.USER_IMAGE, path.toString()
        );
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
    }

}
