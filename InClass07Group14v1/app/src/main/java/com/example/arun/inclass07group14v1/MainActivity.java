package com.example.arun.inclass07group14v1;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.example.arun.inclass07group14v1.R.id.switch1;

public class MainActivity extends AppCompatActivity implements ITuneSearchAsync.IMusicData,MyAppRVAdapter.ICallBack{
    ArrayList<ItuneApp> musicResults= new ArrayList<ItuneApp>();
    ArrayList<ItuneApp>afterRemovingDbList = new ArrayList<ItuneApp>();
    DataBaseManager dm;
    ProgressDialog pd;
    boolean sortvalue;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    TextView switchTitle;
    Switch switch1;
    ItuneApp result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dm= new DataBaseManager(this);
        sortvalue=true;

        new ITuneSearchAsync(MainActivity.this).execute();

        switchTitle  =(TextView) findViewById(R.id.textViewSwitchName);



        mRecyclerView=(RecyclerView)findViewById(R.id.my_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<ItuneApp> dblist= (ArrayList<ItuneApp>) dm.getAllNotes();

        // specify an adapter (see also next example)
        mAdapter = new MyAppRVAdapter(dblist,dm,this);
        mRecyclerView.setAdapter(mAdapter);

        ImageButton refreshbutton= (ImageButton)findViewById(R.id.imageButtonRefresh);

        refreshbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismissProgressDialog();
                new ITuneSearchAsync(MainActivity.this).execute();
                Log.d("Demo","refreshed items");

            }
        });


        switch1 =(Switch)findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sortvalue=b;

                if(!b)
                {
                    Collections.sort(musicResults, new MainActivity.CustomComparator());
                    Collections.reverse(musicResults);
                    ListView listview = (ListView) findViewById(R.id.listView);
                    ItunesAdapter adapter = new ItunesAdapter(MainActivity.this, R.layout.list_layout,musicResults,dm);
                    listview.setAdapter(adapter);
                    adapter.setNotifyOnChange(true);
                    switchTitle.setText("Descending");
                }else
                {
                    Collections.sort(musicResults, new MainActivity.CustomComparator());
                    ListView listview = (ListView) findViewById(R.id.listView);
                    ItunesAdapter adapter = new ItunesAdapter(MainActivity.this, R.layout.list_layout,musicResults,dm);
                    listview.setAdapter(adapter);
                    adapter.setNotifyOnChange(true);


                    switchTitle.setText("Ascending");
                }
            }
        });


    }

    @Override
    public void setMusicSearchResult(ArrayList<ItuneApp> tracks) {


       musicResults=tracks;
        dismissProgressDialog();

        Log.d("Demo",tracks.toString());
        musicResults = getResultsAfterRemovingFromDbItems();
        switchTitle.setText("Ascending");
        switch1.setChecked(true);
        Collections.sort(musicResults, new MainActivity.CustomComparator());
        ListView listview = (ListView) findViewById(R.id.listView);

        ItunesAdapter adapter = new ItunesAdapter(this, R.layout.list_layout, musicResults,dm);
        listview.setAdapter(adapter);
        adapter.setNotifyOnChange(true);

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            public boolean onItemLongClick(AdapterView<?> arg0, View v, int index, long arg3)

            {
                dm.saveNote(musicResults.get(index));
                RecyclerView recyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);

                ArrayList<ItuneApp> dblist= (ArrayList<ItuneApp>) dm.getAllNotes();
                // ArrayAdapter<Color> adapter= new ArrayAdapter<Color>(this,android.R.layout.simple_list_item_1,colors);
                MyAppRVAdapter adapter = new MyAppRVAdapter(dblist,dm,MainActivity.this);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();


                musicResults.remove(musicResults.get(index));
                ListView listview = (ListView) findViewById(R.id.listView);
                ItunesAdapter adapter1 = new ItunesAdapter(MainActivity.this, R.layout.list_layout,musicResults,dm);
                listview.setAdapter(adapter1);
                adapter1.setNotifyOnChange(true);

                return  true;
            } });



    }

    public  ArrayList<ItuneApp> getResultsAfterRemovingFromDbItems(){

        afterRemovingDbList=new ArrayList<ItuneApp>();
        for (ItuneApp app: musicResults){
            ItuneApp iapp = app;
            String iname = iapp.getName();
            Double iprice = iapp.getPrice();
            if (dm.getNote(iname, iprice) ==null ) {
                afterRemovingDbList.add(app);
            }
        }
        musicResults = afterRemovingDbList;
        return afterRemovingDbList;
    }
    @Override
    public void startProgressDialog() {

        pd = new ProgressDialog(this);
        pd.setMessage("Loading...");
        pd.show();
        pd.setCancelable(false);


        ImageButton buttonRefresh = (ImageButton)findViewById(R.id.imageButtonRefresh);
        TextView textViewSwitchName = (TextView)findViewById(R.id.textViewSwitchName);
        switch1 = (Switch)findViewById(R.id.switch1);
        ListView listView = (ListView)findViewById(R.id.listView);
        TextView textView2 = (TextView)findViewById(R.id.textView2);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);

        buttonRefresh.setVisibility(View.INVISIBLE);
        textViewSwitchName.setVisibility(View.INVISIBLE);
        switch1.setVisibility(View.INVISIBLE);
        listView.setVisibility(View.INVISIBLE);
        textView2.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    private void dismissProgressDialog(){
        ImageButton buttonRefresh = (ImageButton)findViewById(R.id.imageButtonRefresh);
        TextView textViewSwitchName = (TextView)findViewById(R.id.textViewSwitchName);
        switch1 = (Switch)findViewById(R.id.switch1);
        ListView listView = (ListView)findViewById(R.id.listView);
        TextView textView2 = (TextView)findViewById(R.id.textView2);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);

        buttonRefresh.setVisibility(View.VISIBLE);
        textViewSwitchName.setVisibility(View.VISIBLE);
        switch1.setVisibility(View.VISIBLE);
        listView.setVisibility(View.VISIBLE);
        textView2.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);

        pd.dismiss();
    }

    @Override
    public void addsortItems(ItuneApp s) {
        result = s;
        musicResults.add(result);
        if(!sortvalue) {
            Collections.sort(musicResults, new MainActivity.CustomComparator());
            Collections.reverse(musicResults);
            ListView listview = (ListView) findViewById(R.id.listView);
            ItunesAdapter adapter1 = new ItunesAdapter(MainActivity.this, R.layout.list_layout,musicResults,dm);
            listview.setAdapter(adapter1);
            adapter1.setNotifyOnChange(true);
        }
        else
        {
            Collections.sort(musicResults, new MainActivity.CustomComparator());
            ListView listview = (ListView) findViewById(R.id.listView);
            ItunesAdapter adapter1 = new ItunesAdapter(MainActivity.this, R.layout.list_layout,musicResults,dm);
            listview.setAdapter(adapter1);
            adapter1.setNotifyOnChange(true);
        }

    }

    // Collections.sort(appArrayList, new MainActivity.CustomComparator());
    private class CustomComparator implements Comparator<ItuneApp> {
        @Override
        public int compare(ItuneApp o1, ItuneApp o2) {
            return (o1.getPrice()==o2.getPrice())?0:(o1.getPrice()>o2.getPrice())?1:-1 ;
        }
    }

}
