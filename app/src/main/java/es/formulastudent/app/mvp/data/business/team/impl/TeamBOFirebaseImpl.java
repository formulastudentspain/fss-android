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

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ConfigConstants;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.model.Team;

public class TeamBOFirebaseImpl implements TeamBO {

    private FirebaseFirestore firebaseFirestore;

    public TeamBOFirebaseImpl(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
    }

    @Override
    public void retrieveAllTeams(final BusinessCallback callback) {

        firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_TEAM)
                .orderBy(Team.NAME, Query.Direction.ASCENDING)
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
                            callback.onSuccess(responseDTO);

                        } else {
                            //TODO añadir mensaje de error
                            //responseDTO.getErrors().add(R.string.mensajedeerror);
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
                        callback.onSuccess(responseDTO);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //TODO añadir mensaje de error
                        //responseDTO.getErrors().add(R.string.mensajedeerror);
                        callback.onFailure(responseDTO);
                    }
                });
    }
}
