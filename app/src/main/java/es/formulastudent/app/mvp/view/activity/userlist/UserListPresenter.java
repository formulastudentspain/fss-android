package es.formulastudent.app.mvp.view.activity.userlist;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.data.model.UserRole;
import es.formulastudent.app.mvp.view.activity.userdetail.UserDetailActivity;
import es.formulastudent.app.mvp.view.activity.userlist.recyclerview.RecyclerViewClickListener;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class UserListPresenter implements RecyclerViewClickListener {

    //Dependencies
    private View view;
    private Context context;

    //Data
    private List<User> allUserList = new ArrayList<>();
    private List<User> filteredUserList = new ArrayList<>();

    private RecyclerView recyclerView;

    private FirebaseFirestore db;



    public UserListPresenter(UserListPresenter.View view, Context context) {
        this.view = view;
        this.context = context;
        db = FirebaseFirestore.getInstance();
    }


    private void updateUserListItems(List<User> newItems){
        //Update all-user-list
        this.allUserList.clear();
        this.allUserList.addAll(newItems);

        //Update and refresh filtered-user-list
        this.filteredUserList.clear();
        this.filteredUserList.addAll(newItems);
        this.view.refreshUserItems();
    }



    public List<User> retrieveUsers(){
       //Result list
        final List<User> items = new ArrayList<>();

        view.showLoading();

        Query query = db.collection(User.COLLECTION_ID);

        query.orderBy(User.NAME, Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            //Add results to list
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                User user = new User(document);
                                items.add(user);
                            }

                            //Update view with new results
                            updateUserListItems(items);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


        return items;
    }




    public void filterUsers(String query){

        //Clear the list
        filteredUserList.clear();

        //Add results
        for(User user: allUserList){
            if(user.getName().toLowerCase().contains(query.toLowerCase())){
                filteredUserList.add(user);
            }
        }

        //Refresh list
        this.view.refreshUserItems();
    }


    @Override
    public void recyclerViewListClicked(android.view.View v, int position) {

        User selectedUser = filteredUserList.get(position);

        Intent intent = new Intent(context, UserDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("selectedUser", selectedUser);
        context.startActivity(intent);

    }


    public void createUser(User user){

        Map<String, Object> docData = user.toDocumentData();

        db.collection(User.COLLECTION_ID)
                .document(user.getID())
                .set(docData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Update list
                        retrieveUsers();
                        view.showMessage("User registered successfully!");
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



    public List<User> getUserItemList() {
        return filteredUserList;
    }

    public void setRecyclerView(RecyclerView recyclerView){
        this.recyclerView = recyclerView;
    }


    public void retrieveCreateUserDialogData() {
        //First retrieve roles, then retrieve teams
        retrieveRoles();
    }

    private void retrieveRoles(){
        view.showLoading();

        db.collection(UserRole.COLLECTION_ID)
                .orderBy(UserRole.NAME, Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            List<UserRole> roles = new ArrayList<>();

                            //Add results to list
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                UserRole role = new UserRole(document);
                                roles.add(role);
                            }

                            //Retrieve Teams now
                            retrieveTeams(roles);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void retrieveTeams(final List<UserRole> roles){
        view.showLoading();

        db.collection(Team.COLLECTION_ID)
                .orderBy(Team.NAME, Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            List<Team> teams = new ArrayList<>();

                            //Add results to list
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Team team = new Team(document);
                                teams.add(team);
                            }

                            view.showCreateUserDialog(teams, roles);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


    public interface View {

        /**
         * On retrieved timeline items
         */
        void refreshUserItems();

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
         * Show loading
         */
        void showLoading();

        /**
         * Hide loading
         */
        void hideLoading();

        /**
         * Show create user dialog
         * @param teams
         * @param roles
         */
        void showCreateUserDialog(List<Team> teams, List<UserRole> roles);
    }

}
