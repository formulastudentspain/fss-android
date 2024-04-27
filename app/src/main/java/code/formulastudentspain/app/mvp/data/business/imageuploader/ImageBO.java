package code.formulastudentspain.app.mvp.data.business.imageuploader;

import android.graphics.Bitmap;
import android.net.Uri;

import org.jetbrains.annotations.NotNull;

import code.formulastudentspain.app.mvp.data.business.DataLoader;
import code.formulastudentspain.app.mvp.data.business.OnFailureCallback;
import code.formulastudentspain.app.mvp.data.business.OnSuccessCallback;

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
