package es.formulastudent.app.mvp.view.screen.conecontrol.recyclerview;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.view.screen.general.actionlisteners.RecyclerViewClickListener;

public class ConeControlViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    ImageView offCourseIcon;
    TextView offCourseCurrentCount;
    ProgressBar offCourseLoading;
    MaterialButton offCourseAdd;
    MaterialButton offCourseMinus;
    TextView offCourseCount;
    ImageView smallOffCourseIcon;

    ImageView coneIcon;
    TextView coneCurrentCount;
    ProgressBar coneLoading;
    MaterialButton coneAdd;
    MaterialButton coneMinus;
    TextView coneCount;
    ImageView smallConeIcon;

    TextView carNumber;
    Button saveButton;
    Button saveButtonDisabled;

    RecyclerViewClickListener clickListener;
    

    public ConeControlViewHolder(View itemView, RecyclerViewClickListener clickListener, int eventType) {
        super(itemView);

        this.clickListener = clickListener;

        offCourseIcon = itemView.findViewById(R.id.off_course_icon);
        offCourseCurrentCount = itemView.findViewById(R.id.off_course_current_count);
        offCourseLoading = itemView.findViewById(R.id.off_course_loading);
        offCourseAdd = itemView.findViewById(R.id.add_off_course);
        offCourseAdd.setOnClickListener(this);
        offCourseMinus = itemView.findViewById(R.id.minus_off_course);
        offCourseMinus.setOnClickListener(this);
        offCourseCount = itemView.findViewById(R.id.off_course_number);
        smallOffCourseIcon = itemView.findViewById(R.id.small_icon_off_course);

        coneIcon = itemView.findViewById(R.id.cone_icon);
        coneCurrentCount = itemView.findViewById(R.id.cone_current_count);
        coneLoading = itemView.findViewById(R.id.cone_loading);
        coneAdd = itemView.findViewById(R.id.add_cone);
        coneAdd.setOnClickListener(this);
        coneMinus = itemView.findViewById(R.id.minus_cone);
        coneMinus.setOnClickListener(this);
        coneCount = itemView.findViewById(R.id.cone_number);
        smallConeIcon = itemView.findViewById(R.id.small_icon_cone);

        saveButton = itemView.findViewById(R.id.save_button);
        saveButton.setOnClickListener(this);
        saveButtonDisabled = itemView.findViewById(R.id.save_button_disabled);
        carNumber = itemView.findViewById(R.id.carNumber);
    }

    @Override
    public void onClick(View view) {
        clickListener.recyclerViewListClicked(view, this.getLayoutPosition());
    }

}
