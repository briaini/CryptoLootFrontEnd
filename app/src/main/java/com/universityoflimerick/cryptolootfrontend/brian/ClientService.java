package com.universityoflimerick.cryptolootfrontend.brian;

import okhttp3.OkHttpClient;

public class ClientService {
    public static OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new LoggingInterceptor())
            .build();
}
