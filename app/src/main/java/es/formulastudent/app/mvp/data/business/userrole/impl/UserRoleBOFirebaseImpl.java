package es.formulastudent.app.mvp.data.business.userrole.impl;

import com.google.firebase.firestore.FirebaseFirestore;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.userrole.UserRoleBO;

public class UserRoleBOFirebaseImpl implements UserRoleBO {

    private FirebaseFirestore firebaseFirestore;

    public UserRoleBOFirebaseImpl(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
    }



    @Override
    public void retrieveUserRoles(final BusinessCallback callback) {
/*
        final ResponseDTO responseDTO = new ResponseDTO();

        firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_ROLES)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        //success
                        if(queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                            List<Role> result = new ArrayList<>();

                            //Add results to list
                            for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                                Role role = new Role(document);
                                result.add(role);
                            }

                            responseDTO.setData(result);
                            responseDTO.setInfo(R.string.team_member_get_user_roles_info);
                            callback.onSuccess(responseDTO);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        responseDTO.setError(R.string.team_member_get_user_roles_error);
                        callback.onFailure(responseDTO);
                    }
                });
                */
    }

}
