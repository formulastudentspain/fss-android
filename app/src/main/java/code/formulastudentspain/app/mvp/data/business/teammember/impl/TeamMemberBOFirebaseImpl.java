package code.formulastudentspain.app.mvp.data.business.teammember.impl;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import code.formulastudentspain.app.R;
import code.formulastudentspain.app.mvp.data.business.ConfigConstants;
import code.formulastudentspain.app.mvp.data.business.DataLoader;
import code.formulastudentspain.app.mvp.data.business.OnFailureCallback;
import code.formulastudentspain.app.mvp.data.business.OnSuccessCallback;
import code.formulastudentspain.app.mvp.data.business.teammember.TeamMemberBO;
import code.formulastudentspain.app.mvp.data.model.Team;
import code.formulastudentspain.app.mvp.data.model.TeamMember;
import code.formulastudentspain.app.mvp.view.utils.messages.Message;

public class TeamMemberBOFirebaseImpl extends DataLoader implements TeamMemberBO {

    private FirebaseFirestore firebaseFirestore;

    public TeamMemberBOFirebaseImpl(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
    }

    @Override
    public void retrieveTeamMemberByNFCTag(String tag,
                                           @NotNull OnSuccessCallback<TeamMember> onSuccessCallback,
                                           @NotNull OnFailureCallback onFailureCallback) {

        loadingData(true);
        firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_TEAM_MEMBERS)
                .whereEqualTo(TeamMember.TAG_NFC, tag)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                        TeamMember teamMember = new TeamMember(queryDocumentSnapshots.getDocuments().get(0));
                        onSuccessCallback.onSuccess(teamMember);
                    } else {
                        onSuccessCallback.onSuccess(null);
                    }
                    loadingData(false);
                    
                })
                .addOnFailureListener(e -> {
                    onFailureCallback.onFailure(new Message(R.string.team_member_get_by_nfc_error));
                    loadingData(false);
                });
    }


    @Override
    public void retrieveTeamMembers(Team filterTeam,
                                    @NotNull OnSuccessCallback<List<TeamMember>> onSuccessCallback,
                                    @NotNull OnFailureCallback onFailureCallback) {
        Query query = firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_TEAM_MEMBERS);

        //Filter by team
        if (filterTeam != null && !"".equals(filterTeam.getID())) {
            query = query.whereEqualTo(TeamMember.TEAM_ID, filterTeam.getID());
        }

        loadingData(true);
        query.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    //success
                    if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                        List<TeamMember> result = new ArrayList<>();

                        //Add results to list
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            TeamMember teamMember = new TeamMember(document);
                            result.add(teamMember);
                        }
                        onSuccessCallback.onSuccess(result);
                        loadingData(false);
                    }
                })
                .addOnFailureListener(e -> {
                    onFailureCallback.onFailure(new Message(R.string.team_member_get_all_error));
                    loadingData(false);
                });
    }

    @Override
    public void createTeamMember(TeamMember teamMember,
                                 @NotNull OnSuccessCallback<?> onSuccessCallback,
                                 @NotNull OnFailureCallback onFailureCallback) {
        Map<String, Object> docData = teamMember.toDocumentData();
        loadingData(true);
        firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_TEAM_MEMBERS)
                .document(teamMember.getID())
                .set(docData)
                .addOnSuccessListener(aVoid -> {
                    onSuccessCallback.onSuccess(null);
                    loadingData(false);
                })
                .addOnFailureListener(e -> {
                    onFailureCallback.onFailure(new Message(R.string.team_member_create_error));
                    loadingData(false);
                });
    }


    @Override
    public void deleteAllTeamMembers(@NotNull OnSuccessCallback<?> onSuccessCallback,
            @NotNull OnFailureCallback onFailureCallback) {
        loadingData(true);
        firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_TEAM_MEMBERS)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                        doc.getReference().delete();
                    }
                    onSuccessCallback.onSuccess(null);
                    loadingData(false);
                    
                })
                .addOnFailureListener(e -> {
                    onFailureCallback.onFailure(new Message(R.string.team_member_delete_all_error));
                    loadingData(false);
                });
    }

    @Override
    public void getRegisteredTeamMemberByTeamId(String teamID,
                                                @NotNull OnSuccessCallback<List<TeamMember>> onSuccessCallback,
                                                @NotNull OnFailureCallback onFailureCallback) {
        loadingData(true);
        firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_TEAM_MEMBERS)
                .whereEqualTo(TeamMember.TEAM_ID, teamID)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<TeamMember> teamMemberList = new ArrayList<>();

                    for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                        TeamMember teamMember = new TeamMember(doc);

                        if (teamMember.getNFCTag() != null) {
                            teamMemberList.add(teamMember);
                        }
                    }
                    onSuccessCallback.onSuccess(teamMemberList);
                    loadingData(false);
                    
                })
                .addOnFailureListener(e -> {
                    onFailureCallback.onFailure(new Message(R.string.team_member_get_registered_by_team_error));
                    loadingData(false);
                });
    }

    @Override
    public void updateTeamMember(final TeamMember teamMember,
                                 @NotNull OnSuccessCallback<?> onSuccessCallback,
                                 @NotNull OnFailureCallback onFailureCallback) {
        final DocumentReference registerReference = firebaseFirestore
                .collection(ConfigConstants.FIREBASE_TABLE_TEAM_MEMBERS)
                .document(teamMember.getID());

        loadingData(true);
        registerReference.get()
                .addOnSuccessListener(documentSnapshot -> registerReference.update(teamMember.toDocumentData())
                        .addOnSuccessListener(aVoid -> {
                            onSuccessCallback.onSuccess(null);
                            loadingData(false);
                        })
                        .addOnFailureListener(e -> {
                            onFailureCallback.onFailure(new Message(R.string.team_member_update_error));
                            loadingData(false);
                        }))
                .addOnFailureListener(e -> {
                    onFailureCallback.onFailure(new Message(R.string.team_member_update_error));
                    loadingData(false);
                });
    }
}
