package es.formulastudent.app.mvp.data.business.team.impl;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

public class TeamBOFirebaseImpl implements TeamBO {

    private FirebaseFirestore firebaseFirestore;

    public TeamBOFirebaseImpl(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
    }

    @Override
    public void retrieveAllTeams(String carType, final BusinessCallback callback) {

        Query query = firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_TEAM);

        //Filter by car type
        if(Car.CAR_TYPE_COMBUSTION.equals(carType)
                || Car.CAR_TYPE_ELECTRIC.equals(carType)
                || Car.CAR_TYPE_AUTONOMOUS_ELECTRIC.equals(carType)
                || Car.CAR_TYPE_AUTONOMOUS_COMBUSTION.equals(carType)){

            query = query.whereEqualTo(Team.CAR_TYPE, carType);

        }

        query.orderBy(Team.CAR_NUMBER, Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

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
                    }
                });
    }

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
}
