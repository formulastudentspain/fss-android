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
    private List<UserDTO> userList = new ArrayList<>();
    private RecyclerView recyclerView;


    public UserListPresenter(UserListPresenter.View view, Context context) {
        this.view = view;
        this.context = context;
    }


    private void updateUserListItems(List<UserDTO> newItems){
        this.userList.clear();
        this.userList.addAll(newItems);
        this.view.refreshUserItems();
    }

    public List<UserDTO> getUserList() {

        //TODO business operation to get the users
        List<UserDTO> items = setUserList();

        //Update the itemList
        this.updateUserListItems(items);

        return userList;
    }


    public List<UserDTO> getUserItemList() {
        return userList;
    }



    //TODO borrar
    private List<UserDTO> setUserList(){

        List<UserDTO> list = new ArrayList<>();

        list.add(new UserDTO(UUID.randomUUID().toString(), "David Pérez", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/1.jpg", null, "1"));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Gerard Martí", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/2.jpg", null, null));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Pere Torres", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/3.jpg", null, ""));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Marc Salvado", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/4.jpg", null, "3"));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Oriol Soler", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/5.jpg", null,""));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Andrés Romero", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/6.jpg", null,""));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Antonio David Llorca", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/7.jpg", null,""));
        list.add(new UserDTO(UUID.randomUUID().toString(), "David Guerra", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/8.jpg", null,"12"));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Miguel Calderón", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/9.jpg", null,null));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Marc Mateu", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/10.jpg", null,""));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Raúl Gracia", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/11.jpg", null,""));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Diego Caudillo", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/12.jpg", null,"6"));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Agnes Creus", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/13.jpg", null,null));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Josep María Pons", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/14.jpg", null,"3"));

        list.add(new UserDTO(UUID.randomUUID().toString(), "David Pérez", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/15.jpg", null, "1"));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Gerard Martí", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/16.jpg", null, null));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Pere Torres", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/17.jpg", null, ""));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Marc Salvado", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/18.jpg", null, "3"));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Oriol Soler", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/19.jpg", null,""));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Andrés Romero", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/20.jpg", null,""));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Antonio David Llorca", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/21.jpg", null,""));
        list.add(new UserDTO(UUID.randomUUID().toString(), "David Guerra", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/22.jpg", null,"12"));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Miguel Calderón", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/23.jpg", null,null));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Marc Mateu", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/23.jpg", null,""));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Raúl Gracia", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/24.jpg", null,""));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Diego Caudillo", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/25.jpg", null,"6"));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Agnes Creus", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/26.jpg", null,null));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Josep María Pons", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/27.jpg", null,"3"));

        list.add(new UserDTO(UUID.randomUUID().toString(), "David Pérez", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/28.jpg", null, "1"));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Gerard Martí", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/29.jpg", null, null));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Pere Torres", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/30.jpg", null, ""));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Marc Salvado", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/31.jpg", null, "3"));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Oriol Soler", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/32.jpg", null,""));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Andrés Romero", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/33.jpg", null,""));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Antonio David Llorca", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/34.jpg", null,""));
        list.add(new UserDTO(UUID.randomUUID().toString(), "David Guerra", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/35.jpg", null,"12"));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Miguel Calderón", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/36.jpg", null,null));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Marc Mateu", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/37.jpg", null,""));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Raúl Gracia", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/38.jpg", null,""));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Diego Caudillo", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/39.jpg", null,"6"));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Agnes Creus", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/40.jpg", null,null));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Josep María Pons", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/41.jpg", null,"3"));

        list.add(new UserDTO(UUID.randomUUID().toString(), "David Pérez", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/1.jpg", null, "1"));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Gerard Martí", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/2.jpg", null, null));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Pere Torres", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/3.jpg", null, ""));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Marc Salvado", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/4.jpg", null, "3"));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Oriol Soler", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/5.jpg", null,""));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Andrés Romero", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/6.jpg", null,""));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Antonio David Llorca", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/7.jpg", null,""));
        list.add(new UserDTO(UUID.randomUUID().toString(), "David Guerra", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/8.jpg", null,"12"));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Miguel Calderón", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/9.jpg", null,null));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Marc Mateu", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/10.jpg", null,""));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Raúl Gracia", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/11.jpg", null,""));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Diego Caudillo", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/12.jpg", null,"6"));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Agnes Creus", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/13.jpg", null,null));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Josep María Pons", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/14.jpg", null,"3"));

        list.add(new UserDTO(UUID.randomUUID().toString(), "David Pérez", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/1.jpg", null, "1"));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Gerard Martí", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/2.jpg", null, null));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Pere Torres", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/3.jpg", null, ""));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Marc Salvado", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/4.jpg", null, "3"));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Oriol Soler", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/5.jpg", null,""));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Andrés Romero", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/6.jpg", null,""));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Antonio David Llorca", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/7.jpg", null,""));
        list.add(new UserDTO(UUID.randomUUID().toString(), "David Guerra", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/8.jpg", null,"12"));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Miguel Calderón", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/9.jpg", null,null));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Marc Mateu", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/10.jpg", null,""));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Raúl Gracia", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/11.jpg", null,""));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Diego Caudillo", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/12.jpg", null,"6"));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Agnes Creus", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/13.jpg", null,null));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Josep María Pons", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/14.jpg", null,"3"));

        list.add(new UserDTO(UUID.randomUUID().toString(), "David Pérez", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/1.jpg", null, "1"));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Gerard Martí", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/2.jpg", null, null));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Pere Torres", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/3.jpg", null, ""));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Marc Salvado", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/4.jpg", null, "3"));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Oriol Soler", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/5.jpg", null,""));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Andrés Romero", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/6.jpg", null,""));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Antonio David Llorca", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/7.jpg", null,""));
        list.add(new UserDTO(UUID.randomUUID().toString(), "David Guerra", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/8.jpg", null,"12"));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Miguel Calderón", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/9.jpg", null,null));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Marc Mateu", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/10.jpg", null,""));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Raúl Gracia", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/11.jpg", null,""));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Diego Caudillo", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/12.jpg", null,"6"));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Agnes Creus", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/13.jpg", null,null));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Josep María Pons", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/14.jpg", null,"3"));

        list.add(new UserDTO(UUID.randomUUID().toString(), "David Pérez", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/1.jpg", null, "1"));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Gerard Martí", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/2.jpg", null, null));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Pere Torres", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/3.jpg", null, ""));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Marc Salvado", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/4.jpg", null, "3"));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Oriol Soler", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/5.jpg", null,""));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Andrés Romero", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/6.jpg", null,""));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Antonio David Llorca", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/7.jpg", null,""));
        list.add(new UserDTO(UUID.randomUUID().toString(), "David Guerra", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/8.jpg", null,"12"));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Miguel Calderón", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/9.jpg", null,null));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Marc Mateu", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/10.jpg", null,""));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Raúl Gracia", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/11.jpg", null,""));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Diego Caudillo", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/12.jpg", null,"6"));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Agnes Creus", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/13.jpg", null,null));
        list.add(new UserDTO(UUID.randomUUID().toString(), "Josep María Pons", "email@gmail.com",true,"https://randomuser.me/api/portraits/men/14.jpg", null,"3"));
        return list;
    }

    public void setRecyclerView(RecyclerView recyclerView){
        this.recyclerView = recyclerView;
    }


    @Override
    public void recyclerViewListClicked(android.view.View v, int position) {

        UserDTO selectedUser = userList.get(position);

        Intent intent = new Intent(context, UserDetailActivity.class);
        intent.putExtra("selectedUser", selectedUser);
        context.startActivity(intent);

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
