package es.formulastudent.app.mvp.view.screen;

import androidx.lifecycle.MutableLiveData;

public class GeneralPresenter {

    //Live data to show the loading dialog
    private MutableLiveData<Boolean> loadingData = new MutableLiveData<>();

    public MutableLiveData<Boolean> getLoadingData() {
        return loadingData;
    }

    public void loadingData(boolean loading) {
        loadingData.setValue(loading);
    }
}
