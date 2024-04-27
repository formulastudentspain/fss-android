package code.formulastudentspain.app.mvp.data.business.auth.impl;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import code.formulastudentspain.app.R;
import code.formulastudentspain.app.mvp.data.business.BusinessCallback;
import code.formulastudentspain.app.mvp.data.business.OnFailureCallback;
import code.formulastudentspain.app.mvp.data.business.OnSuccessCallback;
import code.formulastudentspain.app.mvp.data.business.ResponseDTO;
import code.formulastudentspain.app.mvp.data.business.auth.AuthBO;
import code.formulastudentspain.app.mvp.data.business.user.UserBO;
import code.formulastudentspain.app.mvp.data.model.User;
import code.formulastudentspain.app.mvp.view.utils.messages.Message;

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
                .addOnSuccessListener(authResult -> {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    responseDTO.setData(user);
                    responseDTO.setInfo(R.string.login_business_info_login);
                    callback.onSuccess(responseDTO);
                })
                .addOnFailureListener(e -> {
                    responseDTO.setError(R.string.login_business_error_login_with_mail);
                    callback.onFailure(responseDTO);
                });
    }

    @Override
    public void resetPassword(String mail, final BusinessCallback callback) {

        final ResponseDTO responseDTO = new ResponseDTO();

        firebaseAuth.setLanguageCode("en");
        firebaseAuth.sendPasswordResetEmail(mail)
                .addOnSuccessListener(aVoid -> {
                    responseDTO.setInfo(R.string.login_business_info_reset);
                    callback.onSuccess(responseDTO);
                })
                .addOnFailureListener(e -> {
                    responseDTO.setError(R.string.login_business_error_reset_password);
                    callback.onFailure(responseDTO);
                });
    }

    @Override
    public void createUser(String name, String mail, String password,
                           @NonNull OnSuccessCallback<?> onSuccessCallback,
                           @NonNull OnFailureCallback onFailureCallback) {

        firebaseAuth.createUserWithEmailAndPassword(mail, password)
                .addOnSuccessListener(authResult -> {
                    User user = new User();
                    user.setPhotoUrl("https://lh3.googleusercontent.com/-cXXaVVq8nMM/AAAAAAAAAAI/AAAAAAAAAKI/_Y1WfBiSnRI/photo.jpg?sz=150");
                    user.setName(name);
                    user.setMail(mail);

                    userBO.createUser(user,
                            response -> {
                                firebaseAuth.signOut();
                                onSuccessCallback.onSuccess(null);
                            },
                            errorMessage -> onFailureCallback.onFailure(new Message(R.string.login_business_create_user_failure)));
                })
                .addOnFailureListener(e -> {
                    if ("The email address is already in use by another account.".equals(e.getMessage())) {
                        onFailureCallback.onFailure(new Message(R.string.login_business_create_user_email_existing));
                    } else {
                        onFailureCallback.onFailure(new Message(R.string.login_business_create_user_failure));
                    }
                });
    }
}
