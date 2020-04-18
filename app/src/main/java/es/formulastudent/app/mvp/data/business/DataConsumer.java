package es.formulastudent.app.mvp.data.business;

import androidx.lifecycle.MutableLiveData;

import es.formulastudent.app.mvp.data.business.DataLoader;
import es.formulastudent.app.mvp.view.utils.messages.Message;

public abstract class DataConsumer {

    //Live data to show the loading dialog
    private MutableLiveData<Boolean> loadingData = new MutableLiveData<>();

    //Live data to show errors
    private MutableLiveData<Message> errorToDisplay = new MutableLiveData<>();

    public DataConsumer(DataLoader.Consumer...consumers) {
       for(DataLoader.Consumer consumer: consumers){
           consumer.setDataConsumer(this);
       }
    }



    public MutableLiveData<Message> getErrorToDisplay() {
        return errorToDisplay;
    }

    public MutableLiveData<Boolean> getLoadingData() {
        return loadingData;
    }

    public void loadingData(boolean loading) {
        loadingData.setValue(loading);
    }

    public void setErrorToDisplay(Message message){
        errorToDisplay.setValue(message);
    }
}
