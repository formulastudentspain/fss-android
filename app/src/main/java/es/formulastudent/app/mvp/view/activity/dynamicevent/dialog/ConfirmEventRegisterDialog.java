package es.formulastudent.app.mvp.view.activity.dynamicevent.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.squareup.picasso.Picasso;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.model.Car;
import es.formulastudent.app.mvp.data.model.EventRegister;
import es.formulastudent.app.mvp.data.model.TeamMember;
import es.formulastudent.app.mvp.view.activity.dynamicevent.DynamicEventPresenter;

public class ConfirmEventRegisterDialog extends DialogFragment{

    private AlertDialog dialog;

    private ImageView userPhoto;
    private TextView userName;
    private TextView userTeam;
    private ImageView briefingDoneIcon;

    //Car elements
    private TextView carNumber;
    private ImageView carTypeIcon;

    //Presenter
    private DynamicEventPresenter presenter;

    //Detected teamMember
    private TeamMember teamMember;
    private boolean briefingDone;
    private Car car;

    public ConfirmEventRegisterDialog() {}

    public static ConfirmEventRegisterDialog newInstance(DynamicEventPresenter presenter, TeamMember teamMember,
                                                         boolean briefingDone, Car car) {
        ConfirmEventRegisterDialog frag = new ConfirmEventRegisterDialog();
        frag.setPresenter(presenter);
        frag.setTeamMember(teamMember);
        frag.setBriefingDone(briefingDone);
        frag.setCar(car);
        return frag;
    }

    public static ConfirmEventRegisterDialog newInstance(DynamicEventPresenter presenter, EventRegister register) {
        ConfirmEventRegisterDialog frag = new ConfirmEventRegisterDialog();
        frag.setPresenter(presenter);

        //Create teamMember to show
        TeamMember teamMember = new TeamMember();
        teamMember.setPhotoUrl(register.getUserImage());
        teamMember.setName(register.getUser());
        teamMember.setID(register.getID());
        teamMember.setTeam(register.getTeam());
        teamMember.setTeamID(register.getTeamID());

        frag.setTeamMember(teamMember);
        frag.setBriefingDone(register.getBriefingDone());

        //Create car to show
        Car car = new Car();
        car.setNumber(register.getCarNumber());
        car.setType(register.getCarType());
        frag.setCar(car);

        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.dialog_dynamic_event_confirmation, null);

        // Get view components
        userName = rootView.findViewById(R.id.user_name);
        userTeam = rootView.findViewById(R.id.user_team);
        userPhoto = rootView.findViewById(R.id.user_profile_image);
        briefingDoneIcon = rootView.findViewById(R.id.briefing_done_icon);
        this.initializeCarElements(rootView);

        //Set values
        userName.setText(teamMember.getName());
        userTeam.setText(teamMember.getTeam());
        Picasso.get().load(teamMember.getPhotoUrl()).into(userPhoto);

        if(briefingDone){
            briefingDoneIcon.setImageResource(R.drawable.ic_user_registered);
        }else{
            briefingDoneIcon.setImageResource(R.drawable.ic_red_cross);
        }

        builder.setView(rootView)
                    .setTitle(R.string.acceleration_activity_dialog_confirm_register_title)
                    .setPositiveButton(R.string.acceleration_activity_dialog_confirm_button_confirm,null)
                    .setNegativeButton(R.string.acceleration_activity_dialog_confirm_button_cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ConfirmEventRegisterDialog.this.getDialog().cancel();
                        }
                    });

        dialog = builder.create();
        return dialog;
    }

    private void initializeCarElements(View rootView){

        carNumber = rootView.findViewById(R.id.carNumber);
        carTypeIcon = rootView.findViewById(R.id.carTypeIcon);

        if(car.getType().equalsIgnoreCase(Car.CAR_TYPE_COMBUSTION)){

            carNumber.setText(car.getNumber().toString());
            carTypeIcon.setImageResource(R.drawable.ic_combustion);

        }else if(car.getType().equalsIgnoreCase(Car.CAR_TYPE_ELECTRIC)){

            carNumber.setText(car.getNumber().toString());
            carTypeIcon.setImageResource(R.drawable.ic_electric_icon);

        }else if(car.getType().equalsIgnoreCase(Car.CAR_TYPE_AUTONOMOUS_COMBUSTION)
                || car.getType().equalsIgnoreCase(Car.CAR_TYPE_AUTONOMOUS_ELECTRIC)){

            carNumber.setText(car.getNumber().toString());
            carTypeIcon.setImageResource(R.drawable.ic_steering_wheel);
        }
    }


    @Override
    public void onStart(){
        super.onStart();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                presenter.createRegistry(teamMember, car.getNumber(), car.getType(), briefingDone);
                dialog.dismiss();
            }
        });
    }

    public void setPresenter(DynamicEventPresenter presenter) {
        this.presenter = presenter;
    }

    public void setTeamMember(TeamMember teamMember) {
        this.teamMember = teamMember;
    }

    public void setBriefingDone(boolean briefingDone) {
        this.briefingDone = briefingDone;
    }

    public void setCar(Car car) {
        this.car = car;
    }

}

