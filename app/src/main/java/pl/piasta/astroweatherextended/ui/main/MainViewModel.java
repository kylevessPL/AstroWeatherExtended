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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class MainViewModel extends ViewModel {

    private static final char DEGREE_SYMBOL = '\u00b0';

    private final ExecutorService mSingleExecutor;
    private final ScheduledThreadPoolExecutor mScheduledExecutor;

    private final MutableLiveData<String> mDate;
    private final MutableLiveData<String> mTime;
    private final MutableLiveData<String> mLastUpdateCheck;
    private final MutableLiveData<String> mSunRiseTime;
    private final MutableLiveData<String> mSunRiseAzimuth;
    private final MutableLiveData<String> mSunSetTime;
    private final MutableLiveData<String> mSunSetAzimuth;
    private final MutableLiveData<String> mSunDuskTime;
    private final MutableLiveData<String> mSunDawnTime;
    private final MutableLiveData<String> mMoonRiseTime;
    private final MutableLiveData<String> mMoonSetTime;
    private final MutableLiveData<String> mNewMoonDate;
    private final MutableLiveData<String> mFullMoonDate;
    private final MutableLiveData<String> mMoonPhaseValue;
    private final MutableLiveData<String> mMoonLunarMonthDay;

    private ScheduledFuture<?> mUpdateTask;
    private UpdateInterval mUpdateInterval;
    private double mLatitude;
    private double mLongtitude;

    public MainViewModel() {
        this.mSingleExecutor = Executors.newSingleThreadExecutor();
        this.mScheduledExecutor = new ScheduledThreadPoolExecutor(1);
        this.mScheduledExecutor.setRemoveOnCancelPolicy(true);
        this.mDate = new MutableLiveData<>();
        this.mTime = new MutableLiveData<>();
        this.mLastUpdateCheck = new MutableLiveData<>();
        this.mSunRiseTime = new MutableLiveData<>();
        this.mSunRiseAzimuth = new MutableLiveData<>();
        this.mSunSetTime = new MutableLiveData<>();
        this.mSunSetAzimuth = new MutableLiveData<>();
        this.mSunDuskTime = new MutableLiveData<>();
        this.mSunDawnTime = new MutableLiveData<>();
        this.mMoonRiseTime = new MutableLiveData<>();
        this.mMoonSetTime = new MutableLiveData<>();
        this.mNewMoonDate = new MutableLiveData<>();
        this.mFullMoonDate = new MutableLiveData<>();
        this.mMoonPhaseValue = new MutableLiveData<>();
        this.mMoonLunarMonthDay = new MutableLiveData<>();
        setCurrentDateTime();
    }

    public LiveData<String> getDate() {
        return mDate;
    }

    public LiveData<String> getTime() {
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

    public void updateClock() {
        mSingleExecutor.execute(() -> {
            String date = getCurrentDateString();
            String time = getCurrentTimeString();
            mDate.postValue(date);
            mTime.postValue(time);
        });
    }

    public void refreshData(Double latitude, Double longtitude) {
        mSingleExecutor.execute(() -> calculateAstro(latitude, longtitude));
    }

    public void setupDataUpdate(UpdateInterval updateInterval,
                                Double latitude, Double longtitude) {
        mSingleExecutor.execute(() -> {
            if (updateInterval.equals(mUpdateInterval) &&
                    mLatitude == latitude && mLongtitude == longtitude) {
                return;
            }
            tearDownDataUpdate();
            if ((mLatitude != latitude || mLongtitude != longtitude) &&
                    updateInterval.equals(UpdateInterval.DISABLED)
            ) {
                updateData(latitude, longtitude);
            } else if (!updateInterval.equals(UpdateInterval.DISABLED)) {
                mUpdateTask = mScheduledExecutor.scheduleWithFixedDelay(() ->
                        updateData(latitude, longtitude),
                        0, updateInterval.getInterval(), updateInterval.getUnit());
            }
            mUpdateInterval = updateInterval;
            mLatitude = latitude;
            mLongtitude = longtitude;
        });
    }

    public void updateLastUpdateCheckTime() {
        mSingleExecutor.execute(this::setLastUpdateCheckTime);
    }

    private void updateData(double longtitude, double latitude) {
        calculateAstro(latitude, longtitude);
        setLastUpdateCheckTime();
    }

    private void tearDownDataUpdate() {
        if (mUpdateTask != null && !mUpdateTask.isCancelled()) {
            mUpdateTask.cancel(true);
            mUpdateTask = null;
        }
    }

    private void setCurrentDateTime() {
        String dateString = getCurrentDateString();
        String timeString = getCurrentTimeString();
        mDate.setValue(dateString);
        mTime.setValue(timeString);
    }

    private String getCurrentTimeString() {
        final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
        LocalTime time = LocalTime.now();
        return time.format(formatter);
    }

    private String getCurrentDateString() {
        final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
        LocalDate date = LocalDate.now();
        return date.format(formatter);
    }

    private void calculateAstro(Double latitude, Double longtitude) {
        AstroDateTime dateTime = createAstroDateTime();
        Location location = new Location(latitude, longtitude);
        AstroCalculator calculator = new AstroCalculator(dateTime, location);
        setSunInfo(calculator);
        setMoonInfo(calculator);
    }

    private void setLastUpdateCheckTime() {
        final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
        LocalTime time = LocalTime.now();
        mLastUpdateCheck.postValue(time.format(formatter));
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
        TemporalAccessor temporalDate = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).parse(mDate.getValue());
        TemporalAccessor temporalTime = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).parse(mTime.getValue());
        LocalDate date = LocalDate.of(
                temporalDate.get(ChronoField.YEAR),
                temporalDate.get(ChronoField.MONTH_OF_YEAR),
                temporalDate.get(ChronoField.DAY_OF_MONTH));
        LocalTime time = LocalTime.of(
                temporalTime.get(ChronoField.HOUR_OF_DAY),
                temporalTime.get(ChronoField.MINUTE_OF_HOUR),
                temporalTime.get(ChronoField.SECOND_OF_MINUTE));
        return new AstroDateTime(date.getYear(), date.getMonthValue(), date.getDayOfMonth(),
                time.getHour(), time.getMinute(), time.getSecond(),
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
}
