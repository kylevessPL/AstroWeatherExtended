package pl.piasta.astroweatherextended.ui.main;

import java.util.concurrent.TimeUnit;

public enum UpdateInterval {

    MINUTES_15(15, TimeUnit.MINUTES),
    MINUTES_30(30, TimeUnit.MINUTES),
    HOURS_1(1, TimeUnit.HOURS),
    HOURS_2(2, TimeUnit.HOURS),
    HOURS_5(5, TimeUnit.HOURS),
    HOURS_12(12, TimeUnit.HOURS),
    HOURS_24(24, TimeUnit.HOURS),
    DISABLED(0, TimeUnit.SECONDS);

    private final int mInterval;
    private final TimeUnit mUnit;

    UpdateInterval(int interval, TimeUnit unit) {
        this.mInterval = interval;
        this.mUnit = unit;
    }

    public int getInterval() {
        return mInterval;
    }

    public TimeUnit getUnit() {
        return mUnit;
    }
}
