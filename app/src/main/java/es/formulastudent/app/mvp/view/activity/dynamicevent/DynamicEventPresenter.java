package es.formulastudent.app.mvp.view.activity.dynamicevent;

import java.util.Date;

public interface DynamicEventPresenter {

    void setFilteringValues(Date from, Date to, String day, String teamID, Long carNumber);
    void retrieveRegisterList();
    void createMessage(String message);
}
