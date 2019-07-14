package es.formulastudent.app.mvp.view.activity.welcome;

import android.os.Bundle;

import es.formulastudent.app.FSSApp;
import es.formulastudent.app.R;
import es.formulastudent.app.di.component.AppComponent;
import es.formulastudent.app.di.component.DaggerWelcomeComponent;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.mvp.view.activity.general.GeneralActivity;


public class WelcomeActivity extends GeneralActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupComponent(FSSApp.getApp().component());
        setContentView(R.layout.activity_welcome);
        super.onCreate(savedInstanceState);

        //Add drawer
        addDrawer();

        //Add toolbar title
        setToolbarTitle("Formula Student Spain");
    }


    /**
     * Inject dependencies method
     * @param appComponent
     */
    protected void setupComponent(AppComponent appComponent) {

        DaggerWelcomeComponent.builder()
                .appComponent(appComponent)
                .contextModule(new ContextModule(this))
                .build()
                .inject(this);
    }


    @Override
    protected void onResume(){
        super.onResume();
    }


    @Override
    protected void onStart(){
        super.onStart();
    }

}
