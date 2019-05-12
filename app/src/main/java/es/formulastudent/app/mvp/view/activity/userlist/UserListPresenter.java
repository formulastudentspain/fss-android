package es.formulastudent.app.mvp.view.activity.userlist;

import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.activity.userdetail.UserDetailActivity;
import es.formulastudent.app.mvp.view.activity.userlist.recyclerview.RecyclerViewClickListener;

public class UserListPresenter implements RecyclerViewClickListener {

    //Dependencies
    private View view;
    private Context context;

    //Data
    private List<User> allUserList = new ArrayList<>();
    private List<User> filteredUserList = new ArrayList<>();

    private RecyclerView recyclerView;


    public UserListPresenter(UserListPresenter.View view, Context context) {
        this.view = view;
        this.context = context;
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

    public List<User> getUserList() {

        //TODO business operation to get the users
        List<User> items = setUserList();

        //Update the itemList
        this.updateUserListItems(items);

        return filteredUserList;
    }


    //TODO borrar
    private List<User> setUserList(){

        List<User> list = new ArrayList<>();

        for(int i=0; i<250; i++){
            list.add(User.createRandomUser());
        }

        return list;
    }

    public void setRecyclerView(RecyclerView recyclerView){
        this.recyclerView = recyclerView;
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

    public List<User> getUserItemList() {
        return filteredUserList;
    }


//TODO este método estaba en el presenter del Briefing, en verdad está creando en "UserInfo"
/*
    public void createRegistry(User user){
        BriefingRegister newRegister = new BriefingRegister(UUID.randomUUID(), Calendar.getInstance().getTime(),user);
        filteredBriefingRegisterList.add(newRegister); //quizas no cumpla el filtro y no hay que añadirlo
        allBriefingRegisterList.add(newRegister);

        Map<String, Object> docData = new HashMap<>();
        docData.put("mail", user.getMail());
        docData.put("name", user.getName());
        docData.put("preScrutineering", false);
        docData.put("role", "default");
        docData.put("tagNFC", user.getNFCTag());

        db.collection("UserInfo").document(user.getUid())
                .set(docData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //success
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

        //Update the list with the new registry
        updateBriefingRegisters(filteredBriefingRegisterList);
    }
*/


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
    }

}
