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
        observeForecastTitles();
        observeForecastIcons();
        observeDayWeatherDetailsDescriptions();
        observeDayWeatherDetailsTemperatureDays();
        observeDayWeatherDetailsTemperatureNights();
        observeDayWeatherDetailsHumidities();
        observeDayWeatherDetailsWindSpeeds();
        observeDayWeatherDetailsWindDirections();
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

    private void observeDayWeatherDetailsWindDirections() {
        mModel.getDayWeatherDetailsWindDirection1().observe(getViewLifecycleOwner(), value ->
                mDayWeatherDetailsWindDirection1.setText(value));
        mModel.getDayWeatherDetailsWindDirection2().observe(getViewLifecycleOwner(), value ->
                mDayWeatherDetailsWindDirection2.setText(value));
        mModel.getDayWeatherDetailsWindDirection3().observe(getViewLifecycleOwner(), value ->
                mDayWeatherDetailsWindDirection3.setText(value));
        mModel.getDayWeatherDetailsWindDirection4().observe(getViewLifecycleOwner(), value ->
                mDayWeatherDetailsWindDirection4.setText(value));
        mModel.getDayWeatherDetailsWindDirection5().observe(getViewLifecycleOwner(), value ->
                mDayWeatherDetailsWindDirection5.setText(value));
        mModel.getDayWeatherDetailsWindDirection6().observe(getViewLifecycleOwner(), value ->
                mDayWeatherDetailsWindDirection6.setText(value));
    }

    private void observeDayWeatherDetailsWindSpeeds() {
        mModel.getDayWeatherDetailsWindSpeed1().observe(getViewLifecycleOwner(), value ->
                mDayWeatherDetailsWindSpeed1.setText(value));
        mModel.getDayWeatherDetailsWindSpeed2().observe(getViewLifecycleOwner(), value ->
                mDayWeatherDetailsWindSpeed2.setText(value));
        mModel.getDayWeatherDetailsWindSpeed3().observe(getViewLifecycleOwner(), value ->
                mDayWeatherDetailsWindSpeed3.setText(value));
        mModel.getDayWeatherDetailsWindSpeed4().observe(getViewLifecycleOwner(), value ->
                mDayWeatherDetailsWindSpeed4.setText(value));
        mModel.getDayWeatherDetailsWindSpeed5().observe(getViewLifecycleOwner(), value ->
                mDayWeatherDetailsWindSpeed5.setText(value));
        mModel.getDayWeatherDetailsWindSpeed6().observe(getViewLifecycleOwner(), value ->
                mDayWeatherDetailsWindSpeed6.setText(value));
    }

    private void observeDayWeatherDetailsHumidities() {
        mModel.getDayWeatherDetailsHumidity1().observe(getViewLifecycleOwner(), value ->
                mDayWeatherDetailsHumidity1.setText(value));
        mModel.getDayWeatherDetailsHumidity2().observe(getViewLifecycleOwner(), value ->
                mDayWeatherDetailsHumidity2.setText(value));
        mModel.getDayWeatherDetailsHumidity3().observe(getViewLifecycleOwner(), value ->
                mDayWeatherDetailsHumidity3.setText(value));
        mModel.getDayWeatherDetailsHumidity4().observe(getViewLifecycleOwner(), value ->
                mDayWeatherDetailsHumidity4.setText(value));
        mModel.getDayWeatherDetailsHumidity5().observe(getViewLifecycleOwner(), value ->
                mDayWeatherDetailsHumidity5.setText(value));
        mModel.getDayWeatherDetailsHumidity6().observe(getViewLifecycleOwner(), value ->
                mDayWeatherDetailsHumidity6.setText(value));
    }

    private void observeDayWeatherDetailsTemperatureNights() {
        mModel.getDayWeatherDetailsTemperatureNight1().observe(getViewLifecycleOwner(), value ->
                mDayWeatherDetailsTemperatureNight1.setText(value));
        mModel.getDayWeatherDetailsTemperatureNight2().observe(getViewLifecycleOwner(), value ->
                mDayWeatherDetailsTemperatureNight2.setText(value));
        mModel.getDayWeatherDetailsTemperatureNight3().observe(getViewLifecycleOwner(), value ->
                mDayWeatherDetailsTemperatureNight3.setText(value));
        mModel.getDayWeatherDetailsTemperatureNight4().observe(getViewLifecycleOwner(), value ->
                mDayWeatherDetailsTemperatureNight4.setText(value));
        mModel.getDayWeatherDetailsTemperatureNight5().observe(getViewLifecycleOwner(), value ->
                mDayWeatherDetailsTemperatureNight5.setText(value));
        mModel.getDayWeatherDetailsTemperatureNight6().observe(getViewLifecycleOwner(), value ->
                mDayWeatherDetailsTemperatureNight6.setText(value));
    }

    private void observeDayWeatherDetailsTemperatureDays() {
        mModel.getDayWeatherDetailsTemperatureDay1().observe(getViewLifecycleOwner(), value ->
                mDayWeatherDetailsTemperatureDay1.setText(value));
        mModel.getDayWeatherDetailsTemperatureDay2().observe(getViewLifecycleOwner(), value ->
                mDayWeatherDetailsTemperatureDay2.setText(value));
        mModel.getDayWeatherDetailsTemperatureDay3().observe(getViewLifecycleOwner(), value ->
                mDayWeatherDetailsTemperatureDay3.setText(value));
        mModel.getDayWeatherDetailsTemperatureDay4().observe(getViewLifecycleOwner(), value ->
                mDayWeatherDetailsTemperatureDay4.setText(value));
        mModel.getDayWeatherDetailsTemperatureDay5().observe(getViewLifecycleOwner(), value ->
                mDayWeatherDetailsTemperatureDay5.setText(value));
        mModel.getDayWeatherDetailsTemperatureDay6().observe(getViewLifecycleOwner(), value ->
                mDayWeatherDetailsTemperatureDay6.setText(value));
    }

    private void observeDayWeatherDetailsDescriptions() {
        mModel.getDayWeatherDetailsDescription1().observe(getViewLifecycleOwner(), value ->
                mDayWeatherDetailsDescription1.setText(value));
        mModel.getDayWeatherDetailsDescription2().observe(getViewLifecycleOwner(), value ->
                mDayWeatherDetailsDescription2.setText(value));
        mModel.getDayWeatherDetailsDescription3().observe(getViewLifecycleOwner(), value ->
                mDayWeatherDetailsDescription3.setText(value));
        mModel.getDayWeatherDetailsDescription4().observe(getViewLifecycleOwner(), value ->
                mDayWeatherDetailsDescription4.setText(value));
        mModel.getDayWeatherDetailsDescription5().observe(getViewLifecycleOwner(), value ->
                mDayWeatherDetailsDescription5.setText(value));
        mModel.getDayWeatherDetailsDescription6().observe(getViewLifecycleOwner(), value ->
                mDayWeatherDetailsDescription6.setText(value));
    }

    private void observeForecastTitles() {
        mModel.getDayForecastTile1().observe(getViewLifecycleOwner(), value ->
                mDayForecastTitle1.setText(value));
        mModel.getDayForecastTile2().observe(getViewLifecycleOwner(), value ->
                mDayForecastTitle2.setText(value));
        mModel.getDayForecastTile3().observe(getViewLifecycleOwner(), value ->
                mDayForecastTitle3.setText(value));
        mModel.getDayForecastTile4().observe(getViewLifecycleOwner(), value ->
                mDayForecastTitle4.setText(value));
        mModel.getDayForecastTile5().observe(getViewLifecycleOwner(), value ->
                mDayForecastTitle5.setText(value));
        mModel.getDayForecastTile6().observe(getViewLifecycleOwner(), value ->
                mDayForecastTitle6.setText(value));
    }

    private void observeForecastIcons() {
        mModel.getDayForecastIcon1().observe(getViewLifecycleOwner(), value ->
                mDayForecastIcon1.setImageResource(getDrawableByName(value)));
        mModel.getDayForecastIcon2().observe(getViewLifecycleOwner(), value ->
                mDayForecastIcon2.setImageResource(getDrawableByName(value)));
        mModel.getDayForecastIcon3().observe(getViewLifecycleOwner(), value ->
                mDayForecastIcon3.setImageResource(getDrawableByName(value)));
        mModel.getDayForecastIcon4().observe(getViewLifecycleOwner(), value ->
                mDayForecastIcon4.setImageResource(getDrawableByName(value)));
        mModel.getDayForecastIcon5().observe(getViewLifecycleOwner(), value ->
                mDayForecastIcon5.setImageResource(getDrawableByName(value)));
        mModel.getDayForecastIcon6().observe(getViewLifecycleOwner(), value ->
                mDayForecastIcon6.setImageResource(getDrawableByName(value)));
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