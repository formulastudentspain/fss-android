package es.formulastudent.app.mvp.data.business.team.impl;


import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ConfigConstants;
import es.formulastudent.app.mvp.data.business.DataLoader;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.model.Car;
import es.formulastudent.app.mvp.data.model.Team;

import static es.formulastudent.app.mvp.view.screen.teams.dialog.FilterTeamsDialog.ENERGY_METER_STEP;
import static es.formulastudent.app.mvp.view.screen.teams.dialog.FilterTeamsDialog.RADIOBUTTON_NOT_PASSED_AI;
import static es.formulastudent.app.mvp.view.screen.teams.dialog.FilterTeamsDialog.RADIOBUTTON_NOT_PASSED_BT;
import static es.formulastudent.app.mvp.view.screen.teams.dialog.FilterTeamsDialog.RADIOBUTTON_NOT_PASSED_EI;
import static es.formulastudent.app.mvp.view.screen.teams.dialog.FilterTeamsDialog.RADIOBUTTON_NOT_PASSED_MI;
import static es.formulastudent.app.mvp.view.screen.teams.dialog.FilterTeamsDialog.RADIOBUTTON_NOT_PASSED_NT;
import static es.formulastudent.app.mvp.view.screen.teams.dialog.FilterTeamsDialog.RADIOBUTTON_NOT_PASSED_PS;
import static es.formulastudent.app.mvp.view.screen.teams.dialog.FilterTeamsDialog.RADIOBUTTON_NOT_PASSED_RT;
import static es.formulastudent.app.mvp.view.screen.teams.dialog.FilterTeamsDialog.RADIOBUTTON_NOT_PASSED_TTT;
import static es.formulastudent.app.mvp.view.screen.teams.dialog.FilterTeamsDialog.RADIOBUTTON_PASSED_AI;
import static es.formulastudent.app.mvp.view.screen.teams.dialog.FilterTeamsDialog.RADIOBUTTON_PASSED_BT;
import static es.formulastudent.app.mvp.view.screen.teams.dialog.FilterTeamsDialog.RADIOBUTTON_PASSED_EI;
import static es.formulastudent.app.mvp.view.screen.teams.dialog.FilterTeamsDialog.RADIOBUTTON_PASSED_MI;
import static es.formulastudent.app.mvp.view.screen.teams.dialog.FilterTeamsDialog.RADIOBUTTON_PASSED_NT;
import static es.formulastudent.app.mvp.view.screen.teams.dialog.FilterTeamsDialog.RADIOBUTTON_PASSED_PS;
import static es.formulastudent.app.mvp.view.screen.teams.dialog.FilterTeamsDialog.RADIOBUTTON_PASSED_RT;
import static es.formulastudent.app.mvp.view.screen.teams.dialog.FilterTeamsDialog.RADIOBUTTON_PASSED_TTT;
import static es.formulastudent.app.mvp.view.screen.teams.dialog.FilterTeamsDialog.TRANSPONDER_STEP;

public class TeamBOFirebaseImpl extends DataLoader implements TeamBO {

    private FirebaseFirestore firebaseFirestore;

    public TeamBOFirebaseImpl(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
    }

    @Override
    public void retrieveTeams(String carType, Map<String, String> filters, final BusinessCallback callback) {
        ResponseDTO responseDTO = new ResponseDTO();
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

                        responseDTO.setData(result);
                        responseDTO.setInfo(R.string.teams_info_retrieving_all_message);
                        callback.onSuccess(responseDTO);
                        loadingData(false);

                    } else {
                        responseDTO.setError(R.string.teams_error_retrieving_all_message);
                        callback.onFailure(responseDTO);
                        loadingData(false);
                    }
                });
    }


    @Override
    public void retrieveTeamById(String id, final BusinessCallback callback) {
        final ResponseDTO responseDTO = new ResponseDTO();

        loadingData(true);
        firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_TEAM)
                .document(id)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    Team team = new Team(documentSnapshot);
                    responseDTO.setData(team);
                    responseDTO.setInfo(R.string.teams_info_retrieving_by_id_message);
                    callback.onSuccess(responseDTO);
                    loadingData(false);
                })
                .addOnFailureListener(e -> {
                    responseDTO.setError(R.string.teams_error_retrieving_by_id_message);
                    callback.onFailure(responseDTO);
                    loadingData(false);
                });
    }


    @Override
    public void deleteAllTeams(final BusinessCallback callback) {
        final ResponseDTO responseDTO = new ResponseDTO();

        loadingData(true);
        firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_TEAM)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                        doc.getReference().delete();
                    }
                    responseDTO.setInfo(R.string.teams_info_delete_all_message);
                    callback.onSuccess(responseDTO);
                    loadingData(false);
                })
                .addOnFailureListener(e -> {
                    responseDTO.setError(R.string.teams_error_delete_all_message);
                    callback.onFailure(responseDTO);
                    loadingData(false);
                });
    }

    @Override
    public void createTeam(Team team, final BusinessCallback callback) {
        final ResponseDTO responseDTO = new ResponseDTO();
        Map<String, Object> docData = team.toDocumentData();

        loadingData(true);
        firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_TEAM)
                .document(team.getID())
                .set(docData)
                .addOnSuccessListener(aVoid -> {
                    responseDTO.setInfo(R.string.teams_info_create_message);
                    callback.onSuccess(responseDTO);
                    loadingData(false);
                })
                .addOnFailureListener(e -> {
                    responseDTO.setError(R.string.teams_error_create_message);
                    callback.onFailure(responseDTO);
                    loadingData(false);
                });
    }


    @Override
    public void updateTeam(final Team team, final BusinessCallback callback) {
        final ResponseDTO responseDTO = new ResponseDTO();
        final DocumentReference registerReference = firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_TEAM).document(team.getID());

        loadingData(true);
        registerReference.get()
                .addOnSuccessListener(documentSnapshot -> registerReference.update(team.toDocumentData())
                        .addOnSuccessListener(aVoid -> {
                            responseDTO.setInfo(R.string.teams_info_update_message);
                            callback.onSuccess(responseDTO);
                            loadingData(false);
                        })
                        .addOnFailureListener(e -> {
                            responseDTO.setError(R.string.teams_error_update_message);
                            callback.onFailure(responseDTO);
                            loadingData(false);
                        }))
                .addOnFailureListener(e -> {
                    responseDTO.setError(R.string.teams_error_update_message);
                    callback.onFailure(responseDTO);
                    loadingData(false);
                });
    }
}
