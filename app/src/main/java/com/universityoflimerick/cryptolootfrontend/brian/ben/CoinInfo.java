package com.universityoflimerick.cryptolootfrontend.brian.ben;

import com.universityoflimerick.cryptolootfrontend.brian.ben.coinViews.Draw;

import java.io.Serializable;
import java.util.Map;

public abstract class CoinInfo implements Serializable {
    protected Draw myDrawing;
    abstract public Map<String, Object> drawView();

    protected CoinInfo(Draw myDrawing)
    {
        this.myDrawing = myDrawing;
    }

    protected  Map<String, Object> drawQuickView(String title, int image){
        return myDrawing.drawQuickView(title, image);
    }

    protected  Map<String, Object> drawInfoView(String title, int image, String desc, long num){
        return myDrawing.drawInfoView(title, image, desc, num);
    }
}
