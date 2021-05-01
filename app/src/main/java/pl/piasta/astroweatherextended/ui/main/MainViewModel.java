package pl.piasta.astroweatherextended.ui.main;

import androidx.lifecycle.LiveData;
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
import pl.piasta.astroweatherextended.repository.WeatherRepository;
import pl.piasta.astroweatherextended.ui.base.MeasurementUnit;
import pl.piasta.astroweatherextended.ui.base.UpdateInterval;
import pl.piasta.astroweatherextended.util.GlobalVariables;
import pl.piasta.astroweatherextended.util.SingleLiveEvent;

public class MainViewModel extends ViewModel {

    private static final char DEGREE_SYMBOL = '\u00b0';

    private final ExecutorService mSingleExecutor = Executors.newSingleThreadExecutor();
    private final ScheduledThreadPoolExecutor mScheduledExecutor = new ScheduledThreadPoolExecutor(1);
    private final WeatherRepository mWeatherRepository = new WeatherRepository();

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
    private LiveData<CurrentWeatherDataResponse> mCurrentWeatherData;
    private LiveData<DailyForecastResponse> mDailyForecastData;
    private SingleLiveEvent<String> mToastMessage;
    private SingleLiveEvent<String> mSnackbarMessage;

    private ScheduledFuture<?> mUpdateTask;
    private UpdateInterval mUpdateInterval;
    private MeasurementUnit mMeasurementUnit;
    private double mLatitude;
    private double mLongtitude;

    public MainViewModel() {
        mScheduledExecutor.setRemoveOnCancelPolicy(true);
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

    public LiveData<CurrentWeatherDataResponse> getCurrentWeatherData() {
        if (mCurrentWeatherData == null) {
            mCurrentWeatherData = new MutableLiveData<>();
        }
        return mCurrentWeatherData;
    }

    public LiveData<DailyForecastResponse> getDailyForecastData() {
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

    public void refreshData(String town, Double latitude, Double longtitude, MeasurementUnit measurementUnit) {
        if (!GlobalVariables.sIsNetworkConnected) {
            mToastMessage.setValue("No Internet connection");
        }
        mSingleExecutor.execute(() -> updateData(town, latitude, longtitude, measurementUnit));
    }

    public void setupDataUpdate(UpdateInterval updateInterval,
                                int delay,
                                String town,
                                Double latitude, Double longtitude,
                                MeasurementUnit measurementUnit) {
        mSingleExecutor.execute(() -> {
            if (updateInterval.equals(mUpdateInterval) && measurementUnit.equals(mMeasurementUnit) &&
                    mLatitude == latitude && mLongtitude == longtitude) {
                return;
            }
            tearDownDataUpdate();
            if ((mLatitude != latitude || mLongtitude != longtitude || mMeasurementUnit != measurementUnit) &&
                    updateInterval.equals(UpdateInterval.DISABLED)
            ) {
                updateData(town, latitude, longtitude, measurementUnit);
            } else if (!updateInterval.equals(UpdateInterval.DISABLED)) {
                mUpdateTask = mScheduledExecutor.scheduleWithFixedDelay(() ->
                                updateData(town, latitude, longtitude, measurementUnit),
                        delay, updateInterval.getInterval(), updateInterval.getUnit());
            }
            mUpdateInterval = updateInterval;
            mMeasurementUnit = measurementUnit;
            mLatitude = latitude;
            mLongtitude = longtitude;
        });
    }

    private void updateData(String town, double latitude, double longtitude, MeasurementUnit measurementUnit) {
        if (!GlobalVariables.sIsNetworkConnected) {
            return;
        }
        fetchCurrentWeatherData(town, measurementUnit);
        fetchDailyForecastData(town, measurementUnit);
        calculateAstro(latitude, longtitude);
        setLastUpdateCheckTime();
    }

    private void tearDownDataUpdate() {
        if (mUpdateTask != null && !mUpdateTask.isCancelled()) {
            mUpdateTask.cancel(true);
            mUpdateTask = null;
        }
    }

    public void setCurrentTime() {
        String timeString = getCurrentTimeString();
        mTime.setValue(timeString);
    }

    private String getCurrentTimeString() {
        final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
        LocalTime time = LocalTime.now();
        return time.format(formatter);
    }

    private void fetchCurrentWeatherData(String town, MeasurementUnit measurementUnit) {
        mCurrentWeatherData =
                mWeatherRepository.getCurrentWeatherData(town, measurementUnit, GlobalVariables.API_KEY);
    }

    private void fetchDailyForecastData(String town, MeasurementUnit measurementUnit) {
        mDailyForecastData =
                mWeatherRepository.getDailyForecast(town, measurementUnit, GlobalVariables.API_KEY);
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
