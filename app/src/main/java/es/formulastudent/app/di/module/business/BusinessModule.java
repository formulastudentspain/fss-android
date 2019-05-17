package es.formulastudent.app.di.module.business;

import com.google.firebase.firestore.FirebaseFirestore;

import dagger.Module;
import dagger.Provides;
import es.formulastudent.app.mvp.data.business.briefing.BriefingBO;
import es.formulastudent.app.mvp.data.business.briefing.impl.BriefingBOFirebaseImpl;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.business.team.impl.TeamBOFirebaseImpl;
import es.formulastudent.app.mvp.data.business.user.UserBO;
import es.formulastudent.app.mvp.data.business.user.impl.UserBOFirebaseImpl;
import es.formulastudent.app.mvp.data.business.userrole.UserRoleBO;
import es.formulastudent.app.mvp.data.business.userrole.impl.UserRoleBOFirebaseImpl;

@Module(includes = {FirebaseModule.class})
public class BusinessModule {

    /**
     * Provide Team business
     * @param firebaseFirestore
     * @return
     */
    @Provides
    public TeamBO provideTeamBO(FirebaseFirestore firebaseFirestore) {
        return new TeamBOFirebaseImpl(firebaseFirestore);
    }


    /**
     * Provide Briefing business
     * @param firebaseFirestore
     * @return
     */
    @Provides
    public BriefingBO provideBriefingBO(FirebaseFirestore firebaseFirestore) {
        return new BriefingBOFirebaseImpl(firebaseFirestore);
    }


    /**
     * Provide User business
     * @param firebaseFirestore
     * @return
     */
    @Provides
    public UserBO provideUserBO(FirebaseFirestore firebaseFirestore) {
        return new UserBOFirebaseImpl(firebaseFirestore);
    }

    /**
     * Provide User Role business
     * @param firebaseFirestore
     * @return
     */
    @Provides
    public UserRoleBO provideUserRoleBO(FirebaseFirestore firebaseFirestore) {
        return new UserRoleBOFirebaseImpl(firebaseFirestore);
    }

}
