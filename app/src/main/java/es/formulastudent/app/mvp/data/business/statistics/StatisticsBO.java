package es.formulastudent.app.mvp.data.business.statistics;

import java.io.IOException;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.model.EventType;

public interface StatisticsBO {

    /**
     * Method to generate an Excel file with Dynamic Events data
     * @param eventType
     * @param businessCallback
     * @throws IOException
     */
    void exportDynamicEvent(EventType eventType, BusinessCallback businessCallback) throws IOException;


    /**
     * Method to generate an Excel file with Users data
     * @param businessCallback
     * @throws IOException
     */
    void exportUsers(BusinessCallback businessCallback) throws IOException;
}
