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

    private final MutableLiveData<String> mTime = new MutableLiveData<>();
    private final MutableLiveData<String> mLastUpdateCheck = new MutableLiveData<>();
    private final MutableLiveData<String> mSunRiseTime = new MutableLiveData<>();
    private final MutableLiveData<String> mSunRiseAzimuth = new MutableLiveData<>();
    private final MutableLiveData<String> mSunSetTime = new MutableLiveData<>();
    private final MutableLiveData<String> mSunSetAzimuth = new MutableLiveData<>();
    private final MutableLiveData<String> mSunDuskTime = new MutableLiveData<>();
    private final MutableLiveData<String> mSunDawnTime = new MutableLiveData<>();
    private final MutableLiveData<String> mMoonRiseTime = new MutableLiveData<>();
    private final MutableLiveData<String> mMoonSetTime = new MutableLiveData<>();
    private final MutableLiveData<String> mNewMoonDate = new MutableLiveData<>();
    private final MutableLiveData<String> mFullMoonDate = new MutableLiveData<>();
    private final MutableLiveData<String> mMoonPhaseValue = new MutableLiveData<>();
    private final MutableLiveData<String> mMoonLunarMonthDay = new MutableLiveData<>();
    private LiveData<CurrentWeatherDataResponse> mCurrentWeatherData = new MutableLiveData<>();
    private LiveData<DailyForecastResponse> mDailyForecastData = new MutableLiveData<>();
    private final SingleLiveEvent<String> mToastMessage = new SingleLiveEvent<>();
    private final SingleLiveEvent<String> mSnackbarMessage = new SingleLiveEvent<>();

    private ScheduledFuture<?> mUpdateTask;
    private UpdateInterval mUpdateInterval;
    private MeasurementUnit mMeasurementUnit;
    private double mLatitude;
    private double mLongtitude;

    public MainViewModel() {
        mScheduledExecutor.setRemoveOnCancelPolicy(true);
    }

    public MutableLiveData<String> getTime() {
        return mTime;
    }

    public MutableLiveData<String> getLastUpdateCheck() {
        return mLastUpdateCheck;
    }

    public MutableLiveData<String> getSunRiseTime() {
        return mSunRiseTime;
    }

    public MutableLiveData<String> getSunRiseAzimuth() {
        return mSunRiseAzimuth;
    }

    public MutableLiveData<String> getSunSetTime() {
        return mSunSetTime;
    }

    public MutableLiveData<String> getSunSetAzimuth() {
        return mSunSetAzimuth;
    }

    public MutableLiveData<String> getSunDuskTime() {
        return mSunDuskTime;
    }

    public MutableLiveData<String> getSunDawnTime() {
        return mSunDawnTime;
    }

    public MutableLiveData<String> getMoonRiseTime() {
        return mMoonRiseTime;
    }

    public MutableLiveData<String> getMoonSetTime() {
        return mMoonSetTime;
    }

    public MutableLiveData<String> getNewMoonDate() {
        return mNewMoonDate;
    }

    public MutableLiveData<String> getFullMoonDate() {
        return mFullMoonDate;
    }

    public MutableLiveData<String> getMoonPhaseValue() {
        return mMoonPhaseValue;
    }

    public MutableLiveData<String> getMoonLunarMonthDay() {
        return mMoonLunarMonthDay;
    }

    public LiveData<CurrentWeatherDataResponse> getCurrentWeatherData() {
        return mCurrentWeatherData;
    }

    public LiveData<DailyForecastResponse> getDailyForecastData() {
        return mDailyForecastData;
    }

    public SingleLiveEvent<String> getToastMessage() {
        return mToastMessage;
    }

    public SingleLiveEvent<String> getSnackbarMessage() {
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
