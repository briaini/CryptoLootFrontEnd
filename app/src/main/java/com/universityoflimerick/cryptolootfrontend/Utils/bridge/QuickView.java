package com.universityoflimerick.cryptolootfrontend.Utils.bridge;

import android.content.Context;

import java.io.Serializable;
import java.util.Map;

public class QuickView extends CoinInfo implements Serializable {
    private int image;
    private String title;
    private Context context;

    public QuickView(Draw quickViewDraw, String title, int image)
    {
        super(quickViewDraw);
        this.image = image;
        this.title = title;
        this.context = context;
    }

    @Override
    public Map<String, Object> drawView()
    {
        return drawQuickView(title, image);
    }
}
