package es.formulastudent.app.mvp.view.screen.userdetail;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.imageuploader.ImageBO;
import es.formulastudent.app.mvp.data.business.user.UserBO;
import es.formulastudent.app.mvp.data.model.Device;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.screen.DataConsumer;
import es.formulastudent.app.mvp.view.screen.userdetail.dialog.AssignDeviceDialog;
import es.formulastudent.app.mvp.view.screen.userdetail.dialog.EditUserDialog;
import es.formulastudent.app.mvp.view.screen.userdetail.dialog.ReturnDeviceDialog;
import es.formulastudent.app.mvp.view.utils.Messages;


public class UserDetailPresenter extends DataConsumer {

    //Dependencies
    private View view;
    private Context context;
    private UserBO userBO;
    private User loggedUser;
    private ImageBO imageBO;
    private Messages messages;

    public UserDetailPresenter(UserDetailPresenter.View view, Context context, UserBO userBO, User loggedUser,
                               ImageBO imageBO, Messages messages) {
        super(userBO, imageBO);
        this.view = view;
        this.context = context;
        this.userBO = userBO;
        this.loggedUser = loggedUser;
        this.imageBO = imageBO;
        this.messages = messages;
    }

    /**
     * Open the selected user to edit
     */
    void openEditUserDialog() {
        FragmentManager fm = view.getActivity().getSupportFragmentManager();
        EditUserDialog editUserDialog = EditUserDialog
                .newInstance(UserDetailPresenter.this, context, loggedUser, view.getSelectedUser());
        editUserDialog.show(fm, "rc_endurance_create_dialog");
    }

    /**
     * Update user
     *
     * @param user
     */
    public void updateUser(final User user) {
        userBO.editUser(user, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                messages.showInfo(responseDTO.getInfo());
                view.setUserDetails(user);
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                messages.showError(responseDTO.getError());
            }
        });
    }


    void manageDeviceAssignment(Device device) {

        if (Device.WALKIE.equals(device)) {
            if (view.getSelectedUser().getWalkie() == null) {
                FragmentManager fm = view.getActivity().getSupportFragmentManager();
                AssignDeviceDialog assignDeviceDialog = AssignDeviceDialog
                        .newInstance(UserDetailPresenter.this, context, device);
                assignDeviceDialog.show(fm, "rc_endurance_create_dialog");

            } else {
                FragmentManager fm = view.getActivity().getSupportFragmentManager();
                ReturnDeviceDialog returnDeviceDialog = ReturnDeviceDialog
                        .newInstance(UserDetailPresenter.this, context, device);
                returnDeviceDialog.show(fm, "rc_endurance_create_dialog");
            }

        } else if (Device.CELLPHONE.equals(device)) {
            if (view.getSelectedUser().getCellPhone() == null) {
                FragmentManager fm = view.getActivity().getSupportFragmentManager();
                AssignDeviceDialog assignDeviceDialog = AssignDeviceDialog
                        .newInstance(UserDetailPresenter.this, context, device);
                assignDeviceDialog.show(fm, "rc_endurance_create_dialog");

            } else {
                FragmentManager fm = view.getActivity().getSupportFragmentManager();
                ReturnDeviceDialog returnDeviceDialog = ReturnDeviceDialog
                        .newInstance(UserDetailPresenter.this, context, device);
                returnDeviceDialog.show(fm, "rc_endurance_create_dialog");
            }
        }
    }

    /**
     * Return device (walkie or phone)
     *
     * @param device
     */
    public void returnDevice(Device device) {
        User user = view.getSelectedUser();

        if (Device.WALKIE.equals(device)) {
            user.setWalkie(null);
            this.updateUser(user);

        } else if (Device.CELLPHONE.equals(device)) {
            user.setCellPhone(null);
            this.updateUser(user);
        }
    }

    /**
     * Assign device (walkie or phone) with the device number
     *
     * @param device
     * @param number
     */
    public void assignDevice(Device device, Long number) {
        User user = view.getSelectedUser();

        if (Device.WALKIE.equals(device)) {
            user.setWalkie(number);
            this.updateUser(user);

        } else if (Device.CELLPHONE.equals(device)) {
            user.setCellPhone(number);
            this.updateUser(user);
        }
    }


    /**
     * Update usere profile picture
     *
     * @param bitmap
     * @param user
     */
    void uploadProfilePicture(final Bitmap bitmap, final User user) {
        imageBO.uploadImage(bitmap, user.getID(), new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                Uri path = (Uri) responseDTO.getData();
                user.setPhotoUrl(path.toString());

                userBO.editUser(user, new BusinessCallback() {
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

    public interface View {

        /**
         * Update the teamMember profile imageView
         *
         * @param imageBitmap
         */
        void updateProfilePicture(Bitmap imageBitmap);

        /**
         * Get selected User
         *
         * @return
         */
        User getSelectedUser();

        /**
         * Get Activity
         *
         * @return
         */
        FragmentActivity getActivity();

        /**
         * Update user information
         *
         * @param user
         */
        void setUserDetails(User user);
    }
}
