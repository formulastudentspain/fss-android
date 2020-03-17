package es.formulastudent.app.mvp.data.model;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Map;

public interface Parameter {

    Parameter fromDocumentSnapshot(DocumentSnapshot documentSnapshot);

    Map<String, Object> toDocumentData();

}
