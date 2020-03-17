package es.formulastudent.app.mvp.view.activity.ticketcreator.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import java.util.List;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.data.model.Ticket;
import es.formulastudent.app.mvp.data.model.TicketArea;
import es.formulastudent.app.mvp.data.model.TicketStatus;
import es.formulastudent.app.mvp.view.activity.ticketcreator.TicketCreatorPresenter;

public class CreateTicketDialog extends DialogFragment implements View.OnClickListener {

    private AlertDialog dialog;

    //View elements
    private LinearLayout psContainer, aiContainer, eiContainer, miContainer, tttContainer,
        ntContainer, rtContainer, btContainer;
    private TextView psText, aiText, eiText, miText, tttText, ntText, rtText, btText;
    private TextView psComment, aiComment, eiComment, miComment, tttComment, ntComment, rtComment, btComment;

    //Data
    private Team team;
    private List<Ticket> createdTickets;
    private TicketArea selectedArea;

    //Presenter
    private TicketCreatorPresenter presenter;

    public CreateTicketDialog() {}

    public static CreateTicketDialog newInstance(TicketCreatorPresenter presenter, Team team, List<Ticket> createdTickets) {
        CreateTicketDialog frag = new CreateTicketDialog();
        frag.setPresenter(presenter);
        frag.setTeam(team);
        frag.setCreatedTickets(createdTickets);
        return frag;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.dialog_create_ticket, null);
        initializeElements(rootView);
        initializeValues();

        builder.setView(rootView)
                    .setTitle(R.string.dynamic_event_filtering_dialog_title)
                    .setPositiveButton(R.string.dynamic_event_filtering_dialog_filter_button,null)
                    .setNegativeButton(R.string.dynamic_event_filtering_dialog_cancel_button, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            CreateTicketDialog.this.getDialog().cancel();
                        }
                    });

        dialog = builder.create();
        return dialog;
    }

    private void initializeValues() {

        //Check already created tickets
        for(Ticket ticket: createdTickets){
            if(TicketArea.PRE_SCRUTINEERING.equals(ticket.getArea()) 
                && TicketStatus.WAITING.equals(ticket.getStatus())){
                psContainer.setEnabled(false);
                psText.setTextColor(getResources().getColor(R.color.md_grey_300));
                psComment.setText(R.string.ticket_activity_already_created);
                psComment.setVisibility(View.VISIBLE);
                
            }else if(TicketArea.ACCUMULATION_INSPECTION.equals(ticket.getArea())
                && TicketStatus.DELETED.equals(ticket.getStatus())){
                aiContainer.setEnabled(false);
                aiText.setTextColor(getResources().getColor(R.color.md_grey_300));
                aiComment.setText(R.string.ticket_activity_already_created);
                aiComment.setVisibility(View.VISIBLE);
                
            }else if(TicketArea.ELECTRICAL_INSPECTION.equals(ticket.getArea())
                && TicketStatus.WAITING.equals(ticket.getStatus())){
                eiContainer.setEnabled(false);
                eiText.setTextColor(getResources().getColor(R.color.md_grey_300));
                eiComment.setText(R.string.ticket_activity_already_created);
                eiComment.setVisibility(View.VISIBLE);
                
            }else if(TicketArea.MECHANICAL_INSPECTION.equals(ticket.getArea())
                && TicketStatus.WAITING.equals(ticket.getStatus())){
                miContainer.setEnabled(false);
                miText.setTextColor(getResources().getColor(R.color.md_grey_300));
                miComment.setText(R.string.ticket_activity_already_created);
                miComment.setVisibility(View.VISIBLE);
                
            }else if(TicketArea.TILT_TABLE_TEST.equals(ticket.getArea())
                && TicketStatus.WAITING.equals(ticket.getStatus())){
                tttContainer.setEnabled(false);
                tttText.setTextColor(getResources().getColor(R.color.md_grey_300));
                tttComment.setText(R.string.ticket_activity_already_created);
                tttComment.setVisibility(View.VISIBLE);
                
            }else if(TicketArea.BRAKE_TEST.equals(ticket.getArea())
                && TicketStatus.WAITING.equals(ticket.getStatus())){
                btContainer.setEnabled(false);
                btText.setTextColor(getResources().getColor(R.color.md_grey_300));
                btComment.setText(R.string.ticket_activity_already_created);
                btComment.setVisibility(View.VISIBLE);
                
            }else if(TicketArea.RAIN_TEST.equals(ticket.getArea())
                && TicketStatus.WAITING.equals(ticket.getStatus())){
                rtContainer.setEnabled(false);
                rtText.setTextColor(getResources().getColor(R.color.md_grey_300));
                rtComment.setText(R.string.ticket_activity_already_created);
                rtComment.setVisibility(View.VISIBLE);
                
            }else if(TicketArea.NOISE_TEST.equals(ticket.getArea())
                && TicketStatus.WAITING.equals(ticket.getStatus())){
                ntContainer.setEnabled(false);
                ntText.setTextColor(getResources().getColor(R.color.md_grey_300));
                ntComment.setText(R.string.ticket_activity_already_created);
                ntComment.setVisibility(View.VISIBLE);
            }
        }
        
        //Check car type
        List<TicketArea> areasByCarType = TicketArea.getTestsByCarType(team.getCar().getType());
        for(TicketArea ticketArea: areasByCarType){
            if(TicketArea.PRE_SCRUTINEERING.equals(ticketArea)){
                psContainer.setVisibility(View.VISIBLE);

            }else if(TicketArea.ACCUMULATION_INSPECTION.equals(ticketArea)){
                aiContainer.setVisibility(View.VISIBLE);

            }else if(TicketArea.ELECTRICAL_INSPECTION.equals(ticketArea)){
                eiContainer.setVisibility(View.VISIBLE);

            }else if(TicketArea.MECHANICAL_INSPECTION.equals(ticketArea)){
                miContainer.setVisibility(View.VISIBLE);

            }else if(TicketArea.TILT_TABLE_TEST.equals(ticketArea)){
                tttContainer.setVisibility(View.VISIBLE);

            }else if(TicketArea.BRAKE_TEST.equals(ticketArea)){
                btContainer.setVisibility(View.VISIBLE);

            }else if(TicketArea.RAIN_TEST.equals(ticketArea)){
                rtContainer.setVisibility(View.VISIBLE);

            }else if(TicketArea.NOISE_TEST.equals(ticketArea)){
                ntContainer.setVisibility(View.VISIBLE);
            }
        }


        //Check passed tests
        if(team.getScrutineeringPS()){
            psContainer.setEnabled(false);
            psText.setTextColor(getResources().getColor(R.color.md_grey_300));
            psComment.setText(R.string.ticket_activity_already_passed);
            psComment.setVisibility(View.VISIBLE);
        }
        if(team.getScrutineeringAI()){
            aiContainer.setEnabled(false);
            aiText.setTextColor(getResources().getColor(R.color.md_grey_300));
            aiComment.setText(R.string.ticket_activity_already_passed);
            aiComment.setVisibility(View.VISIBLE);
        }
        if(team.getScrutineeringEI()){
            eiContainer.setEnabled(false);
            eiText.setTextColor(getResources().getColor(R.color.md_grey_300));
            eiComment.setText(R.string.ticket_activity_already_passed);
            eiComment.setVisibility(View.VISIBLE);
        }
        if(team.getScrutineeringMI()){
            miContainer.setEnabled(false);
            miText.setTextColor(getResources().getColor(R.color.md_grey_300));
            miComment.setText(R.string.ticket_activity_already_passed);
            miComment.setVisibility(View.VISIBLE);
        }
        if(team.getScrutineeringTTT()){
            tttContainer.setEnabled(false);
            tttText.setTextColor(getResources().getColor(R.color.md_grey_300));
            tttComment.setText(R.string.ticket_activity_already_passed);
            tttComment.setVisibility(View.VISIBLE);
        }
        if(team.getScrutineeringNT()){
            ntContainer.setEnabled(false);
            ntText.setTextColor(getResources().getColor(R.color.md_grey_300));
            ntComment.setText(R.string.ticket_activity_already_passed);
            ntComment.setVisibility(View.VISIBLE);
        }
        if(team.getScrutineeringRT()){
            rtContainer.setEnabled(false);
            rtText.setTextColor(getResources().getColor(R.color.md_grey_300));
            rtComment.setText(R.string.ticket_activity_already_passed);
            rtComment.setVisibility(View.VISIBLE);
        }
        if(team.getScrutineeringBT()){
            btContainer.setEnabled(false);
            btText.setTextColor(getResources().getColor(R.color.md_grey_300));
            btComment.setText(R.string.ticket_activity_already_passed);
            btComment.setVisibility(View.VISIBLE);
        }


    }


    private void disableArea(){

    }

    private void initializeElements(View rootView){
        psContainer = rootView.findViewById(R.id.ps_container);
        psContainer.setOnClickListener(this);
        psText = rootView.findViewById(R.id.ps_text);
        psComment = rootView.findViewById(R.id.ps_comment);

        aiContainer = rootView.findViewById(R.id.ai_container);
        aiContainer.setOnClickListener(this);
        aiText = rootView.findViewById(R.id.ai_text);
        aiComment = rootView.findViewById(R.id.ai_comment);

        eiContainer = rootView.findViewById(R.id.ei_container);
        eiContainer.setOnClickListener(this);
        eiText = rootView.findViewById(R.id.ei_text);
        eiComment = rootView.findViewById(R.id.ei_comment);

        miContainer = rootView.findViewById(R.id.mi_container);
        miContainer.setOnClickListener(this);
        miText = rootView.findViewById(R.id.mi_text);
        miComment = rootView.findViewById(R.id.mi_comment);

        tttContainer = rootView.findViewById(R.id.ttt_container);
        tttContainer.setOnClickListener(this);
        tttText = rootView.findViewById(R.id.ttt_text);
        tttComment = rootView.findViewById(R.id.ttt_comment);

        ntContainer = rootView.findViewById(R.id.nt_container);
        ntContainer.setOnClickListener(this);
        ntText = rootView.findViewById(R.id.nt_text);
        ntComment = rootView.findViewById(R.id.nt_comment);

        rtContainer = rootView.findViewById(R.id.rt_container);
        rtContainer.setOnClickListener(this);
        rtText = rootView.findViewById(R.id.rt_text);
        rtComment = rootView.findViewById(R.id.rt_comment);

        btContainer = rootView.findViewById(R.id.bt_container);
        btContainer.setOnClickListener(this);
        btText = rootView.findViewById(R.id.bt_text);
        btComment = rootView.findViewById(R.id.bt_comment);
    }


    @Override
    public void onStart(){
        super.onStart();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    @Override
    public void onClick(View view) {
        selectArea(view.getId());
    }

    private void selectArea(int viewId){

        psContainer.setSelected(false);
        aiContainer.setSelected(false);
        eiContainer.setSelected(false);
        miContainer.setSelected(false);
        tttContainer.setSelected(false);
        ntContainer.setSelected(false);
        rtContainer.setSelected(false);
        btContainer.setSelected(false);

        if(viewId == R.id.ps_container){
            psContainer.setSelected(true);
            selectedArea = TicketArea.PRE_SCRUTINEERING;

        }else if(viewId == R.id.ai_container){
            aiContainer.setSelected(true);
            selectedArea = TicketArea.ACCUMULATION_INSPECTION;

        }else if(viewId == R.id.ei_container){
            eiContainer.setSelected(true);
            selectedArea = TicketArea.ELECTRICAL_INSPECTION;

        }else if(viewId == R.id.mi_container){
            miContainer.setSelected(true);
            selectedArea = TicketArea.MECHANICAL_INSPECTION;

        }else if(viewId == R.id.ttt_container){
            tttContainer.setSelected(true);
            selectedArea = TicketArea.TILT_TABLE_TEST;

        }else if(viewId == R.id.nt_container){
            ntContainer.setSelected(true);
            selectedArea = TicketArea.NOISE_TEST;

        }else if(viewId == R.id.rt_container){
            rtContainer.setSelected(true);
            selectedArea = TicketArea.RAIN_TEST;

        }else if(viewId == R.id.bt_container){
            btContainer.setSelected(true);
            selectedArea = TicketArea.BRAKE_TEST;
        }
    }

    public void setPresenter(TicketCreatorPresenter presenter) {
        this.presenter = presenter;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setCreatedTickets(List<Ticket> createdTickets) {
        this.createdTickets = createdTickets;
    }
}