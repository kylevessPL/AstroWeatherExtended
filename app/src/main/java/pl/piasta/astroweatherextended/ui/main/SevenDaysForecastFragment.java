package pl.piasta.astroweatherextended.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import pl.piasta.astroweatherextended.R;
import pl.piasta.astroweatherextended.ui.base.BaseFragment;

public class SevenDaysForecastFragment extends BaseFragment {

    private static final String FRAGMENT_NAME = "7 dni";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_seven_days_forecast, container, false);
    }

    @NonNull
    @Override
    public String getFragmentName() {
        return FRAGMENT_NAME;
    }
}