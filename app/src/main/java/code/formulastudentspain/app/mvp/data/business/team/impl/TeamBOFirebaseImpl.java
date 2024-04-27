package code.formulastudentspain.app.mvp.data.business.team.impl;


import androidx.annotation.NonNull;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import code.formulastudentspain.app.R;
import code.formulastudentspain.app.mvp.data.business.ConfigConstants;
import code.formulastudentspain.app.mvp.data.business.DataLoader;
import code.formulastudentspain.app.mvp.data.business.OnFailureCallback;
import code.formulastudentspain.app.mvp.data.business.OnSuccessCallback;
import code.formulastudentspain.app.mvp.data.business.ResponseDTO;
import code.formulastudentspain.app.mvp.data.business.team.TeamBO;
import code.formulastudentspain.app.mvp.data.model.Car;
import code.formulastudentspain.app.mvp.data.model.Team;
import code.formulastudentspain.app.mvp.view.utils.messages.Message;

import static code.formulastudentspain.app.mvp.view.screen.teams.dialog.FilterTeamsDialog.ENERGY_METER_STEP;
import static code.formulastudentspain.app.mvp.view.screen.teams.dialog.FilterTeamsDialog.RADIOBUTTON_NOT_PASSED_AI;
import static code.formulastudentspain.app.mvp.view.screen.teams.dialog.FilterTeamsDialog.RADIOBUTTON_NOT_PASSED_BT;
import static code.formulastudentspain.app.mvp.view.screen.teams.dialog.FilterTeamsDialog.RADIOBUTTON_NOT_PASSED_EI;
import static code.formulastudentspain.app.mvp.view.screen.teams.dialog.FilterTeamsDialog.RADIOBUTTON_NOT_PASSED_MI;
import static code.formulastudentspain.app.mvp.view.screen.teams.dialog.FilterTeamsDialog.RADIOBUTTON_NOT_PASSED_NT;
import static code.formulastudentspain.app.mvp.view.screen.teams.dialog.FilterTeamsDialog.RADIOBUTTON_NOT_PASSED_PS;
import static code.formulastudentspain.app.mvp.view.screen.teams.dialog.FilterTeamsDialog.RADIOBUTTON_NOT_PASSED_RT;
import static code.formulastudentspain.app.mvp.view.screen.teams.dialog.FilterTeamsDialog.RADIOBUTTON_NOT_PASSED_TTT;
import static code.formulastudentspain.app.mvp.view.screen.teams.dialog.FilterTeamsDialog.RADIOBUTTON_PASSED_AI;
import static code.formulastudentspain.app.mvp.view.screen.teams.dialog.FilterTeamsDialog.RADIOBUTTON_PASSED_BT;
import static code.formulastudentspain.app.mvp.view.screen.teams.dialog.FilterTeamsDialog.RADIOBUTTON_PASSED_EI;
import static code.formulastudentspain.app.mvp.view.screen.teams.dialog.FilterTeamsDialog.RADIOBUTTON_PASSED_MI;
import static code.formulastudentspain.app.mvp.view.screen.teams.dialog.FilterTeamsDialog.RADIOBUTTON_PASSED_NT;
import static code.formulastudentspain.app.mvp.view.screen.teams.dialog.FilterTeamsDialog.RADIOBUTTON_PASSED_PS;
import static code.formulastudentspain.app.mvp.view.screen.teams.dialog.FilterTeamsDialog.RADIOBUTTON_PASSED_RT;
import static code.formulastudentspain.app.mvp.view.screen.teams.dialog.FilterTeamsDialog.RADIOBUTTON_PASSED_TTT;
import static code.formulastudentspain.app.mvp.view.screen.teams.dialog.FilterTeamsDialog.TRANSPONDER_STEP;

public class TeamBOFirebaseImpl extends DataLoader implements TeamBO {

    private FirebaseFirestore firebaseFirestore;

    public TeamBOFirebaseImpl(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
    }

    @Override
    public void retrieveTeams(String carType, Map<String, String> filters,
                              @NonNull OnSuccessCallback<List<Team>> onSuccessCallback,
                              @NonNull OnFailureCallback onFailureCallback) {

        Query query = firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_TEAM);

        //Filter by car type
        if (Car.CAR_TYPE_COMBUSTION.equals(carType)
                || Car.CAR_TYPE_ELECTRIC.equals(carType)
                || Car.CAR_TYPE_AUTONOMOUS_ELECTRIC.equals(carType)
                || Car.CAR_TYPE_AUTONOMOUS_COMBUSTION.equals(carType)) {

            query = query.whereEqualTo(Team.CAR_TYPE, carType);
        }

        //Filter by Scrutineering and Fees
        if (filters != null) {

            boolean feeFilters = false;
            if ((filters.containsKey(TRANSPONDER_STEP) && Integer.parseInt(filters.get(TRANSPONDER_STEP)) != 0)
                    || (filters.containsKey(ENERGY_METER_STEP) && Integer.parseInt(filters.get(ENERGY_METER_STEP)) != 0)) {
                feeFilters = true;
            }

            for (String filterKey : filters.keySet()) {

                if (!feeFilters) {
                    if (filterKey.equals(RADIOBUTTON_PASSED_PS)) {
                        query = query.whereEqualTo(Team.SCRUTINEERING_PS, true);
                    }
                    if (filterKey.equals(RADIOBUTTON_PASSED_AI)) {
                        query = query.whereEqualTo(Team.SCRUTINEERING_AI, true);
                    }
                    if (filterKey.equals(RADIOBUTTON_PASSED_EI)) {
                        query = query.whereEqualTo(Team.SCRUTINEERING_EI, true);
                    }
                    if (filterKey.equals(RADIOBUTTON_PASSED_MI)) {
                        query = query.whereEqualTo(Team.SCRUTINEERING_MI, true);
                    }
                    if (filterKey.equals(RADIOBUTTON_PASSED_TTT)) {
                        query = query.whereEqualTo(Team.SCRUTINEERING_TTT, true);
                    }
                    if (filterKey.equals(RADIOBUTTON_PASSED_RT)) {
                        query = query.whereEqualTo(Team.SCRUTINEERING_RT, true);
                    }
                    if (filterKey.equals(RADIOBUTTON_PASSED_NT)) {
                        query = query.whereEqualTo(Team.SCRUTINEERING_NT, true);
                    }
                    if (filterKey.equals(RADIOBUTTON_PASSED_BT)) {
                        query = query.whereEqualTo(Team.SCRUTINEERING_BT, true);
                    }
                    if (filterKey.equals(RADIOBUTTON_NOT_PASSED_PS)) {
                        query = query.whereEqualTo(Team.SCRUTINEERING_PS, false);
                    }
                    if (filterKey.equals(RADIOBUTTON_NOT_PASSED_AI)) {
                        query = query.whereEqualTo(Team.SCRUTINEERING_AI, false);
                    }
                    if (filterKey.equals(RADIOBUTTON_NOT_PASSED_EI)) {
                        query = query.whereEqualTo(Team.SCRUTINEERING_EI, false);
                    }
                    if (filterKey.equals(RADIOBUTTON_NOT_PASSED_MI)) {
                        query = query.whereEqualTo(Team.SCRUTINEERING_MI, false);
                    }
                    if (filterKey.equals(RADIOBUTTON_NOT_PASSED_TTT)) {
                        query = query.whereEqualTo(Team.SCRUTINEERING_TTT, false);
                    }
                    if (filterKey.equals(RADIOBUTTON_NOT_PASSED_RT)) {
                        query = query.whereEqualTo(Team.SCRUTINEERING_RT, false);
                    }
                    if (filterKey.equals(RADIOBUTTON_NOT_PASSED_NT)) {
                        query = query.whereEqualTo(Team.SCRUTINEERING_NT, false);
                    }
                    if (filterKey.equals(RADIOBUTTON_NOT_PASSED_BT)) {
                        query = query.whereEqualTo(Team.SCRUTINEERING_BT, false);
                    }
                }
                if (filterKey.equals(TRANSPONDER_STEP)) {
                    int value = Integer.parseInt(filters.get(filterKey));
                    switch (value) {
                        case 1:
                            query = query.whereEqualTo(Team.TRANSPONDER_FEE_GIVEN, true);
                            break;
                        case 2:
                            query = query.whereEqualTo(Team.TRANSPONDER_ITEM_GIVEN, true);
                            break;
                        case 3:
                            query = query.whereEqualTo(Team.TRANSPONDER_ITEM_RETURNED, true);
                            break;
                        case 4:
                            query = query.whereEqualTo(Team.TRANSPONDER_FEE_RETURNED, true);
                            break;
                    }

                }
                if (filterKey.equals(ENERGY_METER_STEP)) {
                    int value = Integer.parseInt(filters.get(filterKey));
                    switch (value) {
                        case 1:
                            query = query.whereEqualTo(Team.ENERGY_METER_FEE_GIVEN, true);
                            break;
                        case 2:
                            query = query.whereEqualTo(Team.ENERGY_METER_ITEM_GIVEN, true);
                            break;
                        case 3:
                            query = query.whereEqualTo(Team.ENERGY_METER_ITEM_RETURNED, true);
                            break;
                        case 4:
                            query = query.whereEqualTo(Team.ENERGY_METER_FEE_RETURNED, true);
                            break;
                    }
                }
            }
        }

        loadingData(true);
        query.orderBy(Team.CAR_NUMBER, Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Team> result = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Team team = new Team(document);
                            result.add(team);
                        }
                        onSuccessCallback.onSuccess(result);
                        loadingData(false);
                    } else {
                        onFailureCallback.onFailure(new Message(R.string.teams_error_retrieving_all_message));
                        loadingData(false);
                    }
                });
    }


    @Override
    public void retrieveTeamById(String id, @NonNull OnSuccessCallback<Team> onSuccessCallback,
                                 @NonNull OnFailureCallback onFailureCallback) {
        loadingData(true);
        firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_TEAM)
                .document(id)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    onSuccessCallback.onSuccess(new Team(documentSnapshot));
                    loadingData(false);
                })
                .addOnFailureListener(e -> {
                    onFailureCallback.onFailure(new Message(R.string.teams_error_retrieving_by_id_message));
                    loadingData(false);
                });
    }


    @Override
    public void deleteAllTeams(@NonNull OnSuccessCallback<?> onSuccessCallback,
                               @NonNull OnFailureCallback onFailureCallback) {
        final ResponseDTO responseDTO = new ResponseDTO();

        loadingData(true);
        firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_TEAM)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                        doc.getReference().delete();
                    }
                    onSuccessCallback.onSuccess(null);
                    loadingData(false);
                })
                .addOnFailureListener(e -> {
                    onFailureCallback.onFailure(new Message(R.string.teams_error_delete_all_message));
                    loadingData(false);
                });
    }

    @Override
    public void createTeam(Team team, @NonNull OnSuccessCallback<?> onSuccessCallback,
                           @NonNull OnFailureCallback onFailureCallback) {

        Map<String, Object> docData = team.toDocumentData();
        loadingData(true);
        firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_TEAM)
                .document(team.getID())
                .set(docData)
                .addOnSuccessListener(aVoid -> {
                    onSuccessCallback.onSuccess(null);
                    loadingData(false);
                })
                .addOnFailureListener(e -> {
                    onFailureCallback.onFailure(new Message(R.string.teams_error_create_message));
                    loadingData(false);
                });
    }


    @Override
    public void updateTeam(final Team team, @NonNull OnSuccessCallback<?> onSuccessCallback,
                           @NonNull OnFailureCallback onFailureCallback) {
        final DocumentReference registerReference = firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_TEAM).document(team.getID());

        loadingData(true);
        registerReference.get()
                .addOnSuccessListener(documentSnapshot -> registerReference.update(team.toDocumentData())
                        .addOnSuccessListener(aVoid -> {
                            onSuccessCallback.onSuccess(null);
                            loadingData(false);
                        })
                        .addOnFailureListener(e -> {
                            onFailureCallback.onFailure(new Message(R.string.teams_error_update_message));
                            loadingData(false);
                        }))
                .addOnFailureListener(e -> {
                    onFailureCallback.onFailure(new Message(R.string.teams_error_update_message));
                    loadingData(false);
                });
    }
}
