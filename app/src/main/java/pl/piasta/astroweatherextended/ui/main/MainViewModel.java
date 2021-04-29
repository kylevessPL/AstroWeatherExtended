package pl.piasta.astroweatherextended.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroCalculator.Location;
import com.astrocalculator.AstroCalculator.MoonInfo;
import com.astrocalculator.AstroCalculator.SunInfo;
import com.astrocalculator.AstroDateTime;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import pl.piasta.astroweatherextended.ui.base.UpdateInterval;
import pl.piasta.astroweatherextended.util.GlobalVariables;
import pl.piasta.astroweatherextended.util.SingleLiveEvent;

public class MainViewModel extends ViewModel {

    private static final char DEGREE_SYMBOL = '\u00b0';

    private final ExecutorService mSingleExecutor = Executors.newSingleThreadExecutor();
    private final ScheduledThreadPoolExecutor mScheduledExecutor = new ScheduledThreadPoolExecutor(1);

    private MutableLiveData<String> mTime;
    private MutableLiveData<String> mLastUpdateCheck;
    private MutableLiveData<String> mSunRiseTime;
    private MutableLiveData<String> mSunRiseAzimuth;
    private MutableLiveData<String> mSunSetTime;
    private MutableLiveData<String> mSunSetAzimuth;
    private MutableLiveData<String> mSunDuskTime;
    private MutableLiveData<String> mSunDawnTime;
    private MutableLiveData<String> mMoonRiseTime;
    private MutableLiveData<String> mMoonSetTime;
    private MutableLiveData<String> mNewMoonDate;
    private MutableLiveData<String> mFullMoonDate;
    private MutableLiveData<String> mMoonPhaseValue;
    private MutableLiveData<String> mMoonLunarMonthDay;
    private MutableLiveData<String> mTodayWeatherHeaderTemperature;
    private MutableLiveData<String> mTodayWeatherHeaderMain;
    private MutableLiveData<String> mTodayWeatherIcon;
    private MutableLiveData<String> mTodayWeatherDetailsDescription;
    private MutableLiveData<String> mTodayWeatherDetailsTemperature;
    private MutableLiveData<String> mTodayWeatherDetailsHumidity;
    private MutableLiveData<String> mTodayWeatherDetailsWindSpeed;
    private MutableLiveData<String> mTodayWeatherDetailsWindDirection;
    private MutableLiveData<String> mDayForecastTile1;
    private MutableLiveData<String> mDayForecastTile2;
    private MutableLiveData<String> mDayForecastTile3;
    private MutableLiveData<String> mDayForecastTile4;
    private MutableLiveData<String> mDayForecastTile5;
    private MutableLiveData<String> mDayForecastTile6;
    private MutableLiveData<String> mDayForecastIcon1;
    private MutableLiveData<String> mDayForecastIcon2;
    private MutableLiveData<String> mDayForecastIcon3;
    private MutableLiveData<String> mDayForecastIcon4;
    private MutableLiveData<String> mDayForecastIcon5;
    private MutableLiveData<String> mDayForecastIcon6;
    private MutableLiveData<String> mDayWeatherDetailsDescription1;
    private MutableLiveData<String> mDayWeatherDetailsDescription2;
    private MutableLiveData<String> mDayWeatherDetailsDescription3;
    private MutableLiveData<String> mDayWeatherDetailsDescription4;
    private MutableLiveData<String> mDayWeatherDetailsDescription5;
    private MutableLiveData<String> mDayWeatherDetailsDescription6;
    private MutableLiveData<String> mDayWeatherDetailsTemperatureDay1;
    private MutableLiveData<String> mDayWeatherDetailsTemperatureDay2;
    private MutableLiveData<String> mDayWeatherDetailsTemperatureDay3;
    private MutableLiveData<String> mDayWeatherDetailsTemperatureDay4;
    private MutableLiveData<String> mDayWeatherDetailsTemperatureDay5;
    private MutableLiveData<String> mDayWeatherDetailsTemperatureDay6;
    private MutableLiveData<String> mDayWeatherDetailsTemperatureNight1;
    private MutableLiveData<String> mDayWeatherDetailsTemperatureNight2;
    private MutableLiveData<String> mDayWeatherDetailsTemperatureNight3;
    private MutableLiveData<String> mDayWeatherDetailsTemperatureNight4;
    private MutableLiveData<String> mDayWeatherDetailsTemperatureNight5;
    private MutableLiveData<String> mDayWeatherDetailsTemperatureNight6;
    private MutableLiveData<String> mDayWeatherDetailsHumidity1;
    private MutableLiveData<String> mDayWeatherDetailsHumidity2;
    private MutableLiveData<String> mDayWeatherDetailsHumidity3;
    private MutableLiveData<String> mDayWeatherDetailsHumidity4;
    private MutableLiveData<String> mDayWeatherDetailsHumidity5;
    private MutableLiveData<String> mDayWeatherDetailsHumidity6;
    private MutableLiveData<String> mDayWeatherDetailsWindSpeed1;
    private MutableLiveData<String> mDayWeatherDetailsWindSpeed2;
    private MutableLiveData<String> mDayWeatherDetailsWindSpeed3;
    private MutableLiveData<String> mDayWeatherDetailsWindSpeed4;
    private MutableLiveData<String> mDayWeatherDetailsWindSpeed5;
    private MutableLiveData<String> mDayWeatherDetailsWindSpeed6;
    private MutableLiveData<String> mDayWeatherDetailsWindDirection1;
    private MutableLiveData<String> mDayWeatherDetailsWindDirection2;
    private MutableLiveData<String> mDayWeatherDetailsWindDirection3;
    private MutableLiveData<String> mDayWeatherDetailsWindDirection4;
    private MutableLiveData<String> mDayWeatherDetailsWindDirection5;
    private MutableLiveData<String> mDayWeatherDetailsWindDirection6;

    private SingleLiveEvent<String> mToastMessage;
    private SingleLiveEvent<String> mSnackbarMessage;

    private ScheduledFuture<?> mUpdateTask;
    private UpdateInterval mUpdateInterval;
    private String mTown;

    public MainViewModel() {
        mScheduledExecutor.setRemoveOnCancelPolicy(true);
        setCurrentTime();
    }

    public MutableLiveData<String> getTime() {
        if (mTime == null) {
            mTime = new MutableLiveData<>();
        }
        return mTime;
    }

    public MutableLiveData<String> getLastUpdateCheck() {
        if (mLastUpdateCheck == null) {
            mLastUpdateCheck = new MutableLiveData<>();
        }
        return mLastUpdateCheck;
    }

    public MutableLiveData<String> getSunRiseTime() {
        if (mSunRiseTime == null) {
            mSunRiseTime = new MutableLiveData<>();
        }
        return mSunRiseTime;
    }

    public MutableLiveData<String> getSunRiseAzimuth() {
        if (mSunRiseAzimuth == null) {
            mSunRiseAzimuth = new MutableLiveData<>();
        }
        return mSunRiseAzimuth;
    }

    public MutableLiveData<String> getSunSetTime() {
        if (mSunSetTime == null) {
            mSunSetTime = new MutableLiveData<>();
        }
        return mSunSetTime;
    }

    public MutableLiveData<String> getSunSetAzimuth() {
        if (mSunSetAzimuth == null) {
            mSunSetAzimuth = new MutableLiveData<>();
        }
        return mSunSetAzimuth;
    }

    public MutableLiveData<String> getSunDuskTime() {
        if (mSunDuskTime == null) {
            mSunDuskTime = new MutableLiveData<>();
        }
        return mSunDuskTime;
    }

    public MutableLiveData<String> getSunDawnTime() {
        if (mSunDawnTime == null) {
            mSunDawnTime = new MutableLiveData<>();
        }
        return mSunDawnTime;
    }

    public MutableLiveData<String> getMoonRiseTime() {
        if (mMoonRiseTime == null) {
            mMoonRiseTime = new MutableLiveData<>();
        }
        return mMoonRiseTime;
    }

    public MutableLiveData<String> getMoonSetTime() {
        if (mMoonSetTime == null) {
            mMoonSetTime = new MutableLiveData<>();
        }
        return mMoonSetTime;
    }

    public MutableLiveData<String> getNewMoonDate() {
        if (mNewMoonDate == null) {
            mNewMoonDate = new MutableLiveData<>();
        }
        return mNewMoonDate;
    }

    public MutableLiveData<String> getFullMoonDate() {
        if (mFullMoonDate == null) {
            mFullMoonDate = new MutableLiveData<>();
        }
        return mFullMoonDate;
    }

    public MutableLiveData<String> getMoonPhaseValue() {
        if (mMoonPhaseValue == null) {
            mMoonPhaseValue = new MutableLiveData<>();
        }
        return mMoonPhaseValue;
    }

    public MutableLiveData<String> getMoonLunarMonthDay() {
        if (mMoonLunarMonthDay == null) {
            mMoonLunarMonthDay = new MutableLiveData<>();
        }
        return mMoonLunarMonthDay;
    }

    public MutableLiveData<String> getTodayWeatherHeaderTemperature() {
        if (mTodayWeatherHeaderTemperature == null) {
            mTodayWeatherHeaderTemperature = new MutableLiveData<>();
        }
        return mTodayWeatherHeaderTemperature;
    }

    public MutableLiveData<String> getTodayWeatherHeaderMain() {
        if (mTodayWeatherHeaderMain == null) {
            mTodayWeatherHeaderMain = new MutableLiveData<>();
        }
        return mTodayWeatherHeaderMain;
    }

    public MutableLiveData<String> getTodayWeatherIcon() {
        if (mTodayWeatherIcon == null) {
            mTodayWeatherIcon = new MutableLiveData<>();
        }
        return mTodayWeatherIcon;
    }

    public MutableLiveData<String> getTodayWeatherDetailsDescription() {
        if (mTodayWeatherDetailsDescription == null) {
            mTodayWeatherDetailsDescription = new MutableLiveData<>();
        }
        return mTodayWeatherDetailsDescription;
    }

    public MutableLiveData<String> getTodayWeatherDetailsTemperature() {
        if (mTodayWeatherDetailsTemperature == null) {
            mTodayWeatherDetailsTemperature = new MutableLiveData<>();
        }
        return mTodayWeatherDetailsTemperature;
    }

    public MutableLiveData<String> getTodayWeatherDetailsHumidity() {
        if (mTodayWeatherDetailsHumidity == null) {
            mTodayWeatherDetailsHumidity = new MutableLiveData<>();
        }
        return mTodayWeatherDetailsHumidity;
    }

    public MutableLiveData<String> getTodayWeatherDetailsWindSpeed() {
        if (mTodayWeatherDetailsWindSpeed == null) {
            mTodayWeatherDetailsWindSpeed = new MutableLiveData<>();
        }
        return mTodayWeatherDetailsWindSpeed;
    }

    public MutableLiveData<String> getTodayWeatherDetailsWindDirection() {
        if (mTodayWeatherDetailsWindDirection == null) {
            mTodayWeatherDetailsWindDirection = new MutableLiveData<>();
        }
        return mTodayWeatherDetailsWindDirection;
    }

    public MutableLiveData<String> getDayForecastTile1() {
        if (mDayForecastTile1 == null) {
            mDayForecastTile1 = new MutableLiveData<>();
        }
        return mDayForecastTile1;
    }

    public MutableLiveData<String> getDayForecastTile2() {
        if (mDayForecastTile2 == null) {
            mDayForecastTile2 = new MutableLiveData<>();
        }
        return mDayForecastTile2;
    }

    public MutableLiveData<String> getDayForecastTile3() {
        if (mDayForecastTile3 == null) {
            mDayForecastTile3 = new MutableLiveData<>();
        }
        return mDayForecastTile3;
    }

    public MutableLiveData<String> getDayForecastTile4() {
        if (mDayForecastTile4 == null) {
            mDayForecastTile4 = new MutableLiveData<>();
        }
        return mDayForecastTile4;
    }

    public MutableLiveData<String> getDayForecastTile5() {
        if (mDayForecastTile5 == null) {
            mDayForecastTile5 = new MutableLiveData<>();
        }
        return mDayForecastTile5;
    }

    public MutableLiveData<String> getDayForecastTile6() {
        if (mDayForecastTile6 == null) {
            mDayForecastTile6 = new MutableLiveData<>();
        }
        return mDayForecastTile6;
    }

    public MutableLiveData<String> getDayForecastIcon1() {
        if (mDayForecastIcon1 == null) {
            mDayForecastIcon1 = new MutableLiveData<>();
        }
        return mDayForecastIcon1;
    }

    public MutableLiveData<String> getDayForecastIcon2() {
        if (mDayForecastIcon2 == null) {
            mDayForecastIcon2 = new MutableLiveData<>();
        }
        return mDayForecastIcon2;
    }

    public MutableLiveData<String> getDayForecastIcon3() {
        if (mDayForecastIcon3 == null) {
            mDayForecastIcon3 = new MutableLiveData<>();
        }
        return mDayForecastIcon3;
    }

    public MutableLiveData<String> getDayForecastIcon4() {
        if (mDayForecastIcon4 == null) {
            mDayForecastIcon4 = new MutableLiveData<>();
        }
        return mDayForecastIcon4;
    }

    public MutableLiveData<String> getDayForecastIcon5() {
        if (mDayForecastIcon5 == null) {
            mDayForecastIcon5 = new MutableLiveData<>();
        }
        return mDayForecastIcon5;
    }

    public MutableLiveData<String> getDayForecastIcon6() {
        if (mDayForecastIcon6 == null) {
            mDayForecastIcon6 = new MutableLiveData<>();
        }
        return mDayForecastIcon6;
    }

    public MutableLiveData<String> getDayWeatherDetailsDescription1() {
        if (mDayWeatherDetailsDescription1 == null) {
            mDayWeatherDetailsDescription1 = new MutableLiveData<>();
        }
        return mDayWeatherDetailsDescription1;
    }

    public MutableLiveData<String> getDayWeatherDetailsDescription2() {
        if (mDayWeatherDetailsDescription2 == null) {
            mDayWeatherDetailsDescription2 = new MutableLiveData<>();
        }
        return mDayWeatherDetailsDescription2;
    }

    public MutableLiveData<String> getDayWeatherDetailsDescription3() {
        if (mDayWeatherDetailsDescription3 == null) {
            mDayWeatherDetailsDescription3 = new MutableLiveData<>();
        }
        return mDayWeatherDetailsDescription3;
    }

    public MutableLiveData<String> getDayWeatherDetailsDescription4() {
        if (mDayWeatherDetailsDescription4 == null) {
            mDayWeatherDetailsDescription4 = new MutableLiveData<>();
        }
        return mDayWeatherDetailsDescription4;
    }

    public MutableLiveData<String> getDayWeatherDetailsDescription5() {
        if (mDayWeatherDetailsDescription5 == null) {
            mDayWeatherDetailsDescription5 = new MutableLiveData<>();
        }
        return mDayWeatherDetailsDescription5;
    }

    public MutableLiveData<String> getDayWeatherDetailsDescription6() {
        if (mDayWeatherDetailsDescription6 == null) {
            mDayWeatherDetailsDescription6 = new MutableLiveData<>();
        }
        return mDayWeatherDetailsDescription6;
    }

    public MutableLiveData<String> getDayWeatherDetailsTemperatureDay1() {
        if (mDayWeatherDetailsTemperatureDay1 == null) {
            mDayWeatherDetailsTemperatureDay1 = new MutableLiveData<>();
        }
        return mDayWeatherDetailsTemperatureDay1;
    }

    public MutableLiveData<String> getDayWeatherDetailsTemperatureDay2() {
        if (mDayWeatherDetailsTemperatureDay2 == null) {
            mDayWeatherDetailsTemperatureDay2 = new MutableLiveData<>();
        }
        return mDayWeatherDetailsTemperatureDay2;
    }

    public MutableLiveData<String> getDayWeatherDetailsTemperatureDay3() {
        if (mDayWeatherDetailsTemperatureDay3 == null) {
            mDayWeatherDetailsTemperatureDay3 = new MutableLiveData<>();
        }
        return mDayWeatherDetailsTemperatureDay3;
    }

    public MutableLiveData<String> getDayWeatherDetailsTemperatureDay4() {
        if (mDayWeatherDetailsTemperatureDay4 == null) {
            mDayWeatherDetailsTemperatureDay4 = new MutableLiveData<>();
        }
        return mDayWeatherDetailsTemperatureDay4;
    }

    public MutableLiveData<String> getDayWeatherDetailsTemperatureDay5() {
        if (mDayWeatherDetailsTemperatureDay5 == null) {
            mDayWeatherDetailsTemperatureDay5 = new MutableLiveData<>();
        }
        return mDayWeatherDetailsTemperatureDay5;
    }

    public MutableLiveData<String> getDayWeatherDetailsTemperatureDay6() {
        if (mDayWeatherDetailsTemperatureDay6 == null) {
            mDayWeatherDetailsTemperatureDay6 = new MutableLiveData<>();
        }
        return mDayWeatherDetailsTemperatureDay6;
    }

    public MutableLiveData<String> getDayWeatherDetailsTemperatureNight1() {
        if (mDayWeatherDetailsTemperatureNight1 == null) {
            mDayWeatherDetailsTemperatureNight1 = new MutableLiveData<>();
        }
        return mDayWeatherDetailsTemperatureNight1;
    }

    public MutableLiveData<String> getDayWeatherDetailsTemperatureNight2() {
        if (mDayWeatherDetailsTemperatureNight2 == null) {
            mDayWeatherDetailsTemperatureNight2 = new MutableLiveData<>();
        }
        return mDayWeatherDetailsTemperatureNight2;
    }

    public MutableLiveData<String> getDayWeatherDetailsTemperatureNight3() {
        if (mDayWeatherDetailsTemperatureNight3 == null) {
            mDayWeatherDetailsTemperatureNight3 = new MutableLiveData<>();
        }
        return mDayWeatherDetailsTemperatureNight3;
    }

    public MutableLiveData<String> getDayWeatherDetailsTemperatureNight4() {
        if (mDayWeatherDetailsTemperatureNight4 == null) {
            mDayWeatherDetailsTemperatureNight4 = new MutableLiveData<>();
        }
        return mDayWeatherDetailsTemperatureNight4;
    }

    public MutableLiveData<String> getDayWeatherDetailsTemperatureNight5() {
        if (mDayWeatherDetailsTemperatureNight5 == null) {
            mDayWeatherDetailsTemperatureNight5 = new MutableLiveData<>();
        }
        return mDayWeatherDetailsTemperatureNight5;
    }

    public MutableLiveData<String> getDayWeatherDetailsTemperatureNight6() {
        if (mDayWeatherDetailsTemperatureNight6 == null) {
            mDayWeatherDetailsTemperatureNight6 = new MutableLiveData<>();
        }
        return mDayWeatherDetailsTemperatureNight6;
    }

    public MutableLiveData<String> getDayWeatherDetailsHumidity1() {
        if (mDayWeatherDetailsHumidity1 == null) {
            mDayWeatherDetailsHumidity1 = new MutableLiveData<>();
        }
        return mDayWeatherDetailsHumidity1;
    }

    public MutableLiveData<String> getDayWeatherDetailsHumidity2() {
        if (mDayWeatherDetailsHumidity2 == null) {
            mDayWeatherDetailsHumidity2 = new MutableLiveData<>();
        }
        return mDayWeatherDetailsHumidity2;
    }

    public MutableLiveData<String> getDayWeatherDetailsHumidity3() {
        if (mDayWeatherDetailsHumidity3 == null) {
            mDayWeatherDetailsHumidity3 = new MutableLiveData<>();
        }
        return mDayWeatherDetailsHumidity3;
    }

    public MutableLiveData<String> getDayWeatherDetailsHumidity4() {
        if (mDayWeatherDetailsHumidity4 == null) {
            mDayWeatherDetailsHumidity4 = new MutableLiveData<>();
        }
        return mDayWeatherDetailsHumidity4;
    }

    public MutableLiveData<String> getDayWeatherDetailsHumidity5() {
        if (mDayWeatherDetailsHumidity5 == null) {
            mDayWeatherDetailsHumidity5 = new MutableLiveData<>();
        }
        return mDayWeatherDetailsHumidity5;
    }

    public MutableLiveData<String> getDayWeatherDetailsHumidity6() {
        if (mDayWeatherDetailsHumidity6 == null) {
            mDayWeatherDetailsHumidity6 = new MutableLiveData<>();
        }
        return mDayWeatherDetailsHumidity6;
    }

    public MutableLiveData<String> getDayWeatherDetailsWindSpeed1() {
        if (mDayWeatherDetailsWindSpeed1 == null) {
            mDayWeatherDetailsWindSpeed1 = new MutableLiveData<>();
        }
        return mDayWeatherDetailsWindSpeed1;
    }

    public MutableLiveData<String> getDayWeatherDetailsWindSpeed2() {
        if (mDayWeatherDetailsWindSpeed2 == null) {
            mDayWeatherDetailsWindSpeed2 = new MutableLiveData<>();
        }
        return mDayWeatherDetailsWindSpeed2;
    }

    public MutableLiveData<String> getDayWeatherDetailsWindSpeed3() {
        if (mDayWeatherDetailsWindSpeed3 == null) {
            mDayWeatherDetailsWindSpeed3 = new MutableLiveData<>();
        }
        return mDayWeatherDetailsWindSpeed3;
    }

    public MutableLiveData<String> getDayWeatherDetailsWindSpeed4() {
        if (mDayWeatherDetailsWindSpeed4 == null) {
            mDayWeatherDetailsWindSpeed4 = new MutableLiveData<>();
        }
        return mDayWeatherDetailsWindSpeed4;
    }

    public MutableLiveData<String> getDayWeatherDetailsWindSpeed5() {
        if (mDayWeatherDetailsWindSpeed5 == null) {
            mDayWeatherDetailsWindSpeed5 = new MutableLiveData<>();
        }
        return mDayWeatherDetailsWindSpeed5;
    }

    public MutableLiveData<String> getDayWeatherDetailsWindSpeed6() {
        if (mDayWeatherDetailsWindSpeed6 == null) {
            mDayWeatherDetailsWindSpeed6 = new MutableLiveData<>();
        }
        return mDayWeatherDetailsWindSpeed6;
    }

    public MutableLiveData<String> getDayWeatherDetailsWindDirection1() {
        if (mDayWeatherDetailsWindDirection1 == null) {
            mDayWeatherDetailsWindDirection1 = new MutableLiveData<>();
        }
        return mDayWeatherDetailsWindDirection1;
    }

    public MutableLiveData<String> getDayWeatherDetailsWindDirection2() {
        if (mDayWeatherDetailsWindDirection2 == null) {
            mDayWeatherDetailsWindDirection2 = new MutableLiveData<>();
        }
        return mDayWeatherDetailsWindDirection2;
    }

    public MutableLiveData<String> getDayWeatherDetailsWindDirection3() {
        if (mDayWeatherDetailsWindDirection3 == null) {
            mDayWeatherDetailsWindDirection3 = new MutableLiveData<>();
        }
        return mDayWeatherDetailsWindDirection3;
    }

    public MutableLiveData<String> getDayWeatherDetailsWindDirection4() {
        if (mDayWeatherDetailsWindDirection4 == null) {
            mDayWeatherDetailsWindDirection4 = new MutableLiveData<>();
        }
        return mDayWeatherDetailsWindDirection4;
    }

    public MutableLiveData<String> getDayWeatherDetailsWindDirection5() {
        if (mDayWeatherDetailsWindDirection5 == null) {
            mDayWeatherDetailsWindDirection5 = new MutableLiveData<>();
        }
        return mDayWeatherDetailsWindDirection5;
    }

    public MutableLiveData<String> getDayWeatherDetailsWindDirection6() {
        if (mDayWeatherDetailsWindDirection6 == null) {
            mDayWeatherDetailsWindDirection6 = new MutableLiveData<>();
        }
        return mDayWeatherDetailsWindDirection6;
    }

    public SingleLiveEvent<String> getToastMessage() {
        if (mToastMessage == null) {
            mToastMessage = new SingleLiveEvent<>();
        }
        return mToastMessage;
    }

    public SingleLiveEvent<String> getSnackbarMessage() {
        if (mSnackbarMessage == null) {
            mSnackbarMessage = new SingleLiveEvent<>();
        }
        return mSnackbarMessage;
    }

    public void updateClock() {
        mSingleExecutor.execute(() -> {
            String time = getCurrentTimeString();
            mTime.postValue(time);
        });
    }

    public void refreshData(Double latitude, Double longtitude) {
        if (!GlobalVariables.sIsNetworkConnected) {
            mToastMessage.setValue("No Internet connection");
            return;
        }
        mSingleExecutor.execute(() -> calculateAstro(latitude, longtitude));
    }

    public void setupDataUpdate(UpdateInterval updateInterval, int delay, String town) {
        mSingleExecutor.execute(() -> {
            if (mTown.equals(town) && updateInterval.equals(mUpdateInterval)) {
                return;
            }
            tearDownDataUpdate();
            if (!mTown.equals(town) && updateInterval.equals(UpdateInterval.DISABLED)) {
                updateData(town);
            } else if (!updateInterval.equals(UpdateInterval.DISABLED)) {
                mUpdateTask = mScheduledExecutor.scheduleWithFixedDelay(() -> {
                    if (GlobalVariables.sIsNetworkConnected) {
                        updateData(town);
                    }
                }, delay, updateInterval.getInterval(), updateInterval.getUnit());
            }
            mUpdateInterval = updateInterval;
            mTown = town;
        });
    }

    public void updateLastUpdateCheckTime() {
        mSingleExecutor.execute(this::setLastUpdateCheckTime);
    }

    private void updateData(String town) {
        //TODO pobranie json wspolrzednych z miasta
        calculateAstro(latitude, longtitude);
        setLastUpdateCheckTime();
    }

    private void tearDownDataUpdate() {
        if (mUpdateTask != null && !mUpdateTask.isCancelled()) {
            mUpdateTask.cancel(true);
            mUpdateTask = null;
        }
    }

    private void setCurrentTime() {
        String timeString = getCurrentTimeString();
        mTime.setValue(timeString);
    }

    private String getCurrentTimeString() {
        final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
        LocalTime time = LocalTime.now();
        return time.format(formatter);
    }

    private void calculateAstro(Double latitude, Double longtitude) {
        AstroDateTime dateTime = createAstroDateTime();
        Location location = new Location(latitude, longtitude);
        AstroCalculator calculator = new AstroCalculator(dateTime, location);
        setSunInfo(calculator);
        setMoonInfo(calculator);
    }

    private void setLastUpdateCheckTime() {
        final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
        LocalDateTime dateTime = LocalDateTime.now();
        mLastUpdateCheck.postValue(dateTime.format(formatter));
    }

    private void setSunInfo(AstroCalculator calculator) {
        SunInfo sunInfo = calculator.getSunInfo();
        mSunRiseAzimuth.postValue(getDoubleAsString(sunInfo.getAzimuthRise()) + DEGREE_SYMBOL);
        mSunSetAzimuth.postValue(getDoubleAsString(sunInfo.getAzimuthSet()) + DEGREE_SYMBOL);
        mSunRiseTime.postValue(getAstroDateTimeAsTimeString(sunInfo.getSunrise()));
        mSunSetTime.postValue(getAstroDateTimeAsTimeString(sunInfo.getSunset()));
        mSunDuskTime.postValue(getAstroDateTimeAsTimeString(sunInfo.getTwilightEvening()));
        mSunDawnTime.postValue(getAstroDateTimeAsTimeString(sunInfo.getTwilightMorning()));
    }

    private void setMoonInfo(AstroCalculator calculator) {
        MoonInfo moonInfo = calculator.getMoonInfo();
        mMoonRiseTime.postValue(getAstroDateTimeAsTimeString(moonInfo.getMoonrise()));
        mMoonSetTime.postValue(getAstroDateTimeAsTimeString(moonInfo.getMoonset()));
        mNewMoonDate.postValue(getAstroDateTimeAsDateString(moonInfo.getNextNewMoon()));
        mFullMoonDate.postValue(getAstroDateTimeAsDateString(moonInfo.getNextFullMoon()));
        mMoonPhaseValue.postValue(getMoonIlluminationAsPercentValue(moonInfo.getIllumination()));
        mMoonLunarMonthDay.postValue(getMoonLunarMonthDayAsString(moonInfo.getAge()));
    }

    private AstroDateTime createAstroDateTime() {
        LocalDateTime dateTime = LocalDateTime.now();
        return new AstroDateTime(dateTime.getYear(), dateTime.getMonthValue(), dateTime.getDayOfMonth(),
                dateTime.getHour(), dateTime.getMinute(), dateTime.getSecond(),
                2, true);
    }

    private String getDoubleAsString(Double value) {
        NumberFormat numberFormat = DecimalFormat.getInstance(Locale.US);
        numberFormat.setMinimumFractionDigits(6);
        return numberFormat.format(value);
    }

    private String getAstroDateTimeAsTimeString(AstroDateTime dateTime) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
        LocalTime time = LocalTime.of(dateTime.getHour(), dateTime.getMinute(), dateTime.getSecond());
        return time.format(formatter);
    }

    private String getAstroDateTimeAsDateString(AstroDateTime dateTime) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
        LocalDate date = LocalDate.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDay());
        return date.format(formatter);
    }

    private String getMoonIlluminationAsPercentValue(double value) {
        return (int) (value * 100) + "%";
    }

    private String getMoonLunarMonthDayAsString(double value) {
        final NumberFormat numberFormat = DecimalFormat.getInstance(Locale.US);
        numberFormat.setMaximumFractionDigits(2);
        return numberFormat.format(value);
    }

    public void setOfflineDataUseSnackbarMessage() {
        mSingleExecutor.execute(() ->
                mSnackbarMessage.postValue("AstroWeather uses offline data until an Internet connection is established"));
    }
}
