package es.formulastudent.app.mvp.view.activity.userdetail;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.business.ConfigConstants;
import es.formulastudent.app.mvp.data.business.teammember.TeamMemberBO;
import es.formulastudent.app.mvp.data.model.TeamMember;
import es.formulastudent.app.mvp.data.model.User;


public class UserDetailPresenter {

    //Dependencies
    private View view;
    private Context context;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private StorageReference mStorageRef;
    private TeamMemberBO teamMemberBO;

    public UserDetailPresenter(UserDetailPresenter.View view, Context context, TeamMemberBO teamMemberBO) {
        this.view = view;
        this.context = context;
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        this.teamMemberBO = teamMemberBO;
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
        void createMessage(Integer message, Object... args);

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
         * Get selected User
         * @return
         */
        User getSelectedUser();

        /**
         * Open NFC Reader Activity
         */
        void openNFCReader();
    }

}
