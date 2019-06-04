package shoaziz.example.com.weatherapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import shoaziz.example.com.weatherapplication.model.CurrentCity;
import shoaziz.example.com.weatherapplication.model.ListCityMain;
import shoaziz.example.com.weatherapplication.model.Main;
import shoaziz.example.com.weatherapplication.model.WeatherData;
import shoaziz.example.com.weatherapplication.network.ApiClient;
import shoaziz.example.com.weatherapplication.network.ApiService;

public class ListCityActivity extends AppCompatActivity implements CityAdapter.TicketsAdapterListener {
    List<ListCityMain> cities_id;
    RecyclerView recyclerView;
    CityAdapter cityAdapter;
    CompositeDisposable compostieDisposable = new CompositeDisposable();
    ArrayList<ListCityMain> listCityMains = new ArrayList<>();
    List<WeatherData>  weatherDataList;
    ApiService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_city);
        weatherDataList = new ArrayList<>();
        apiService = ApiClient.getClient().create(ApiService.class);
        recyclerView = findViewById(R.id.recycler);
        cityAdapter = new CityAdapter(this,listCityMains,this);
        recyclerView.setAdapter(cityAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cities_id = new ArrayList<>();

        Observable<ListCityMain> cityObservable = getCityObservable();
        compostieDisposable.add(cityObservable
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableObserver<ListCityMain>(){

            @Override
            public void onNext(ListCityMain listCityMain) {
                listCityMains.add(listCityMain);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }));

        compostieDisposable.add(cityObservable
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .concatMap(new Function<ListCityMain, ObservableSource<ListCityMain>>() {
            @Override
            public ObservableSource<ListCityMain> apply(ListCityMain listCityMain) throws Exception {
                return getNameCity(listCityMain);
            }
        })
        .subscribeWith(new DisposableObserver<ListCityMain>(){

            @Override
            public void onNext(ListCityMain listCityMain) {
                    int pos = listCityMains.indexOf(listCityMain);
                if (pos == -1) {
                    return;
                }
                listCityMains.set(pos,listCityMain);
                cityAdapter.notifyItemChanged(pos);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("tagged", "onError: "+e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        }));

    }

    private Observable<ListCityMain> getCityObservable() {
        prepareCityList();
        return Observable.create(new ObservableOnSubscribe<ListCityMain>() {
            @Override
            public void subscribe(ObservableEmitter<ListCityMain> emitter) throws Exception {
                for (ListCityMain listCityMain :
                        cities_id) {
                    if (!emitter.isDisposed()){
                        emitter.onNext(listCityMain);
                    }
                }
                if (!emitter.isDisposed()){
                    emitter.onComplete();
                }
            }
        });
    }
    void prepareCityList(){
        cities_id.add(new ListCityMain(1512569,""));
        cities_id.add(new ListCityMain(1216265,""));
        cities_id.add(new ListCityMain(1514589,""));
        cities_id.add(new ListCityMain(1514019,""));
        cities_id.add(new ListCityMain(1512979,""));
        cities_id.add(new ListCityMain(1217662,""));
        cities_id.add(new ListCityMain(1513966,""));
        cities_id.add(new ListCityMain(453752,""));
        cities_id.add(new ListCityMain(601294,""));
        cities_id.add(new ListCityMain(1513157,""));
        cities_id.add(new ListCityMain(1216311,""));
        cities_id.add(new ListCityMain(1513131,""));
        cities_id.add(new ListCityMain(1484843,""));
        cities_id.add(new ListCityMain(1512473,""));
        cities_id.add(new ListCityMain(1514210,""));
    }
    private ObservableSource<ListCityMain> getNameCity(final ListCityMain listCityMain) {
        return apiService.getPrice(listCityMain.getId(),"c2989af15ca926a633683688294c21b7")
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<WeatherData, ListCityMain>() {
                    @Override
                    public ListCityMain apply(WeatherData weatherData) throws Exception {
                        weatherDataList.add(weatherData);
                        listCityMain.setCelsius((int)(weatherData.getList().get(0).getMain().getTemp()-273.15));
                        listCityMain.setWeatherCondition(weatherData.getList().get(0).getWeather().get(0).getDescription());
                        listCityMain.setName(weatherData.getCity().getName());
                        return listCityMain;
                    }
                });
    }


    @Override
    public void onTicketSelected(int currentPosition) {
        int listSize = weatherDataList.size();
        if (listSize > currentPosition){
            CurrentCity.weatherData = weatherDataList.get(currentPosition);
            if (CurrentCity.weatherData != null){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }

        }else {
            Toast.makeText(this, "data is empty", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compostieDisposable.dispose();
    }
}
