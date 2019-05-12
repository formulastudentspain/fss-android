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
import es.formulastudent.app.mvp.data.model.Team;
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

    //Selected chip to filter
    private Date selectedDateFrom;
    private Date selectedDateTo;

    //Team
    private String selectedTeamID;


    public BriefingPresenter(BriefingPresenter.View view, Context context) {
        this.view = view;
        this.context = context;
        db = FirebaseFirestore.getInstance();
    }


    public void createRegistry(User user){

        //Get current date
        Date registerDate = Calendar.getInstance().getTime();

        //Show loading before asynchronous call
        view.showLoading();

        final BriefingRegister briefingRegister = new BriefingRegister(user, registerDate);
        db.collection(BriefingRegister.COLLECTION_ID)
                .document(briefingRegister.getID())
                .set(briefingRegister.toObjectData())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //success
                        view.showMessage("User registered successfully!");

                        //Retrieve again the list to see updated results
                        retrieveBriefingRegisterList();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //fail
                        view.showMessage("Failed to save data to db");
                    }
                });
    }



    public List<BriefingRegister> retrieveBriefingRegisterList() {

        //Result list
        final List<BriefingRegister> items = new ArrayList<>();

        view.showLoading();

        Query query = db.collection(BriefingRegister.COLLECTION_ID);

        //Competition day filter
        if(selectedDateFrom != null && selectedDateTo != null){
            query = query.whereLessThanOrEqualTo(BriefingRegister.DATE, selectedDateTo);
            query = query.whereGreaterThan(BriefingRegister.DATE, selectedDateFrom);
        }

        //Teams filter
        if(selectedTeamID != null && !selectedTeamID.equals("-1")){
            query = query.whereEqualTo(BriefingRegister.TEAM_ID, selectedTeamID);
        }


        query.orderBy(BriefingRegister.DATE, Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            //Add results to list
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                BriefingRegister briefingRegister = new BriefingRegister(document);
                                items.add(briefingRegister);
                            }

                            //Update view with new results
                            updateBriefingRegisters(items);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


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
                }
            }
        });
    }

    public List<Team> retrieveTeams(){

        //Result list
        final List<Team> teams = new ArrayList<>();

        view.showLoading();

        db.collection(Team.COLLECTION_ID)
                .orderBy(Team.NAME, Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            //Add All option
                            Team teamAll = new Team("-1", "All");
                            teams.add(teamAll);

                            //Add results to list
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Team team = new Team(document);
                                teams.add(team);
                            }

                            view.initializeTeamsSpinner(teams);

                        } else {
                            Log.d(TAG, "Error getting teams: ", task.getException());
                        }
                    }
                });

        return teams;
    }


    public List<BriefingRegister> getBriefingRegisterList() {
        return filteredBriefingRegisterList;
    }

    public Date getSelectedDateFrom() {
        return selectedDateFrom;
    }

    public void setSelectedDateFrom(Date selectedDateFrom) {
        this.selectedDateFrom = selectedDateFrom;
    }

    public Date getSelectedDateTo() {
        return selectedDateTo;
    }

    public void setSelectedDateTo(Date selectedDateTo) {
        this.selectedDateTo = selectedDateTo;
    }

    public String getSelectedTeamID() {
        return selectedTeamID;
    }

    public void setSelectedTeamID(String selectedTeamID) {
        this.selectedTeamID = selectedTeamID;
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
        void hideLoading();

        /**
         * Refresh items in list
         */
        void refreshBriefingRegisterItems();

        /**
         * Initialize teams spinner
         */
        void initializeTeamsSpinner(List<Team> teams);
    }

}
