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
import es.formulastudent.app.mvp.data.business.user.UserBO;
import es.formulastudent.app.mvp.data.model.User;

public class AuthBOFirebaseImpl implements AuthBO {

    private FirebaseAuth firebaseAuth;
    private UserBO userBO;

    public AuthBOFirebaseImpl(FirebaseAuth firebaseAuth, UserBO userBO) {
        this.firebaseAuth = firebaseAuth;
        this.userBO = userBO;
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

    @Override
    public void createUser(String name, String mail, String password, final BusinessCallback callback) {

        final ResponseDTO responseDTO = new ResponseDTO();

       firebaseAuth.createUserWithEmailAndPassword(mail, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
           @Override
           public void onSuccess(AuthResult authResult) {

               User user = new User();
               user.setPhotoUrl("https://lh3.googleusercontent.com/-cXXaVVq8nMM/AAAAAAAAAAI/AAAAAAAAAKI/_Y1WfBiSnRI/photo.jpg?sz=150");
               user.setName(name);
               user.setMail(mail);

               userBO.createUser(user, new BusinessCallback() {
                   @Override
                   public void onSuccess(ResponseDTO responseDTO) {
                       firebaseAuth.signOut();
                       responseDTO.setInfo(R.string.login_business_create_user_success);
                       callback.onSuccess(responseDTO);
                   }

                   @Override
                   public void onFailure(ResponseDTO responseDTO) {
                       responseDTO.setInfo(R.string.login_business_create_user_failure);
                       callback.onFailure(responseDTO);
                   }
               });

           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
              if("The email address is already in use by another account.".equals(e.getMessage())){
                   responseDTO.setError(R.string.login_business_create_user_email_existing);
               }else{
                   responseDTO.setError(R.string.login_business_create_user_failure);
               }
               callback.onFailure(responseDTO);
           }
       });
    }
}
