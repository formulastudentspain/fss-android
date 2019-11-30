package es.formulastudent.app.mvp.data.business.imageuploader.impl;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.imageuploader.ImageBO;

public class ImageBOFirebaseImpl implements ImageBO {


    private FirebaseStorage firebaseStorage;

    public ImageBOFirebaseImpl(FirebaseStorage firebaseStorage) {
        this.firebaseStorage = firebaseStorage;
    }


    @Override
    public void uploadImage(Bitmap bitmap, String ID, final BusinessCallback callback) {

        final ResponseDTO responseDTO = new ResponseDTO();
        final StorageReference storageReference = firebaseStorage.getReference().child(ID);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = storageReference.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //success
                taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            Uri path = task.getResult();

                            responseDTO.setData(path);
                            responseDTO.setInfo(R.string.upload_picture_success);
                            callback.onSuccess(responseDTO);

                        } else {
                            //on failure
                            responseDTO.setError(R.string.upload_picture_error);
                            callback.onFailure(responseDTO);
                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //on failure
                responseDTO.setError(R.string.upload_picture_error);
                callback.onFailure(responseDTO);
            }
        });
    }
}
