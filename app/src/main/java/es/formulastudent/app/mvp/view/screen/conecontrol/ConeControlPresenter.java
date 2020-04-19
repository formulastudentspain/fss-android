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
import es.formulastudent.app.mvp.data.business.DataConsumer;
import es.formulastudent.app.mvp.data.business.conecontrol.ConeControlBO;
import es.formulastudent.app.mvp.data.business.mailsender.MailSender;
import es.formulastudent.app.mvp.data.model.ConeControlEvent;
import es.formulastudent.app.mvp.data.model.ConeControlRegister;
import es.formulastudent.app.mvp.data.model.ConeControlRegisterLog;
import es.formulastudent.app.mvp.view.screen.general.actionlisteners.RecyclerViewClickListener;
import es.formulastudent.app.mvp.view.utils.messages.Message;


public class ConeControlPresenter extends DataConsumer implements RecyclerViewClickListener {

    //Cone Control Event Type
    private ConeControlEvent ccEventType;

    //Dependencies
    private View view;
    private ConeControlBO coneControlBO;
    private MailSender mailSender;

    //Data
    private List<ConeControlRegister> coneControlRegisterList = new ArrayList<>();
    private ListenerRegistration registration;

    public ConeControlPresenter(ConeControlPresenter.View view, ConeControlEvent ccEventType,
                                ConeControlBO coneControlBO, MailSender mailSender) {
        super(coneControlBO);
        this.view = view;
        this.ccEventType = ccEventType;
        this.coneControlBO = coneControlBO;
        this.mailSender = mailSender;
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

            coneControlBO.updateConeControlRegister(ccEventType, register,
                    onSuccess -> {
                        coneControlRegisterList.get(position).setState(0);
                        refreshList();
                    },
                    this::setErrorToDisplay);
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
        registration = coneControlBO.getConeControlRegistersRealTime(ccEventType, filters,
                this::updateEventRegisters,
                this::setErrorToDisplay);

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

    void exportConesToExcel(ConeControlEvent ccEvent) {
        try {
            coneControlBO.exportConesToExcel(ccEvent,
                    exportStatisticsDTO -> mailSender.sendMail(exportStatisticsDTO),
                    this::setErrorToDisplay);
        }catch (IOException e){
            setErrorToDisplay(new Message(R.string.cone_control_excel_export_error));
        }
    }

    private void refreshList(){
        this.view.refreshEventRegisterItems();
    }
    List<ConeControlRegister> getEventRegisterList() {
        return coneControlRegisterList;
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
