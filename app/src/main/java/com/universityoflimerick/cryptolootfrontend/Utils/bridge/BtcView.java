package com.universityoflimerick.cryptolootfrontend.Utils.bridge;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BtcView implements Draw, Serializable {

    /**
     * drawInfoView
     * @return a map which contains all information relating to bitcoin
     * @param title is a String for the name of the coin
     * @param image contains an int which represent the picture for this coin
     * @param description is a string of the coin description
     * @param num is a long which represents the bitcoin market cap
     */
    @Override
    public Map<String, Object> drawInfoView(String title, int image, String description, long num) {
        Map<String, Object> map = new HashMap<>();
        map.put("Title", title);
        map.put("Image", image);
        map.put("Desc", description);
        map.put("num", num);
        return map;
    }

    /**
     * drawQuickView
     * @return a map which contains all information relating to bitcoin
     * @param title is a String for the name of the coin
     * @param image contains an int which represent the picture for this coin
     */
    @Override
    public Map<String, Object> drawQuickView(String title, int image) {
        Map<String, Object> map = new HashMap<>();
        map.put("Title", title);
        map.put("Image", image);
        return map;
    }

}
