package es.formulastudent.app.mvp.data.business.user.impl;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ConfigConstants;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.user.UserBO;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.data.model.UserRole;

public class UserBOFirebaseImpl implements UserBO {

    private FirebaseFirestore firebaseFirestore;

    public UserBOFirebaseImpl(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
    }

    @Override
    public void retrieveUsers(UserRole selectedRole, final BusinessCallback callback) {

        final ResponseDTO responseDTO = new ResponseDTO();

        Query query = firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_USER);

        if (selectedRole != null) {
            query = query.whereEqualTo(User.ROLE, selectedRole.getName());
        }

        query.orderBy(User.NAME, Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    //success
                    if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                        List<User> result = new ArrayList<>();

                        //Add results to list
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            User user = new User(document);
                            result.add(user);
                        }
                        responseDTO.setData(result);
                        responseDTO.setInfo(R.string.user_get_all_info);
                        callback.onSuccess(responseDTO);
                    }
                })
                .addOnFailureListener(e -> {
                    responseDTO.setError(R.string.user_get_all_error);
                    callback.onFailure(responseDTO);
                });
    }

    @Override
    public void createUser(final User user, final BusinessCallback callback) {
        final Map<String, Object> docData = user.toDocumentData();

        ResponseDTO responseDTO = new ResponseDTO();
        firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_USER)
                .document(user.getID())
                .set(docData)
                .addOnSuccessListener(aVoid -> {
                    responseDTO.setInfo(R.string.user_create_info);
                    callback.onSuccess(responseDTO);
                })
                .addOnFailureListener(e -> {
                    responseDTO.setError(R.string.user_create_error);
                    callback.onFailure(responseDTO);
                });
    }

    @Override
    public void retrieveUserByMail(String mail, final BusinessCallback callback) {
        final ResponseDTO responseDTO = new ResponseDTO();

        firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_USER)
                .whereEqualTo(User.MAIL, mail)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    //success
                    if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                        User user = new User(queryDocumentSnapshots.getDocuments().get(0));
                        responseDTO.setData(user);
                    }
                    responseDTO.setInfo(R.string.user_get_by_mail_info);
                    callback.onSuccess(responseDTO);
                })
                .addOnFailureListener(e -> {
                    responseDTO.setError(R.string.user_get_by_mail_error);
                    callback.onFailure(responseDTO);
                });
    }

    @Override
    public void editUser(final User user, final BusinessCallback callback) {

        final ResponseDTO responseDTO = new ResponseDTO();
        final DocumentReference registerReference = firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_USER).document(user.getID());

        registerReference.get()
                .addOnSuccessListener(documentSnapshot -> registerReference.update(user.toDocumentData())
                        .addOnSuccessListener(aVoid -> {
                            responseDTO.setInfo(R.string.user_update_info);
                            callback.onSuccess(responseDTO);
                        })
                        .addOnFailureListener(e -> {
                            responseDTO.setError(R.string.user_update_error);
                            callback.onFailure(responseDTO);
                        }))
                .addOnFailureListener(e -> {
                    responseDTO.setError(R.string.user_update_error);
                    callback.onFailure(responseDTO);
                });
    }

}
