package es.formulastudent.app.mvp.view.activity.conecontrolstats;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.business.conecontrol.dto.ConeControlStatsDTO;
import es.formulastudent.app.mvp.view.activity.conecontrolstats.recyclerview.ConeControlStatsAdapter;


public class ConeControlStatsFragment extends Fragment {

    //Presenter
    ConeControlStatsPresenter presenter;


    //Race round
    String raceRound;
    List<ConeControlStatsDTO> list;

    //View components
    private RecyclerView recyclerView;
    private ConeControlStatsAdapter coneControlStatsAdapter;



    public ConeControlStatsFragment(ConeControlStatsPresenter presenter, String raceRound) {
        this.presenter = presenter;
        this.raceRound = raceRound;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cone_control_stats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        list = new ArrayList<>();
        coneControlStatsAdapter = new ConeControlStatsAdapter(list, getContext());
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(coneControlStatsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        presenter.retrieveRegisterList(raceRound, coneControlStatsAdapter, list);
    }
}
