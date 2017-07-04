package com.android.quieromas.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by lucas on 4/7/17.
 */
@IgnoreExtraProperties
public class DevelopmentItem {

    public String thumbnail;
    public String video;
    public String name;
    public int semana;

    public DevelopmentItem(){}

    public DevelopmentItem(String thumbnail, String video) {
        this.thumbnail = thumbnail;
        this.video = video;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getVideo() {
        return video;
    }

    public String getName() {
        return name;
    }

    public int getSemana(){return semana;}
}
