package es.formulastudent.app.mvp.view.screen.racecontrol.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.view.screen.general.actionlisteners.CheckboxCheckListener;

public class CreateRegisterDialogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<RaceControlTeamDTO> raceControlTeamDTOList;
    private Context context;
    private LayoutInflater mLayoutInflater;
    private CheckboxCheckListener checkListener;


    public CreateRegisterDialogAdapter(List<RaceControlTeamDTO> raceControlTeamDTOList, Context context, CheckboxCheckListener checkListener) {
        this.raceControlTeamDTOList = raceControlTeamDTOList;
        this.checkListener = checkListener;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        mLayoutInflater = LayoutInflater.from(context);
        View view;

        view = mLayoutInflater.inflate(R.layout.dialog_create_rc_register_list_item, parent, false);

        return new RaceControlCreateRegisterViewHolder(view, checkListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        RaceControlTeamDTO register = raceControlTeamDTOList.get(position);

        RaceControlCreateRegisterViewHolder viewHolder = (RaceControlCreateRegisterViewHolder)holder;
        viewHolder.checkBox.setText(register.getCarNumber().toString() + " " + register.getCarName());

        if(register.getAlreadyAdded()){
            viewHolder.checkBox.setChecked(register.getAlreadyAdded());
            viewHolder.checkBox.setEnabled(false);

        }else{
            viewHolder.checkBox.setChecked(register.getSelected()==null ? false : register.getSelected());
            viewHolder.checkBox.setEnabled(true);
        }
    }

    @Override
    public int getItemCount() {
        return raceControlTeamDTOList.size();
    }
}
