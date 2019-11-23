package es.formulastudent.app.mvp.view.activity.userdetail;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

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
import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ConfigConstants;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.user.UserBO;
import es.formulastudent.app.mvp.data.model.TeamMember;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.activity.userdetail.dialog.AssignDeviceDialog;
import es.formulastudent.app.mvp.view.activity.userdetail.dialog.EditUserDialog;
import es.formulastudent.app.mvp.view.activity.userdetail.dialog.ReturnDeviceDialog;


public class UserDetailPresenter {

    //Dependencies
    private View view;
    private Context context;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private StorageReference mStorageRef;
    private UserBO userBO;
    private User loggedUser;

    public UserDetailPresenter(UserDetailPresenter.View view, Context context, UserBO userBO, User loggedUser) {
        this.view = view;
        this.context = context;
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        this.userBO = userBO;
        this.loggedUser = loggedUser;
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

    /**
     * Open the selected user to edit
     */
    public void openEditUserDialog() {

        FragmentManager fm = view.getActivity().getSupportFragmentManager();
        EditUserDialog editUserDialog = EditUserDialog
                .newInstance(UserDetailPresenter.this, context, loggedUser, view.getSelectedUser());

        editUserDialog.show(fm, "rc_endurance_create_dialog");

    }

    /**
     * Update user
     * @param user
     */
   public void updateUser(final User user){

       view.showLoading();

        userBO.editUser(user, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                view.createMessage(responseDTO.getInfo());
                view.setUserDetails(user);

                view.hideLoading();
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                view.createMessage(responseDTO.getError());

            }
        });
   }


   public void manageDeviceAssignment(String device) {

       if("walkie".equals(device)){

           //assign walkie
           if(view.getSelectedUser().getWalkie() == null){
               FragmentManager fm = view.getActivity().getSupportFragmentManager();
               AssignDeviceDialog assignDeviceDialog = AssignDeviceDialog
                       .newInstance(UserDetailPresenter.this, context, device);
               assignDeviceDialog.show(fm, "rc_endurance_create_dialog");

           //return walkie
           }else{
               FragmentManager fm = view.getActivity().getSupportFragmentManager();
               ReturnDeviceDialog returnDeviceDialog = ReturnDeviceDialog
                       .newInstance(UserDetailPresenter.this, context, device);
               returnDeviceDialog.show(fm, "rc_endurance_create_dialog");
           }
       }else if("phone".equals(device)){

           //Assign phone
           if(view.getSelectedUser().getCellPhone() == null){
               FragmentManager fm = view.getActivity().getSupportFragmentManager();
               AssignDeviceDialog assignDeviceDialog = AssignDeviceDialog
                       .newInstance(UserDetailPresenter.this, context, device);
               assignDeviceDialog.show(fm, "rc_endurance_create_dialog");

           //Return phone
           }else{
               FragmentManager fm = view.getActivity().getSupportFragmentManager();
               ReturnDeviceDialog returnDeviceDialog = ReturnDeviceDialog
                       .newInstance(UserDetailPresenter.this, context, device);
               returnDeviceDialog.show(fm, "rc_endurance_create_dialog");
           }
       }
   }

    /**
     * Return device (walkie or phone)
     * @param device
     */
   public void returnDevice(String device){
       User user = view.getSelectedUser();

       if("walkie".equals(device)){
           user.setWalkie(null);
           this.updateUser(user);

       }else if("phone".equals(device)){
           user.setCellPhone(null);
           this.updateUser(user);
       }
   }

    /**
     * Assign device (walkie or phone) with the device number
     * @param device
     * @param number
     */
    public void assignDevice(String device, Long number){
        User user = view.getSelectedUser();

        if("walkie".equals(device)){
            user.setWalkie(number);
            this.updateUser(user);

        }else if("phone".equals(device)){
            user.setCellPhone(number);
            this.updateUser(user);
        }
    }

    public interface View {

        /**
         * Show message to teamMember
         * @param message
         */
        void createMessage(Integer message, Object... args);

        /**
         * Show loading icon
         */
        void showLoading();

        /**
         * Hide loading
         */
        void hideLoading();

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
         * Get Activity
         * @return
         */
        UserDetailActivity getActivity();

        /**
         * Update user information
         * @param user
         */
        void setUserDetails(User user);
    }

}
