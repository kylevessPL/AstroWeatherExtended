package pl.piasta.astroweatherextended.ui.main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import pl.piasta.astroweatherextended.R;
import pl.piasta.astroweatherextended.ui.base.BaseFragment;

import static android.content.Context.MODE_PRIVATE;

public class MoonFragment extends BaseFragment {

    private static final String FRAGMENT_NAME = "MOON";

    private MainViewModel mModel;
    private SharedPreferences mPreferences;

    private TextView mMoonRiseTime;
    private TextView mMoonSetTime;
    private TextView mNewMoonDate;
    private TextView mFullMoonDate;
    private TextView mMoonPhaseValue;
    private TextView mMoonLunarMonthDay;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState
    ) {
        View root = inflater.inflate(R.layout.fragment_moon, container, false);
        mMoonRiseTime = root.findViewById(R.id.time_rise);
        mMoonSetTime = root.findViewById(R.id.time_set);
        mNewMoonDate = root.findViewById(R.id.date_new_moon);
        mFullMoonDate = root.findViewById(R.id.date_full_moon);
        mMoonPhaseValue = root.findViewById(R.id.value_moon_phase);
        mMoonLunarMonthDay = root.findViewById(R.id.day_lunar_month);
        mModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        mPreferences = requireActivity().getPreferences(MODE_PRIVATE);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadPreferences();
        observeModel();
    }

    @NonNull
    @Override
    public String getFragmentName() {
        return FRAGMENT_NAME;
    }

    private void loadPreferences() {
        mMoonRiseTime.setText(mPreferences.getString("moonRiseTime", ""));
        mMoonSetTime.setText(mPreferences.getString("moonSetTime", ""));
        mNewMoonDate.setText(mPreferences.getString("newMoonDate", ""));
        mFullMoonDate.setText(mPreferences.getString("fullMoonDate", ""));
        mMoonPhaseValue.setText(mPreferences.getString("moonPhaseValue", ""));
        mMoonLunarMonthDay.setText(mPreferences.getString("moonLunarMonthDay", ""));
    }

    private void observeModel() {
        mModel.getMoonRiseTime().observe(getViewLifecycleOwner(), value -> {
            mMoonRiseTime.setText(value);
            setPreference("moonRiseTime", value);
        });
        mModel.getMoonSetTime().observe(getViewLifecycleOwner(), value -> {
            mMoonSetTime.setText(value);
            setPreference("moonSetTime", value);
        });
        mModel.getNewMoonDate().observe(getViewLifecycleOwner(), value -> {
            mNewMoonDate.setText(value);
            setPreference("newMoonDate", value);
        });
        mModel.getFullMoonDate().observe(getViewLifecycleOwner(), value -> {
            mFullMoonDate.setText(value);
            setPreference("fullMoonDate", value);
        });
        mModel.getMoonPhaseValue().observe(getViewLifecycleOwner(), value -> {
            mMoonPhaseValue.setText(value);
            setPreference("moonPhaseValue", value);
        });
        mModel.getMoonLunarMonthDay().observe(getViewLifecycleOwner(), value -> {
            mMoonLunarMonthDay.setText(value);
            setPreference("moonLunarMonthDay", value);
        });
    }

    private void setPreference(String key, String value) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
}
