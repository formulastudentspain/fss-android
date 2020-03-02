package es.formulastudent.app.mvp.view.activity.conecontrolstats;

import android.app.Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.conecontrol.ConeControlBO;
import es.formulastudent.app.mvp.data.business.conecontrol.dto.ConeControlStatsDTO;
import es.formulastudent.app.mvp.data.model.ConeControlEvent;
import es.formulastudent.app.mvp.data.model.ConeControlRegister;
import es.formulastudent.app.mvp.view.activity.conecontrolstats.recyclerview.ConeControlStatsAdapter;


public class ConeControlStatsPresenter {

    //Dependencies
    private View view;
    private ConeControlBO coneControlBO;
    ConeControlEvent ccEvent;


    public ConeControlStatsPresenter(ConeControlStatsPresenter.View view, ConeControlBO coneControlBO, ConeControlEvent ccEvent) {
        this.view = view;
        this.coneControlBO = coneControlBO;
        this.ccEvent = ccEvent;
    }



    /**
     * Retrieve Event registers
     */
    public void retrieveRegisterList(String raceRound, ConeControlStatsAdapter adapter, List<ConeControlStatsDTO> list) {

        //Show loading
        view.showLoading();

        //Call Event business
        coneControlBO.getConeControlRegistersByRaceRound(ccEvent, raceRound, new BusinessCallback() {

            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                //Refresh the records
                List<ConeControlRegister> results = (List<ConeControlRegister>) responseDTO.getData();
                List<ConeControlStatsDTO> resultListDTO = getDTOList(results);
                list.clear();
                list.addAll(resultListDTO);
                adapter.notifyDataSetChanged();
                view.hideLoading();
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                //Show error message
                //TODO
                view.hideLoading();
            }
        });
    }

    private List<ConeControlStatsDTO> getDTOList(List<ConeControlRegister> coneControlRegisters){
        Map<Long, ConeControlStatsDTO> statsDTOMap = new HashMap<>();

        for(ConeControlRegister register: coneControlRegisters){
            if(statsDTOMap.containsKey(register.getCarNumber())){
                ConeControlStatsDTO dto = statsDTOMap.get(register.getCarNumber());
                dto.setConesNumber(dto.getConesNumber() + register.getTrafficCones());
                dto.setOffCourseNumber(dto.getOffCourseNumber() + register.getOffCourses());

            }else{
                ConeControlStatsDTO dto = new ConeControlStatsDTO();
                dto.setCarNumber(register.getCarNumber());
                dto.setConesNumber(register.getTrafficCones());
                dto.setOffCourseNumber(register.getOffCourses());
                statsDTOMap.put(register.getCarNumber(), dto);
            }
        }

        List<ConeControlStatsDTO> result = new ArrayList<>();
        for(ConeControlStatsDTO dto: statsDTOMap.values()){
            result.add(dto);
        }

        return result;
    }


    public interface View {

        Activity getActivity();

        /**
         * Show message to user
         * @param message
         */
        void createMessage(Integer message, Object... args);

        /**
         * Show loading icon
         */
        void showLoading();

        /**
         * Hide loading icon
         */
        void hideLoading();
    }
}
