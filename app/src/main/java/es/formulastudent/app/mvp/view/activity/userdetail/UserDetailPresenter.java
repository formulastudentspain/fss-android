package es.formulastudent.app.mvp.view.activity.userdetail;

import android.content.Context;
import android.graphics.Bitmap;

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

import java.util.HashMap;
import java.util.Map;


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


    public void onNFCTagDetected(String tag){
        final String tagNFC = tag;

        final DocumentReference userRef = db.collection("UserInfo").document(mAuth.getUid());
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


    protected void uploadProfilePicture(Bitmap bitmap){

        //TODO guardar la imagen en el storage
        //TODO en el onsuccess, recuperar la URL de descarga y enchufarla al usuario y además
        // llamar a view.updateProfilePicture(Bitmap imageBitmap) para actualizar la imagen

        view.updateProfilePicture(bitmap);

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
