package es.formulastudent.app.mvp.view.presenter;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.api.UserDataManager;
import es.formulastudent.app.mvp.data.model.User;


public class UserDetailPresenter implements OnMapReadyCallback {

    //Dependencies
    private View view;
    private UserDataManager userDataManager;
    private Geocoder geocoder;
    private Context context;

    private User currentUser;

    public UserDetailPresenter(UserDetailPresenter.View view, UserDataManager userDataManager, Geocoder geocoder, Context context) {
        this.view = view;
        this.userDataManager = userDataManager;
        this.geocoder = geocoder;
        this.context = context;
    }

    /**
     * Get current user from the system
     * @param userId
     */
    public void getUserFromUserId(Long userId){
        User retrievedUser =  userDataManager.getUserById(userId);
        setCurrentUser(retrievedUser);
    }


    /**
     * Call business to delete current user from the system
     */
    public void deleteUser(){
        boolean deleteSuccess = userDataManager.deleteUser(currentUser.getId().toString());

        if(deleteSuccess){
            view.finishView();
        }else{
            view.showMessage(context.getString(R.string.activity_user_detail_delete_fail));
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        List<Address> addresses = null;

        try {
            //Get coordinates for the given city
            addresses = geocoder.getFromLocationName(this.getCurrentUser().getLocation().getCity(), 1);
        } catch (IOException e) {
            e.printStackTrace();
            userLocationNotFound();
        }

        if(addresses!=null && !addresses.isEmpty()) {

            //Add marker for the retrieved location
            LatLng currentLocation = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
            googleMap.addMarker(new MarkerOptions().position(currentLocation).title(currentUser.getName().getFirst()));

            //Move map to Marker
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,15));
            googleMap.animateCamera(CameraUpdateFactory.zoomIn());
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
        }else{
            userLocationNotFound();
        }
    }

    private void userLocationNotFound(){
        view.showMessage(context.getResources().getString(R.string.activity_user_detail_location_not_found));
    }

    public User getCurrentUser(){
        return currentUser;
    }


    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }


    public interface View {

        /**
         * Show message to user
         * @param message
         */
        void showMessage(String message);

        /**
         * Finish current activity
         */
        void finishView();
    }

}
