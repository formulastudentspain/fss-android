package es.formulastudent.app.mvp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Location implements Parcelable{

    @Id(autoincrement = true) private Long id;
    private String street;
    private String city;
    private String state;
    private String postcode;

    public Location() {
    }

    protected Location(Parcel in) {
        street = in.readString();
        city = in.readString();
        state = in.readString();
        postcode = in.readString();
    }

    @Generated(hash = 290587919)
    public Location(Long id, String street, String city, String state,
            String postcode) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.state = state;
        this.postcode = postcode;
    }


    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(street);
        dest.writeString(city);
        dest.writeString(state);
        dest.writeString(postcode);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

    @Override
    public String toString() {
        return street + ", " + city + ", " + state + " Postcode: " + postcode;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
