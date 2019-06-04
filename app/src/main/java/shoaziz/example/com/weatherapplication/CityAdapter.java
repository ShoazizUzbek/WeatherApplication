package shoaziz.example.com.weatherapplication;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;

import java.util.List;

import shoaziz.example.com.weatherapplication.model.ListCityMain;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.MyViewHolder> {
    private Context context;
    private List<ListCityMain> contactList;
    private TicketsAdapterListener listener;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView  textView;
        SpinKitView spinKitView;
        TextView weatherCondition;
        TextView celsius;
        ImageView imageView;
        public MyViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.city_name);
            spinKitView = view.findViewById(R.id.loader);
            weatherCondition = view.findViewById(R.id.weather_condition);
            celsius = view.findViewById(R.id.celsius_in_list);
            imageView  = view.findViewById(R.id.celsius_icon);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onTicketSelected(getAdapterPosition());
                }
            });
        }
    }
    public CityAdapter(Context context, List<ListCityMain> contactList, TicketsAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.contactList = contactList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_city, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        ListCityMain city = contactList.get(i);
        myViewHolder.weatherCondition.setText(city.getWeatherCondition());
        myViewHolder.celsius.setText(city.getCelsius()+"");
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "fonts/RobotoSlab-Regular.ttf");
        Typeface custom_font_bold = Typeface.createFromAsset(context.getAssets(),  "fonts/RobotoSlab-Regular.ttf");
        myViewHolder.textView.setTypeface(custom_font_bold);
        myViewHolder.weatherCondition.setTypeface(custom_font);
        if (!city.getName().equals("") ) {
            myViewHolder.textView.setText(city.getName());
            myViewHolder.spinKitView.setVisibility(View.INVISIBLE);
            myViewHolder.imageView.setVisibility(View.VISIBLE);

        } else  {
            myViewHolder.imageView.setVisibility(View.INVISIBLE);
            myViewHolder.spinKitView.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public interface TicketsAdapterListener {
        void onTicketSelected(int i);
    }
}
