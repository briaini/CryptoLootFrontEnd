package com.universityoflimerick.cryptolootfrontend.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.universityoflimerick.cryptolootfrontend.R;
import com.universityoflimerick.cryptolootfrontend.Adapter.CustomTabsHelper;
import com.universityoflimerick.cryptolootfrontend.Model.User.JwtObj;
import com.universityoflimerick.cryptolootfrontend.Utils.httpClient.ClientService;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CodeExchangeActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.universityoflimerick.cryptolootfrontend.brian.MESSAGE";
    public static final String EXTRA_MESSAGETWO = "com.universityoflimerick.cryptolootfrontend.brian.MESSAGETWO";

    //Retrieved in onCreate(), leave blank
    String code = "";
    //Retrieved in exchangeCode()
    private String accessToken = "";

    private Handler mHandler;
    private JwtObj jwt;

    private TextView authCodeTextView;
    private TextView accessCodeTextView;
    private TextView apiResponseTextView;
    private Button xBtn;
    private Button apiBtn;
    private Button profileBtn;
    private Button logoutBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_exchange);

        mHandler = new Handler(Looper.getMainLooper());

        SharedPreferences pref = getApplicationContext().getSharedPreferences("ShPref", 0);

        Intent intent = getIntent();

        authCodeTextView = findViewById(R.id.authCodeTextView);
        apiResponseTextView = findViewById(R.id.apiResponseTextView);
        accessCodeTextView = findViewById(R.id.accessCodeTextView);

        xBtn = findViewById(R.id.xBtn);
        apiBtn = findViewById(R.id.apiBtn);
        profileBtn = findViewById(R.id.profileBtn);
        logoutBtn = findViewById(R.id.logoutBtn);
        apiBtn.setEnabled(false);
        profileBtn.setEnabled(false);

        //First time arriving at CodeExchangeActivity, we need to retrieve the authorization code from intent and save it to SharedPreferences
        //If we come back to CodeExchangeActivity from child activity (ProfilePageActivity) and try to retrieve intent, will throw error
        //Instead we retrieve the authorization code and access token from SharedPreferences and ensure Exchange Button is disabled
        if(intent.getStringExtra(ProfilePageActivity.EXTRA_FROMPROFILE) == null){
            Uri data = intent.getData();

            String response = data.toString();
            String matcher = "?code=";
            int index = response.indexOf(matcher);
            index += matcher.length();
            code = response.substring(index);

            SharedPreferences.Editor editor = pref.edit();
            editor.putString("authCode", code); // Storing string
            editor.commit();
        } else {
            xBtn.setEnabled(false);
            apiBtn.setEnabled(true);
            profileBtn.setEnabled(true);

            code = pref.getString("authCode", null);
            jwt = new JwtObj();
            jwt.setAccess_token(pref.getString("accesstoken", null));
            jwt.setScope(pref.getString("scope", null));
            jwt.setExpires_in(pref.getString("expiresin", null));
            jwt.setToken_type(pref.getString("tokentype", null));
            accessCodeTextView.setText(jwt.getAccess_token());
        }

        //Set Auth Code Text View with auth code
        authCodeTextView.setText("Your authorization code: " + code);

        //Set button onClickListeners
        xBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exchangeCode();
            }
        });
        apiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    callApi();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profilePage();
            }
        });
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    /**
     * exchangeCode allows for exchanging auth code for access token
     * Need to retrieve and pass code verifier created at first step of authorization flow (before opening custom tab)
     */
    public void exchangeCode() {
        xBtn.setEnabled(false);
        apiBtn.setEnabled(true);
        profileBtn.setEnabled(true);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("ShPref", 0);
        String codeVerifier = pref.getString("verifier", null);

        //OAuth Provider exhange endpoint
        String url = "https://dev-4d3z8kfx.eu.auth0.com/oauth/token";
        String clientId = "vlx4Jr4CxWqIaMGn5wf0lOTx29ukbx8E";
        //Android app redirectUri, needs to be unique. Inspired by beatles song I was listening to at the time...
        String redirectUri = "iamthewalrusdontyouthinkthejokerlaughsatyou://brian";

        //Parameters needed to be passed to exchange endpoint
        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("grant_type", "authorization_code")
                .add("code_verifier", codeVerifier)
                .add("code", code)
                .add("client_id", clientId)
                .add("redirect_uri", redirectUri);

        RequestBody formBody = formBuilder.build();

        Request request = new Request.Builder()
                .post(formBody)
                .url(url)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();

        //Retrieve http client and enqueue call
        //Call saveAccessToken() upon successful response
        ClientService.client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Failed call");
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful())
                    throw new IOException("Error exchanging auth code for access token " + response);

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            saveAccessToken(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    /**
     * saveAccessToken() maps jwt retrieved in form of json string to JwtObj
     * Set jwt textView to display retrieved jwt
     * Save jwt info to shared preferences
     * @param response response retrieved from calling auth code for access token exchange endpoint
     */
    public void saveAccessToken(String response) {
        Gson gson = new Gson();
        jwt = gson.fromJson(response, JwtObj.class);
        accessToken = jwt.getAccess_token();
        accessCodeTextView.setText(accessToken);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("ShPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("accesstoken", jwt.getAccess_token());
        editor.putString("scope", jwt.getScope());
        editor.putString("expiresin", jwt.getExpires_in());
        editor.putString("tokentype", jwt.getToken_type());
        editor.commit();
    }

    public String getJwtHeader(){
        return "Bearer " + jwt.getAccess_token();
    }

    /**
     * callApi allows for testing jwt on secured backend endpoint
     * upon successful response, uses thread to call saveApiResponse
     */
    public void callApi() throws Exception {
        String url = "http://"+getString(R.string.ipaddress)+":8080/api/test";
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", getJwtHeader())
                .build();

        ClientService.client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response2) throws IOException {
                if (!response2.isSuccessful())
                    throw new IOException("Failed API call " + response2);

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            saveApiResponse(response2.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    /**
     * sets textView with response "hello"
     * @param test response from calling secured test endpoint
     */
    public void saveApiResponse(String test) {
        apiResponseTextView.setText(test);
    }

    /**
     * profilePage calls secured endpoint to retrieve profile info
     * if successful, calls profilePageActivity method
     */
    public void profilePage(){
        String url = "http://"+getString(R.string.ipaddress)+":8080/api/profile";
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", getJwtHeader())
                .build();

        ClientService.client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful())
                    throw new IOException("Failed API call " + response);
                profilePageActivity(response.body().string());
            }
        });
    }

    /**
     * profilePageActivity puts profile data in intent and starts ProfilePageActivity
     * @param pageDetails contains data related to profile
     */
    public void profilePageActivity(String pageDetails){
        Intent intent = new Intent(this, ProfilePageActivity.class);
        intent.putExtra(EXTRA_MESSAGE, pageDetails);
        intent.putExtra(EXTRA_MESSAGETWO, getJwtHeader());
        startActivity(intent);
    }

    /**
     * creates CustomTab to call OAuth provider, forcing complete logout.
     * Apps rarely log out a user from the actual OAuth provider, however, it is possible
     */
    public void logout() {
        String url = "https://dev-4d3z8kfx.eu.auth0.com/v2/logout?federated&client_id=vlx4Jr4CxWqIaMGn5wf0lOTx29ukbx8E&returnTo=itsabeatlessongman://logmeoutplease";

        CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder().build();
        String packageName = CustomTabsHelper.getPackageNameToUse(this);
        customTabsIntent.intent.setPackage(packageName);
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }
}
