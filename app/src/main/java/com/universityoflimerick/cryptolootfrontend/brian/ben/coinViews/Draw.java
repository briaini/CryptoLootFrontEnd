package com.universityoflimerick.cryptolootfrontend.brian.ben.coinViews;

import java.util.Map;

public interface Draw
{
    abstract public Map<String, Object> drawInfoView(String title, int image, String description, long num);
    abstract public Map<String, Object> drawQuickView(String title, int image);
}
