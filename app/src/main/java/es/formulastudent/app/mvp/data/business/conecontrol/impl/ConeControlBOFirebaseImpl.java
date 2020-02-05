package es.formulastudent.app.mvp.data.business.conecontrol.impl;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ConfigConstants;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.conecontrol.ConeControlBO;
import es.formulastudent.app.mvp.data.model.ConeControlRegister;
import es.formulastudent.app.mvp.data.model.Team;

public class ConeControlBOFirebaseImpl implements ConeControlBO {

    private FirebaseFirestore firebaseFirestore;

    public ConeControlBOFirebaseImpl(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
    }

    @Override
    public ListenerRegistration getConeControlRegistersRealTime(final Map<String, Object> filters, final BusinessCallback callback) {

        //Event type
        Query query = firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_CONE_CONTROL);

        //Race Type filter (electric, combustion and final)
        query = query.whereEqualTo(ConeControlRegister.RACE_ROUND, filters.get("raceRound"));

        //Sector
        query = query.whereEqualTo(ConeControlRegister.SECTOR_NUMBER, filters.get("sectorNumber"));

        //Only registers that are racing
        query = query.whereEqualTo(ConeControlRegister.IS_RACING, filters.get("isRacing"));


        ListenerRegistration registration = query.orderBy(ConeControlRegister.CAR_NUMBER, Query.Direction.ASCENDING)
                .addSnapshotListener((value, e) -> {

                    //Response object
                    ResponseDTO responseDTO = new ResponseDTO();

                    if (e != null) {
                        responseDTO.setError(R.string.rc_realtime_error_retrieving_message);//FIXME
                        callback.onFailure(responseDTO);
                        return;
                    }

                    List<ConeControlRegister> result = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : value) {
                        result.add(new ConeControlRegister(doc));
                    }

                    responseDTO.setData(result);
                    callback.onSuccess(responseDTO);
                });

        return registration;
    }

    @Override
    public void createConeControlForAllSectors(Team team, String raceRound, BusinessCallback callback) {
        //TODO
    }

    @Override
    public void updateConeControlRegister(Team team, @Nullable Long sector, @Nullable Long cones, @Nullable Long offCourses, @Nullable Boolean isRacing, BusinessCallback callback) {
        //TODO
    }

}
