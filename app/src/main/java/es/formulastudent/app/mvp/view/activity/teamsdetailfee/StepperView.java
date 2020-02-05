package es.formulastudent.app.mvp.view.activity.teamsdetailfee;


import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import es.formulastudent.app.R;

public class StepperView extends LinearLayout {

    public StepperView( Context context ) {
        super( context );
        initialize( context );
    }

    public StepperView( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
        initialize( context );
    }

    public StepperView(
            Context context,
            @Nullable AttributeSet attrs,
            int defStyleAttr ) {
        super( context, attrs, defStyleAttr );
        initialize( context );
    }

    private void initialize( Context context ) {
        setClipChildren( true );
        setOrientation( VERTICAL );

        LayoutInflater.from( context ).inflate( R.layout.activity_teams_fee_stepper_item, this, true );
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Log.wtf( "WTF", "Saving MainItemView state" );
        return super.onSaveInstanceState();
    }

    @Override
    protected void onRestoreInstanceState( Parcelable state ) {
        Log.wtf( "WTF", "Restoring MainItemView state" );
        super.onRestoreInstanceState( state );
    }
}