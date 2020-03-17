package es.formulastudent.app.mvp.view.activity.ticketcreator.recyclerview;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.ViewBinderHelper;

import java.util.List;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.model.Ticket;


public class TicketCreatorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // This object helps you save/restore the open/close state of each view
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    private List<Ticket> ticketList;
    private Context context;
    private LayoutInflater mLayoutInflater;


    public TicketCreatorAdapter(List<Ticket> ticketList, Context context) {
        this.ticketList = ticketList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mLayoutInflater = LayoutInflater.from(context);
        viewBinderHelper.setOpenOnlyOne(true);
        View view;

        view = mLayoutInflater.inflate(R.layout.activity_ticket_creator_list_item, parent, false);
        return new TicketCreatorViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Ticket ticket = ticketList.get(position);
        TicketCreatorViewHolder ticketCreatorViewHolder = (TicketCreatorViewHolder)holder;

        ticketCreatorViewHolder.myTurn.setText(String.valueOf(ticket.getNumber()));
        ticketCreatorViewHolder.currentTurn.setText(String.valueOf(ticket.getCurrentTurn()));

    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    public void saveStates(Bundle outState) {
        viewBinderHelper.saveStates(outState);
    }

    public void restoreStates(Bundle inState) {
        viewBinderHelper.restoreStates(inState);
    }

}


