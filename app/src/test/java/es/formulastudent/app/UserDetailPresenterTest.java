package es.formulastudent.app;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;

import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import es.formulastudent.app.mvp.data.api.UserDataManager;
import es.formulastudent.app.mvp.data.model.Location;
import es.formulastudent.app.mvp.data.model.Name;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.presenter.UserDetailPresenter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class UserDetailPresenterTest {

    private UserDetailPresenter.View mMockView;
    private UserDataManager mMockUserDataManager;
    private Geocoder mMockGeocoder;
    private Context mMockContext;
    private UserDetailPresenter mToTest;
    private GoogleMap mMockGoogleMap;
    private User mMockUser;


    private static final String ANY_STRING = "ANY_STRING";

    @Before
    public void setup() {

        mMockView = mock(UserDetailPresenter.View.class);
        mMockUserDataManager = mock(UserDataManager.class);
        mMockGeocoder = mock(Geocoder.class);
        mMockContext = mock(Context.class);
        mToTest = new UserDetailPresenter(mMockView, mMockUserDataManager, mMockGeocoder, mMockContext);

        mMockGoogleMap = mock(GoogleMap.class);

        //Create current user
        mMockUser = mock(User.class);
        mMockUser.setId(1L);
    }

    @Test
    public void testDeleteUserSuccess() {
        when(mMockUserDataManager.deleteUser(anyString())).thenReturn(true);
        mToTest.setCurrentUser(mMockUser);
        mToTest.deleteUser();
        verify(mMockView).finishView();
    }

    @Test
    public void testDeleteUserFail() {
        when(mMockUserDataManager.deleteUser(anyString())).thenReturn(false);
        when(mMockContext.getString(anyInt())).thenReturn(ANY_STRING);
        mToTest.setCurrentUser(mMockUser);
        mToTest.deleteUser();
        verify(mMockView).showMessage(ANY_STRING);
    }


    @Test
    public void testGetUserByID() {
        when(mMockUserDataManager.getUserById(anyLong())).thenReturn(mMockUser);
        mToTest.getUserFromUserId(anyLong());
        assertTrue(mToTest.getCurrentUser()==mMockUser);
    }


    @Test
    @Ignore
    public void testOnMapReady() throws Exception{

        //Create fake location
        Location location = new Location();
        location.setCity("Tarragona");

        //Create fake name
        Name name = new Name();
        name.setFirst("name");

        //Setup user
        mToTest.setCurrentUser(mMockUser);

        //Create list of Addresses
        List<Address> addressList = new ArrayList<>();
        Address address = mock(Address.class);
        address.setLatitude(1L);
        address.setLongitude(1L);
        addressList.add(address);

        when(mMockGeocoder.getFromLocationName(anyString(),anyInt())).thenReturn(addressList);
        when(mMockUser.getLocation()).thenReturn(location);
        when(mMockUser.getName()).thenReturn(name);
        mToTest.onMapReady(mMockGoogleMap);

        verify(mMockGoogleMap).addMarker(any(MarkerOptions.class));

        //Unable to Mock static methods: CameraUpdateFactory from Google Maps
    }


}