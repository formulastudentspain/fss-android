package es.formulastudent.app.mvp.data.api.user.impl;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import es.formulastudent.app.mvp.data.api.BusinessCallback;
import es.formulastudent.app.mvp.data.api.ResponseDTO;
import es.formulastudent.app.mvp.data.api.user.UserBO;
import es.formulastudent.app.mvp.data.model.User;

public class UserBOFirebaseImpl implements UserBO {

    private FirebaseFirestore firebaseFirestore;

    public UserBOFirebaseImpl(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
    }

    @Override
    public void retrieveUserByNFCTag(String tag, final BusinessCallback callback) {

        final ResponseDTO responseDTO = new ResponseDTO();

        firebaseFirestore.collection(User.COLLECTION_ID)
                .whereEqualTo(User.TAG_NFC, tag)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        //success
                        if(queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                            User user = new User(queryDocumentSnapshots.getDocuments().get(0));
                            responseDTO.setData(user);
                            callback.onSuccess(responseDTO);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //TODO añadir mensaje de error
                        responseDTO.getErrors().add("");
                        callback.onFailure(responseDTO);
                    }
                });

    }
}
