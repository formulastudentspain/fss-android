package code.formulastudentspain.app.mvp.view.screen.userdetail;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import code.formulastudentspain.app.mvp.data.business.DataConsumer;
import code.formulastudentspain.app.mvp.data.business.imageuploader.ImageBO;
import code.formulastudentspain.app.mvp.data.business.user.UserBO;
import code.formulastudentspain.app.mvp.data.model.Device;
import code.formulastudentspain.app.mvp.data.model.User;
import code.formulastudentspain.app.mvp.view.screen.userdetail.dialog.AssignDeviceDialog;
import code.formulastudentspain.app.mvp.view.screen.userdetail.dialog.EditUserDialog;
import code.formulastudentspain.app.mvp.view.screen.userdetail.dialog.ReturnDeviceDialog;
import code.formulastudentspain.app.mvp.view.utils.messages.Messages;


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


    public void updateUser(final User user) {
        userBO.editUser(user,
                onSuccess -> view.setUserDetails(user),
                this::setErrorToDisplay);
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
        imageBO.uploadImage(bitmap, user.getID(),
                path -> {
                    user.setPhotoUrl(path.toString());
                    userBO.editUser(user,
                            response -> view.updateProfilePicture(bitmap),
                            this::setErrorToDisplay);
                },
                this::setErrorToDisplay
        );
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
