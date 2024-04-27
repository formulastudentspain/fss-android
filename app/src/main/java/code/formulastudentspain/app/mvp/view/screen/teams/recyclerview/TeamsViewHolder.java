package code.formulastudentspain.app.mvp.view.screen.teams.recyclerview;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;

import code.formulastudentspain.app.R;
import code.formulastudentspain.app.mvp.view.screen.general.actionlisteners.RecyclerViewClickListener;
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

    //Comment indicators
    FontTextView psCommentIndicator;
    FontTextView aiCommentIndicator;
    FontTextView eiCommentIndicator;
    FontTextView miCommentIndicator;
    FontTextView tttCommentIndicator;
    FontTextView ntCommentIndicator;
    FontTextView rtCommentIndicator;
    FontTextView btCommentIndicator;

    //Test containers
    LinearLayout psContainer;
    LinearLayout aiContainer;
    LinearLayout eiContainer;
    LinearLayout miContainer;
    LinearLayout tttContainer;
    LinearLayout ntContainer;
    LinearLayout rtContainer;
    LinearLayout btContainer;

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

        //Comment indicator
        psCommentIndicator = itemView.findViewById(R.id.PS_comment_indicator);
        aiCommentIndicator = itemView.findViewById(R.id.AI_comment_indicator);
        eiCommentIndicator = itemView.findViewById(R.id.EI_comment_indicator);
        miCommentIndicator = itemView.findViewById(R.id.MI_comment_indicator);
        tttCommentIndicator = itemView.findViewById(R.id.TTT_comment_indicator);
        ntCommentIndicator = itemView.findViewById(R.id.NT_comment_indicator);
        rtCommentIndicator = itemView.findViewById(R.id.RT_comment_indicator);
        btCommentIndicator = itemView.findViewById(R.id.BT_comment_indicator);

        //Test containers
        psContainer = itemView.findViewById(R.id.ps_container);
        aiContainer = itemView.findViewById(R.id.ai_container);
        eiContainer = itemView.findViewById(R.id.ei_container);
        miContainer = itemView.findViewById(R.id.mi_container);
        tttContainer = itemView.findViewById(R.id.ttt_container);
        ntContainer = itemView.findViewById(R.id.nt_container);
        rtContainer = itemView.findViewById(R.id.rt_container);
        btContainer = itemView.findViewById(R.id.bt_container);
    }

    @Override
    public void onClick(View view) {
        clickListener.recyclerViewListClicked(view, this.getLayoutPosition());
    }
}
