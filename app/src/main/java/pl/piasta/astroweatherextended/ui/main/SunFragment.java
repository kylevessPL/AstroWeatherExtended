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

public class SunFragment extends BaseFragment {

    private static final String FRAGMENT_NAME = "SUN";

    private MainViewModel mModel;
    private SharedPreferences mPreferences;

    private TextView mSunRiseTime;
    private TextView mSunRiseAzimuth;
    private TextView mSunSetTime;
    private TextView mSunSetAzimuth;
    private TextView mSunDuskTime;
    private TextView mSunDawnTime;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState
    ) {
        View root = inflater.inflate(R.layout.fragment_sun, container, false);
        mSunRiseTime = root.findViewById(R.id.time_rise);
        mSunRiseAzimuth = root.findViewById(R.id.azimuth_rise);
        mSunSetTime = root.findViewById(R.id.time_set);
        mSunSetAzimuth = root.findViewById(R.id.azimuth_set);
        mSunDuskTime = root.findViewById(R.id.time_dusk);
        mSunDawnTime = root.findViewById(R.id.time_dawn);
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
        mSunRiseTime.setText(mPreferences.getString("sunRiseTime", ""));
        mSunRiseAzimuth.setText(mPreferences.getString("sunRiseAzimuth", ""));
        mSunSetTime.setText(mPreferences.getString("sunSetTime", ""));
        mSunSetAzimuth.setText(mPreferences.getString("sunSetAzimuth", ""));
        mSunDuskTime.setText(mPreferences.getString("sunDuskTime", ""));
        mSunDawnTime.setText(mPreferences.getString("sunDawnTime", ""));
    }

    private void observeModel() {
        mModel.getSunRiseTime().observe(getViewLifecycleOwner(), value -> {
            mSunRiseTime.setText(value);
            setPreference("sunRiseTime", value);
        });
        mModel.getSunRiseAzimuth().observe(getViewLifecycleOwner(), value -> {
            mSunRiseAzimuth.setText(value);
            setPreference("sunRiseAzimuth", value);
        });
        mModel.getSunSetTime().observe(getViewLifecycleOwner(), value -> {
            mSunSetTime.setText(value);
            setPreference("sunSetTime", value);
        });
        mModel.getSunSetAzimuth().observe(getViewLifecycleOwner(), value -> {
            mSunSetAzimuth.setText(value);
            setPreference("sunSetAzimuth", value);
        });
        mModel.getSunDuskTime().observe(getViewLifecycleOwner(), value -> {
            mSunDuskTime.setText(value);
            setPreference("sunDuskTime", value);
        });
        mModel.getSunDawnTime().observe(getViewLifecycleOwner(), value -> {
            mSunDawnTime.setText(value);
            setPreference("sunDawnTime", value);
        });
    }

    private void setPreference(String key, String value) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
}
