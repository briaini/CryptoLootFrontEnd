package com.universityoflimerick.cryptolootfrontend.brian.ben.coinViews;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RipView implements Draw, Serializable{

    public Map<String, Object> drawInfoView(String title, int image, String description, long num) {
        Map<String, Object> map = new HashMap<>();
        map.put("Title", title);
        map.put("Image", image);
        map.put("Desc", description);
        map.put("num", num);
        return map;
    }

    @Override
    public Map<String, Object> drawQuickView(String title, int image) {
        Map<String, Object> map = new HashMap<>();
        map.put("Title", title);
        map.put("Image", image);
        return map;
    }

}
