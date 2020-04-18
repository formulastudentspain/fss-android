package es.formulastudent.app.mvp.data.business.imageuploader;

import android.graphics.Bitmap;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.DataLoader;

public interface ImageBO extends DataLoader.Consumer {

    /**
     * Method to upload a picture. It returns the URL to download it
     * @param bitmap
     * @param ID
     * @param callback
     */
    void uploadImage(Bitmap bitmap, String ID, BusinessCallback callback);

}
