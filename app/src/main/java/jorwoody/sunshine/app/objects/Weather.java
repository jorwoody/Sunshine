package jorwoody.sunshine.app.objects;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/* Created by: Jordan Wood - July 2014
 * Description:
 * Holds the information regarding weather conditions
 */
public class Weather implements Parcelable {

    private int mId;
    private String mMain;
    private String mDescription;
    private String mIcon;
    private Bitmap mIconBmp;

    private final String OWM_ICON_URL = "http://openweathermap.org/img/w/";

    public Weather(int id, String main, String description, String icon) {
        mId = id;
        mMain = main;
        mDescription = description;
        mIcon = icon;
        mIconBmp = null;
    }

    public Weather(Parcel in) {
        mId = in.readInt();
        mMain = in.readString();
        mDescription = in.readString();
        mIcon = in.readString();
        mIconBmp = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public int getId() {
        return mId;
    }

    public String getMain() {
        return mMain;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getIconUrl() {
        return OWM_ICON_URL + mIcon + ".png";
    }

    public Bitmap getIconBmp() {
        return mIconBmp;
    }

    public void setIconBmp(Bitmap iconBmp) {
        mIconBmp = iconBmp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mMain);
        dest.writeString(mDescription);
        dest.writeString(mIcon);
        dest.writeParcelable(mIconBmp, flags);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public Object createFromParcel(Parcel source) {
            return new Weather(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new Weather[size];
        }

    };

}
