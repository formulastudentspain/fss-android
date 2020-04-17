package es.formulastudent.app.mvp.view.screen;

import androidx.lifecycle.MutableLiveData;

import es.formulastudent.app.mvp.data.business.DataLoader;

public abstract class DataConsumer {

    //Live data to show the loading dialog
    private MutableLiveData<Boolean> loadingData = new MutableLiveData<>();

    public DataConsumer(DataLoader.Consumer...consumers) {
       for(DataLoader.Consumer consumer: consumers){
           consumer.setDataConsumer(this);
       }
    }

    public MutableLiveData<Boolean> getLoadingData() {
        return loadingData;
    }

    public void loadingData(boolean loading) {
        loadingData.setValue(loading);
    }
}
