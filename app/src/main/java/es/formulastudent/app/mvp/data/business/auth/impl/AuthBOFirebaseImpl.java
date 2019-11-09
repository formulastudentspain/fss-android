package es.formulastudent.app.mvp.data.business.auth.impl;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.auth.AuthBO;

public class AuthBOFirebaseImpl implements AuthBO {

    private FirebaseAuth firebaseAuth;

    public AuthBOFirebaseImpl(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }


    @Override
    public void doLoginWithMail(String mail, String password, final BusinessCallback callback) {

        final ResponseDTO responseDTO = new ResponseDTO();

        firebaseAuth.signInWithEmailAndPassword(mail, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                      @Override
                      public void onSuccess(AuthResult authResult) {
                          FirebaseUser user = firebaseAuth.getCurrentUser();
                          responseDTO.setData(user);
                          responseDTO.setInfo(R.string.login_business_info_login);
                          callback.onSuccess(responseDTO);
                      }

                  }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        responseDTO.setError(R.string.login_business_error_login_with_mail);
                        callback.onFailure(responseDTO);
                    }
                });
    }

    @Override
    public void resetPassword(String mail, final BusinessCallback callback) {

        final ResponseDTO responseDTO = new ResponseDTO();

        firebaseAuth.setLanguageCode("en");
        firebaseAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                responseDTO.setInfo(R.string.login_business_info_reset);
                callback.onSuccess(responseDTO);
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                responseDTO.setError(R.string.login_business_error_reset_password);
                callback.onFailure(responseDTO);
            }
        });
    }
}
