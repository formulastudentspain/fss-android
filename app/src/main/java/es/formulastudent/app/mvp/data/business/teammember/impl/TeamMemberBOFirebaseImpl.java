package es.formulastudent.app.mvp.data.business.teammember.impl;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
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
import es.formulastudent.app.mvp.data.business.teammember.TeamMemberBO;
import es.formulastudent.app.mvp.data.model.TeamMember;

public class TeamMemberBOFirebaseImpl implements TeamMemberBO {

    private FirebaseFirestore firebaseFirestore;

    public TeamMemberBOFirebaseImpl(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
    }

    @Override
    public void retrieveTeamMemberByNFCTag(String tag, final BusinessCallback callback) {

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
                            responseDTO.setInfo(R.string.team_member_get_by_nfc_info);
                        }
                        callback.onSuccess(responseDTO);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        responseDTO.setError(R.string.team_member_get_by_nfc_error);
                        callback.onFailure(responseDTO);
                    }
                });

    }



    @Override
    public void retrieveTeamMembers(final BusinessCallback callback) {

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
                            responseDTO.setInfo(R.string.team_member_get_all_info);
                            callback.onSuccess(responseDTO);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        responseDTO.setError(R.string.team_member_get_all_error);
                        callback.onFailure(responseDTO);
                    }
                });
    }

    @Override
    public void createTeamMember(TeamMember teamMember, final BusinessCallback callback) {

        final ResponseDTO responseDTO = new ResponseDTO();
        Map<String, Object> docData = teamMember.toDocumentData();

        firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_TEAM_MEMBERS)
                .document(teamMember.getID())
                .set(docData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        responseDTO.setInfo(R.string.team_member_create_info);
                        callback.onSuccess(responseDTO);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        responseDTO.setError(R.string.team_member_create_error);
                        callback.onFailure(responseDTO);
                    }
                });
    }


    @Override
    public void deleteAllTeamMembers(final BusinessCallback callback) {
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
                        responseDTO.setInfo(R.string.team_member_delete_all_info);
                        callback.onSuccess(responseDTO);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        responseDTO.setError(R.string.team_member_delete_all_error);
                        callback.onFailure(responseDTO);
                    }
                });
    }

    @Override
    public void getRegisteredTeamMemberByTeamId(String teamID, final BusinessCallback callback){
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
                        responseDTO.setInfo(R.string.team_member_get_registered_by_team_info);
                        callback.onSuccess(responseDTO);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        responseDTO.setError(R.string.team_member_get_registered_by_team_error);
                        callback.onFailure(responseDTO);
                    }
                });
    }

    @Override
    public void updateTeamMember(final TeamMember teamMember, final BusinessCallback callback) {
        final ResponseDTO responseDTO = new ResponseDTO();
        final DocumentReference registerReference = firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_TEAM_MEMBERS).document(teamMember.getID());

        registerReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                registerReference.update(teamMember.toDocumentData())

                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                responseDTO.setInfo(R.string.team_member_update_info);
                                callback.onSuccess(responseDTO);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                responseDTO.setError(R.string.team_member_update_error);
                                callback.onFailure(responseDTO);
                            }
                        });
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        responseDTO.setError(R.string.team_member_update_error);
                        callback.onFailure(responseDTO);
                    }
                });
    }
}
