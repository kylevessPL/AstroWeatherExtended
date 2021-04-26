package pl.piasta.astroweatherextended.repository.retrofit;

import pl.piasta.astroweatherextended.util.GlobalVariables;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitRequest {

    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(GlobalVariables.sBaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(EnumConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private RetrofitRequest() {
    }
}
