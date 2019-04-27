package es.formulastudent.app.mvp.view.activity.timeline;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import javax.inject.Inject;

import es.formulastudent.app.FSSApp;
import es.formulastudent.app.R;
import es.formulastudent.app.di.component.AppComponent;
import es.formulastudent.app.di.component.DaggerTimelineComponent;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.TimelineModule;
import es.formulastudent.app.mvp.view.activity.GeneralActivity;
import es.formulastudent.app.mvp.view.activity.timeline.recyclerview.TimelineAdapter;


public class TimelineActivity extends GeneralActivity implements TimelinePresenter.View, View.OnClickListener{


    @Inject
    TimelinePresenter presenter;

    //View components
    private RecyclerView recyclerView;
    private TimelineAdapter timeLineAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupComponent(FSSApp.getApp().component());
        setContentView(R.layout.activity_timeline);
        super.onCreate(savedInstanceState);

        initViews();
        presenter.getTimelineItems();
    }


    /**
     * Inject dependencies method
     * @param appComponent
     */
    protected void setupComponent(AppComponent appComponent) {

        DaggerTimelineComponent.builder()
                .appComponent(appComponent)
                .contextModule(new ContextModule(this))
                .timelineModule(new TimelineModule(this, new TimelineHelper()))
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


    private void initViews(){

        //Add drawer
        addDrawer();

        //Recycler view
        recyclerView = findViewById(R.id.recyclerView);
        timeLineAdapter = new TimelineAdapter(presenter.getTimelineItemList(), this, presenter);
        recyclerView.setAdapter(timeLineAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        presenter.setRecyclerView(recyclerView);

        //Add toolbar title
        setToolbarTitle(getString(R.string.drawer_menu_common_timeline));
    }


    @Override
    public void refreshTimelineItems() {
        timeLineAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finishView() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoadingIcon() {

    }

    @Override
    public void onClick(View view) {


    }
}
