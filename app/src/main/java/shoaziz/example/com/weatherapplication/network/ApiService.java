package shoaziz.example.com.weatherapplication.network;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import shoaziz.example.com.weatherapplication.model.WeatherData;

public interface ApiService {


    @GET("forecast")
    Single<WeatherData> getPrice(@Query("id") int ID, @Query("APPID") String KEY);
}
