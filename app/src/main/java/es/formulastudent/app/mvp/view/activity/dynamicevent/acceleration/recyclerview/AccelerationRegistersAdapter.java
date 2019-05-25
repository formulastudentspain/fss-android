package es.formulastudent.app.mvp.view.activity.dynamicevent.acceleration.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.model.AccelerationRegister;
import es.formulastudent.app.mvp.data.model.Car;


public class AccelerationRegistersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<AccelerationRegister> accelerationRegisterList;
    private Context context;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewLongClickedListener longClickedListener;


    public AccelerationRegistersAdapter(List<AccelerationRegister> accelerationRegisterList, Context context, RecyclerViewLongClickedListener longClickedListener) {
        this.accelerationRegisterList = accelerationRegisterList;
        this.longClickedListener = longClickedListener;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mLayoutInflater = LayoutInflater.from(context);

        View view;

        view = mLayoutInflater.inflate(R.layout.activity_acceleration_list_item, parent, false);
        return new AccelerationRegistersViewHolder(view, longClickedListener);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        AccelerationRegister register = accelerationRegisterList.get(position);

        DateFormat sdf = new SimpleDateFormat("EEE, dd MMM 'at' HH:mm", Locale.US);

        AccelerationRegistersViewHolder accelerationRegistersViewHolder = (AccelerationRegistersViewHolder)holder;
        accelerationRegistersViewHolder.userName.setText(register.getUser());
        accelerationRegistersViewHolder.userTeam.setText(register.getTeam());
        accelerationRegistersViewHolder.registerDate.setText(sdf.format(register.getDate()));
        accelerationRegistersViewHolder.carNumber.setText(register.getCarNumber().toString());

        Picasso.get().load(register.getUserImage()).into(accelerationRegistersViewHolder.profileImage);

        switch(register.getCarType()) {
            case Car.CAR_TYPE_COMBUSTION:
                accelerationRegistersViewHolder.carTypeIcon.setImageResource(R.drawable.ic_combustion);
            break;
            case Car.CAR_TYPE_ELECTRIC:
                accelerationRegistersViewHolder.carTypeIcon.setImageResource(R.drawable.ic_electric_icon);
                break;
            case Car.CAR_TYPE_AUTONOMOUS:
                accelerationRegistersViewHolder.carTypeIcon.setImageResource(R.drawable.ic_steering_wheel);
                break;
            default:
                // code block
        }
    }

    @Override
    public int getItemCount() {
        return accelerationRegisterList.size();
    }

}


