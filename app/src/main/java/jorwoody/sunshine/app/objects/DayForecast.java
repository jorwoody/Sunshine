package jorwoody.sunshine.app.objects;

import android.os.Parcel;
import android.os.Parcelable;

/* Created by: Jordan Wood - July 2014
 * Description:
 * Object representing a single day's forecast information
 */
public class DayForecast implements Parcelable {

    public static final String EXTRA_FORECAST = "forecast";
    private String mDay;        // e.g. Monday, April 20
    private double[] mTemps;    // 0 = day; 1 = min; 2 = max; 3 = night; 4 = evening; 5 = morning
    private double mPressure;   // atmospheric pressure, hPa
    private int mHumidity;      // %
    private Weather mWeather;
    private double mSpeed;      // wind speed, mps
    private int mDegree;        // wind direction, degrees (meteorological)
    private int mClouds;        // cloudiness, %
    private double mRain;       // per 3 hours, mm
    private double mSnow;       // per 3 hours, mm

    public DayForecast(String day, double[] temps, double pressure, int humidity, Weather weather, double speed, int degree, int clouds, double rain, double snow) {
        mDay = day;
        mTemps = temps;
        mPressure = pressure;
        mHumidity = humidity;
        mWeather = weather;
        mSpeed = speed;
        mDegree = degree;
        mClouds = clouds;
        mRain = rain;
        mSnow = snow;
    }

    public DayForecast(Parcel in) {
        mDay = in.readString();
        mTemps = new double[6];
        in.readDoubleArray(mTemps);
        mPressure = in.readDouble();
        mHumidity = in.readInt();
        mWeather = in.readParcelable(Weather.class.getClassLoader());
        mSpeed = in.readDouble();
        mDegree = in.readInt();
        mClouds = in.readInt();
        mRain = in.readDouble();
        mSnow = in.readDouble();
    }

    public String getDay() {
        return mDay;
    }

    public double getDayTemp() {
        return mTemps[0];
    }

    public double getMinTemp() {
        return mTemps[1];
    }

    public double getMaxTemp() {
        return mTemps[2];
    }

    public double getNightTemp() {
        return mTemps[3];
    }

    public double getEveningTemp() {
        return mTemps[4];
    }

    public double getMorningTemp() {
        return mTemps[5];
    }

    public double getPressure() {
        return mPressure;
    }

    public int getHumidity() {
        return mHumidity;
    }

    public Weather getWeather() {
        return mWeather;
    }

    public double getSpeed() {
        return mSpeed;
    }

    public int getDegree() {
        return mDegree;
    }

    public int getClouds() {
        return mClouds;
    }

    public double getRain() {
        return mRain;
    }

    public double getSnow() {
        return mSnow;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mDay);
        dest.writeDoubleArray(mTemps);
        dest.writeDouble(mPressure);
        dest.writeInt(mHumidity);
        dest.writeParcelable(mWeather, flags);
        dest.writeDouble(mSpeed);
        dest.writeInt(mDegree);
        dest.writeInt(mClouds);
        dest.writeDouble(mRain);
        dest.writeDouble(mSnow);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public Object createFromParcel(Parcel source) {
            return new DayForecast(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new DayForecast[size];
        }

    };
}
