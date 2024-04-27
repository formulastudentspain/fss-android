package code.formulastudentspain.app.mvp.data.business.imageuploader.impl;

import android.graphics.Bitmap;
import android.net.Uri;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;

import code.formulastudentspain.app.R;
import code.formulastudentspain.app.mvp.data.business.DataLoader;
import code.formulastudentspain.app.mvp.data.business.OnFailureCallback;
import code.formulastudentspain.app.mvp.data.business.OnSuccessCallback;
import code.formulastudentspain.app.mvp.data.business.imageuploader.ImageBO;
import code.formulastudentspain.app.mvp.view.utils.messages.Message;

public class ImageBOFirebaseImpl extends DataLoader implements ImageBO {


    private FirebaseStorage firebaseStorage;

    public ImageBOFirebaseImpl(FirebaseStorage firebaseStorage) {
        this.firebaseStorage = firebaseStorage;
    }


    @Override
    public void uploadImage(Bitmap bitmap, String ID,
                            @NotNull OnSuccessCallback<Uri> onSuccessCallback,
                            @NotNull OnFailureCallback onFailureCallback) {
        final StorageReference storageReference = firebaseStorage.getReference().child(ID);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] data = byteArrayOutputStream.toByteArray();

        storageReference.putBytes(data)
                .addOnSuccessListener(taskSnapshot -> {
                    taskSnapshot.getMetadata().getReference().getDownloadUrl()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Uri path = task.getResult();
                                    onSuccessCallback.onSuccess(path);
                                } else {
                                    onFailureCallback.onFailure(new Message(R.string.upload_picture_error));
                                }
                            });
                })
                .addOnFailureListener(e -> {
                    onFailureCallback.onFailure(new Message(R.string.upload_picture_error));
                });
    }
}
