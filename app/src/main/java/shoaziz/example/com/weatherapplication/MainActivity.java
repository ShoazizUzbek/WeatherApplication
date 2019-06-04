package shoaziz.example.com.weatherapplication;

import android.graphics.Typeface;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.marcinmoskala.arcseekbar.ArcSeekBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.Timed;
import shoaziz.example.com.weatherapplication.adapters.StatisticsAdapter;
import shoaziz.example.com.weatherapplication.model.CurrentCity;
import shoaziz.example.com.weatherapplication.model.ListWeather;
import shoaziz.example.com.weatherapplication.model.Statistics;
import shoaziz.example.com.weatherapplication.model.Weather;
import shoaziz.example.com.weatherapplication.model.WeatherData;

public class MainActivity extends AppCompatActivity {
    ArcSeekBar arcSeekBar;
    ConstraintLayout constraintLayout;
    RelativeLayout relativeLayout;
    TextView celsius, condition, wind, time, title,date_txt;
    RecyclerView recyclerView;
    StatisticsAdapter statisticsAdapter;
    ScrollView scrollView;
    ListWeather firstWeather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WeatherData currentWeatherData = CurrentCity.weatherData;
        List<ListWeather> wholeList = currentWeatherData.getList();

        recyclerView = findViewById(R.id.recycler);

        List<Statistics>  statistics = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));


        firstWeather = wholeList.get(0);

        Date dateFirst = new Date ();
        dateFirst.setTime((long)firstWeather.getDt()*1000);
        String localizedDate = DateUtils.formatDateTime(this, dateFirst.getTime(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_WEEKDAY);

        boolean check = false;
        String firstDate = localizedDate;
        for (ListWeather l :
                wholeList) {
            Date date = new Date ();
            date.setTime((long)l.getDt()*1000);
            String tempDate = DateUtils.formatDateTime(this, date.getTime(),
                    DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_WEEKDAY);
            if (!firstDate.equals(tempDate)){
                int noon = (int)(l.getMain().getTempMax()-273.15);
                int night = (int)(l.getMain().getTempMin()-273.15);
                statistics.add(new Statistics(tempDate.substring(0,2),noon,night));
                firstDate = tempDate;
            }

        }
        statisticsAdapter = new StatisticsAdapter(this,statistics);
        recyclerView.setAdapter(statisticsAdapter);

        relativeLayout = findViewById(R.id.city_bar);

        constraintLayout = findViewById(R.id.date_layout);


        //layout comes from leftside with animation
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.date_anim);
        constraintLayout.startAnimation(animation);
        arcSeekBar = findViewById(R.id.seek_bar);
        arcSeekBar.setProgress(50);

        //we should make untouchable seekbar
        arcSeekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });


        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/RobotoSlab-Regular.ttf");
        Typeface custom_font_bold = Typeface.createFromAsset(getAssets(),  "fonts/RobotoSlab-Regular.ttf");


        celsius = findViewById(R.id.celsius);
        int cel = (int)(firstWeather.getMain().getTemp()-273.15);
        celsius.setText(String.valueOf(cel));
        condition = findViewById(R.id.condition);
        String description = firstWeather.getWeather().get(0).getDescription();
        condition.setText(description);
        date_txt = findViewById(R.id.date);
        wind  = findViewById(R.id.wind);
        wind.setText(String.valueOf(firstWeather.getWind().getSpeed())+" km/h");
        time = findViewById(R.id.time);
        title = findViewById(R.id.city_title);
        title.setText(currentWeatherData.getCity().getName());
        celsius.setTypeface(custom_font);
        condition.setTypeface(custom_font);
        wind.setTypeface(custom_font);
        time.setTypeface(custom_font);
        title.setTypeface(custom_font_bold);
        date_txt.setTypeface(custom_font_bold);

        date_txt.setText(localizedDate);
        updateEverySecond();




        }
    void updateEverySecond(){
        Disposable timer = Observable.interval(1000L, TimeUnit.MILLISECONDS)
                .timeInterval()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Timed<Long>>() {
                    @Override
                    public void accept(Timed<Long> longTimed) throws Exception {
                        String currentDateAndTime = new SimpleDateFormat("HH:mm:ss").format(new Date());
                        time.setText(currentDateAndTime);
                    }
                });
    }


}
