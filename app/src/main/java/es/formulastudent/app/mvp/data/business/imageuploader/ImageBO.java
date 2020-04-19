package es.formulastudent.app.mvp.data.business.imageuploader;

import android.graphics.Bitmap;
import android.net.Uri;

import org.jetbrains.annotations.NotNull;

import es.formulastudent.app.mvp.data.business.DataLoader;
import es.formulastudent.app.mvp.data.business.OnFailureCallback;
import es.formulastudent.app.mvp.data.business.OnSuccessCallback;

public interface ImageBO extends DataLoader.Consumer {

    /**
     * Method to upload a picture. It returns the URL to download it
     * @param bitmap
     * @param ID
     * @param onSuccessCallback
     * @param onFailureCallback
     */
    void uploadImage(Bitmap bitmap, String ID,
                     @NotNull OnSuccessCallback<Uri> onSuccessCallback,
                     @NotNull OnFailureCallback onFailureCallback);

}
