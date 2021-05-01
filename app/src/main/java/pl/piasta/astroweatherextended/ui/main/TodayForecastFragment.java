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

import com.google.gson.Gson;

import java.util.Locale;

import pl.piasta.astroweatherextended.R;
import pl.piasta.astroweatherextended.model.CurrentWeatherDataResponse;
import pl.piasta.astroweatherextended.model.base.MainData;
import pl.piasta.astroweatherextended.model.base.WeatherData;
import pl.piasta.astroweatherextended.model.base.WindData;
import pl.piasta.astroweatherextended.ui.base.BaseFragment;
import pl.piasta.astroweatherextended.ui.base.MeasurementUnit;

import static android.content.Context.MODE_PRIVATE;

public class TodayForecastFragment extends BaseFragment {

    private static final String FRAGMENT_NAME = "TODAY";
    private static final String TEMPERATURE_UNIT_DEFAULT = "0";

    private MainViewModel mModel;
    private SharedPreferences mPreferences;

    private ImageView mTodayWeatherIcon;
    private TextView mTodayWeatherHeaderTemperature;
    private TextView mTodayWeatherHeaderMain;
    private TextView mTodayWeatherDetailsDescription;
    private TextView mTodayWeatherDetailsTemperature;
    private TextView mTodayWeatherDetailsHumidity;
    private TextView mTodayWeatherDetailsPressure;
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
        mTodayWeatherDetailsPressure = root.findViewById(R.id.today_weather_details_pressure);
        mTodayWeatherDetailsWindSpeed = root.findViewById(R.id.today_weather_details_wind_speed);
        mTodayWeatherDetailsWindDirection = root.findViewById(R.id.today_weather_details_wind_direction);
        mModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        mPreferences = requireActivity().getPreferences(MODE_PRIVATE);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observeModel();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadPreferences();
    }

    @NonNull
    @Override
    public String getFragmentName() {
        return FRAGMENT_NAME;
    }

    private void loadPreferences() {
        Gson gson = new Gson();
        MeasurementUnit measurementUnit =
                MeasurementUnit.values()[Integer.parseInt(
                        mPreferences.getString("temperatureUnit", TEMPERATURE_UNIT_DEFAULT))];
        String json = mPreferences.getString("currentWeatherData", "");
        CurrentWeatherDataResponse data = gson.fromJson(json, CurrentWeatherDataResponse.class);
        if (data != null) {
            setCurrentWeather(data, measurementUnit);
        }
    }

    private void observeModel() {
        mModel.getCurrentWeatherData().observe(getViewLifecycleOwner(), data -> {
            MeasurementUnit measurementUnit =
                    MeasurementUnit.values()[Integer.parseInt(
                            mPreferences.getString("temperatureUnit", TEMPERATURE_UNIT_DEFAULT))];
            setCurrentWeather(data, measurementUnit);
            setPreference(data);
        });
    }

    private void setCurrentWeather(CurrentWeatherDataResponse data, MeasurementUnit measurementUnit) {
        MainData mainData = data.getMainData();
        WindData windData = data.getWindData();
        WeatherData weatherData = data.getWeatherDataList().get(0);
        mTodayWeatherHeaderMain.setText(weatherData.getMain());
        mTodayWeatherHeaderTemperature.setText(String.format(Locale.US,
                "%d %s",
                (int) Math.round(mainData.getTemperature()), measurementUnit.getTemperatureUnit()));
        mTodayWeatherIcon.setImageResource(getDrawableByName(weatherData.getIcon()));
        mTodayWeatherDetailsDescription.setText(weatherData.getDescription());
        mTodayWeatherDetailsHumidity.setText(String.format(Locale.US,
                "%d %s",
                (int) Math.round(mainData.getHumidity()), measurementUnit.getHumidityUnit()));
        mTodayWeatherDetailsTemperature.setText(String.format(Locale.US,
                "%d %s",
                (int) Math.round(mainData.getTemperature()), measurementUnit.getTemperatureUnit()));
        mTodayWeatherDetailsPressure.setText(String.format(Locale.US,
                "%d %s",
                (int) Math.round(mainData.getPressure()), measurementUnit.getPressureUnit()));
        mTodayWeatherDetailsWindSpeed.setText(String.format(Locale.US,
                "%d %s",
                (int) Math.round(windData.getSpeed()), measurementUnit.getWindSpeedUnit()));
        mTodayWeatherDetailsWindDirection.setText(String.format(Locale.US,
                "%d %s",
                (int) Math.round(windData.getDirection()), measurementUnit.getWindDirectionUnit()));
    }

    private int getDrawableByName(final String value) {
        return getResources().getIdentifier(value, "drawable", requireContext().getPackageName());
    }

    private void setPreference(CurrentWeatherDataResponse data) {
        SharedPreferences.Editor editor = mPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(data);
        editor.putString("currentWeatherData", json);
        editor.apply();
    }
}