package es.formulastudent.app.mvp.view.activity.statistics;

import android.content.Context;

import java.io.IOException;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.statistics.StatisticsBO;
import es.formulastudent.app.mvp.data.model.EventType;

public class StatisticsPresenter {

    //Dependencies
    private StatisticsPresenter.View view;
    private Context context;
    private StatisticsBO statisticsBO;

    //Current register


    public StatisticsPresenter(StatisticsPresenter.View view, Context context, StatisticsBO statisticsBO) {
        this.view = view;
        this.context = context;
        this.statisticsBO = statisticsBO;
    }


    public void exportDynamicEvent(EventType eventType){

        try {
            statisticsBO.exportDynamicEvent(eventType, new BusinessCallback() {
                @Override
                public void onSuccess(ResponseDTO responseDTO) {

                }

                @Override
                public void onFailure(ResponseDTO responseDTO) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public interface View {

        /**
         * Show message to user
         * @param message
         */
        void createMessage(String message);

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
