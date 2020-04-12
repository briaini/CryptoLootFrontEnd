package com.universityoflimerick.cryptolootfrontend.Utils.httpClient;

import okhttp3.OkHttpClient;

/**
 * creates static OkHttpClient to avoid creating client and adding interceptor for each api call
 */
public class ClientService {
    public static OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new LoggingInterceptor())
            .build();
}