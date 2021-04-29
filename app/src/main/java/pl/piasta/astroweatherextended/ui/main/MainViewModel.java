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

import pl.piasta.astroweatherextended.model.CurrentWeatherDataResponse;
import pl.piasta.astroweatherextended.model.DailyForecastResponse;
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

    private MutableLiveData<CurrentWeatherDataResponse> mCurrentWeatherData;
    private MutableLiveData<DailyForecastResponse> mDailyForecastData;

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

    public MutableLiveData<CurrentWeatherDataResponse> getCurrentWeatherData() {
        if (mCurrentWeatherData == null) {
            mCurrentWeatherData = new MutableLiveData<>();
        }
        return mCurrentWeatherData;
    }

    public MutableLiveData<DailyForecastResponse> getDailyForecastData() {
        if (mDailyForecastData == null) {
            mDailyForecastData = new MutableLiveData<>();
        }
        return mDailyForecastData;
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
        mSingleExecutor.execute(() -> updateData());
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
