package es.formulastudent.app.mvp.view.screen.conecontrolstats.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.business.conecontrol.dto.ConeControlStatsDTO;


public class ConeControlStatsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ConeControlStatsDTO> coneControlStatsDTOList;
    private Context context;
    private LayoutInflater mLayoutInflater;


    public ConeControlStatsAdapter(List<ConeControlStatsDTO> coneControlStatsDTOList, Context context) {
        this.coneControlStatsDTOList = coneControlStatsDTOList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mLayoutInflater = LayoutInflater.from(context);
        View view = mLayoutInflater.inflate(R.layout.fragment_cone_control_stats_list_item, parent, false);
        return new ConeControlStatsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ConeControlStatsDTO register = coneControlStatsDTOList.get(position);
        ConeControlStatsViewHolder coneControlStatsViewHolder = (ConeControlStatsViewHolder)holder;
        coneControlStatsViewHolder.carNumber.setText(register.getCarNumber().toString());
        coneControlStatsViewHolder.conesNumber.setText(register.getConesNumber().toString());
        coneControlStatsViewHolder.offCourseNumber.setText(register.getOffCourseNumber().toString());
    }

    @Override
    public int getItemCount() {
        return coneControlStatsDTOList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }
}


