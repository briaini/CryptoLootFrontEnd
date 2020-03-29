package com.universityoflimerick.cryptolootfrontend.brian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.universityoflimerick.cryptolootfrontend.R;
import com.universityoflimerick.cryptolootfrontend.adam.PaymentActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    private static final String API_URL = "https://dev-4d3z8kfx.eu.auth0.com";// https://dev-4d3z8kfx.eu.auth0.com/  /localhost:3010/public/api

    private static final String API_IDENTIFIER = "https://cryptoloot/api";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button loginButton = findViewById(R.id.logout);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        Button callBtn = findViewById(R.id.testCallBtn);
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getAccessToken();
            }
        });

        //Obtain the token from the Intent's extras
        String accessToken = getIntent().getStringExtra(LoginActivity.EXTRA_ACCESS_TOKEN);
        TextView textView = findViewById(R.id.credentials);
        textView.setText(accessToken);
        Button paymentBtn = findViewById(R.id.payment);
        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PaymentActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
//
//    public void getAccessToken(){
//        HttpResponse<String> response = Unirest.post("https://dev-4d3z8kfx.eu.auth0.com/oauth/token")
//                .header("content-type", "application/x-www-form-urlencoded")
//                .body("grant_type=authorization_code&client_id=%24%7Baccount.clientId%7D&code_verifier=YOUR_GENERATED_CODE_VERIFIER&code=YOUR_AUTHORIZATION_CODE&redirect_uri=%24%7Baccount.callback%7D")
//                .asString();
//    }

    public void tester() {
        System.out.println("Bob tester()");
        String accessToken = getIntent().getStringExtra(LoginActivity.EXTRA_ACCESS_TOKEN);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(API_URL+"/authorize")
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();
//        Request request = new Request.Builder()
//                .get()
//                .url(API_URL+"/userinfo")
//                .addHeader("Authorization", "Bearer " + accessToken)
//                .build();
        System.out.println("Prepare for call bob");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    // API call success
                    System.out.println("holy fuck bob");
                    System.out.println("bob you legend-response: " + response.toString());
                    System.out.println("bob you legend-response.body: " + response.body().string());

                } else {
                    // API call failed. Check http error code and message
                    System.out.println("bob, what the fuck man");
                }
            }
        });
    }

//    private void tester2(){
//        DataAPI loginAPI;
//        loginAPI = ServiceGenerator.createService(DataAPI.class);
//
//        Call<String> call = loginAPI.authenticateLogin();
//
//        try {
//            // TODO: handle loggedInUser authentication
//            Log.i("OAUTH","before enqueue");
//            call.enqueue(new Callback<String>() {
//                @Override
//                public void onResponse(Call<String>  call, Response<String> response) {
//                    if (!response.isSuccessful()){
//                                System.out.println("OAUTH: error logging in. Code: " + response.code());
//                    }
//                    if (response.body() != null) {
//                        Log.i("OAUTH.onSuccess", response.body().toString());
//
////                        if(response.body().containsValue("good")) {
////                            Log.i("onResponse", "success: " +response.body().toString());
////                            setResult(Activity.RESULT_OK);
////
////                            //Complete and destroy login activity once successful
////                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
////                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
////                            startActivity(intent);
////                            finish();
////                        }
////                        else {
////                            Log.i("onResponse","fail: "+response.code() + response.body().toString());
////                        }
//                    }
//                }
//                @Override
//                public void onFailure(Call<String> call, Throwable t) {
//                    System.out.println("error in call");
//                }
//            });
//
//        } catch (Exception e) {
//            Log.i("Authentication.Error","do nothing");
//        }
//    }

    private void logout() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(LoginActivity.EXTRA_CLEAR_CREDENTIALS, true);
        startActivity(intent);
        finish();
    }

}
