package pl.piasta.astroweatherextended.repository.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import pl.piasta.astroweatherextended.util.GlobalVariables;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitRequest {

    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .client(client)
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
