package es.formulastudent.app.mvp.view.screen.racecontrol.dialog;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.RecyclerView;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.view.screen.general.actionlisteners.CheckboxCheckListener;

public class RaceControlCreateRegisterViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {

    CheckBox checkBox;
    CheckboxCheckListener checkListener;

    public RaceControlCreateRegisterViewHolder(View itemView, CheckboxCheckListener checkListener) {
        super(itemView);

        this.checkListener = checkListener;
        checkBox = itemView.findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
        checkListener.checkboxChecked(checked, this.getLayoutPosition());
    }
}
