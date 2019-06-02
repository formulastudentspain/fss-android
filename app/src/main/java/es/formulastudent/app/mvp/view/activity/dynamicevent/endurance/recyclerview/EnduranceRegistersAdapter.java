package es.formulastudent.app.mvp.view.activity.dynamicevent.endurance.recyclerview;

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
import es.formulastudent.app.mvp.data.model.Car;
import es.formulastudent.app.mvp.data.model.EnduranceRegister;


public class EnduranceRegistersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<EnduranceRegister> enduranceRegisterList;
    private Context context;
    private LayoutInflater mLayoutInflater;


    public EnduranceRegistersAdapter(List<EnduranceRegister> enduranceRegisterList, Context context) {
        this.enduranceRegisterList = enduranceRegisterList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mLayoutInflater = LayoutInflater.from(context);

        View view;

        view = mLayoutInflater.inflate(R.layout.activity_endurance_list_item, parent, false);
        return new EnduranceRegistersViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        EnduranceRegister register = enduranceRegisterList.get(position);

        DateFormat sdf = new SimpleDateFormat("EEE, dd MMM 'at' HH:mm", Locale.US);

        EnduranceRegistersViewHolder enduranceRegistersViewHolder = (EnduranceRegistersViewHolder)holder;
        enduranceRegistersViewHolder.userName.setText(register.getUser());
        enduranceRegistersViewHolder.userTeam.setText(register.getTeam());
        enduranceRegistersViewHolder.registerDate.setText(sdf.format(register.getDate()));

        Picasso.get().load(register.getUserImage()).into(enduranceRegistersViewHolder.profileImage);

        enduranceRegistersViewHolder.carNumber.setText(register.getCarNumber().toString());

        switch(register.getCarType()) {
            case Car.CAR_TYPE_COMBUSTION:
                enduranceRegistersViewHolder.carTypeIcon.setImageResource(R.drawable.ic_combustion);
                break;
            case Car.CAR_TYPE_ELECTRIC:
                enduranceRegistersViewHolder.carTypeIcon.setImageResource(R.drawable.ic_electric_icon);
                break;
            case Car.CAR_TYPE_AUTONOMOUS_COMBUSTION:
                enduranceRegistersViewHolder.carTypeIcon.setImageResource(R.drawable.ic_steering_wheel);
                break;
            case Car.CAR_TYPE_AUTONOMOUS_ELECTRIC:
                enduranceRegistersViewHolder.carTypeIcon.setImageResource(R.drawable.ic_steering_wheel);
                break;
            default:
                // code block
        }

    }

    @Override
    public int getItemCount() {
        return enduranceRegisterList.size();
    }

}


