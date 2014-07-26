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
    private String mDirection;  // polar direction
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
        mDirection = calcPolarDirection(degree);
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
        mDirection = in.readString();
        mClouds = in.readInt();
        mRain = in.readDouble();
        mSnow = in.readDouble();
    }

    /*
     * Description:
     * Converts degrees to polar direction using the following ranges...
     *
     * N = 0,        NE = 45,      E = 90,       SE = 135,       S = 180,       SW = 225,       W = 270,       NW = 315
     * N = 337 - 21, NE = 22 - 66, E = 67 - 111, SE = 112 - 156, S = 157 - 201, SW = 202 - 246, W = 247 - 291, NW = 292 - 336
     */
    public String calcPolarDirection(int degree) {
        if(degree >= 337 && degree < 22)
            return "North";
        else if(degree >= 22 && degree < 67)
            return "Northeast";
        else if(degree >= 67 && degree < 112)
            return "East";
        else if(degree >= 112 && degree < 157)
            return "Southeast";
        else if(degree >= 157 && degree < 202)
            return "South";
        else if(degree >= 202 && degree < 247)
            return "Southwest";
        else if(degree >= 247 && degree < 292)
            return "West";
        else
            return "Northwest";
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

    public String getDirection() {
        return mDirection;
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
        dest.writeString(mDirection);
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
