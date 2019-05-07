package es.formulastudent.app.mvp.view.activity.userlist;

import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import java.util.UUID;

import es.formulastudent.app.mvp.data.model.dto.UserDTO;
import es.formulastudent.app.mvp.view.activity.userdetail.UserDetailActivity;
import es.formulastudent.app.mvp.view.activity.userlist.recyclerview.RecyclerViewClickListener;

public class UserListPresenter implements RecyclerViewClickListener {

    //Dependencies
    private View view;
    private Context context;

    //Data
    private List<UserDTO> allUserList = new ArrayList<>();
    private List<UserDTO> filteredUserList = new ArrayList<>();

    private RecyclerView recyclerView;


    public UserListPresenter(UserListPresenter.View view, Context context) {
        this.view = view;
        this.context = context;
    }


    private void updateUserListItems(List<UserDTO> newItems){
        //Update all-user-list
        this.allUserList.clear();
        this.allUserList.addAll(newItems);

        //Update and refresh filtered-user-list
        this.filteredUserList.clear();
        this.filteredUserList.addAll(newItems);
        this.view.refreshUserItems();
    }

    public List<UserDTO> getUserList() {

        //TODO business operation to get the users
        List<UserDTO> items = setUserList();

        //Update the itemList
        this.updateUserListItems(items);

        return filteredUserList;
    }


    //TODO borrar
    private List<UserDTO> setUserList(){

        List<UserDTO> list = new ArrayList<>();

        list.add(new UserDTO(UUID.randomUUID().toString(), "David Pérez", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/1.jpg", null, "1", true));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Gerard Martí", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/2.jpg", null, null,false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Pere Torres", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/3.jpg", null, "",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Marc Salvado", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/4.jpg", null, "3",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Oriol Soler", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/5.jpg", null,"",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Andrés Romero", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/6.jpg", null,"",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Antonio David Llorca", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/7.jpg", null,"",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "David Guerra", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/8.jpg", null,"12",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Miguel Calderón", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/9.jpg", null,null,false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Marc Mateu", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/10.jpg", null,"",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Raúl Gracia", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/11.jpg", null,"",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Diego Caudillo", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/12.jpg", null,"6",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Agnes Creus", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/13.jpg", null,null,false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Josep María Pons", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/14.jpg", null,"3",true));

        list.add(new UserDTO(UUID.randomUUID().toString(), "David Pérez", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/1.jpg", null, "1", true));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Gerard Martí", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/2.jpg", null, null,false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Pere Torres", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/3.jpg", null, "",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Marc Salvado", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/4.jpg", null, "3",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Oriol Soler", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/5.jpg", null,"",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Andrés Romero", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/6.jpg", null,"",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Antonio David Llorca", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/7.jpg", null,"",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "David Guerra", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/8.jpg", null,"12",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Miguel Calderón", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/9.jpg", null,null,false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Marc Mateu", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/10.jpg", null,"",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Raúl Gracia", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/11.jpg", null,"",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Diego Caudillo", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/12.jpg", null,"6",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Agnes Creus", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/13.jpg", null,null,false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Josep María Pons", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/14.jpg", null,"3",true));

        list.add(new UserDTO(UUID.randomUUID().toString(), "David Pérez", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/1.jpg", null, "1", true));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Gerard Martí", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/2.jpg", null, null,false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Pere Torres", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/3.jpg", null, "",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Marc Salvado", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/4.jpg", null, "3",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Oriol Soler", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/5.jpg", null,"",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Andrés Romero", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/6.jpg", null,"",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Antonio David Llorca", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/7.jpg", null,"",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "David Guerra", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/8.jpg", null,"12",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Miguel Calderón", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/9.jpg", null,null,false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Marc Mateu", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/10.jpg", null,"",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Raúl Gracia", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/11.jpg", null,"",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Diego Caudillo", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/12.jpg", null,"6",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Agnes Creus", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/13.jpg", null,null,false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Josep María Pons", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/14.jpg", null,"3",true));

        list.add(new UserDTO(UUID.randomUUID().toString(), "David Pérez", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/1.jpg", null, "1", true));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Gerard Martí", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/2.jpg", null, null,false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Pere Torres", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/3.jpg", null, "",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Marc Salvado", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/4.jpg", null, "3",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Oriol Soler", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/5.jpg", null,"",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Andrés Romero", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/6.jpg", null,"",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Antonio David Llorca", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/7.jpg", null,"",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "David Guerra", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/8.jpg", null,"12",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Miguel Calderón", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/9.jpg", null,null,false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Marc Mateu", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/10.jpg", null,"",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Raúl Gracia", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/11.jpg", null,"",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Diego Caudillo", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/12.jpg", null,"6",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Agnes Creus", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/13.jpg", null,null,false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Josep María Pons", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/14.jpg", null,"3",true));

        list.add(new UserDTO(UUID.randomUUID().toString(), "David Pérez", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/1.jpg", null, "1", true));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Gerard Martí", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/2.jpg", null, null,false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Pere Torres", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/3.jpg", null, "",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Marc Salvado", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/4.jpg", null, "3",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Oriol Soler", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/5.jpg", null,"",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Andrés Romero", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/6.jpg", null,"",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Antonio David Llorca", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/7.jpg", null,"",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "David Guerra", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/8.jpg", null,"12",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Miguel Calderón", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/9.jpg", null,null,false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Marc Mateu", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/10.jpg", null,"",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Raúl Gracia", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/11.jpg", null,"",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Diego Caudillo", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/12.jpg", null,"6",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Agnes Creus", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/13.jpg", null,null,false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Josep María Pons", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/14.jpg", null,"3",true));

        list.add(new UserDTO(UUID.randomUUID().toString(), "David Pérez", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/1.jpg", null, "1", true));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Gerard Martí", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/2.jpg", null, null,false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Pere Torres", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/3.jpg", null, "",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Marc Salvado", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/4.jpg", null, "3",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Oriol Soler", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/5.jpg", null,"",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Andrés Romero", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/6.jpg", null,"",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Antonio David Llorca", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/7.jpg", null,"",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "David Guerra", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/8.jpg", null,"12",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Miguel Calderón", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/9.jpg", null,null,false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Marc Mateu", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/10.jpg", null,"",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Raúl Gracia", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/11.jpg", null,"",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Diego Caudillo", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/12.jpg", null,"6",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Agnes Creus", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/13.jpg", null,null,false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Josep María Pons", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/14.jpg", null,"3",true));

        list.add(new UserDTO(UUID.randomUUID().toString(), "David Pérez", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/1.jpg", null, "1", true));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Gerard Martí", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/2.jpg", null, null,false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Pere Torres", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/3.jpg", null, "",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Marc Salvado", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/4.jpg", null, "3",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Oriol Soler", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/5.jpg", null,"",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Andrés Romero", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/6.jpg", null,"",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Antonio David Llorca", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/7.jpg", null,"",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "David Guerra", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/8.jpg", null,"12",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Miguel Calderón", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/9.jpg", null,null,false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Marc Mateu", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/10.jpg", null,"",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Raúl Gracia", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/11.jpg", null,"",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Diego Caudillo", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/12.jpg", null,"6",false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Agnes Creus", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/13.jpg", null,null,false));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Josep María Pons", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/14.jpg", null,"3",true));
        return list;
    }

    public void setRecyclerView(RecyclerView recyclerView){
        this.recyclerView = recyclerView;
    }


    public void filterUsers(String query){

        //Clear the list
        filteredUserList.clear();

        //Add results
        for(UserDTO user: allUserList){
            if(user.getName().toLowerCase().contains(query.toLowerCase())){
                filteredUserList.add(user);
            }
        }

        //Refresh list
        this.view.refreshUserItems();
    }


    @Override
    public void recyclerViewListClicked(android.view.View v, int position) {

        UserDTO selectedUser = filteredUserList.get(position);

        Intent intent = new Intent(context, UserDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("selectedUser", selectedUser);
        context.startActivity(intent);

    }

    public List<UserDTO> getUserItemList() {
        return filteredUserList;
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
         * Show loading icon
         */
        void showLoading();

        /**
         * Hide loading icon
         */
        void hideLoadingIcon();
    }

}
