package es.formulastudent.app.mvp.view.activity.conecontrol.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Map;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.view.activity.general.actionlisteners.RecyclerViewClickListener;


public class SectorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Map<Integer,Boolean> sectorList;
    private Context context;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewClickListener clickListener;

    public SectorAdapter(Map<Integer,Boolean> sectorList, Context context, RecyclerViewClickListener clickListener) {
        this.sectorList = sectorList;
        this.clickListener = clickListener;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mLayoutInflater = LayoutInflater.from(context);
        View view;

        view = mLayoutInflater.inflate(R.layout.activity_cone_control_sectors_item, parent, false);
        return new SectorViewHolder(view, clickListener);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        SectorViewHolder coneControlViewHolder = (SectorViewHolder)holder;
        coneControlViewHolder.sectorNumber.setText(String.valueOf(position+1));

        if(sectorList.get(position+1)){
            coneControlViewHolder.container.setSelected(true);
        }else{
            coneControlViewHolder.container.setSelected(false);
        }
    }

    @Override
    public int getItemCount() {
        return sectorList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }


    /**
     * Sector view holder
     */
    public class SectorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView sectorNumber;
        RecyclerViewClickListener clickListener;
        LinearLayout container;

        public SectorViewHolder(View itemView, RecyclerViewClickListener clickListener) {
            super(itemView);

            this.clickListener = clickListener;
            sectorNumber = itemView.findViewById(R.id.sectorNumber);
            container = itemView.findViewById(R.id.sectorContainer);
            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.recyclerViewListClicked(view, this.getLayoutPosition());
        }

    }

}


