package es.formulastudent.app.mvp.view.screen.conecontrol;

import androidx.fragment.app.FragmentActivity;

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
import es.formulastudent.app.mvp.view.screen.DataConsumer;
import es.formulastudent.app.mvp.view.screen.general.actionlisteners.RecyclerViewClickListener;
import es.formulastudent.app.mvp.view.utils.Messages;


public class ConeControlPresenter extends DataConsumer implements RecyclerViewClickListener {

    //Cone Control Event Type
    private ConeControlEvent ccEventType;

    //Dependencies
    private View view;
    private ConeControlBO coneControlBO;
    private MailSender mailSender;
    private Messages messages;

    //Data
    private List<ConeControlRegister> coneControlRegisterList = new ArrayList<>();
    private ListenerRegistration registration;

    public ConeControlPresenter(ConeControlPresenter.View view, ConeControlEvent ccEventType,
                                ConeControlBO coneControlBO, MailSender mailSender, Messages messages) {
        super(coneControlBO);
        this.view = view;
        this.ccEventType = ccEventType;
        this.coneControlBO = coneControlBO;
        this.mailSender = mailSender;
        this.messages = messages;
    }

    @Override
    public void recyclerViewListClicked(android.view.View view, int position) {
        ConeControlRegister register = coneControlRegisterList.get(position);

        if(view.getId() == R.id.minus_cone){
            register.setCurrentConesCount(register.getCurrentConesCount()-1);
            register.setState(1);
            refreshList();

        }else if(view.getId() == R.id.add_cone){
            register.setCurrentConesCount(register.getCurrentConesCount()+1);
            register.setState(1);
            refreshList();

        }else if(view.getId() == R.id.minus_off_course){
            register.setCurrentOffCourseCount(register.getCurrentOffCourseCount()-1);
            register.setState(1);
            refreshList();

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
                    messages.showError(responseDTO.getError());
                }
            });
        }
    }

    public ListenerRegistration retrieveRegisterList() {

        //We need to prevent multiple listeners if user filters multiple times
        if(registration != null){
            registration.remove();
        }

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
                messages.showError(responseDTO.getError());
            }
        });
        return registration;
    }

    private void updateEventRegisters(List<ConeControlRegister> items){

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

    private void refreshList(){
        this.view.refreshEventRegisterItems();
    }


    List<ConeControlRegister> getEventRegisterList() {
        return coneControlRegisterList;
    }


    void exportConesToExcel(ConeControlEvent ccEvent) {

        try {
            coneControlBO.exportConesToExcel(ccEvent, new BusinessCallback() {
                @Override
                public void onSuccess(ResponseDTO responseDTO) {
                    ExportStatisticsDTO exportStatisticsDTO = (ExportStatisticsDTO) responseDTO.getData();
                    mailSender.sendMail(exportStatisticsDTO);
                }

                @Override
                public void onFailure(ResponseDTO responseDTO) {
                    messages.showError(responseDTO.getError());
                }
            });
        }catch (IOException e){
            //TODO
        }
    }


    public interface View {

        FragmentActivity getActivity();

        /**
         * Refresh list
         */
        void refreshEventRegisterItems();

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
