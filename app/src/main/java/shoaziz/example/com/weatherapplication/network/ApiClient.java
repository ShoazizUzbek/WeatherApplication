package shoaziz.example.com.weatherapplication.network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static String TAG = "tagging";
    private static Retrofit retrofit = null;
    private static int req_timeout = 10;
    private static OkHttpClient okHttpClient;
    public static Retrofit getClient(){
        if (okHttpClient == null){
            initOkHttp();
        }
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.openweathermap.org/data/2.5/")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }


        private static void initOkHttp() {
            OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(req_timeout, TimeUnit.SECONDS)
                    .readTimeout(req_timeout, TimeUnit.SECONDS)
                    .writeTimeout(req_timeout, TimeUnit.SECONDS);

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            httpClient.addInterceptor(interceptor);

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder()
                            .addHeader("Accept", "application/json")
                            .addHeader("Request-Type", "Android")
                            .addHeader("Content-Type", "application/json");

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });

            okHttpClient = httpClient.build();
        }


}
