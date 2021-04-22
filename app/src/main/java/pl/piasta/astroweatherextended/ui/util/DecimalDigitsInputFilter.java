package pl.piasta.astroweatherextended.ui.util;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DecimalDigitsInputFilter implements InputFilter {

    private final Pattern mPattern;

    public DecimalDigitsInputFilter(int integerPartDigits, int fractionalPartDigits) {
        mPattern = Pattern.compile(String.format(Locale.US,
                "^[-\\.]?\\d{0,%d}(\\.\\d{0,%d})?$",
                integerPartDigits, fractionalPartDigits));
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        String replacement = source.subSequence(start, end).toString();
        String newVal = dest.subSequence(0, dstart).toString() + replacement
                + dest.subSequence(dend, dest.length()).toString();
        Matcher matcher = mPattern.matcher(newVal);
        if (matcher.matches() || newVal.matches("^[.\\-]{1,2}")) {
            return null;
        }
        if (TextUtils.isEmpty(source)) {
            return dest.subSequence(dstart, dend);
        }
        return "";
    }
}
