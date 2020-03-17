package es.formulastudent.app.mvp.view.activity.ticketcreator.recyclerview;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import es.formulastudent.app.R;

public class TicketCreatorViewHolder extends RecyclerView.ViewHolder{

    TextView area;
    TextView myTurn;
    TextView currentTurn;
    TextView status;
    TextView created;
    TextView closed;
    

    public TicketCreatorViewHolder(View itemView) {
        super(itemView);

        area = itemView.findViewById(R.id.area);
        myTurn = itemView.findViewById(R.id.my_turn);
        currentTurn = itemView.findViewById(R.id.currentTurn);
        status = itemView.findViewById(R.id.status);
        created = itemView.findViewById(R.id.createdDate);
        closed = itemView.findViewById(R.id.closedDate);

    }
}
