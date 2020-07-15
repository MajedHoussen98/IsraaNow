package ps.edu.israaNow.Retrofit;

import ps.edu.israaNow.interfacesApi.Api;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroiftGet {

    private static final String BASE_URL = "http://192.168.1.7/majed/api/";

    private static RetroiftGet retrofitGet;
    private Retrofit retrofit;

    private RetroiftGet() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetroiftGet getInstance() {

        if (retrofitGet == null) {
            retrofitGet = new RetroiftGet();
        }

        return retrofitGet;
    }

    public Api getApi() {
        return retrofit.create(Api.class);
    }
}
