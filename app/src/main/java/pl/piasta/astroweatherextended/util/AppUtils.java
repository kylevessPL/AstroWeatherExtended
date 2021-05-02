package pl.piasta.astroweatherextended.util;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public final class AppUtils {

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

    public static Snackbar createSnackbar(final View view, final String message) {
        return Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction("DISMISS", v -> {});
    }

    private AppUtils() {
    }
}
