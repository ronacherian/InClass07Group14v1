package com.example.arun.inclass07group14v1;

/**
 * Created by Arun on 10/23/2017.
 */

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Arun on 10/8/2017.
 */

public class ItunesAdapter extends ArrayAdapter<ItuneApp> {

    List<ItuneApp> trackList;
    int resource;
    Context context;
   // SharedPreference sharedPreference;
    boolean isfromFavorites;
    //boolean isfromMainActivity;

    DataBaseManager dm;
    public ItunesAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ItuneApp> objects,DataBaseManager dm) {
        super(context, resource, objects);
        Log.d("Context",""+context);
        this.context = context;
        this.trackList = objects;
        this.resource = resource;
        this.isfromFavorites=false;
        this.dm=dm;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final int position1 = position;
        ItuneApp iapp = trackList.get(position);
        String iname = iapp.getName();
        Double iprice = iapp.getPrice();

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(resource, parent, false);

        }

            ImageView imageView = (ImageView) convertView.findViewById(R.id.imageViewSmall);
            Picasso.with(context).load(trackList.get(position).getSmallImageURL()).into(imageView);

            TextView textViewAppName = (TextView)convertView.findViewById(R.id.textViewSwitchName);
            TextView textViewPriceValue = (TextView)convertView.findViewById(R.id.textViewPricevalue);
            ImageView imageViewPriceIcon = (ImageView)convertView.findViewById(R.id.imageView3);

            textViewAppName.setText(trackList.get(position).getName());
            textViewPriceValue.setText(trackList.get(position).getPrice() + "");
            Double priceVal = trackList.get(position).getPrice();
            if (priceVal >= 0 && priceVal <= 1.99) {
                imageViewPriceIcon.setImageResource(R.drawable.price_low);

            } else if (priceVal >= 2 && priceVal <= 5.99) {
                imageViewPriceIcon.setImageResource(R.drawable.price_medium);
            } else {
                imageViewPriceIcon.setImageResource(R.drawable.price_high);
            }

            return convertView;


    }

}
