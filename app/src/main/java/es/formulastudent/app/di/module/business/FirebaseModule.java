package es.formulastudent.app.di.module.business;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import dagger.Module;
import dagger.Provides;

@Module
public class FirebaseModule {

    /**
     * Provide Firebase Firestone
     * @return
     */
    @Provides
    public FirebaseFirestore provideFirebaseFirestore() {
        return FirebaseFirestore.getInstance();
    }


    /**
     * Provide Firebase Auth
     * @return
     */
    @Provides
    public FirebaseAuth provideFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }


    /**
     * Provide Firebase Storage
     * @return
     */
    @Provides
    public FirebaseStorage provideFirebaseStorage() {
        return FirebaseStorage.getInstance();
    }


}
