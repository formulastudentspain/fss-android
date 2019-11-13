package es.formulastudent.app.mvp.data.business.user.impl;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ConfigConstants;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.user.UserBO;
import es.formulastudent.app.mvp.data.model.TeamMember;
import es.formulastudent.app.mvp.data.model.User;

public class UserBOFirebaseImpl implements UserBO {

    private FirebaseFirestore firebaseFirestore;

    public UserBOFirebaseImpl(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
    }

    @Override
    public void retrieveUserByNFCTag(String tag, final BusinessCallback callback) {

        final ResponseDTO responseDTO = new ResponseDTO();

        firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_TEAM_MEMBERS)
                .whereEqualTo(TeamMember.TAG_NFC, tag)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        //success
                        if(queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                            TeamMember teamMember = new TeamMember(queryDocumentSnapshots.getDocuments().get(0));
                            responseDTO.setData(teamMember);
                            responseDTO.setInfo(R.string.users_get_by_nfc_info);
                        }
                        callback.onSuccess(responseDTO);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        responseDTO.setError(R.string.users_get_by_nfc_error);
                        callback.onFailure(responseDTO);
                    }
                });

    }



    @Override
    public void retrieveUsers(final BusinessCallback callback) {

        final ResponseDTO responseDTO = new ResponseDTO();

        firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_TEAM_MEMBERS)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        //success
                        if(queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                            List<TeamMember> result = new ArrayList<>();

                            //Add results to list
                            for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                                TeamMember teamMember = new TeamMember(document);
                                result.add(teamMember);
                            }

                            responseDTO.setData(result);
                            responseDTO.setInfo(R.string.users_get_all_info);
                            callback.onSuccess(responseDTO);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        responseDTO.setError(R.string.users_get_all_error);
                        callback.onFailure(responseDTO);
                    }
                });
    }

    @Override
    public void createUser(TeamMember teamMember, final BusinessCallback callback) {

        final ResponseDTO responseDTO = new ResponseDTO();
        Map<String, Object> docData = teamMember.toDocumentData();

        firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_TEAM_MEMBERS)
                .document(teamMember.getID())
                .set(docData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        responseDTO.setInfo(R.string.users_create_info);
                        callback.onSuccess(responseDTO);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        responseDTO.setError(R.string.users_create_error);
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
                        responseDTO.setInfo(R.string.users_get_by_mail_info);
                        callback.onSuccess(responseDTO);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        responseDTO.setError(R.string.users_get_by_mail_error);
                        callback.onFailure(responseDTO);
                    }
                });
    }

    @Override
    public void deleteAllDrivers(final BusinessCallback callback) {
        final ResponseDTO responseDTO = new ResponseDTO();

        firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_TEAM_MEMBERS)
                .whereEqualTo(TeamMember.ROLE, "DRIVER")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for(DocumentSnapshot doc: queryDocumentSnapshots.getDocuments()){
                            doc.getReference().delete();
                        }
                        responseDTO.setInfo(R.string.users_delete_all_info);
                        callback.onSuccess(responseDTO);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        responseDTO.setError(R.string.users_delete_all_error);
                        callback.onFailure(responseDTO);
                    }
                });
    }

    @Override
    public void getRegisteredUsersByTeamId(String teamID, final BusinessCallback callback){
        final ResponseDTO responseDTO = new ResponseDTO();

        firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_TEAM_MEMBERS)
                .whereEqualTo(TeamMember.TEAM_ID, teamID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<TeamMember> teamMemberList = new ArrayList<>();

                        for(DocumentSnapshot doc: queryDocumentSnapshots.getDocuments()){
                            TeamMember teamMember = new TeamMember(doc);

                            if(teamMember.getNFCTag()!=null){
                                teamMemberList.add(teamMember);
                            }
                        }

                        responseDTO.setData(teamMemberList);
                        responseDTO.setInfo(R.string.users_get_registered_by_team_info);
                        callback.onSuccess(responseDTO);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        responseDTO.setError(R.string.users_get_registered_by_team_error);
                        callback.onFailure(responseDTO);
                    }
                });
    }
}
