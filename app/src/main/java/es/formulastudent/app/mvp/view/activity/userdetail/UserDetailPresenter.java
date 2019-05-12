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
import java.util.Map;

import es.formulastudent.app.mvp.data.model.User;


public class UserDetailPresenter {

    //Dependencies
    private View view;
    private Context context;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private StorageReference mStorageRef;

    public UserDetailPresenter(UserDetailPresenter.View view, Context context) {
        this.view = view;
        this.context = context;
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }


    public void onNFCTagDetected(User user, String tag){
        final String tagNFC = tag;
        final DocumentReference userRef = db.collection("UserInfo").document(user.getID());
        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        userRef.update("tagNFC", tagNFC).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                view.showMessage("tag NFC updated Correctly");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                view.showMessage("error on update NFC Tag");
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
                                        view.showMessage("Failed to save data to db");
                                    }
                                });
                    }
                }
            }
        });
        view.updateNFCInformation(tag);
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
                view.showMessage("Error uploading profile picture");
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
                            view.showMessage("Error updating profile picture.");
                        }
                    }
                });
            }
        });
    }

    private void updatePhotoUrl(User actualUser, Uri path) {
        db.collection("UserInfo").document(actualUser.getID()).update(
                User.USER_IMAGE, path.toString()
        );
    }


    public interface View {

        /**
         * Show message to user
         * @param message
         */
        void showMessage(String message);

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
    }

}
