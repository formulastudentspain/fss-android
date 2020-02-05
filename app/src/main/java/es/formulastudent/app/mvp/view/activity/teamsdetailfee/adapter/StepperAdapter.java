package es.formulastudent.app.mvp.view.activity.teamsdetailfee.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.liefery.android.vertical_stepper_view.VerticalStepperAdapter;

import java.util.List;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.model.FeeItem;
import es.formulastudent.app.mvp.view.activity.teamsdetailfee.StepperView;
import es.formulastudent.app.mvp.view.activity.teamsdetailfee.TeamsDetailFeePresenter;

import static com.liefery.android.vertical_stepper_view.VerticalStepperItemView.STATE_ACTIVE;
import static com.liefery.android.vertical_stepper_view.VerticalStepperItemView.STATE_COMPLETE;
import static com.liefery.android.vertical_stepper_view.VerticalStepperItemView.STATE_INACTIVE;

public class StepperAdapter extends VerticalStepperAdapter {

    List<FeeItem> feeItems;
    TeamsDetailFeePresenter presenter;

    public StepperAdapter(Context context, List<FeeItem> feeItems, TeamsDetailFeePresenter presenter) {
        super( context );
        this.feeItems = feeItems;
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public CharSequence getTitle( int position ) {
        return feeItems.get(position).getName();
    }

    @Nullable
    @Override
    public CharSequence getSummary( int position ) {
        return feeItems.get(position).getDescription();
    }

    @Override
    public boolean isEditable( int position ) {
        return false;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public FeeItem getItem( int position ) {
        return feeItems.get(position);
    }

    @NonNull
    @Override
    public View onCreateContentView(Context context, final int position ) {
        View content = new StepperView( context );

        Button actionContinue = content.findViewById( R.id.action_continue );
        actionContinue.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {

                presenter.showConfirmNextStepDialog(feeItems.get(position));

                notifyDataSetChanged();
            }
        } );

        return content;
    }


    @Override
    public int getState( int position ) {

        if(feeItems.get(position).getValue()){
            jumpTo(position+1);
            return STATE_COMPLETE;

        }else if(position == getFocus()){
            return STATE_ACTIVE;

        }else{
            return STATE_INACTIVE;
        }
    }

    @Override
    public boolean hasNext() {
        return super.getFocus() <= getCount() - 1;
    }

}