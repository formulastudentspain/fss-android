package es.formulastudent.app.mvp.data.business.parameter.impl;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.parameter.ParameterBO;
import es.formulastudent.app.mvp.data.model.Parameter;
import es.formulastudent.app.mvp.data.model.TicketCurrentTurns;

import static es.formulastudent.app.mvp.data.business.ConfigConstants.FIREBASE_TABLE_PARAMETER;

public class ParameterBOFirebaseImpl implements ParameterBO {

    private FirebaseFirestore firebaseFirestore;

    public ParameterBOFirebaseImpl(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
    }


    @Override
    public void getItemByKey(String key, BusinessCallback callback) {

    }

    @Override
    public ListenerRegistration getItemByKeyInRealTime(String key, BusinessCallback callback) {

        ListenerRegistration registration = firebaseFirestore.collection(FIREBASE_TABLE_PARAMETER)
            .document(key)
            .addSnapshotListener((value, e) -> {

            //Response object
            ResponseDTO responseDTO = new ResponseDTO();

            if (e != null) {
                responseDTO.setError(R.string.ticket_activity_error_retrieving_parameter);
                callback.onFailure(responseDTO);
                return;
            }

            Parameter result = null;
            if(TicketCurrentTurns.KEY.equals(key)){
                result = new TicketCurrentTurns(value);
            }

            responseDTO.setData(result);
            callback.onSuccess(responseDTO);
        });

        return registration;
    }

    @Override
    public void createItem(String key, Object value, BusinessCallback callback) {

    }
}
