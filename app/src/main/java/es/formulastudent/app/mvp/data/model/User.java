package es.formulastudent.app.mvp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.NotNull;


@Entity
public class User implements Parcelable {

    @Id(autoincrement = true) private Long idee;
    private String gender;
    private long nameId;

    @ToOne(joinProperty = "nameId")
    private Name name;

    private long locationId;

    @ToOne(joinProperty = "locationId")
    private Location location;

    private String email;
    private String phone;
    private String cell;
    private long pictureId;

    @ToOne(joinProperty = "pictureId")
    private Picture picture;

    private String nat;



    @Keep
    protected User(Parcel in) {
        gender = in.readString();
        name = in.readParcelable(Name.class.getClassLoader());
        location = in.readParcelable(Location.class.getClassLoader());
        email = in.readString();
        phone = in.readString();
        cell = in.readString();
        picture = in.readParcelable(Picture.class.getClassLoader());
        nat = in.readString();
    }




    @Generated(hash = 48971421)
    public User(Long idee, String gender, long nameId, long locationId, String email, String phone,
            String cell, long pictureId, String nat) {
        this.idee = idee;
        this.gender = gender;
        this.nameId = nameId;
        this.locationId = locationId;
        this.email = email;
        this.phone = phone;
        this.cell = cell;
        this.pictureId = pictureId;
        this.nat = nat;
    }




    @Generated(hash = 586692638)
    public User() {
    }


    

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(gender);
        dest.writeParcelable(name, flags);
        dest.writeParcelable(location, flags);
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeString(cell);
        dest.writeParcelable(picture, flags);
        dest.writeString(nat);
    }

    @Override
    public int describeContents() {
        return 0;
    }




    public Long getId() {
        return this.idee;
    }




    public void setId(Long id) {
        this.idee = id;
    }




    public String getGender() {
        return this.gender;
    }




    public void setGender(String gender) {
        this.gender = gender;
    }




    public long getNameId() {
        return this.nameId;
    }




    public void setNameId(long nameId) {
        this.nameId = nameId;
    }




    public long getLocationId() {
        return this.locationId;
    }




    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }




    public String getEmail() {
        return this.email;
    }




    public void setEmail(String email) {
        this.email = email;
    }




    public String getPhone() {
        return this.phone;
    }




    public void setPhone(String phone) {
        this.phone = phone;
    }




    public String getCell() {
        return this.cell;
    }




    public void setCell(String cell) {
        this.cell = cell;
    }




    public long getPictureId() {
        return this.pictureId;
    }




    public void setPictureId(long pictureId) {
        this.pictureId = pictureId;
    }




    public String getNat() {
        return this.nat;
    }




    public void setNat(String nat) {
        this.nat = nat;
    }




    /** To-one relationship, resolved on first access. */
    @Generated(hash = 831212213)
    public Name getName() {
        long __key = this.nameId;
        if (name__resolvedKey == null || !name__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            NameDao targetDao = daoSession.getNameDao();
            Name nameNew = targetDao.load(__key);
            synchronized (this) {
                name = nameNew;
                name__resolvedKey = __key;
            }
        }
        return name;
    }

    @Keep
    public Name getName(Object obj) {
        return name;
    }




    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 468406620)
    public void setName(@NotNull Name name) {
        if (name == null) {
            throw new DaoException(
                    "To-one property 'nameId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.name = name;
            nameId = name.getId();
            name__resolvedKey = nameId;
        }
    }




    /** To-one relationship, resolved on first access. */
    @Keep
    public Location getLocation(Object obj) {
        return location;
    }




    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2065453948)
    public void setLocation(@NotNull Location location) {
        if (location == null) {
            throw new DaoException(
                    "To-one property 'locationId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.location = location;
            locationId = location.getId();
            location__resolvedKey = locationId;
        }
    }




    /** To-one relationship, resolved on first access. */
    @Generated(hash = 545923159)
    public Picture getPicture() {
        long __key = this.pictureId;
        if (picture__resolvedKey == null || !picture__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PictureDao targetDao = daoSession.getPictureDao();
            Picture pictureNew = targetDao.load(__key);
            synchronized (this) {
                picture = pictureNew;
                picture__resolvedKey = __key;
            }
        }
        return picture;
    }

    @Keep
    public Picture getPicture(Object obj) {
        return picture;
    }


    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1581040998)
    public void setPicture(@NotNull Picture picture) {
        if (picture == null) {
            throw new DaoException(
                    "To-one property 'pictureId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.picture = picture;
            pictureId = picture.getId();
            picture__resolvedKey = pictureId;
        }
    }




    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }




    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }




    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }




    public Long getIdee() {
        return this.idee;
    }




    public void setIdee(Long idee) {
        this.idee = idee;
    }




    /** To-one relationship, resolved on first access. */
    @Generated(hash = 469564222)
    public Location getLocation() {
        long __key = this.locationId;
        if (location__resolvedKey == null || !location__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            LocationDao targetDao = daoSession.getLocationDao();
            Location locationNew = targetDao.load(__key);
            synchronized (this) {
                location = locationNew;
                location__resolvedKey = __key;
            }
        }
        return location;
    }




    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2059241980)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserDao() : null;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1507654846)
    private transient UserDao myDao;
    @Generated(hash = 324352602)
    private transient Long name__resolvedKey;
    @Generated(hash = 1068795426)
    private transient Long location__resolvedKey;
    @Generated(hash = 1986840853)
    private transient Long picture__resolvedKey;



}