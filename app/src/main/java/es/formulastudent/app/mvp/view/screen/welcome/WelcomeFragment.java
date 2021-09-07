package es.formulastudent.app.mvp.view.screen.welcome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import es.formulastudent.app.R;

public class WelcomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).unlockDrawer(); //TODO change this
    }
}
