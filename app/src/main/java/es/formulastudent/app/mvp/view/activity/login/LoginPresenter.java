package es.formulastudent.app.mvp.view.activity.login;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.data.model.dto.UserDTO;
import es.formulastudent.app.mvp.view.activity.fssinformation.FssInformationActivity;


public class LoginPresenter {

    //Dependencies
    private View view;
    private Context context;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private User currentUser;

    public LoginPresenter(LoginPresenter.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void loginSuccess(){
        Intent myIntent = new Intent(context, FssInformationActivity.class);
        //myIntent.putExtra("key", value); //Optional parameters
        context.startActivity(myIntent);
    }


    /**
     * Get an email with the steps to reset your password
     * @param mail
     */
    public void forgotPassword(String mail){
        mAuth.setLanguageCode("en");
        mAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    view.showMessage("Mail Sent."); //TODO Mirar la opcion de plantillas de correo electronico: https://support.google.com/firebase/answer/7000714?hl=es
                }
            }
        });
        //view.showMessage("Ups, forgotten password!!");
    }

    /**
     * Sign in with mail and password
     * @param mail
     * @param password
     * @return
     */
    public UserDTO startSignIn(String mail, String password){

        final UserDTO[] userDTO = {new UserDTO()};

        //show dialog bar

        mAuth.signInWithEmailAndPassword(mail, password)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //Sign in success, update UI with the signed-in user's information
                    FirebaseUser user = mAuth.getCurrentUser();
                    userDTO[0] = getUserDTObyFirebaseUser(user);
                    //updateUI(user);
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(context, "Auth Failed", Toast.LENGTH_SHORT).show();
                }

                //START EXCLUDE
                if(!task.isSuccessful()){
                    //set status text as failed?
                }

                //hide bar dialog
            }
        });

        return userDTO[0];
    }

    /**
     * Create Account by Mail and Password
     * @param mail
     * @param password
     */
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

    /**
     * Convert FirebaseUser to User DTO
     * @param user
     * @return
     */
    private UserDTO getUserDTObyFirebaseUser(FirebaseUser user){
        UserDTO userDTO = new UserDTO();

        userDTO.setName(user.getDisplayName());
        userDTO.setMail(user.getEmail());
        userDTO.setMailVerified(user.isEmailVerified());
        userDTO.setUid(user.getUid());
        userDTO.setPhoneNumber(user.getPhoneNumber());

        return userDTO;
    }






    public interface View {

        /**
         * Show message to user
         * @param message
         */
        void showMessage(String message);

        /**
         * Finish current activity
         */
        void finishView();

        /**
         * Show loading icon
         */
        void showLoading();

        /**
         * Hide loading icon
         */
        void hideLoadingIcon();
    }

}
