package code.formulastudentspain.app.mvp.view.screen.general.spinneradapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import javax.annotation.Nullable;

import code.formulastudentspain.app.R;
import code.formulastudentspain.app.mvp.data.model.Car;

public class CarsSpinnerAdapter extends ArrayAdapter<Car> {

    private Context context;
    private List<Car> cars;
    private LayoutInflater inflater;

    public CarsSpinnerAdapter(Context context, int textViewResourceId, List<Car> cars) {
        super(context, textViewResourceId, cars);
        this.context = context;
        this.cars = cars;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount(){
        return cars.size();
    }

    @Override
    public Car getItem(int position){
        return cars.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView,
                                @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public @NonNull View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }


    private View createItemView(int position, View convertView, ViewGroup parent) {
        final View view = inflater.inflate(R.layout.activity_general_spinner, parent, false);

        Car car = cars.get(position);

        String carType = "type_undefined";
        if(Car.CAR_TYPE_AUTONOMOUS_ELECTRIC.equalsIgnoreCase(car.getType()) ||
                Car.CAR_TYPE_AUTONOMOUS_COMBUSTION.equalsIgnoreCase(car.getType())){
            carType = context.getString(R.string.app_car_type_autonomous);
        }else if(Car.CAR_TYPE_COMBUSTION.equalsIgnoreCase(car.getType())){
            carType = context.getString(R.string.app_car_type_combustion);
        }else if(Car.CAR_TYPE_ELECTRIC.equalsIgnoreCase(car.getType())){
            carType = context.getString(R.string.app_car_type_electric);
        }

        TextView carText = view.findViewById(R.id.spinner_value);

        if(car.getNumber().equals(-1L)){
            carText.setText(context.getText(R.string.app_car_type_empty));
        }else{
            carText.setText("[" + car.getNumber() + "] " + carType);
        }

        return view;
    }

}
