package code.formulastudentspain.app.mvp.data.business.user.impl;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import code.formulastudentspain.app.R;
import code.formulastudentspain.app.mvp.data.business.ConfigConstants;
import code.formulastudentspain.app.mvp.data.business.DataLoader;
import code.formulastudentspain.app.mvp.data.business.OnFailureCallback;
import code.formulastudentspain.app.mvp.data.business.OnSuccessCallback;
import code.formulastudentspain.app.mvp.data.business.user.UserBO;
import code.formulastudentspain.app.mvp.data.model.User;
import code.formulastudentspain.app.mvp.data.model.UserRole;
import code.formulastudentspain.app.mvp.view.utils.messages.Message;

public class UserBOFirebaseImpl extends DataLoader implements UserBO {

    private FirebaseFirestore firebaseFirestore;

    public UserBOFirebaseImpl(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
    }

    @Override
    public void retrieveUsers(UserRole selectedRole,
                              @NotNull OnSuccessCallback<List<User>> onSuccessCallback,
                              @NotNull OnFailureCallback onFailureCallback) {

        Query query = firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_USER);

        if (selectedRole != null) {
            query = query.whereEqualTo(User.ROLE, selectedRole.getName());
        }

        loadingData(true);
        query.orderBy(User.NAME, Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    //success
                    if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                        List<User> result = new ArrayList<>();

                        //Add results to list
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            User user = new User(document);
                            result.add(user);
                        }
                        onSuccessCallback.onSuccess(result);
                        loadingData(false);
                    }
                })
                .addOnFailureListener(e -> {
                    onFailureCallback.onFailure(new Message(R.string.user_get_all_error));
                    loadingData(false);
                });
    }

    @Override
    public void createUser(final User user,
                           @NotNull OnSuccessCallback<?> onSuccessCallback,
                           @NotNull OnFailureCallback onFailureCallback) {
        final Map<String, Object> docData = user.toDocumentData();
        loadingData(true);
        firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_USER)
                .document(user.getID())
                .set(docData)
                .addOnSuccessListener(aVoid -> {
                    onSuccessCallback.onSuccess(null);
                    loadingData(false);
                })
                .addOnFailureListener(e -> {
                    onFailureCallback.onFailure(new Message(R.string.user_create_error));
                    loadingData(false);
                });
    }

    @Override
    public void retrieveUserByMail(String mail,
                                   @NotNull OnSuccessCallback<User> onSuccessCallback,
                                   @NotNull OnFailureCallback onFailureCallback) {
        loadingData(true);
        firebaseFirestore.collection(ConfigConstants.FIREBASE_TABLE_USER)
                .whereEqualTo(User.MAIL, mail)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    //success
                    if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                        User user = new User(queryDocumentSnapshots.getDocuments().get(0));
                        onSuccessCallback.onSuccess(user);
                    }
                    loadingData(false);
                })
                .addOnFailureListener(e -> {
                    onFailureCallback.onFailure(new Message(R.string.user_get_by_mail_error));
                    loadingData(false);
                });
    }

    @Override
    public void editUser(final User user, @NotNull OnSuccessCallback<?> onSuccessCallback,
                         @NotNull OnFailureCallback onFailureCallback) {

        final DocumentReference registerReference = firebaseFirestore
                .collection(ConfigConstants.FIREBASE_TABLE_USER)
                .document(user.getID());

        loadingData(true);
        registerReference.get()
                .addOnSuccessListener(documentSnapshot -> registerReference.update(user.toDocumentData())
                        .addOnSuccessListener(aVoid -> {
                            onSuccessCallback.onSuccess(null);
                            loadingData(false);
                        })
                        .addOnFailureListener(e -> {
                            onFailureCallback.onFailure(new Message(R.string.user_update_error));
                            loadingData(false);
                        }))
                .addOnFailureListener(e -> {
                    onFailureCallback.onFailure(new Message(R.string.user_update_error));
                    loadingData(false);
                });
    }
}
