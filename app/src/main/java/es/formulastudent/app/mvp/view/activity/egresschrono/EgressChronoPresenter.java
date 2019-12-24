package es.formulastudent.app.mvp.view.activity.egresschrono;

import android.content.Context;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.egress.EgressBO;
import es.formulastudent.app.mvp.data.model.EgressRegister;

public class EgressChronoPresenter {

    //Dependencies
    private EgressChronoPresenter.View view;
    private Context context;
    private EgressBO egressBO;

    //Current register


    public EgressChronoPresenter(EgressChronoPresenter.View view, Context context, EgressBO egressBO) {
        this.view = view;
        this.context = context;
        this.egressBO = egressBO;
    }


    public void saveTime(final String registerID, final String preScrutineeringRegister, final Long time){

        //Show loading
        view.showLoading();

        egressBO.saveTime(registerID, time, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                getEgressRegister(preScrutineeringRegister);

                //Sucessfull time, time to update time
                if(time <= 5000){
                    view.returnResult(time);
                }
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                //Hide loading
                view.hideLoadingIcon();
            }
        });
    }


    public void getEgressRegister(String registerID){

        //Show loading
        view.showLoading();

        egressBO.retrieveEgressByPreScrutineeringId(registerID, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                EgressRegister register = (EgressRegister) responseDTO.getData();
                view.updateTimingValues(register);

                //Hide loading
                view.hideLoadingIcon();
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {

            }
        });
    }



    public interface View {

        /**
         * Show message to user
         * @param message
         */
        void createMessage(Integer message, Object...args);

        void returnResult(Long time);

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
         * Update the time attempts
         */
        void updateTimingValues(EgressRegister register);


    }
}
