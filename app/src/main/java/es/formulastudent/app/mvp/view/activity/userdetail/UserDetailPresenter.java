package es.formulastudent.app.mvp.view.activity.userdetail;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.fragment.app.FragmentManager;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.imageuploader.ImageBO;
import es.formulastudent.app.mvp.data.business.user.UserBO;
import es.formulastudent.app.mvp.data.model.Device;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.activity.userdetail.dialog.AssignDeviceDialog;
import es.formulastudent.app.mvp.view.activity.userdetail.dialog.EditUserDialog;
import es.formulastudent.app.mvp.view.activity.userdetail.dialog.ReturnDeviceDialog;


public class UserDetailPresenter {

    //Dependencies
    private View view;
    private Context context;
    private UserBO userBO;
    private User loggedUser;
    private ImageBO imageBO;

    public UserDetailPresenter(UserDetailPresenter.View view, Context context, UserBO userBO, User loggedUser, ImageBO imageBO) {
        this.view = view;
        this.context = context;
        this.userBO = userBO;
        this.loggedUser = loggedUser;
        this.imageBO = imageBO;
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


   public void manageDeviceAssignment(Device device) {

       if(Device.WALKIE.equals(device)){

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
       }else if(Device.CELLPHONE.equals(device)){

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
   public void returnDevice(Device device){
       User user = view.getSelectedUser();

       if(Device.WALKIE.equals(device)){
           user.setWalkie(null);
           this.updateUser(user);

       }else if(Device.CELLPHONE.equals(device)){
           user.setCellPhone(null);
           this.updateUser(user);
       }
   }

    /**
     * Assign device (walkie or phone) with the device number
     * @param device
     * @param number
     */
    public void assignDevice(Device device, Long number){
        User user = view.getSelectedUser();

        if(Device.WALKIE.equals(device)){
            user.setWalkie(number);
            this.updateUser(user);

        }else if(Device.CELLPHONE.equals(device)){
            user.setCellPhone(number);
            this.updateUser(user);
        }
    }


    /**
     * Update usere profile picture
     * @param bitmap
     * @param user
     */
    void uploadProfilePicture(final Bitmap bitmap, final User user){

        //Show loading
        view.showLoading();

        //Upload image and get the URL
        imageBO.uploadImage(bitmap, user.getID(), new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                Uri path = (Uri) responseDTO.getData();
                user.setPhotoUrl(path.toString());

                //Update de team member with the URL
                userBO.editUser(user, new BusinessCallback() {
                    @Override
                    public void onSuccess(ResponseDTO responseDTO) {

                        //Update the image in view
                        view.updateProfilePicture(bitmap);
                        view.hideLoading();
                        view.createMessage(responseDTO.getInfo());
                    }

                    @Override
                    public void onFailure(ResponseDTO responseDTO) {
                        view.createMessage(responseDTO.getError());
                        view.hideLoading();
                    }
                });
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                view.createMessage(responseDTO.getError());
                view.hideLoading();
            }
        });

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
