package es.formulastudent.app.mvp.data.business.auth.impl;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
                          callback.onSuccess(responseDTO);
                      }

                  }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //TODO añadir mensaje de error
                        responseDTO.getErrors().add("");
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
                callback.onSuccess(responseDTO);
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //TODO añadir mensaje de error
                responseDTO.getErrors().add("");
                callback.onFailure(responseDTO);
            }
        });
    }





    /**
     * Create Account by Mail and Password
     * @param mail
     * @param password
     */
    /*
    public void createAccount(String mail, String password){

        //show progress dialog

        mAuth.createUserWithEmailAndPassword(mail, password)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

        //close progress dialog

    }
    */
}
