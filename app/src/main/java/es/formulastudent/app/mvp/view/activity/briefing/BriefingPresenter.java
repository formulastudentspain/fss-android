package es.formulastudent.app.mvp.view.activity.briefing;

import android.content.Context;

import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import es.formulastudent.app.mvp.data.model.BriefingRegister;
import es.formulastudent.app.mvp.data.model.dto.UserDTO;
import es.formulastudent.app.mvp.view.activity.briefing.dialog.ConfirmBriefingRegisterDialog;


public class BriefingPresenter {

    //Dependencies
    private View view;
    private Context context;


    //Data
    List<BriefingRegister> allBriefingRegisterList = new ArrayList<>();
    List<BriefingRegister> filteredBriefingRegisterList = new ArrayList<>();


    public BriefingPresenter(BriefingPresenter.View view, Context context) {
        this.view = view;
        this.context = context;
    }



    public void createRegistry(UserDTO user){

        BriefingRegister newRegister = new BriefingRegister(UUID.randomUUID(), Calendar.getInstance().getTime(),user);
        filteredBriefingRegisterList.add(newRegister); //quizas no cumpla el filtro y no hay que añadirlo
        allBriefingRegisterList.add(newRegister);

        //TODO guardar el registro del usuario en la BBDD

        //Update the list with the new registry
        updateBriefingRegisters(filteredBriefingRegisterList);
    }



    public List<BriefingRegister> retrieveBriefingRegisterList() {

        //TODO business operation to get the users
        List<BriefingRegister> items = setUserList();

        //Update the itemList
        this.updateBriefingRegisters(items);

        return items;
    }


    //TODO borrar
    private List<BriefingRegister> setUserList(){

        List<BriefingRegister> list = new ArrayList<>();

        for(int x = 0; x<50; x++){
            list.add(new BriefingRegister(UUID.randomUUID(), Calendar.getInstance().getTime(), UserDTO.createRandomUser()));
        }

        return list;
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

        //TODO buscar usuario por el TAG NFC

        //TODO BORRAR ESTO
        UserDTO user = UserDTO.createRandomUser();
        //TODO BORRAR ESTO


        //Open confirm user dialog
        FragmentManager fm = ((BriefingActivity)context).getSupportFragmentManager();
        ConfirmBriefingRegisterDialog createUserDialog = ConfirmBriefingRegisterDialog.newInstance(this, user);
        createUserDialog.show(fm, "fragment_briefing_confirm");

    }


    public List<BriefingRegister> getBriefingRegisterList() {
        return filteredBriefingRegisterList;
    }


    public interface View {

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
