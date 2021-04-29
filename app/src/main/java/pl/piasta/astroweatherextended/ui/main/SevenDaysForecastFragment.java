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

import static android.content.Context.MODE_PRIVATE;

public class SevenDaysForecastFragment extends BaseFragment {

    private static final String FRAGMENT_NAME = "WEEK";

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
        mModel.getDailyForecastData().observe(getViewLifecycleOwner(), this::setDailyForecast);
    }

    private void setDailyForecast(DailyForecastResponse data) {
        List<DayData> list = data.getDayDataList().subList(1, data.getDayDataList().size());
        setDay1Data(list.get(0));
        setDay2Data(list.get(1));
        setDay3Data(list.get(2));
        setDay4Data(list.get(3));
        setDay5Data(list.get(4));
        setDay6Data(list.get(5));
    }

    private void setDay1Data(final DayData dayData) {
        WeatherData weatherData = dayData.getWeatherDataList().get(0);
        TemperatureData temperatureData = dayData.getTemperatureData();
        mDayForecastTitle1.setText(LocalDate.now().plusDays(1).getDayOfWeek()
                .getDisplayName(TextStyle.FULL, Locale.US));
        mDayForecastIcon1.setImageResource(getDrawableByName(weatherData.getIcon()));
        mDayWeatherDetailsDescription1.setText(weatherData.getDescription());
        mDayWeatherDetailsHumidity1.setText((int) Math.round(dayData.getHumidity()));
        mDayWeatherDetailsTemperatureDay1.setText((int) Math.round(temperatureData.getDayTemperature()));
        mDayWeatherDetailsTemperatureNight1.setText((int) Math.round(temperatureData.getNightTemperature()));
        mDayWeatherDetailsPressure1.setText((int) Math.round(dayData.getPressure()));
        mDayWeatherDetailsWindSpeed1.setText((int) Math.round(dayData.getSpeed()));
        mDayWeatherDetailsWindDirection1.setText((int) Math.round(dayData.getDirection()));
    }

    private void setDay2Data(final DayData dayData) {
        WeatherData weatherData = dayData.getWeatherDataList().get(0);
        TemperatureData temperatureData = dayData.getTemperatureData();
        mDayForecastTitle2.setText(LocalDate.now().plusDays(2).getDayOfWeek()
                .getDisplayName(TextStyle.FULL, Locale.US));
        mDayForecastIcon2.setImageResource(getDrawableByName(weatherData.getIcon()));
        mDayWeatherDetailsDescription2.setText(weatherData.getDescription());
        mDayWeatherDetailsHumidity2.setText((int) Math.round(dayData.getHumidity()));
        mDayWeatherDetailsTemperatureDay2.setText((int) Math.round(temperatureData.getDayTemperature()));
        mDayWeatherDetailsTemperatureNight2.setText((int) Math.round(temperatureData.getNightTemperature()));
        mDayWeatherDetailsPressure2.setText((int) Math.round(dayData.getPressure()));
        mDayWeatherDetailsWindSpeed2.setText((int) Math.round(dayData.getSpeed()));
        mDayWeatherDetailsWindDirection2.setText((int) Math.round(dayData.getDirection()));
    }

    private void setDay3Data(final DayData dayData) {
        WeatherData weatherData = dayData.getWeatherDataList().get(0);
        TemperatureData temperatureData = dayData.getTemperatureData();
        mDayForecastTitle3.setText(LocalDate.now().plusDays(3).getDayOfWeek()
                .getDisplayName(TextStyle.FULL, Locale.US));
        mDayForecastIcon3.setImageResource(getDrawableByName(weatherData.getIcon()));
        mDayWeatherDetailsDescription3.setText(weatherData.getDescription());
        mDayWeatherDetailsHumidity3.setText((int) Math.round(dayData.getHumidity()));
        mDayWeatherDetailsTemperatureDay3.setText((int) Math.round(temperatureData.getDayTemperature()));
        mDayWeatherDetailsTemperatureNight3.setText((int) Math.round(temperatureData.getNightTemperature()));
        mDayWeatherDetailsPressure3.setText((int) Math.round(dayData.getPressure()));
        mDayWeatherDetailsWindSpeed3.setText((int) Math.round(dayData.getSpeed()));
        mDayWeatherDetailsWindDirection3.setText((int) Math.round(dayData.getDirection()));
    }

    private void setDay4Data(final DayData dayData) {
        WeatherData weatherData = dayData.getWeatherDataList().get(0);
        TemperatureData temperatureData = dayData.getTemperatureData();
        mDayForecastTitle4.setText(LocalDate.now().plusDays(4).getDayOfWeek()
                .getDisplayName(TextStyle.FULL, Locale.US));
        mDayForecastIcon4.setImageResource(getDrawableByName(weatherData.getIcon()));
        mDayWeatherDetailsDescription4.setText(weatherData.getDescription());
        mDayWeatherDetailsHumidity4.setText((int) Math.round(dayData.getHumidity()));
        mDayWeatherDetailsTemperatureDay4.setText((int) Math.round(temperatureData.getDayTemperature()));
        mDayWeatherDetailsTemperatureNight4.setText((int) Math.round(temperatureData.getNightTemperature()));
        mDayWeatherDetailsPressure4.setText((int) Math.round(dayData.getPressure()));
        mDayWeatherDetailsWindSpeed4.setText((int) Math.round(dayData.getSpeed()));
        mDayWeatherDetailsWindDirection4.setText((int) Math.round(dayData.getDirection()));
    }

    private void setDay5Data(final DayData dayData) {
        WeatherData weatherData = dayData.getWeatherDataList().get(0);
        TemperatureData temperatureData = dayData.getTemperatureData();
        mDayForecastTitle5.setText(LocalDate.now().plusDays(5).getDayOfWeek()
                .getDisplayName(TextStyle.FULL, Locale.US));
        mDayForecastIcon5.setImageResource(getDrawableByName(weatherData.getIcon()));
        mDayWeatherDetailsDescription5.setText(weatherData.getDescription());
        mDayWeatherDetailsHumidity5.setText((int) Math.round(dayData.getHumidity()));
        mDayWeatherDetailsTemperatureDay5.setText((int) Math.round(temperatureData.getDayTemperature()));
        mDayWeatherDetailsTemperatureNight5.setText((int) Math.round(temperatureData.getNightTemperature()));
        mDayWeatherDetailsPressure5.setText((int) Math.round(dayData.getPressure()));
        mDayWeatherDetailsWindSpeed5.setText((int) Math.round(dayData.getSpeed()));
        mDayWeatherDetailsWindDirection5.setText((int) Math.round(dayData.getDirection()));
    }

    private void setDay6Data(final DayData dayData) {
        WeatherData weatherData = dayData.getWeatherDataList().get(0);
        TemperatureData temperatureData = dayData.getTemperatureData();
        mDayForecastTitle6.setText(LocalDate.now().plusDays(6).getDayOfWeek()
                .getDisplayName(TextStyle.FULL, Locale.US));
        mDayForecastIcon6.setImageResource(getDrawableByName(weatherData.getIcon()));
        mDayWeatherDetailsDescription6.setText(weatherData.getDescription());
        mDayWeatherDetailsHumidity6.setText((int) Math.round(dayData.getHumidity()));
        mDayWeatherDetailsTemperatureDay6.setText((int) Math.round(temperatureData.getDayTemperature()));
        mDayWeatherDetailsTemperatureNight6.setText((int) Math.round(temperatureData.getNightTemperature()));
        mDayWeatherDetailsPressure6.setText((int) Math.round(dayData.getPressure()));
        mDayWeatherDetailsWindSpeed6.setText((int) Math.round(dayData.getSpeed()));
        mDayWeatherDetailsWindDirection6.setText((int) Math.round(dayData.getDirection()));
    }

    private void loadPreferences() {
        loadDayForecastTitlesPreferences();
        loadDayForecastIconPreferences();
        loadDayWeatherDetailsDescriptionPreferences();
        loadDayWeatherDetailsTemperatureDayPreferences();
        loadDayWeatherDetailsTemperatureNightPreferences();
        loadDayWeatherDetailsHumidityPreferences();
        loadDayWeatherDetailsWindSpeedPreferences();
        loadDayWeatherDetailsWindDirectionPreferences();
    }

    private void loadDayWeatherDetailsWindDirectionPreferences() {
        mDayWeatherDetailsWindDirection1.setText(
                mPreferences.getString("dayWeatherDetailsWindDirection1", ""));
        mDayWeatherDetailsWindDirection2.setText(
                mPreferences.getString("dayWeatherDetailsWindDirection2", ""));
        mDayWeatherDetailsWindDirection3.setText(
                mPreferences.getString("dayWeatherDetailsWindDirection3", ""));
        mDayWeatherDetailsWindDirection4.setText(
                mPreferences.getString("dayWeatherDetailsWindDirection4", ""));
        mDayWeatherDetailsWindDirection5.setText(
                mPreferences.getString("dayWeatherDetailsWindDirection5", ""));
        mDayWeatherDetailsWindDirection6.setText(
                mPreferences.getString("dayWeatherDetailsWindDirection6", ""));
    }

    private void loadDayWeatherDetailsWindSpeedPreferences() {
        mDayWeatherDetailsWindSpeed1.setText(
                mPreferences.getString("dayWeatherDetailsWindSpeed1", ""));
        mDayWeatherDetailsWindSpeed2.setText(
                mPreferences.getString("dayWeatherDetailsWindSpeed2", ""));
        mDayWeatherDetailsWindSpeed3.setText(
                mPreferences.getString("dayWeatherDetailsWindSpeed3", ""));
        mDayWeatherDetailsWindSpeed4.setText(
                mPreferences.getString("dayWeatherDetailsWindSpeed4", ""));
        mDayWeatherDetailsWindSpeed5.setText(
                mPreferences.getString("dayWeatherDetailsWindSpeed5", ""));
        mDayWeatherDetailsWindSpeed6.setText(
                mPreferences.getString("dayWeatherDetailsWindSpeed6", ""));
    }

    private void loadDayWeatherDetailsHumidityPreferences() {
        mDayWeatherDetailsHumidity1.setText(
                mPreferences.getString("dayWeatherDetailsHumidity1", ""));
        mDayWeatherDetailsHumidity2.setText(
                mPreferences.getString("dayWeatherDetailsHumidity2", ""));
        mDayWeatherDetailsHumidity3.setText(
                mPreferences.getString("dayWeatherDetailsHumidity3", ""));
        mDayWeatherDetailsHumidity4.setText(
                mPreferences.getString("dayWeatherDetailsHumidity4", ""));
        mDayWeatherDetailsHumidity5.setText(
                mPreferences.getString("dayWeatherDetailsHumidity5", ""));
        mDayWeatherDetailsHumidity6.setText(
                mPreferences.getString("dayWeatherDetailsHumidity6", ""));
    }

    private void loadDayWeatherDetailsTemperatureNightPreferences() {
        mDayWeatherDetailsTemperatureNight1.setText(
                mPreferences.getString("dayWeatherDetailsTemperatureNight1", ""));
        mDayWeatherDetailsTemperatureNight2.setText(
                mPreferences.getString("dayWeatherDetailsTemperatureNight2", ""));
        mDayWeatherDetailsTemperatureNight3.setText(
                mPreferences.getString("dayWeatherDetailsTemperatureNight3", ""));
        mDayWeatherDetailsTemperatureNight4.setText(
                mPreferences.getString("dayWeatherDetailsTemperatureNight4", ""));
        mDayWeatherDetailsTemperatureNight5.setText(
                mPreferences.getString("dayWeatherDetailsTemperatureNight5", ""));
        mDayWeatherDetailsTemperatureNight6.setText(
                mPreferences.getString("dayWeatherDetailsTemperatureNight6", ""));
    }

    private void loadDayWeatherDetailsTemperatureDayPreferences() {
        mDayWeatherDetailsTemperatureDay1.setText(
                mPreferences.getString("dayWeatherDetailsTemperatureDay1", ""));
        mDayWeatherDetailsTemperatureDay2.setText(
                mPreferences.getString("dayWeatherDetailsTemperatureDay2", ""));
        mDayWeatherDetailsTemperatureDay3.setText(
                mPreferences.getString("dayWeatherDetailsTemperatureDay3", ""));
        mDayWeatherDetailsTemperatureDay4.setText(
                mPreferences.getString("dayWeatherDetailsTemperatureDay4", ""));
        mDayWeatherDetailsTemperatureDay5.setText(
                mPreferences.getString("dayWeatherDetailsTemperatureDay5", ""));
        mDayWeatherDetailsTemperatureDay6.setText(
                mPreferences.getString("dayWeatherDetailsTemperatureDay6", ""));
    }

    private void loadDayWeatherDetailsDescriptionPreferences() {
        mDayWeatherDetailsDescription1.setText(
                mPreferences.getString("dayWeatherDetailsDescription1", ""));
        mDayWeatherDetailsDescription2.setText(
                mPreferences.getString("dayWeatherDetailsDescription2", ""));
        mDayWeatherDetailsDescription3.setText(
                mPreferences.getString("dayWeatherDetailsDescription3", ""));
        mDayWeatherDetailsDescription4.setText(
                mPreferences.getString("dayWeatherDetailsDescription4", ""));
        mDayWeatherDetailsDescription5.setText(
                mPreferences.getString("dayWeatherDetailsDescription5", ""));
        mDayWeatherDetailsDescription6.setText(
                mPreferences.getString("dayWeatherDetailsDescription6", ""));
    }

    private void loadDayForecastTitlesPreferences() {
        mDayForecastTitle1.setText(
                mPreferences.getString("dayForecastTitle1", ""));
        mDayForecastTitle2.setText(
                mPreferences.getString("dayForecastTitle2", ""));
        mDayForecastTitle3.setText(
                mPreferences.getString("dayForecastTitle3", ""));
        mDayForecastTitle4.setText(
                mPreferences.getString("dayForecastTitle4", ""));
        mDayForecastTitle5.setText(
                mPreferences.getString("dayForecastTitle5", ""));
        mDayForecastTitle6.setText(
                mPreferences.getString("dayForecastTitle6", ""));
    }

    private void loadDayForecastIconPreferences() {
        mDayForecastIcon1.setImageResource(getDrawableByName(
                mPreferences.getString("dayForecastIcon1", "")));
        mDayForecastIcon2.setImageResource(getDrawableByName(
                mPreferences.getString("dayForecastIcon2", "")));
        mDayForecastIcon3.setImageResource(getDrawableByName(
                mPreferences.getString("dayForecastIcon3", "")));
        mDayForecastIcon4.setImageResource(getDrawableByName(
                mPreferences.getString("dayForecastIcon4", "")));
        mDayForecastIcon5.setImageResource(getDrawableByName(
                mPreferences.getString("dayForecastIcon5", "")));
        mDayForecastIcon6.setImageResource(getDrawableByName(
                mPreferences.getString("dayForecastIcon6", "")));
    }

    private int getDrawableByName(final String value) {
        return getResources().getIdentifier(value, "drawable", requireContext().getPackageName());
    }
}