package shoaziz.example.com.weatherapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.util.List;

import shoaziz.example.com.weatherapplication.R;
import shoaziz.example.com.weatherapplication.model.Statistics;


public class StatisticsAdapter extends RecyclerView.Adapter<StatisticsAdapter.ViewHolder>    {
    Context context;
    List<Statistics> statisticsList;
    public StatisticsAdapter(Context context, List<Statistics> statistics) {
        this.statisticsList  = statistics;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_statistics, viewGroup, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        Statistics  statistics = statisticsList.get(i);
        viewHolder.textView.setText(statistics.getDay());
        viewHolder.txt_noon.setText(statistics.getNoon()+"");
        viewHolder.txt_night.setText(statistics.getNight()+"");
        viewHolder.progNoon.setProgress(statistics.getNoon());
        viewHolder.progNight.setProgress(statistics.getNight());
    }



    @Override
    public int getItemCount() {
        return statisticsList.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView,txt_noon, txt_night;
        ProgressBar progNoon, progNight;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.name_of_day);
            txt_night = itemView.findViewById(R.id.txt_night);
            txt_noon = itemView.findViewById(R.id.txt_noon);
            progNight = itemView.findViewById(R.id.progress_night);
            progNoon = itemView.findViewById(R.id.progress_noon);
        }


    }
}