package es.formulastudent.app.mvp.view.activity.racecontrol.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.view.activity.general.actionlisteners.CheckboxCheckListener;
import es.formulastudent.app.mvp.view.activity.racecontrol.RaceControlPresenter;

public class CreateRegisterDialog extends DialogFragment implements CheckboxCheckListener {

    private AlertDialog dialog;

    //View elements
    private RecyclerView recyclerView;
    private CreateRegisterDialogAdapter adapter;


    //Data
    private List<RaceControlTeamDTO> raceControlTeamDTOList;
    private List<RaceControlTeamDTO> raceControlTeamDTOSelected = new ArrayList<>();
    private Context context;

    //Presenter
    private RaceControlPresenter presenter;

    public CreateRegisterDialog() {}

    public static CreateRegisterDialog newInstance(RaceControlPresenter presenter, List<RaceControlTeamDTO> raceControlTeamDTOList, Context context) {
        CreateRegisterDialog frag = new CreateRegisterDialog();
        frag.setRaceControlTeamDTOList(raceControlTeamDTOList);
        frag.setPresenter(presenter);
        frag.setContext(context);

        return frag;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.dialog_create_rc_register, null);
        initializeElements(rootView);

        builder.setView(rootView)
                    .setTitle(R.string.rc_dialog_add_team_title)
                    .setPositiveButton(R.string.rc_dialog_add_team_button,null)
                    .setNegativeButton(R.string.dynamic_event_filtering_dialog_cancel_button, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            CreateRegisterDialog.this.getDialog().cancel();
                        }
                    });

        dialog = builder.create();
        return dialog;
    }



    private void initializeElements(View rootView){

        recyclerView = rootView.findViewById(R.id.recyclerView);
        adapter = new CreateRegisterDialogAdapter(raceControlTeamDTOList, context, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);

    }


    @Override
    public void onStart(){
        super.onStart();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    //Set values for filtering
                    presenter.createRaceControlRegisters(raceControlTeamDTOSelected, getMaxIndex());


                    //Close dialog
                    dialog.dismiss();

            }
        });
    }

    @Override
    public void checkboxChecked(boolean checked, int position) {

        RaceControlTeamDTO rcTeam = this.raceControlTeamDTOList.get(position);
        rcTeam.setSelected(checked);

        if(checked && !rcTeam.getAlreadyAdded()){
            raceControlTeamDTOSelected.add(rcTeam);
        }else{
            raceControlTeamDTOSelected.remove(rcTeam);
        }

    }

    private Long getMaxIndex(){

        long count = 0;
        for(RaceControlTeamDTO rcTeam: raceControlTeamDTOList){
            if(rcTeam.getAlreadyAdded()){
                count++;
            }
        }
        return count;
    }


    public void setPresenter(RaceControlPresenter presenter) {
        this.presenter = presenter;
    }

    public void setRaceControlTeamDTOList(List<RaceControlTeamDTO> raceControlTeamDTOList) {
        this.raceControlTeamDTOList = raceControlTeamDTOList;
    }

    public void setContext(Context context) {
        this.context = context;
    }


}