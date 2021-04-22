package pl.piasta.astroweatherextended.ui.settings;

public enum CoordinateType {

    LATITUDE(2, 6, -90, 90),
    LONGTITUDE(3, 6, -180, 180);

    private final int mIntegerPartDigits;
    private final int mFractionalPartDigits;
    private final int mMinValue;
    private final int mMaxValue;

    CoordinateType(int integerPartDigits, int fractionalPartDigits, int minValue, int maxValue) {
        mIntegerPartDigits = integerPartDigits;
        mFractionalPartDigits = fractionalPartDigits;
        mMinValue = minValue;
        mMaxValue = maxValue;
    }

    public int getIntegerPartDigits() {
        return mIntegerPartDigits;
    }

    public int getFractionalPartDigits() {
        return mFractionalPartDigits;
    }

    public int getMinValue() {
        return mMinValue;
    }

    public int getMaxValue() {
        return mMaxValue;
    }
}
