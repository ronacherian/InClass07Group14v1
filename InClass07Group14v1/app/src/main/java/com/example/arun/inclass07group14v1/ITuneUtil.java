package com.example.arun.inclass07group14v1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Arun on 10/23/2017.
 */

public class ITuneUtil {





    public static ArrayList<ItuneApp> JSONApps(String in) throws JSONException {

        ArrayList<ItuneApp> appsList = new ArrayList<ItuneApp>();

        JSONObject parentObject = new JSONObject(in);

        JSONArray parentArray = parentObject.getJSONObject("feed").getJSONArray("entry");

        for (int i = 0; i < parentArray.length(); i++) {

            JSONObject finalObject = parentArray.getJSONObject(i);

            ItuneApp apps = new ItuneApp();

            if (null != finalObject.getJSONObject("im:name") && null != finalObject.getJSONObject("im:name").getString("label"))
                apps.setName(finalObject.getJSONObject("im:name").getString("label"));


            if (null != finalObject.getJSONArray("im:image")){
                JSONArray imageArray = finalObject.getJSONArray("im:image");
                for (int j = 0; j < imageArray.length(); j++) {

                    if (null != imageArray.getJSONObject(j).getJSONObject("attributes") && null != imageArray.getJSONObject(j).getJSONObject("attributes").getString("height")){
                        if ( imageArray.getJSONObject(j).getJSONObject("attributes").getString("height").equals("53")){
                            apps.setSmallImageURL(imageArray.getJSONObject(j).getString("label"));

                        }
                        if (imageArray.getJSONObject(j).getJSONObject("attributes").getString("height").equals("100")){
                            apps.setImageURL(imageArray.getJSONObject(j).getString("label"));

                        }
                    }

                }
            }


            if (null != finalObject.getJSONObject("im:price") && null != finalObject.getJSONObject("im:price").getJSONObject("attributes") && null != finalObject.getJSONObject("im:price").getJSONObject("attributes").getString("amount")){
                JSONObject jo=finalObject.getJSONObject("im:price");
                apps.setPrice(Double.parseDouble(finalObject.getJSONObject("im:price").getJSONObject("attributes").getString("amount")));

            }

            appsList.add(apps);
        }
        return appsList;
    }
}
