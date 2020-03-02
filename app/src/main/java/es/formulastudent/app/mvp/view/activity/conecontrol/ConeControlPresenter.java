package es.formulastudent.app.mvp.view.activity.conecontrol;

import android.app.Activity;
import android.content.Context;

import com.google.firebase.firestore.ListenerRegistration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.conecontrol.ConeControlBO;
import es.formulastudent.app.mvp.data.business.mailsender.MailSender;
import es.formulastudent.app.mvp.data.business.statistics.dto.ExportStatisticsDTO;
import es.formulastudent.app.mvp.data.model.ConeControlEvent;
import es.formulastudent.app.mvp.data.model.ConeControlRegister;
import es.formulastudent.app.mvp.data.model.ConeControlRegisterLog;
import es.formulastudent.app.mvp.data.model.RaceControlRegister;
import es.formulastudent.app.mvp.data.model.RaceControlEnduranceState;
import es.formulastudent.app.mvp.view.activity.general.actionlisteners.RecyclerViewClickListener;


public class ConeControlPresenter implements RecyclerViewClickListener {

    //Cone Control Event Type
    ConeControlEvent ccEventType;

    //Dependencies
    private View view;
    private Context context;
    private ConeControlBO coneControlBO;
    private MailSender mailSender;


    //Data
    List<ConeControlRegister> coneControlRegisterList = new ArrayList<>();
    ListenerRegistration registration;
    RaceControlEnduranceState newState = null;
    RaceControlRegister register = null;


    //Filtering values
    private String selectedArea;
    private Long selectedCarNumber;


    public ConeControlPresenter(ConeControlPresenter.View view, Context context, ConeControlEvent ccEventType, ConeControlBO coneControlBO, MailSender mailSender) {
        this.view = view;
        this.context = context;
        this.ccEventType = ccEventType;
        this.coneControlBO = coneControlBO;
        this.selectedArea = context.getString(R.string.rc_area_all);
        this.mailSender = mailSender;
    }

    @Override
    public void recyclerViewListClicked(android.view.View view, int position) {
        ConeControlRegister register = coneControlRegisterList.get(position);

        if(view.getId() == R.id.minus_cone){
            if(register.getCurrentConesCount()>0){
                register.setCurrentConesCount(register.getCurrentConesCount()-1);
                register.setState(1);
                refreshList();
            }

        }else if(view.getId() == R.id.add_cone){
            register.setCurrentConesCount(register.getCurrentConesCount()+1);
            register.setState(1);
            refreshList();

        }else if(view.getId() == R.id.minus_off_course){
            if(register.getCurrentOffCourseCount()>0){
                register.setCurrentOffCourseCount(register.getCurrentOffCourseCount()-1);
                register.setState(1);
                refreshList();
            }

        }else if(view.getId() == R.id.add_off_course){
            register.setCurrentOffCourseCount(register.getCurrentOffCourseCount()+1);
            register.setState(1);
            refreshList();

        }else if(view.getId() == R.id.save_button){
            register.setState(2);
            refreshList();

            //Add log
            ConeControlRegisterLog log = new ConeControlRegisterLog();
            log.setDate(Calendar.getInstance().getTime());
            log.setCones((long) register.getCurrentConesCount());
            log.setOffcourses((long) register.getCurrentOffCourseCount());
            register.getLogs().add(log);

            register.setTrafficCones(register.getTrafficCones()+register.getCurrentConesCount());
            register.setCurrentConesCount(0);

            register.setOffCourses(register.getOffCourses()+register.getCurrentOffCourseCount());
            register.setCurrentOffCourseCount(0);



            coneControlBO.updateConeControlRegister(ccEventType, register, new BusinessCallback() {
                @Override
                public void onSuccess(ResponseDTO responseDTO) {
                    coneControlRegisterList.get(position).setState(0);
                    refreshList();
                }

                @Override
                public void onFailure(ResponseDTO responseDTO) {

                }
            });
        }
    }

    public ListenerRegistration retrieveRegisterList() {

        //We need to prevent multiple listeners if user filters multiple times
        if(registration != null){
            registration.remove();
        }

        //Show loading
        view.showLoading();

        Map<String, Object> filters = new HashMap<>();
        filters.put("sector", view.getSelectedSector());
        filters.put("round", view.getSelectedRound());

        //Retrieve race control registers in real-time
        registration = coneControlBO.getConeControlRegistersRealTime(ccEventType, filters,  new BusinessCallback() {

            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                List<ConeControlRegister> results = (List<ConeControlRegister>) responseDTO.getData();
                updateEventRegisters(results==null ? new ArrayList<>() : results);
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                //Show error message
                view.createMessage(responseDTO.getError());
            }
        });


        return registration;
    }

    public void updateEventRegisters(List<ConeControlRegister> items){

        //Copy states
        for(ConeControlRegister ccRegister: coneControlRegisterList){
            for(ConeControlRegister registerNew: items){
                if(ccRegister.getCarNumber().equals(registerNew.getCarNumber())){
                    registerNew.setState(ccRegister.getState());
                    registerNew.setCurrentConesCount(ccRegister.getCurrentConesCount());
                    registerNew.setCurrentOffCourseCount(ccRegister.getCurrentOffCourseCount());
                    break;
                }
            }
        }


        //Update all-register-list
        this.coneControlRegisterList.clear();
        this.coneControlRegisterList.addAll(items);
        this.view.refreshEventRegisterItems();
    }

    public void refreshList(){
        this.view.refreshEventRegisterItems();
    }


    public List<ConeControlRegister> getEventRegisterList() {
        return coneControlRegisterList;
    }


    public void exportConesToExcel(ConeControlEvent ccEvent) {

        try {
            coneControlBO.exportConesToExcel(ccEvent, new BusinessCallback() {
                @Override
                public void onSuccess(ResponseDTO responseDTO) {
                    ExportStatisticsDTO exportStatisticsDTO = (ExportStatisticsDTO) responseDTO.getData();
                    mailSender.sendMail(exportStatisticsDTO);
                }

                @Override
                public void onFailure(ResponseDTO responseDTO) {
                    //TODO
                }
            });
        }catch (IOException e){
            //TODO
        }

    }


    public interface View {

        Activity getActivity();

        /**
         * Show message to user
         * @param message
         */
        void createMessage(Integer message, Object... args);

        /**
         * Refresh list
         */
        void refreshEventRegisterItems();

        /**
         * Show loading icon
         */
        void showLoading();

        /**
         * Hide loading icon
         */
        void hideLoading();

        /**
         * Get current round
         * @return
         */
        String getSelectedRound();

        /**
         * Get current sector
         * @return
         */
        Long getSelectedSector();


    }

}
