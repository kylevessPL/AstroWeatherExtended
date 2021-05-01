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

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import pl.piasta.astroweatherextended.R;
import pl.piasta.astroweatherextended.model.DailyForecastResponse;
import pl.piasta.astroweatherextended.model.base.DayData;
import pl.piasta.astroweatherextended.model.base.TemperatureData;
import pl.piasta.astroweatherextended.model.base.WeatherData;
import pl.piasta.astroweatherextended.ui.base.BaseFragment;
import pl.piasta.astroweatherextended.ui.base.MeasurementUnit;

import static android.content.Context.MODE_PRIVATE;

public class SevenDaysForecastFragment extends BaseFragment {

    private static final String FRAGMENT_NAME = "WEEK";
    private static final String TEMPERATURE_UNIT_DEFAULT = "0";

    private MainViewModel mModel;
    private SharedPreferences mPreferences;

    private TextView mDayForecastTitle1;
    private TextView mDayForecastTitle2;
    private TextView mDayForecastTitle3;
    private TextView mDayForecastTitle4;
    private TextView mDayForecastTitle5;
    private TextView mDayForecastTitle6;
    private ImageView mDayForecastIcon1;
    private ImageView mDayForecastIcon2;
    private ImageView mDayForecastIcon3;
    private ImageView mDayForecastIcon4;
    private ImageView mDayForecastIcon5;
    private ImageView mDayForecastIcon6;
    private TextView mDayWeatherDetailsDescription1;
    private TextView mDayWeatherDetailsDescription2;
    private TextView mDayWeatherDetailsDescription3;
    private TextView mDayWeatherDetailsDescription4;
    private TextView mDayWeatherDetailsDescription5;
    private TextView mDayWeatherDetailsDescription6;
    private TextView mDayWeatherDetailsTemperatureDay1;
    private TextView mDayWeatherDetailsTemperatureDay2;
    private TextView mDayWeatherDetailsTemperatureDay3;
    private TextView mDayWeatherDetailsTemperatureDay4;
    private TextView mDayWeatherDetailsTemperatureDay5;
    private TextView mDayWeatherDetailsTemperatureDay6;
    private TextView mDayWeatherDetailsTemperatureNight1;
    private TextView mDayWeatherDetailsTemperatureNight2;
    private TextView mDayWeatherDetailsTemperatureNight3;
    private TextView mDayWeatherDetailsTemperatureNight4;
    private TextView mDayWeatherDetailsTemperatureNight5;
    private TextView mDayWeatherDetailsTemperatureNight6;
    private TextView mDayWeatherDetailsHumidity1;
    private TextView mDayWeatherDetailsHumidity2;
    private TextView mDayWeatherDetailsHumidity3;
    private TextView mDayWeatherDetailsHumidity4;
    private TextView mDayWeatherDetailsHumidity5;
    private TextView mDayWeatherDetailsHumidity6;
    private TextView mDayWeatherDetailsPressure1;
    private TextView mDayWeatherDetailsPressure2;
    private TextView mDayWeatherDetailsPressure3;
    private TextView mDayWeatherDetailsPressure4;
    private TextView mDayWeatherDetailsPressure5;
    private TextView mDayWeatherDetailsPressure6;
    private TextView mDayWeatherDetailsWindSpeed1;
    private TextView mDayWeatherDetailsWindSpeed2;
    private TextView mDayWeatherDetailsWindSpeed3;
    private TextView mDayWeatherDetailsWindSpeed4;
    private TextView mDayWeatherDetailsWindSpeed5;
    private TextView mDayWeatherDetailsWindSpeed6;
    private TextView mDayWeatherDetailsWindDirection1;
    private TextView mDayWeatherDetailsWindDirection2;
    private TextView mDayWeatherDetailsWindDirection3;
    private TextView mDayWeatherDetailsWindDirection4;
    private TextView mDayWeatherDetailsWindDirection5;
    private TextView mDayWeatherDetailsWindDirection6;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_seven_days_forecast_shared, container, false);
        loadWeatherDetailsTitles(root);
        loadWeatherDetailsIcons(root);
        loadWeatherDetailsDescription(root);
        loadWeatherDetailsTemperatureDay(root);
        loadWeatherDetailsTemperatureNight(root);
        loadWeatherDetaisHumidity(root);
        loadWeatherDetailsPressure(root);
        loadWeatherDetailsWindSpeed(root);
        loadWeatherDetailsWindDirection(root);
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

    private void loadWeatherDetailsWindDirection(final View view) {
        mDayWeatherDetailsWindDirection1 = view.findViewById(R.id.day_weather_details_wind_direction_1);
        mDayWeatherDetailsWindDirection2 = view.findViewById(R.id.day_weather_details_wind_direction_2);
        mDayWeatherDetailsWindDirection3 = view.findViewById(R.id.day_weather_details_wind_direction_3);
        mDayWeatherDetailsWindDirection4 = view.findViewById(R.id.day_weather_details_wind_direction_4);
        mDayWeatherDetailsWindDirection5 = view.findViewById(R.id.day_weather_details_wind_direction_5);
        mDayWeatherDetailsWindDirection6 = view.findViewById(R.id.day_weather_details_wind_direction_6);
    }

    private void loadWeatherDetailsWindSpeed(final View view) {
        mDayWeatherDetailsWindSpeed1 = view.findViewById(R.id.day_weather_details_wind_speed_1);
        mDayWeatherDetailsWindSpeed2 = view.findViewById(R.id.day_weather_details_wind_speed_2);
        mDayWeatherDetailsWindSpeed3 = view.findViewById(R.id.day_weather_details_wind_speed_3);
        mDayWeatherDetailsWindSpeed4 = view.findViewById(R.id.day_weather_details_wind_speed_4);
        mDayWeatherDetailsWindSpeed5 = view.findViewById(R.id.day_weather_details_wind_speed_5);
        mDayWeatherDetailsWindSpeed6 = view.findViewById(R.id.day_weather_details_wind_speed_6);
    }

    private void loadWeatherDetaisHumidity(final View view) {
        mDayWeatherDetailsHumidity1 = view.findViewById(R.id.day_weather_details_humidity_1);
        mDayWeatherDetailsHumidity2 = view.findViewById(R.id.day_weather_details_humidity_2);
        mDayWeatherDetailsHumidity3 = view.findViewById(R.id.day_weather_details_humidity_3);
        mDayWeatherDetailsHumidity4 = view.findViewById(R.id.day_weather_details_humidity_4);
        mDayWeatherDetailsHumidity5 = view.findViewById(R.id.day_weather_details_humidity_5);
        mDayWeatherDetailsHumidity6 = view.findViewById(R.id.day_weather_details_humidity_6);
    }

    private void loadWeatherDetailsPressure(final View view) {
        mDayWeatherDetailsPressure1 = view.findViewById(R.id.day_weather_details_pressure_1);
        mDayWeatherDetailsPressure2 = view.findViewById(R.id.day_weather_details_pressure_2);
        mDayWeatherDetailsPressure3 = view.findViewById(R.id.day_weather_details_pressure_3);
        mDayWeatherDetailsPressure4 = view.findViewById(R.id.day_weather_details_pressure_4);
        mDayWeatherDetailsPressure5 = view.findViewById(R.id.day_weather_details_pressure_5);
        mDayWeatherDetailsPressure6 = view.findViewById(R.id.day_weather_details_pressure_6);
    }

    private void loadWeatherDetailsTemperatureNight(final View view) {
        mDayWeatherDetailsTemperatureNight1 = view.findViewById(R.id.day_weather_details_temperature_night_1);
        mDayWeatherDetailsTemperatureNight2 = view.findViewById(R.id.day_weather_details_temperature_night_2);
        mDayWeatherDetailsTemperatureNight3 = view.findViewById(R.id.day_weather_details_temperature_night_3);
        mDayWeatherDetailsTemperatureNight4 = view.findViewById(R.id.day_weather_details_temperature_night_4);
        mDayWeatherDetailsTemperatureNight5 = view.findViewById(R.id.day_weather_details_temperature_night_5);
        mDayWeatherDetailsTemperatureNight6 = view.findViewById(R.id.day_weather_details_temperature_night_6);
    }

    private void loadWeatherDetailsTemperatureDay(final View view) {
        mDayWeatherDetailsTemperatureDay1 = view.findViewById(R.id.day_weather_details_temperature_day_1);
        mDayWeatherDetailsTemperatureDay2 = view.findViewById(R.id.day_weather_details_temperature_day_2);
        mDayWeatherDetailsTemperatureDay3 = view.findViewById(R.id.day_weather_details_temperature_day_3);
        mDayWeatherDetailsTemperatureDay4 = view.findViewById(R.id.day_weather_details_temperature_day_4);
        mDayWeatherDetailsTemperatureDay5 = view.findViewById(R.id.day_weather_details_temperature_day_5);
        mDayWeatherDetailsTemperatureDay6 = view.findViewById(R.id.day_weather_details_temperature_day_6);
    }

    private void loadWeatherDetailsDescription(final View view) {
        mDayWeatherDetailsDescription1 = view.findViewById(R.id.day_weather_details_description_1);
        mDayWeatherDetailsDescription2 = view.findViewById(R.id.day_weather_details_description_2);
        mDayWeatherDetailsDescription3 = view.findViewById(R.id.day_weather_details_description_3);
        mDayWeatherDetailsDescription4 = view.findViewById(R.id.day_weather_details_description_4);
        mDayWeatherDetailsDescription5 = view.findViewById(R.id.day_weather_details_description_5);
        mDayWeatherDetailsDescription6 = view.findViewById(R.id.day_weather_details_description_6);
    }

    private void loadWeatherDetailsTitles(final View view) {
        mDayForecastTitle1 = view.findViewById(R.id.day_forecast_title_1);
        mDayForecastTitle2 = view.findViewById(R.id.day_forecast_title_2);
        mDayForecastTitle3 = view.findViewById(R.id.day_forecast_title_3);
        mDayForecastTitle4 = view.findViewById(R.id.day_forecast_title_4);
        mDayForecastTitle5 = view.findViewById(R.id.day_forecast_title_5);
        mDayForecastTitle6 = view.findViewById(R.id.day_forecast_title_6);
    }

    private void loadWeatherDetailsIcons(final View view) {
        mDayForecastIcon1 = view.findViewById(R.id.day_forecast_icon_1);
        mDayForecastIcon2 = view.findViewById(R.id.day_forecast_icon_2);
        mDayForecastIcon3 = view.findViewById(R.id.day_forecast_icon_3);
        mDayForecastIcon4 = view.findViewById(R.id.day_forecast_icon_4);
        mDayForecastIcon5 = view.findViewById(R.id.day_forecast_icon_5);
        mDayForecastIcon6 = view.findViewById(R.id.day_forecast_icon_6);
    }

    private void observeModel() {
        mModel.getDailyForecastData().observe(getViewLifecycleOwner(), data -> {
            MeasurementUnit measurementUnit =
                    MeasurementUnit.values()[Integer.parseInt(
                            mPreferences.getString("temperatureUnit", TEMPERATURE_UNIT_DEFAULT))];
            setDailyForecast(data, measurementUnit);
            setPreference(data);
        });
    }

    private void setDailyForecast(DailyForecastResponse data, MeasurementUnit measurementUnit) {
        List<DayData> list = data.getDayDataList().subList(1, data.getDayDataList().size());
        setDay1Data(list.get(0), measurementUnit);
        setDay2Data(list.get(1), measurementUnit);
        setDay3Data(list.get(2), measurementUnit);
        setDay4Data(list.get(3), measurementUnit);
        setDay5Data(list.get(4), measurementUnit);
        setDay6Data(list.get(5), measurementUnit);
    }

    private void setDay1Data(final DayData dayData, final MeasurementUnit measurementUnit) {
        WeatherData weatherData = dayData.getWeatherDataList().get(0);
        TemperatureData temperatureData = dayData.getTemperatureData();
        mDayForecastTitle1.setText(getTitleText(1));
        mDayForecastIcon1.setImageResource(getDrawableByName(weatherData.getIcon()));
        mDayWeatherDetailsDescription1.setText(weatherData.getDescription());
        mDayWeatherDetailsHumidity1.setText(getHumidityText(dayData.getHumidity(), measurementUnit));
        mDayWeatherDetailsTemperatureDay1.setText(
                getTemperatureText(temperatureData.getDayTemperature(), measurementUnit));
        mDayWeatherDetailsTemperatureNight1.setText(
                getTemperatureText(temperatureData.getNightTemperature(), measurementUnit));
        mDayWeatherDetailsPressure1.setText(getPressureText((dayData.getPressure()), measurementUnit));
        mDayWeatherDetailsWindSpeed1.setText(getWindSpeedText((dayData.getWindSpeed()), measurementUnit));
        mDayWeatherDetailsWindDirection1.setText(getWindDirectionText((dayData.getWindDirection()), measurementUnit));
    }

    private void setDay2Data(final DayData dayData, final MeasurementUnit measurementUnit) {
        WeatherData weatherData = dayData.getWeatherDataList().get(0);
        TemperatureData temperatureData = dayData.getTemperatureData();
        mDayForecastTitle2.setText(getTitleText(2));
        mDayForecastIcon2.setImageResource(getDrawableByName(weatherData.getIcon()));
        mDayWeatherDetailsDescription2.setText(weatherData.getDescription());
        mDayWeatherDetailsHumidity2.setText(getHumidityText(dayData.getHumidity(), measurementUnit));
        mDayWeatherDetailsTemperatureDay2.setText(
                getTemperatureText(temperatureData.getDayTemperature(), measurementUnit));
        mDayWeatherDetailsTemperatureNight2.setText(
                getTemperatureText(temperatureData.getNightTemperature(), measurementUnit));
        mDayWeatherDetailsPressure2.setText(getPressureText((dayData.getPressure()), measurementUnit));
        mDayWeatherDetailsWindSpeed2.setText(getWindSpeedText((dayData.getWindSpeed()), measurementUnit));
        mDayWeatherDetailsWindDirection2.setText(getWindDirectionText((dayData.getWindDirection()), measurementUnit));
    }

    private void setDay3Data(final DayData dayData, final MeasurementUnit measurementUnit) {
        WeatherData weatherData = dayData.getWeatherDataList().get(0);
        TemperatureData temperatureData = dayData.getTemperatureData();
        mDayForecastTitle3.setText(getTitleText(3));
        mDayForecastIcon3.setImageResource(getDrawableByName(weatherData.getIcon()));
        mDayWeatherDetailsDescription3.setText(weatherData.getDescription());
        mDayWeatherDetailsHumidity3.setText(getHumidityText(dayData.getHumidity(), measurementUnit));
        mDayWeatherDetailsTemperatureDay3.setText(
                getTemperatureText(temperatureData.getDayTemperature(), measurementUnit));
        mDayWeatherDetailsTemperatureNight3.setText(
                getTemperatureText(temperatureData.getNightTemperature(), measurementUnit));
        mDayWeatherDetailsPressure3.setText(getPressureText((dayData.getPressure()), measurementUnit));
        mDayWeatherDetailsWindSpeed3.setText(getWindSpeedText((dayData.getWindSpeed()), measurementUnit));
        mDayWeatherDetailsWindDirection3.setText(getWindDirectionText((dayData.getWindDirection()), measurementUnit));
    }

    private void setDay4Data(final DayData dayData, final MeasurementUnit measurementUnit) {
        WeatherData weatherData = dayData.getWeatherDataList().get(0);
        TemperatureData temperatureData = dayData.getTemperatureData();
        mDayForecastTitle4.setText(getTitleText(4));
        mDayForecastIcon4.setImageResource(getDrawableByName(weatherData.getIcon()));
        mDayWeatherDetailsDescription4.setText(weatherData.getDescription());
        mDayWeatherDetailsHumidity4.setText(getHumidityText(dayData.getHumidity(), measurementUnit));
        mDayWeatherDetailsTemperatureDay4.setText(
                getTemperatureText(temperatureData.getDayTemperature(), measurementUnit));
        mDayWeatherDetailsTemperatureNight4.setText(
                getTemperatureText(temperatureData.getNightTemperature(), measurementUnit));
        mDayWeatherDetailsPressure4.setText(getPressureText((dayData.getPressure()), measurementUnit));
        mDayWeatherDetailsWindSpeed4.setText(getWindSpeedText((dayData.getWindSpeed()), measurementUnit));
        mDayWeatherDetailsWindDirection4.setText(getWindDirectionText((dayData.getWindDirection()), measurementUnit));
    }

    private void setDay5Data(final DayData dayData, final MeasurementUnit measurementUnit) {
        WeatherData weatherData = dayData.getWeatherDataList().get(0);
        TemperatureData temperatureData = dayData.getTemperatureData();
        mDayForecastTitle5.setText(getTitleText(5));
        mDayForecastIcon5.setImageResource(getDrawableByName(weatherData.getIcon()));
        mDayWeatherDetailsDescription5.setText(weatherData.getDescription());
        mDayWeatherDetailsHumidity5.setText(getHumidityText(dayData.getHumidity(), measurementUnit));
        mDayWeatherDetailsTemperatureDay5.setText(
                getTemperatureText(temperatureData.getDayTemperature(), measurementUnit));
        mDayWeatherDetailsTemperatureNight5.setText(
                getTemperatureText(temperatureData.getNightTemperature(), measurementUnit));
        mDayWeatherDetailsPressure5.setText(getPressureText((dayData.getPressure()), measurementUnit));
        mDayWeatherDetailsWindSpeed5.setText(getWindSpeedText((dayData.getWindSpeed()), measurementUnit));
        mDayWeatherDetailsWindDirection5.setText(getWindDirectionText((dayData.getWindDirection()), measurementUnit));
    }

    private void setDay6Data(final DayData dayData, final MeasurementUnit measurementUnit) {
        WeatherData weatherData = dayData.getWeatherDataList().get(0);
        TemperatureData temperatureData = dayData.getTemperatureData();
        mDayForecastTitle6.setText(getTitleText(6));
        mDayForecastIcon6.setImageResource(getDrawableByName(weatherData.getIcon()));
        mDayWeatherDetailsDescription6.setText(weatherData.getDescription());
        mDayWeatherDetailsHumidity6.setText(getHumidityText(dayData.getHumidity(), measurementUnit));
        mDayWeatherDetailsTemperatureDay6.setText(
                getTemperatureText(temperatureData.getDayTemperature(), measurementUnit));
        mDayWeatherDetailsTemperatureNight6.setText(
                getTemperatureText(temperatureData.getNightTemperature(), measurementUnit));
        mDayWeatherDetailsPressure6.setText(getPressureText((dayData.getPressure()), measurementUnit));
        mDayWeatherDetailsWindSpeed6.setText(getWindSpeedText((dayData.getWindSpeed()), measurementUnit));
        mDayWeatherDetailsWindDirection6.setText(getWindDirectionText((dayData.getWindDirection()), measurementUnit));
    }

    private String getTitleText(int dayNumber) {
        return LocalDate.now().plusDays(dayNumber).getDayOfWeek()
                .getDisplayName(TextStyle.FULL, Locale.US);
    }

    private String getHumidityText(double humidity, MeasurementUnit measurementUnit) {
        return (int) Math.round(humidity) + measurementUnit.getHumidityUnit();
    }

    private String getTemperatureText(double temperature, MeasurementUnit measurementUnit) {
        return (int) Math.round(temperature) + measurementUnit.getTemperatureUnit();
    }

    private String getPressureText(double pressure, MeasurementUnit measurementUnit) {
        return (int) Math.round(pressure) + measurementUnit.getPressureUnit();
    }

    private String getWindSpeedText(double windSpeed, MeasurementUnit measurementUnit) {
        return (int) Math.round(windSpeed) + measurementUnit.getWindSpeedUnit();
    }

    private String getWindDirectionText(double windDirection, MeasurementUnit measurementUnit) {
        return (int) Math.round(windDirection) + measurementUnit.getWindDirectionUnit();
    }

    private void loadPreferences() {
        Gson gson = new Gson();
        MeasurementUnit measurementUnit =
                MeasurementUnit.values()[Integer.parseInt(
                        mPreferences.getString("temperatureUnit", TEMPERATURE_UNIT_DEFAULT))];
        String json = mPreferences.getString("dailyForecastData", "");
        DailyForecastResponse data = gson.fromJson(json, DailyForecastResponse.class);
        setDailyForecast(data, measurementUnit);
    }

    private int getDrawableByName(final String value) {
        return getResources().getIdentifier(value, "drawable", requireContext().getPackageName());
    }

    private void setPreference(DailyForecastResponse data) {
        SharedPreferences.Editor editor = mPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(data);
        editor.putString("dailyForecastData", json);
        editor.apply();
    }
}