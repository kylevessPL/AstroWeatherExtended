package pl.piasta.astroweatherextended.ui.util;

import android.text.InputFilter;
import android.text.Spanned;

public class RangeInputFilter implements InputFilter {

    private final int mMin;
    private final int mMax;

    public RangeInputFilter(int minValue, int maxValue) {
        mMin = minValue;
        mMax = maxValue;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            String newVal = dest.toString().substring(0, dstart) + dest.toString().substring(dend);
            newVal = newVal.substring(0, dstart) + source.toString() + newVal.substring(dstart);
            if (newVal.matches("^[.\\-]{1,2}")) {
                return null;
            }
            double input = Double.parseDouble(newVal);
            if (isInRange(mMin, mMax, input)) {
                return null;
            }
        } catch (NumberFormatException ignored) {
        }
        return "";
    }

    private boolean isInRange(int a, int b, double c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }
}
