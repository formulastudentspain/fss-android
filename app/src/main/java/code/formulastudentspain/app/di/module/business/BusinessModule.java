package code.formulastudentspain.app.di.module.business;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import dagger.Module;
import dagger.Provides;
import code.formulastudentspain.app.di.module.ContextModule;
import code.formulastudentspain.app.mvp.data.business.auth.AuthBO;
import code.formulastudentspain.app.mvp.data.business.auth.impl.AuthBOFirebaseImpl;
import code.formulastudentspain.app.mvp.data.business.briefing.BriefingBO;
import code.formulastudentspain.app.mvp.data.business.briefing.impl.BriefingBOFirebaseImpl;
import code.formulastudentspain.app.mvp.data.business.conecontrol.ConeControlBO;
import code.formulastudentspain.app.mvp.data.business.conecontrol.impl.ConeControlBOFirebaseImpl;
import code.formulastudentspain.app.mvp.data.business.raceaccess.RaceAccessBO;
import code.formulastudentspain.app.mvp.data.business.raceaccess.impl.RaceAccessBOFirebaseImpl;
import code.formulastudentspain.app.mvp.data.business.egress.EgressBO;
import code.formulastudentspain.app.mvp.data.business.egress.impl.EgressBOFirebaseImpl;
import code.formulastudentspain.app.mvp.data.business.imageuploader.ImageBO;
import code.formulastudentspain.app.mvp.data.business.imageuploader.impl.ImageBOFirebaseImpl;
import code.formulastudentspain.app.mvp.data.business.mailsender.MailSender;
import code.formulastudentspain.app.mvp.data.business.mailsender.MailSenderImpl;
import code.formulastudentspain.app.mvp.data.business.racecontrol.RaceControlBO;
import code.formulastudentspain.app.mvp.data.business.racecontrol.impl.RaceControlBOFirebaseImpl;
import code.formulastudentspain.app.mvp.data.business.statistics.StatisticsBO;
import code.formulastudentspain.app.mvp.data.business.statistics.impl.StatisticsBOImpl;
import code.formulastudentspain.app.mvp.data.business.team.TeamBO;
import code.formulastudentspain.app.mvp.data.business.team.impl.TeamBOFirebaseImpl;
import code.formulastudentspain.app.mvp.data.business.teammember.TeamMemberBO;
import code.formulastudentspain.app.mvp.data.business.teammember.impl.TeamMemberBOFirebaseImpl;
import code.formulastudentspain.app.mvp.data.business.user.UserBO;
import code.formulastudentspain.app.mvp.data.business.user.impl.UserBOFirebaseImpl;
import code.formulastudentspain.app.mvp.data.model.User;


@Module(includes = {FirebaseModule.class, ContextModule.class, SharedPreferencesModule.class, UtilsModule.class})
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
     * Provide TeamMember business
     * @param firebaseFirestore
     * @return
     */
    @Provides
    public TeamMemberBO provideTeamMemberBO(FirebaseFirestore firebaseFirestore) {
        return new TeamMemberBOFirebaseImpl(firebaseFirestore);
    }


    /**
     * Provide auth business
     * @param firebaseAuth
     * @return
     */
    @Provides
    public AuthBO provideAuthBO(FirebaseAuth firebaseAuth, UserBO userBO) {
        return new AuthBOFirebaseImpl(firebaseAuth, userBO);
    }


    /**
     * Provide Dynamic event business
     * @param firebaseFirestore
     * @return
     */
    @Provides
    public RaceAccessBO provideDynamicEventBO(FirebaseFirestore firebaseFirestore, FirebaseAuth firebaseAuth) {
        return new RaceAccessBOFirebaseImpl(firebaseFirestore, firebaseAuth);
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
    public StatisticsBO provideStatisticsBO(RaceAccessBO raceAccessBO, TeamMemberBO teamMemberBO, Context context) {
        return new StatisticsBOImpl(raceAccessBO, teamMemberBO, context);
    }

    /**
     * Provide Race Control Endurance business
     * @return
     */
    @Provides
    public RaceControlBO provideRaceControlBO(FirebaseFirestore firebaseFirestore, TeamBO teamBO,
                                              ConeControlBO coneControlBO) {
        return new RaceControlBOFirebaseImpl(firebaseFirestore, teamBO, coneControlBO);
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
     * Provide image uploader business
     * @param firebaseStorage
     * @return
     */
    @Provides
    public ImageBO provideImageUploaderBO(FirebaseStorage firebaseStorage) {
        return new ImageBOFirebaseImpl(firebaseStorage);
    }

    /**
     * Provide Cone control business
     * @param firebaseFirestore
     * @param context
     * @return
     */
    @Provides
    public ConeControlBO provideConeControlBO(FirebaseFirestore firebaseFirestore, Context context) {
        return new ConeControlBOFirebaseImpl(firebaseFirestore, context);
    }

    /**
     * Provide mail sender
     * @param loggedUser
     * @param context
     * @return
     */
    @Provides
    public MailSender provideMailSender(User loggedUser, Context context) {
        return new MailSenderImpl(context, loggedUser);
    }
}
