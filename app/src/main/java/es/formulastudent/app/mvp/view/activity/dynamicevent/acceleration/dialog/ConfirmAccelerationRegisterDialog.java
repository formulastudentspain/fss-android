package es.formulastudent.app.mvp.view.activity.dynamicevent.acceleration.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.model.Car;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.activity.dynamicevent.acceleration.AccelerationPresenter;

public class ConfirmAccelerationRegisterDialog extends DialogFragment implements View.OnClickListener {

    private AlertDialog dialog;

    private ImageView userPhoto;
    private TextView userName;
    private TextView userTeam;
    private ImageView briefingDoneIcon;

    //Car elements
    private LinearLayout combustionContainer;
    private LinearLayout electricContainer;
    private LinearLayout driverlessContainer;
    private TextView combustionCarNumber;
    private TextView electricCarNumber;
    private TextView driverlessCarNumber;
    private ImageView combustionCarIcon;
    private ImageView electricCarIcon;
    private ImageView driverlessCarIcon;


    //Presenter
    private AccelerationPresenter presenter;

    //Detected user
    private User user;
    private boolean briefingDone;
    private List<Car> cars;
    private Car selectedCar;

    public ConfirmAccelerationRegisterDialog() {}

    public static ConfirmAccelerationRegisterDialog newInstance(AccelerationPresenter presenter, User user,
                                                                boolean briefingDone, List<Car> cars) {
        ConfirmAccelerationRegisterDialog frag = new ConfirmAccelerationRegisterDialog();
        frag.setPresenter(presenter);
        frag.setUser(user);
        frag.setBriefingDone(briefingDone);
        frag.setCars(cars);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        DateFormat sdf = new SimpleDateFormat("EEE, dd MMM 'at' HH:mm", Locale.US);

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.dialog_acceleration_confirmation, null);

        // Get view components
        userName = rootView.findViewById(R.id.user_name);
        userTeam = rootView.findViewById(R.id.user_team);
        userPhoto = rootView.findViewById(R.id.user_profile_image);
        briefingDoneIcon = rootView.findViewById(R.id.briefing_done_icon);
        this.initializeCarElements(rootView);

        //Set values
        userName.setText(user.getName());
        userTeam.setText(user.getTeam());
        Picasso.get().load(user.getPhotoUrl()).into(userPhoto);

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
                            ConfirmAccelerationRegisterDialog.this.getDialog().cancel();
                        }
                    });

        dialog = builder.create();
        return dialog;
    }

    private void initializeCarElements(View rootView){

        //Activate elements depending of team cars
        for(Car car: cars){
            if(car.getType().equalsIgnoreCase(Car.CAR_TYPE_COMBUSTION)){

                //Instantiate elements
                combustionContainer = rootView.findViewById(R.id.combustionContainer);
                combustionCarNumber = rootView.findViewById(R.id.combustionNumber);
                combustionCarIcon = rootView.findViewById(R.id.combustionIcon);

                //Set values
                combustionCarNumber.setText(car.getNumber().toString());
                combustionCarIcon.setImageResource(R.drawable.ic_combustion);
                combustionContainer.setOnClickListener(this);
            }
            if(car.getType().equalsIgnoreCase(Car.CAR_TYPE_ELECTRIC)){

                //Instantiate elements
                electricContainer = rootView.findViewById(R.id.electricContainer);
                electricCarNumber = rootView.findViewById(R.id.electricNumber);
                electricCarIcon = rootView.findViewById(R.id.electricIcon);

                //Set values
                electricCarNumber.setText(car.getNumber().toString());
                electricCarIcon.setImageResource(R.drawable.ic_electric_icon);
                electricContainer.setOnClickListener(this);
            }
            if(car.getType().equalsIgnoreCase(Car.CAR_TYPE_AUTONOMOUS)){

                //Instantiate elements
                driverlessContainer = rootView.findViewById(R.id.driverlessContainer);
                driverlessCarNumber = rootView.findViewById(R.id.driverlessNumber);
                driverlessCarIcon = rootView.findViewById(R.id.driverlessIcon);

                //Set values
                driverlessCarNumber.setText(car.getNumber().toString());
                driverlessCarIcon.setImageResource(R.drawable.ic_steering_wheel);
                driverlessContainer.setOnClickListener(this);
            }
        }
    }


    @Override
    public void onStart(){
        super.onStart();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(selectedCar == null){
                    presenter.createMessage(getContext().getString(R.string.acceleration_activity_dialog_confirm_error_car));
                }else{
                    presenter.createRegistry(user, selectedCar.getNumber(), selectedCar.getType());
                    dialog.dismiss();
                }
            }
        });
    }

    public void setPresenter(AccelerationPresenter presenter) {
        this.presenter = presenter;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setBriefingDone(boolean briefingDone) {
        this.briefingDone = briefingDone;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.combustionContainer){

            //Set selected background
            combustionContainer.setBackgroundResource(R.drawable.dialog_confirm_register_briefing_background_selected);

            //Set others unselected
            electricContainer.setBackgroundResource(R.drawable.dialog_confirm_register_briefing_background);
            driverlessContainer.setBackgroundResource(R.drawable.dialog_confirm_register_briefing_background);

            //Look for the proper car
            for(Car car: cars){
                if(car.getType().equalsIgnoreCase(Car.CAR_TYPE_COMBUSTION)){
                    selectedCar = car;
                    break;
                }
            }
        }else if(view.getId() == R.id.electricContainer){

            //Set selected background
            electricContainer.setBackgroundResource(R.drawable.dialog_confirm_register_briefing_background_selected);

            //Set others unselected
            combustionContainer.setBackgroundResource(R.drawable.dialog_confirm_register_briefing_background);
            driverlessContainer.setBackgroundResource(R.drawable.dialog_confirm_register_briefing_background);

            //Look for the proper car
            for(Car car: cars){
                if(car.getType().equalsIgnoreCase(Car.CAR_TYPE_ELECTRIC)){
                    selectedCar = car;
                    break;
                }
            }

        }else if(view.getId() == R.id.driverlessContainer){

            //Set selected background
            driverlessContainer.setBackgroundResource(R.drawable.dialog_confirm_register_briefing_background_selected);

            //Set others unselected
            combustionContainer.setBackgroundResource(R.drawable.dialog_confirm_register_briefing_background);
            electricContainer.setBackgroundResource(R.drawable.dialog_confirm_register_briefing_background);

            //Look for the proper car
            for(Car car: cars){
                if(car.getType().equalsIgnoreCase(Car.CAR_TYPE_AUTONOMOUS)){
                    selectedCar = car;
                    break;
                }
            }
        }
    }
}

