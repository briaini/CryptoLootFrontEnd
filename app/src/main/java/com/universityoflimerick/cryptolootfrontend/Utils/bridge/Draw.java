package com.universityoflimerick.cryptolootfrontend.Utils.bridge;

import java.util.Map;

public interface Draw
{
    public abstract  Map<String, Object> drawInfoView(String title, int image, String description, long num);
    public abstract  Map<String, Object> drawQuickView(String title, int image);
}
