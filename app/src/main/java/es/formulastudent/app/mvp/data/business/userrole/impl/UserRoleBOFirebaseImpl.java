package es.formulastudent.app.mvp.data.business.userrole.impl;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ConfigConstants;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.userrole.UserRoleBO;
import es.formulastudent.app.mvp.data.model.UserRole;

public class UserRoleBOFirebaseImpl implements UserRoleBO {

    private FirebaseFirestore firebaseFirestore;

    public UserRoleBOFirebaseImpl(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
    }



    @Override
    public void retrieveUserRoles(final BusinessCallback callback) {

        final ResponseDTO responseDTO = new ResponseDTO();

        firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_USER_ROLE)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        //success
                        if(queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                            List<UserRole> result = new ArrayList<>();

                            //Add results to list
                            for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                                UserRole userRole = new UserRole(document);
                                result.add(userRole);
                            }

                            responseDTO.setData(result);
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
