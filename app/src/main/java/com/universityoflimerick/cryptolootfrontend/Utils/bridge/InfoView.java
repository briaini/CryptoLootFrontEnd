package com.universityoflimerick.cryptolootfrontend.Utils.bridge;

import android.content.Context;

import java.io.Serializable;
import java.util.Map;

public class InfoView extends CoinInfo implements Serializable {
    private int image;
    private String title;
    private String desc;
    private long num;

    public InfoView(Draw infoViewDraw, String title, int image, String desc, long num)
    {
        super(infoViewDraw);
        this.image = image;
        this.title = title;
        this.desc = desc;
        this.num = num;
    }

    @Override
    public Map<String, Object> drawView()
    {
        return drawInfoView(title, image, desc, num);
    }
}
