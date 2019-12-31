package es.formulastudent.app.mvp.view.activity.teams.recyclerview;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.view.activity.general.actionlisteners.RecyclerViewClickListener;
import info.androidhive.fontawesome.FontTextView;

public class TeamsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    ImageView countryFlag;
    TextView teamName;
    TextView carNumber;

    //Scrutineering Tests
    TextView psTest;
    TextView aiTest;
    TextView eiTest;
    TextView miTest;
    TextView tttTest;
    TextView ntTest;
    TextView rtTest;
    TextView btTest;


    //Swipe components
    SwipeRevealLayout swipeRevealLayout;
    CardView scrutineeringLayout;
    CardView feeLayout;

    //Fee states
    FontTextView transponderState1;
    FontTextView transponderState2;
    FontTextView transponderState3;
    FontTextView transponderState4;

    LinearLayout energyMeterContainer;
    FontTextView energyMeterState1;
    FontTextView energyMeterState2;
    FontTextView energyMeterState3;
    FontTextView energyMeterState4;

    RecyclerViewClickListener clickListener;

    public TeamsViewHolder(View itemView, RecyclerViewClickListener clickListener) {
        super(itemView);
        swipeRevealLayout = itemView.findViewById(R.id.swipeLayout);
        countryFlag =  itemView.findViewById(R.id.teamFlag);
        teamName =  itemView.findViewById(R.id.teamName);
        carNumber =  itemView.findViewById(R.id.teamNumber);
        this.clickListener = clickListener;

        //Swipe components
        scrutineeringLayout = itemView.findViewById(R.id.scrutineering_button);
        scrutineeringLayout.setOnClickListener(this);
        feeLayout = itemView.findViewById(R.id.fee_button);
        feeLayout.setOnClickListener(this);

        //Scrutineering tests
        psTest = itemView.findViewById(R.id.PS_TEST);
        aiTest = itemView.findViewById(R.id.AI_TEST);
        eiTest = itemView.findViewById(R.id.EI_TEST);
        miTest = itemView.findViewById(R.id.MI_TEST);
        tttTest = itemView.findViewById(R.id.TTT_TEST);
        ntTest = itemView.findViewById(R.id.NT_TEST);
        rtTest = itemView.findViewById(R.id.RT_TEST);
        btTest = itemView.findViewById(R.id.BT_TEST);

        //Fee states
        transponderState1 = itemView.findViewById(R.id.transponder_state_1);
        transponderState2 = itemView.findViewById(R.id.transponder_state_2);
        transponderState3 = itemView.findViewById(R.id.transponder_state_3);
        transponderState4 = itemView.findViewById(R.id.transponder_state_4);
        energyMeterContainer = itemView.findViewById(R.id.energy_meter_container);
        energyMeterState1 = itemView.findViewById(R.id.energy_meter_state_1);
        energyMeterState2 = itemView.findViewById(R.id.energy_meter_state_2);
        energyMeterState3 = itemView.findViewById(R.id.energy_meter_state_3);
        energyMeterState4 = itemView.findViewById(R.id.energy_meter_state_4);


    }

    @Override
    public void onClick(View view) {
        clickListener.recyclerViewListClicked(view, this.getLayoutPosition());
    }
}
