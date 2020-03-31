package com.universityoflimerick.cryptolootfrontend.brian;

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

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
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

    private TextView authCodeTextView;
    private TextView accessCodeTextView;
    private TextView apiResponseTextView;
    private Button xBtn;
    private Button apiBtn;
    private Button profileBtn;
    private Button logoutBtn;


    private JwtObj jwt;

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

        authCodeTextView.setText("Your authorization code: " + code);

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

    public void exchangeCode() {
        xBtn.setEnabled(false);
        apiBtn.setEnabled(true);
        profileBtn.setEnabled(true);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("ShPref", 0);
        String codeVerifier = pref.getString("verifier", null);

        System.out.print("codeverifier " + codeVerifier);

        String url = "https://dev-4d3z8kfx.eu.auth0.com/oauth/token";
        String clientId = "vlx4Jr4CxWqIaMGn5wf0lOTx29ukbx8E";
        String redirectUri = "iamthewalrusdontyouthinkthejokerlaughsatyou://brian";

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


    public void callApi() throws Exception {
        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/api/test")
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

    public void saveApiResponse(String test) {
        apiResponseTextView.setText(test);
    }

    public void profilePage(){
        System.out.println("going to profile page " + getJwtHeader());
        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/api/profile")
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

    public void profilePageActivity(String pageDetails){
        Intent intent = new Intent(this, ProfilePageActivity.class);
        intent.putExtra(EXTRA_MESSAGE, pageDetails);
        intent.putExtra(EXTRA_MESSAGETWO, getJwtHeader());
        startActivity(intent);
    }

    public void logout() {
        String url = "https://dev-4d3z8kfx.eu.auth0.com/v2/logout?federated&client_id=vlx4Jr4CxWqIaMGn5wf0lOTx29ukbx8E&returnTo=itsabeatlessongman://logmeoutplease";

        CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder().build();
        String packageName = CustomTabsHelper.getPackageNameToUse(this);
        customTabsIntent.intent.setPackage(packageName);
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }
}
