package com.universityoflimerick.cryptolootfrontend.Utils.httpClient;

import okhttp3.OkHttpClient;

public class ClientService {
    public static OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new LoggingInterceptor())
            .build();
}
