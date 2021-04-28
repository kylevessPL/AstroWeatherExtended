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

import pl.piasta.astroweatherextended.ui.base.UpdateInterval;
import pl.piasta.astroweatherextended.util.GlobalVariables;
import pl.piasta.astroweatherextended.util.SingleLiveEvent;

public class MainViewModel extends ViewModel {

    private static final char DEGREE_SYMBOL = '\u00b0';

    private final ExecutorService mSingleExecutor;
    private final ScheduledThreadPoolExecutor mScheduledExecutor;

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

    private final SingleLiveEvent<String> mToastMessage;

    private ScheduledFuture<?> mUpdateTask;
    private UpdateInterval mUpdateInterval;
    private String mTown;

    public MainViewModel() {
        this.mSingleExecutor = Executors.newSingleThreadExecutor();
        this.mScheduledExecutor = new ScheduledThreadPoolExecutor(1);
        this.mScheduledExecutor.setRemoveOnCancelPolicy(true);
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
        this.mToastMessage = new SingleLiveEvent<>();
        setCurrentTime();
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

    public SingleLiveEvent<String> getToastMessage() {
        return mToastMessage;
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
}
