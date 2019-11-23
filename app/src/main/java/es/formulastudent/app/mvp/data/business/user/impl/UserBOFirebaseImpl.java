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
import es.formulastudent.app.mvp.data.business.auth.AuthBO;
import es.formulastudent.app.mvp.data.business.user.UserBO;
import es.formulastudent.app.mvp.data.model.User;

public class UserBOFirebaseImpl implements UserBO {


    private FirebaseFirestore firebaseFirestore;
    private AuthBO authBO;

    public UserBOFirebaseImpl(FirebaseFirestore firebaseFirestore, AuthBO authBO) {
        this.firebaseFirestore = firebaseFirestore;
        this.authBO = authBO;
    }

    @Override
    public void retrieveUsers(final BusinessCallback callback) {

        final ResponseDTO responseDTO = new ResponseDTO();

        firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_USER)
                .orderBy(User.NAME, Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        //success
                        if(queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
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
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        responseDTO.setError(R.string.user_get_all_error);
                        callback.onFailure(responseDTO);
                    }
                });
    }

    @Override
    public void createUser(final User user, final BusinessCallback callback) {

        final Map<String, Object> docData = user.toDocumentData();

        authBO.createUser(user.getMail(), new BusinessCallback() {

            @Override
            public void onSuccess(final ResponseDTO responseDTO) {

                firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_USER)
                        .document(user.getID())
                        .set(docData)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                responseDTO.setInfo(R.string.user_create_info);
                                callback.onSuccess(responseDTO);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                responseDTO.setError(R.string.user_create_error);
                                callback.onFailure(responseDTO);
                            }
                        });
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                responseDTO.setError(R.string.user_create_error);
                callback.onFailure(responseDTO);
            }
        });


    }

    @Override
    public void retrieveUserByMail(String mail, final BusinessCallback callback) {
        final ResponseDTO responseDTO = new ResponseDTO();

        firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_USER)
                .whereEqualTo(User.MAIL, mail)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        //success
                        if(queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                            User user = new User(queryDocumentSnapshots.getDocuments().get(0));
                            responseDTO.setData(user);
                        }
                        responseDTO.setInfo(R.string.user_get_by_mail_info);
                        callback.onSuccess(responseDTO);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        responseDTO.setError(R.string.user_get_by_mail_error);
                        callback.onFailure(responseDTO);
                    }
                });
    }

    @Override
    public void editUser(final User user, final BusinessCallback callback) {

        final ResponseDTO responseDTO = new ResponseDTO();
        final DocumentReference registerReference = firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_USER).document(user.getID());

        registerReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        registerReference.update(user.toDocumentData())

                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        responseDTO.setInfo(R.string.user_update_info);
                                        callback.onSuccess(responseDTO);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        responseDTO.setError(R.string.user_update_error);
                                        callback.onFailure(responseDTO);
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        responseDTO.setError(R.string.user_update_error);
                        callback.onFailure(responseDTO);
                    }
                });
    }

}
