package com.example.arun.inclass07group14v1;

import java.io.Serializable;

/**
 * Created by Arun on 10/23/2017.
 */

public class ItuneApp implements Serializable {


    String name;
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    String smallImageURL;
    String imageURL;
    double price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItuneApp ituneApp = (ItuneApp) o;

        if (Double.compare(ituneApp.price, price) != 0) return false;
        if (name != null ? !name.equals(ituneApp.name) : ituneApp.name != null) return false;
        if (smallImageURL != null ? !smallImageURL.equals(ituneApp.smallImageURL) : ituneApp.smallImageURL != null)
            return false;
        return imageURL != null ? imageURL.equals(ituneApp.imageURL) : ituneApp.imageURL == null;

    }


    public String getSmallImageURL() {
        return smallImageURL;
    }

    public void setSmallImageURL(String smallImageURL) {
        this.smallImageURL = smallImageURL;
    }

    public String getImageURL() {

        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
