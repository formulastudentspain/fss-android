package es.formulastudent.app.mvp.data.business.team.impl;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
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

        firebaseFirestore.collection(Team.COLLECTION_ID)
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

}
