package com.example.arun.inclass07group14v1;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

//import static com.example.arun.inclass07group14v1.R.id.imageView;

/**
 * Created by Arun on 10/23/2017.
 */

public class MyAppRVAdapter extends RecyclerView.Adapter<MyAppRVAdapter.ViewHolder>{



    ArrayList<ItuneApp> mData;
    static DataBaseManager dm;
    static ICallBack callback;

    // Context context;

    static MainActivity mainActivity;

    public MyAppRVAdapter(ArrayList<ItuneApp> mData, DataBaseManager dm, MainActivity mainActivity) {
        this.mData = mData;
        this.dm=dm;
        this.mainActivity=mainActivity;
        callback = (ICallBack) mainActivity;

    }


    @Override
    public MyAppRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View emailView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_layout, parent, false);
        MyAppRVAdapter.ViewHolder viewHolder = new MyAppRVAdapter.ViewHolder(emailView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyAppRVAdapter.ViewHolder holder, int position) {


        ItuneApp ituneApp = mData.get(position);
        holder.ituneApp = ituneApp;
        holder.appName.setText(ituneApp.getName());

       if(ituneApp.getPrice()>0)
       {
           holder.priceVal.setText(ituneApp.getPrice()+"");

           Double priceVal=mData.get(position).getPrice();
           if(priceVal >=0 && priceVal <=1.99)
           {
               holder.priceIcon.setImageResource(R.drawable.price_low);

           }
           else if(priceVal >=2 && priceVal <=5.99)
           {
               holder.priceIcon.setImageResource(R.drawable.price_medium);
           }
           else
           {
               holder.priceIcon.setImageResource(R.drawable.price_high);
           }

       }


        if(ituneApp.getImageURL() != null && ituneApp.getImageURL().length()>0)
                Picasso.with(mainActivity).load(mData.get(position).getImageURL()).into(holder.largeImage);


    }

    @Override
    public int getItemCount() {
        if (mData.size() == 0){
            mainActivity.findViewById(R.id.textViewNoFilteredApp).setVisibility(View.VISIBLE);
            mainActivity.findViewById(R.id.my_recycler_view).setVisibility(View.INVISIBLE);
        } else {
            mainActivity.findViewById(R.id.textViewNoFilteredApp).setVisibility(View.INVISIBLE);
            mainActivity.findViewById(R.id.my_recycler_view).setVisibility(View.VISIBLE);
        }
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView appName;
        TextView priceVal;

        ImageView largeImage;
        ImageView priceIcon;
        ImageButton deletebutton;
        ItuneApp ituneApp;

        public ViewHolder(View itemView) {
            super(itemView);
            appName = (TextView)itemView.findViewById(R.id.textViewSwitchName);
            priceVal = (TextView)itemView.findViewById(R.id.textViewPriceValue);
            largeImage=(ImageView) itemView.findViewById(R.id.imageViewBig);
            priceIcon=(ImageView) itemView.findViewById(R.id.imageViewPriceimg);

            deletebutton=(ImageButton)itemView.findViewById(R.id.imageButtonDelete);

            deletebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    dm.deleteNote(ituneApp);


                    RecyclerView recyclerView = (RecyclerView) mainActivity.findViewById(R.id.my_recycler_view);

                    ArrayList<ItuneApp> dblist= (ArrayList<ItuneApp>) dm.getAllNotes();
                    // ArrayAdapter<Color> adapter= new ArrayAdapter<Color>(this,android.R.layout.simple_list_item_1,colors);
                    MyAppRVAdapter adapter = new MyAppRVAdapter(dblist,dm,mainActivity);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    callback.addsortItems(ituneApp);

//                    mainActivity.musicResults.add(ituneApp);
//                    ListView listview = (ListView)mainActivity.findViewById(R.id.listView);
//                    ItunesAdapter adapter1 = new ItunesAdapter(mainActivity, R.layout.list_layout,mainActivity.musicResults,dm);
//                    listview.setAdapter(adapter1);
//                    adapter1.setNotifyOnChange(true);



                }
            });

        }
    }
    public interface ICallBack {
        void addsortItems(ItuneApp s);
    }
}
