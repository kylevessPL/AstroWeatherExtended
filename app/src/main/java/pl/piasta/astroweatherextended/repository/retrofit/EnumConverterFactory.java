package pl.piasta.astroweatherextended.repository.retrofit;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Objects;

import retrofit2.Converter;
import retrofit2.Retrofit;

public class EnumConverterFactory extends Converter.Factory {

    private EnumConverterFactory() {
    }

    public static EnumConverterFactory create() {
        return new EnumConverterFactory();
    }

    @Override
    public Converter<?, String> stringConverter(
            @NonNull Type type,
            @NonNull Annotation[] annotations,
            @NonNull Retrofit retrofit
    ) {
        Converter<?, String> converter = null;
        if (type instanceof Class && ((Class<?>) type).isEnum()) {
            converter = value -> getSerializedNameValue((Enum<?>) value);
        }
        return converter;
    }

    @NonNull
    private static String getSerializedNameValue(Enum<?> value) {
        try {
            return Objects.requireNonNull(value.getClass().getField(value.name())
                    .getAnnotation(SerializedName.class)).value();
        } catch (Exception ex) {
            return value.toString();
        }
    }
}
