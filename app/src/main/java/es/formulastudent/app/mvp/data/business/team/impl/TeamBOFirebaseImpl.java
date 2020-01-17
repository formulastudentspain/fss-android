package es.formulastudent.app.mvp.data.business.team.impl;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ConfigConstants;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.model.Car;
import es.formulastudent.app.mvp.data.model.Team;

import static es.formulastudent.app.mvp.view.activity.teams.dialog.FilterTeamsDialog.RADIOBUTTON_NOT_PASSED_AI;
import static es.formulastudent.app.mvp.view.activity.teams.dialog.FilterTeamsDialog.RADIOBUTTON_NOT_PASSED_BT;
import static es.formulastudent.app.mvp.view.activity.teams.dialog.FilterTeamsDialog.RADIOBUTTON_NOT_PASSED_EI;
import static es.formulastudent.app.mvp.view.activity.teams.dialog.FilterTeamsDialog.RADIOBUTTON_NOT_PASSED_MI;
import static es.formulastudent.app.mvp.view.activity.teams.dialog.FilterTeamsDialog.RADIOBUTTON_NOT_PASSED_NT;
import static es.formulastudent.app.mvp.view.activity.teams.dialog.FilterTeamsDialog.RADIOBUTTON_NOT_PASSED_PS;
import static es.formulastudent.app.mvp.view.activity.teams.dialog.FilterTeamsDialog.RADIOBUTTON_NOT_PASSED_RT;
import static es.formulastudent.app.mvp.view.activity.teams.dialog.FilterTeamsDialog.RADIOBUTTON_NOT_PASSED_TTT;
import static es.formulastudent.app.mvp.view.activity.teams.dialog.FilterTeamsDialog.RADIOBUTTON_PASSED_AI;
import static es.formulastudent.app.mvp.view.activity.teams.dialog.FilterTeamsDialog.RADIOBUTTON_PASSED_BT;
import static es.formulastudent.app.mvp.view.activity.teams.dialog.FilterTeamsDialog.RADIOBUTTON_PASSED_EI;
import static es.formulastudent.app.mvp.view.activity.teams.dialog.FilterTeamsDialog.RADIOBUTTON_PASSED_MI;
import static es.formulastudent.app.mvp.view.activity.teams.dialog.FilterTeamsDialog.RADIOBUTTON_PASSED_NT;
import static es.formulastudent.app.mvp.view.activity.teams.dialog.FilterTeamsDialog.RADIOBUTTON_PASSED_PS;
import static es.formulastudent.app.mvp.view.activity.teams.dialog.FilterTeamsDialog.RADIOBUTTON_PASSED_RT;
import static es.formulastudent.app.mvp.view.activity.teams.dialog.FilterTeamsDialog.RADIOBUTTON_PASSED_TTT;
import static es.formulastudent.app.mvp.view.activity.teams.dialog.FilterTeamsDialog.ENERGY_METER_STEP;
import static es.formulastudent.app.mvp.view.activity.teams.dialog.FilterTeamsDialog.TRANSPONDER_STEP;

public class TeamBOFirebaseImpl implements TeamBO {

    private FirebaseFirestore firebaseFirestore;

    public TeamBOFirebaseImpl(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
    }

    @Override
    public void retrieveTeams(String carType, Map<String, String> filters, final BusinessCallback callback) {

        Query query = firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_TEAM);

        //Filter by car type
        if(Car.CAR_TYPE_COMBUSTION.equals(carType)
                || Car.CAR_TYPE_ELECTRIC.equals(carType)
                || Car.CAR_TYPE_AUTONOMOUS_ELECTRIC.equals(carType)
                || Car.CAR_TYPE_AUTONOMOUS_COMBUSTION.equals(carType)){

            query = query.whereEqualTo(Team.CAR_TYPE, carType);

        }


        //Filter by Scrutineering and Fees
        if(filters != null) {

            boolean feeFilters = false;
            if((filters.containsKey(TRANSPONDER_STEP) && Integer.parseInt(filters.get(TRANSPONDER_STEP))!=0)
                    || (filters.containsKey(ENERGY_METER_STEP) && Integer.parseInt(filters.get(ENERGY_METER_STEP))!=0)){
                feeFilters=true;
            }

            for (String filterKey : filters.keySet()) {

                if(!feeFilters) {


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
                if(filterKey.equals(TRANSPONDER_STEP)){
                    int value = Integer.parseInt(filters.get(filterKey));
                    switch (value){
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

                }if(filterKey.equals(ENERGY_METER_STEP)){
                    int value = Integer.parseInt(filters.get(filterKey));
                    switch (value){
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



        query.orderBy(Team.CAR_NUMBER, Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(task -> {

                    //Response object
                    ResponseDTO responseDTO = new ResponseDTO();

                    if (task.isSuccessful()) {

                        List<Team> result = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Team team = new Team(document);
                            result.add(team);
                        }

                        responseDTO.setData(result);
                        responseDTO.setInfo(R.string.teams_info_retrieving_all_message);
                        callback.onSuccess(responseDTO);

                    } else {
                        responseDTO.setInfo(R.string.teams_error_retrieving_all_message);
                        callback.onFailure(responseDTO);
                    }
                });
    }

/*
    private List<Team> filterTeams(List<Team> teams, Map<String, String> filters){

        List<Team> filteredTeams = new ArrayList<>();

        if(filters != null) {

            if(filters.keySet().isEmpty()){
                filteredTeams.addAll(teams);
                return filteredTeams;
            }

            
            //for (String filterKey : filters.keySet()) {
                if(filters.keySet().contains(RADIOBUTTON_PASSED_PS)){
                    filteredTeams.addAll(teams.stream()
                            .filter(Team::getScrutineeringPS)
                            .collect(Collectors.toList()));

                }else{
                    filteredTeams.addAll(teams.stream()
                            .filter(c -> !c.getScrutineeringPS())
                            .collect(Collectors.toList()));
                }
                
                if(filters.keySet().contains(RADIOBUTTON_PASSED_AI)){
                    filteredTeams.addAll(teams.stream()
                            .filter(Team::getScrutineeringAI)
                            .collect(Collectors.toList()));

                }if(filters.keySet().contains(RADIOBUTTON_PASSED_EI)){
                    filteredTeams.addAll(teams.stream()
                            .filter(Team::getScrutineeringEI)
                            .collect(Collectors.toList()));

                }if(filters.keySet().contains(RADIOBUTTON_PASSED_MI)){
                    filteredTeams.addAll(teams.stream()
                            .filter(Team::getScrutineeringMI)
                            .collect(Collectors.toList()));

                }if(filters.keySet().contains(RADIOBUTTON_PASSED_TTT)){
                    filteredTeams.addAll(teams.stream()
                            .filter(Team::getScrutineeringTTT)
                            .collect(Collectors.toList()));

                }if(filters.keySet().contains(RADIOBUTTON_PASSED_RT)){
                    filteredTeams.addAll(teams.stream()
                            .filter(Team::getScrutineeringRT)
                            .collect(Collectors.toList()));

                }if(filters.keySet().contains(RADIOBUTTON_PASSED_NT)){
                    filteredTeams.addAll(teams.stream()
                            .filter(Team::getScrutineeringNT)
                            .collect(Collectors.toList()));

                }if(filters.keySet().contains(RADIOBUTTON_PASSED_BT)){
                    filteredTeams.addAll(teams.stream()
                            .filter(Team::getScrutineeringBT)
                            .collect(Collectors.toList()));


                }if(filters.keySet().contains(RADIOBUTTON_NOT_PASSED_PS)){
                    filteredTeams.addAll(teams.stream()
                            .filter(c -> !c.getScrutineeringBT())
                            .collect(Collectors.toList()));

                }if(filters.keySet().contains(RADIOBUTTON_NOT_PASSED_AI)){
                    filteredTeams.addAll(teams.stream()
                            .filter(c -> !c.getScrutineeringAI())
                            .collect(Collectors.toList()));

                }if(filters.keySet().contains(RADIOBUTTON_NOT_PASSED_EI)){
                    filteredTeams.addAll(teams.stream()
                            .filter(c -> !c.getScrutineeringEI())
                            .collect(Collectors.toList()));

                }if(filters.keySet().contains(RADIOBUTTON_NOT_PASSED_MI)){
                    filteredTeams.addAll(teams.stream()
                            .filter(c -> !c.getScrutineeringMI())
                            .collect(Collectors.toList()));

                }if(filters.keySet().contains(RADIOBUTTON_NOT_PASSED_TTT)){
                    filteredTeams.addAll(teams.stream()
                            .filter(c -> !c.getScrutineeringTTT())
                            .collect(Collectors.toList()));

                }if(filters.keySet().contains(RADIOBUTTON_NOT_PASSED_RT)){
                    filteredTeams.addAll(teams.stream()
                            .filter(c -> !c.getScrutineeringRT())
                            .collect(Collectors.toList()));

                }if(filters.keySet().contains(RADIOBUTTON_NOT_PASSED_NT)){
                    filteredTeams.addAll(teams.stream()
                            .filter(c -> !c.getScrutineeringNT())
                            .collect(Collectors.toList()));

                }if(filters.keySet().contains(RADIOBUTTON_NOT_PASSED_BT)){
                    filteredTeams.addAll(teams.stream()
                            .filter(c -> !c.getScrutineeringBT())
                            .collect(Collectors.toList()));

                }if(filters.keySet().contains(TRANSPONDER_STEP)){
                    int value = Integer.parseInt(filters.get(filterKey));
                    switch (value){
                        case 1:
                            filteredTeams.addAll(teams.stream()
                                    .filter(Team::getTransponderFeeGiven)
                                    .collect(Collectors.toList()));
                            break;
                        case 2:
                            filteredTeams.addAll(teams.stream()
                                    .filter(Team::getTransponderItemGiven)
                                    .collect(Collectors.toList()));
                            break;
                        case 3:
                            filteredTeams.addAll(teams.stream()
                                    .filter(Team::getTransponderItemReturned)
                                    .collect(Collectors.toList()));
                            break;
                        case 4:
                            filteredTeams.addAll(teams.stream()
                                    .filter(Team::getTransponderFeeReturned)
                                    .collect(Collectors.toList()));
                            break;
                    }

                }if(filters.keySet().contains(ENERGY_METER_STEP)){
                    int value = Integer.parseInt(filters.get(filterKey));
                    switch (value){
                        case 1:
                            filteredTeams.addAll(teams.stream()
                                    .filter(Team::getEnergyMeterFeeGiven)
                                    .collect(Collectors.toList()));
                            break;
                        case 2:
                            filteredTeams.addAll(teams.stream()
                                    .filter(Team::getEnergyMeterItemGiven)
                                    .collect(Collectors.toList()));
                            break;
                        case 3:
                            filteredTeams.addAll(teams.stream()
                                    .filter(Team::getEnergyMeterItemReturned)
                                    .collect(Collectors.toList()));
                            break;
                        case 4:
                            filteredTeams.addAll(teams.stream()
                                    .filter(Team::getEnergyMeterFeeReturned)
                                    .collect(Collectors.toList()));
                            break;
                    }
                }
    //        }
        }else{
            filteredTeams.addAll(teams);
        }
        return filteredTeams;
    }

*/


    @Override
    public void retrieveTeamById(String id, final BusinessCallback callback) {

        //Response object
        final ResponseDTO responseDTO = new ResponseDTO();

        firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_TEAM)
                .document(id)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Team team = new Team(documentSnapshot);
                        responseDTO.setData(team);
                        responseDTO.setInfo(R.string.teams_info_retrieving_by_id_message);
                        callback.onSuccess(responseDTO);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        responseDTO.setError(R.string.teams_error_retrieving_by_id_message);
                        callback.onFailure(responseDTO);
                    }
                });
    }


    @Override
    public void deleteAllTeams(final BusinessCallback callback){

        final ResponseDTO responseDTO = new ResponseDTO();

        firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_TEAM)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                            doc.getReference().delete();
                        }
                        responseDTO.setInfo(R.string.teams_info_delete_all_message);
                        callback.onSuccess(responseDTO);
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        responseDTO.setError(R.string.teams_error_delete_all_message);
                        callback.onFailure(responseDTO);
                    }
                });

    }

    @Override
    public void createTeam(Team team, final BusinessCallback callback) {
        final ResponseDTO responseDTO = new ResponseDTO();
        Map<String, Object> docData = team.toDocumentData();

        firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_TEAM)
                .document(team.getID())
                .set(docData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        responseDTO.setInfo(R.string.teams_info_create_message);
                        callback.onSuccess(responseDTO);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        responseDTO.setError(R.string.teams_error_create_message);
                        callback.onFailure(responseDTO);
                    }
                });
    }



    @Override
    public void updateTeam(final Team team, final BusinessCallback callback) {

        final ResponseDTO responseDTO = new ResponseDTO();
        final DocumentReference registerReference = firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_TEAM).document(team.getID());

        registerReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                registerReference.update(team.toDocumentData())

                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                responseDTO.setInfo(R.string.teams_info_update_message);
                                callback.onSuccess(responseDTO);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                responseDTO.setError(R.string.teams_error_update_message);
                                callback.onFailure(responseDTO);
                            }
                        });
            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        responseDTO.setError(R.string.teams_error_update_message);
                        callback.onFailure(responseDTO);
                    }
                });
    }
}
