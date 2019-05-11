package es.formulastudent.app.mvp.view.activity.briefing;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import es.formulastudent.app.mvp.data.model.BriefingRegister;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.activity.briefing.dialog.ConfirmBriefingRegisterDialog;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class BriefingPresenter {

    //Dependencies
    private View view;
    private Context context;
    private FirebaseFirestore db;


    //Data
    List<BriefingRegister> allBriefingRegisterList = new ArrayList<>();
    List<BriefingRegister> filteredBriefingRegisterList = new ArrayList<>();


    public BriefingPresenter(BriefingPresenter.View view, Context context) {
        this.view = view;
        this.context = context;
        db = FirebaseFirestore.getInstance();
    }



    public void createRegistry(User user){

        //Get current date
        Date registerDate = Calendar.getInstance().getTime();

        final BriefingRegister briefingRegister = new BriefingRegister(user, registerDate);

        db.collection(BriefingRegister.COLLECTION_ID)
                .document(briefingRegister.getID())
                .set(briefingRegister.toObjectData())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //success
                        view.showMessage("User registered successfully!");

                        filteredBriefingRegisterList.add(briefingRegister);
                        allBriefingRegisterList.add(briefingRegister);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //fail
                        view.showMessage("Failed to save data to db");
                    }
                });

        //Update the list with the new registry
        updateBriefingRegisters(filteredBriefingRegisterList);
    }



    public List<BriefingRegister> retrieveBriefingRegisterList() {
        //TODO mirar que hayan sido registrados
        final List<BriefingRegister> items = new ArrayList<>();
        db.collection("EVENT_CONTROL_BRIEFING").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //List<BriefingRegister> items = new ArrayList<>();
                            //final User userDTO;
                            BriefingRegister briefingRegister;
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                //userDTO = new User(document);
                                //briefingRegister = new BriefingRegister(UUID.randomUUID(), Calendar.getInstance().getTime(),userDTO);
                                //items.add(briefingRegister);
                            }
                            //updateBriefingRegisters(items);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
            //TODO don't exit until finish
        return items;
    }


    public void updateBriefingRegisters(List<BriefingRegister> items){
        //Update all-register-list
        this.allBriefingRegisterList.clear();
        this.allBriefingRegisterList.addAll(items);

        //Update and refresh filtered-register-list
        this.filteredBriefingRegisterList.clear();
        this.filteredBriefingRegisterList.addAll(items);
        this.view.refreshBriefingRegisterItems();
    }



    public void onNFCTagDetected(String tag){
        final String tagNFC = tag;
        view.showLoading();
        CollectionReference userRef = db.collection(User.COLLECTION_ID);
        Query userFromNFC = userRef.whereEqualTo(User.TAG_NFC, tagNFC);
        userFromNFC.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                //success
                if(queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                    User user = new User(queryDocumentSnapshots.getDocuments().get(0));
                    FragmentManager fm = ((BriefingActivity)view.getActivity()).getSupportFragmentManager();
                    ConfirmBriefingRegisterDialog createUserDialog = ConfirmBriefingRegisterDialog.newInstance(BriefingPresenter.this, user);
                    createUserDialog.show(fm, "fragment_briefing_confirm");
                    view.hideLoadingIcon();
                }
            }
        });
    }


    public List<BriefingRegister> getBriefingRegisterList() {
        return filteredBriefingRegisterList;
    }


    public interface View {

        Activity getActivity();

        /**
         * Show message to user
         * @param message
         */
        void showMessage(String message);

        /**
         * Finish current activity
         */
        void finishView();

        /**
         * Show loading icon
         */
        void showLoading();

        /**
         * Hide loading icon
         */
        void hideLoadingIcon();

        /**
         * Refresh items in list
         */
        void refreshBriefingRegisterItems();
    }

}
