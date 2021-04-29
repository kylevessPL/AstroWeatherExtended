package pl.piasta.astroweatherextended.ui.main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import pl.piasta.astroweatherextended.R;
import pl.piasta.astroweatherextended.ui.base.BaseFragment;

import static android.content.Context.MODE_PRIVATE;

public class TodayForecastFragment extends BaseFragment {

    private static final String FRAGMENT_NAME = "TODAY";

    private MainViewModel mModel;
    private SharedPreferences mPreferences;

    private ImageView mTodayWeatherIcon;
    private TextView mTodayWeatherHeaderTemperature;
    private TextView mTodayWeatherHeaderMain;
    private TextView mTodayWeatherDetailsDescription;
    private TextView mTodayWeatherDetailsTemperature;
    private TextView mTodayWeatherDetailsHumidity;
    private TextView mTodayWeatherDetailsWindSpeed;
    private TextView mTodayWeatherDetailsWindDirection;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_today_forecast_shared, container, false);
        mTodayWeatherIcon = root.findViewById(R.id.today_weather_icon);
        mTodayWeatherHeaderTemperature = root.findViewById(R.id.today_weather_header_temperature);
        mTodayWeatherHeaderMain = root.findViewById(R.id.today_weather_header_main);
        mTodayWeatherDetailsDescription = root.findViewById(R.id.today_weather_details_description);
        mTodayWeatherDetailsTemperature = root.findViewById(R.id.today_weather_details_temperature);
        mTodayWeatherDetailsHumidity = root.findViewById(R.id.today_weather_details_humidity);
        mTodayWeatherDetailsWindSpeed = root.findViewById(R.id.today_weather_details_wind_speed);
        mTodayWeatherDetailsWindDirection = root.findViewById(R.id.today_weather_details_wind_direction);
        mModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        mPreferences = requireActivity().getPreferences(MODE_PRIVATE);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!mPreferences.getAll().isEmpty()) {
            loadPreferences();
        }
        observeModel();
    }

    @NonNull
    @Override
    public String getFragmentName() {
        return FRAGMENT_NAME;
    }

    private void loadPreferences() {
        mTodayWeatherIcon.setImageResource(getDrawableByName(
                mPreferences.getString("todayWeatherIcon", "")));
        mTodayWeatherHeaderTemperature.setText(
                mPreferences.getString("todayWeatherHeaderTemperature", ""));
        mTodayWeatherHeaderMain.setText(
                mPreferences.getString("todayWeatherHeaderMain", ""));
        mTodayWeatherDetailsDescription.setText(
                mPreferences.getString("todayWeatherDetailsDescription", ""));
        mTodayWeatherDetailsTemperature.setText(
                mPreferences.getString("todayWeatherDetailsTemperature", ""));
        mTodayWeatherDetailsHumidity.setText(
                mPreferences.getString("todayWeatherDetailsHumidity", ""));
        mTodayWeatherDetailsWindSpeed.setText(
                mPreferences.getString("todayWeatherDetailsWindSpeed", ""));
        mTodayWeatherDetailsWindDirection.setText(
                mPreferences.getString("todayWeatherDetailsWindDirection", ""));
    }

    private void observeModel() {
        mModel.getTodayWeatherHeaderTemperature().observe(getViewLifecycleOwner(), value ->
                mTodayWeatherHeaderTemperature.setText(value));
        mModel.getTodayWeatherHeaderMain().observe(getViewLifecycleOwner(), value ->
                mTodayWeatherHeaderMain.setText(value));
        mModel.getTodayWeatherIcon().observe(getViewLifecycleOwner(), value ->
                mTodayWeatherIcon.setImageResource(getDrawableByName(value)));
        mModel.getTodayWeatherDetailsDescription().observe(getViewLifecycleOwner(), value ->
                mTodayWeatherDetailsDescription.setText(value));
        mModel.getTodayWeatherDetailsTemperature().observe(getViewLifecycleOwner(), value ->
                mTodayWeatherDetailsTemperature.setText(value));
        mModel.getTodayWeatherDetailsHumidity().observe(getViewLifecycleOwner(), value ->
                mTodayWeatherDetailsHumidity.setText(value));
        mModel.getTodayWeatherDetailsWindSpeed().observe(getViewLifecycleOwner(), value ->
                mTodayWeatherDetailsWindSpeed.setText(value));
        mModel.getTodayWeatherDetailsWindDirection().observe(getViewLifecycleOwner(), value ->
                mTodayWeatherDetailsWindDirection.setText(value));
    }

    private int getDrawableByName(final String value) {
        return getResources().getIdentifier(value, "drawable", requireContext().getPackageName());
    }
}