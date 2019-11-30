package es.formulastudent.app.mvp.data.business.imageuploader;

import android.graphics.Bitmap;

import es.formulastudent.app.mvp.data.business.BusinessCallback;

public interface ImageBO {

    /**
     * Method to upload a picture. It returns the URL to download it
     * @param bitmap
     * @param ID
     * @param callback
     */
    void uploadImage(Bitmap bitmap, String ID, BusinessCallback callback);

}
