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
import es.formulastudent.app.mvp.data.business.user.UserBO;
import es.formulastudent.app.mvp.data.model.User;


public class UserDetailPresenter {

    //Dependencies
    private View view;
    private Context context;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private StorageReference mStorageRef;
    private UserBO userBO;

    public UserDetailPresenter(UserDetailPresenter.View view, Context context, UserBO userBO) {
        this.view = view;
        this.context = context;
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        this.userBO = userBO;
    }


    public void onNFCTagDetected(User user, String tag){
        final String tagNFC = tag;
        final DocumentReference userRef = db.collection(ConfigConstants.FIREBASE_TABLE_USER_INFO).document(user.getID());
        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        userBO.retrieveUserByNFCTag(tagNFC, new BusinessCallback() {
                            @Override
                            public void onSuccess(ResponseDTO responseDTO) {
                                if(responseDTO.getData() == null) {
                                    userRef.update("tagNFC", tagNFC).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            view.createMessage(R.string.users_info_registered);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            view.createMessage(R.string.users_error_registering);
                                        }
                                    });
                                } else {
                                    User user = (User) responseDTO.getData();
                                    view.createMessage(R.string.users_error_tag_already_used, user.getName());
                                }
                            }

                            @Override
                            public void onFailure(ResponseDTO responseDTO) {
                                view.createMessage(R.string.users_error_registering);
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
                                        view.createMessage(R.string.users_error_creating_user);
                                    }
                                });
                    }
                }
            }
        });
        view.updateNFCInformation(tag);
    }


    public void checkMaxNumDrivers(){

        userBO.getRegisteredUsersByTeamId(view.getSelectedUser().getTeamID(), new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                List<User> users = (List<User>) responseDTO.getData();

                boolean updatingUserNFC = false;
                for(User user: users){
                    if(user.getID().equals(view.getSelectedUser().getID())){
                        updatingUserNFC = true;
                        break;
                    }
                }

                if(!updatingUserNFC && users.size() >= 6){
                    view.createMessage(R.string.users_error_max_6_drivers);
                }else{
                    view.openNFCReader();
                }

            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {

            }
        });
    }


    protected void uploadProfilePicture(Bitmap bitmap, User user){
        final User actualUser = user;
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final Bitmap profileImage = bitmap;
        StorageReference storageReference = storage.getReference();
        final StorageReference userProfile = storageReference.child(user.getID()+"_ProfilePhoto.jpg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();


        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = userProfile.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //on failure
                view.createMessage(R.string.users_error_updating_profile_picture);
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
                            updatePhotoUrl(actualUser, path);
                            view.updateProfilePicture(profileImage);
                        } else {
                            view.createMessage(R.string.users_error_updating_profile_picture);
                        }
                    }
                });
            }
        });
    }

    private void updatePhotoUrl(User actualUser, Uri path) {
        db.collection(ConfigConstants.FIREBASE_TABLE_USER_INFO).document(actualUser.getID()).update(
                User.USER_IMAGE, path.toString()
        );
    }


    public interface View {

        /**
         * Show message to user
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
         * Update user NFC infomation
         */
        void updateNFCInformation(String TAG);

        /**
         * Update the user profile imageView
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
