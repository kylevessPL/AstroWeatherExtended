package pl.piasta.astroweatherextended.util;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.text.Normalizer;
import java.util.regex.Pattern;

public final class AppUtils {

    public static final String DIALOG_FRAGMENT_TAG = "androidx.preference.PreferenceFragment.DIALOG";

    private static final Pattern sStripAccentsPattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");

    public static int getDrawableByName(final Context context, final String value) {
        return context.getResources().getIdentifier(
                "ic_" + value.replaceAll(".$", "n"),
                "drawable",
                context.getPackageName());
    }

    public static boolean isCoordinateValid(final String value) {
        return !(value.isEmpty() || value.matches("^[.\\-]{1,2}"));
    }

    public static Toast createToast(final Context context, final String message) {
        return Toast.makeText(context, message, Toast.LENGTH_SHORT);
    }

    public static Snackbar createSnackbar(
            final View view,
            final int length,
            final String message
    ) {
        return Snackbar.make(view, message, length)
                .setAction("DISMISS", v -> {});
    }

    public static String stripAccents(String input)
    {
        if (input == null) {
            return null;
        }
        final StringBuilder decomposed = new StringBuilder(Normalizer.normalize(input, Normalizer.Form.NFD));
        convertRemainingAccentCharacters(decomposed);
        return sStripAccentsPattern.matcher(decomposed).replaceAll("");
    }

    private static void convertRemainingAccentCharacters(final StringBuilder decomposed) {
        for (int i = 0; i < decomposed.length(); i++) {
            if (decomposed.charAt(i) == '\u0141') {
                decomposed.setCharAt(i, 'L');
            } else if (decomposed.charAt(i) == '\u00f8') {
                decomposed.setCharAt(i, 'o');
            } else if (decomposed.charAt(i) == '\u0142') {
                decomposed.setCharAt(i, 'l');
            }
        }
    }

    private AppUtils() {
    }
}
