package es.formulastudent.app.di.module.business;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import dagger.Module;
import dagger.Provides;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.mvp.data.business.auth.AuthBO;
import es.formulastudent.app.mvp.data.business.auth.impl.AuthBOFirebaseImpl;
import es.formulastudent.app.mvp.data.business.briefing.BriefingBO;
import es.formulastudent.app.mvp.data.business.briefing.impl.BriefingBOFirebaseImpl;
import es.formulastudent.app.mvp.data.business.dynamicevent.DynamicEventBO;
import es.formulastudent.app.mvp.data.business.dynamicevent.impl.DynamicEventBOFirebaseImpl;
import es.formulastudent.app.mvp.data.business.egress.EgressBO;
import es.formulastudent.app.mvp.data.business.egress.impl.EgressBOFirebaseImpl;
import es.formulastudent.app.mvp.data.business.racecontrol.RaceControlBO;
import es.formulastudent.app.mvp.data.business.racecontrol.impl.RaceControlBOFirebaseImpl;
import es.formulastudent.app.mvp.data.business.statistics.StatisticsBO;
import es.formulastudent.app.mvp.data.business.statistics.impl.StatisticsBOImpl;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.business.team.impl.TeamBOFirebaseImpl;
import es.formulastudent.app.mvp.data.business.user.UserBO;
import es.formulastudent.app.mvp.data.business.user.impl.UserBOFirebaseImpl;
import es.formulastudent.app.mvp.data.business.userrole.UserRoleBO;
import es.formulastudent.app.mvp.data.business.userrole.impl.UserRoleBOFirebaseImpl;

@Module(includes = {FirebaseModule.class, ContextModule.class})
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


    /**
     * Provide auth business
     * @param firebaseAuth
     * @return
     */
    @Provides
    public AuthBO provideAuthBO(FirebaseAuth firebaseAuth) {
        return new AuthBOFirebaseImpl(firebaseAuth);
    }


    /**
     * Provide Dynamic event business
     * @param firebaseFirestore
     * @return
     */
    @Provides
    public DynamicEventBO provideDynamicEventBO(FirebaseFirestore firebaseFirestore, FirebaseAuth firebaseAuth) {
        return new DynamicEventBOFirebaseImpl(firebaseFirestore, firebaseAuth);
    }

    /**
     * Provide Egress business
     * @param firebaseFirestore
     * @return
     */
    @Provides
    public EgressBO provideEgressBO(FirebaseFirestore firebaseFirestore) {
        return new EgressBOFirebaseImpl(firebaseFirestore);
    }

    /**
     * Provide Statistics business
     * @return
     */
    @Provides
    public StatisticsBO provideStatisticsBO(DynamicEventBO dynamicEventBO, UserBO userBO, Context context) {
        return new StatisticsBOImpl(dynamicEventBO, userBO, context);
    }

    /**
     * Provide Race Control Endurance business
     * @return
     */
    @Provides
    public RaceControlBO provideRaceControlBO(FirebaseFirestore firebaseFirestore, TeamBO teamBO) {
        return new RaceControlBOFirebaseImpl(firebaseFirestore, teamBO);
    }
}
