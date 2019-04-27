package es.formulastudent.app.mvp.view.activity.timelinedetail;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import es.formulastudent.app.R;

import es.formulastudent.app.mvp.data.model.TimelineItem;
import es.formulastudent.app.mvp.view.activity.GeneralActivity;


public class TimelineDetailActivity extends GeneralActivity {

    TimelineItem item;
    DateFormat dateFormat = new SimpleDateFormat("d MMM, K:mm a", Locale.US);

    //View components
    ImageView photo;
    TextView title;
    TextView message;
    TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_timeline_detail);
        super.onCreate(savedInstanceState);

        item = (TimelineItem) getIntent().getSerializableExtra("timelineItem");

        initViews();
    }

    private void initViews(){

        //Add toolbar title
        setToolbarTitle(getString(R.string.activity_timeline_detail_label));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Bind components
        title = findViewById(R.id.timeline_detail_title);
        message = findViewById(R.id.timeline_detail_message);
        photo = findViewById(R.id.timeline_detail_photo);
        date = findViewById(R.id.timeline_detail_date);

        //Set content to components
        title.setText(item.getTitle());
        message.setText(item.getMessage());
        date.setText(dateFormat.format(item.getDate()));
        Picasso.get().load(item.getPhotoURL()).into(photo);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
