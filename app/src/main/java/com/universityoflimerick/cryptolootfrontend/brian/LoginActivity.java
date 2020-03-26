package com.universityoflimerick.cryptolootfrontend.brian;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.auth0.android.Auth0;
import com.auth0.android.Auth0Exception;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.authentication.storage.SecureCredentialsManager;
import com.auth0.android.authentication.storage.SharedPreferencesStorage;
import com.auth0.android.provider.AuthCallback;
import com.auth0.android.provider.VoidCallback;
import com.auth0.android.provider.WebAuthProvider;
import com.auth0.android.result.Credentials;
import com.universityoflimerick.cryptolootfrontend.R;


public class LoginActivity extends AppCompatActivity {

    private Auth0 auth0;
    private SecureCredentialsManager credentialsManager; //bob

    public static final String EXTRA_CLEAR_CREDENTIALS = "com.auth0.CLEAR_CREDENTIALS";
    public static final String EXTRA_ACCESS_TOKEN = "com.auth0.ACCESS_TOKEN";
    private static final String API_URL = "https://dev-4d3z8kfx.eu.auth0.com/";// https://dev-4d3z8kfx.eu.auth0.com/  /localhost:3010/public/api
    private static final String API_IDENTIFIER = "https://cryptoloot/api";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        System.out.println("BRIENER: LoginActivity.onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
//                testCall();
            }
        });
        auth0 = new Auth0(this);
        auth0.setOIDCConformant(true);
        credentialsManager = new SecureCredentialsManager(this, new AuthenticationAPIClient(auth0), new SharedPreferencesStorage(this)); //bob

        //Check if the activity was launched to log the user out
        if (getIntent().getBooleanExtra(EXTRA_CLEAR_CREDENTIALS, false)) {
            logout();
        }

//        if (credentialsManager.hasValidCredentials()) {
//            // Obtain the existing credentials and move to the next activity
//            showNextActivity();
//            System.out.println("LoginActivity.onCreate has valid credentials!!!!");
//        }
    }


    private void login() {
//        String url = "https://dev-4d3z8kfx.eu.auth0.com/authorize?response_type=code&code_challenge=E9Melhoa2OwvFrEMTJguCHaoeK1t8URWbuGJSstw-cM&"
//                + "code_challenge_method=S256&client_id=BJlrGX0iqc4kGrILvb0JDRaTuRZvJpnQ&redirect_uri=https://www.google.com/&scope=openid%20profile&state=xyzABC123";
        WebAuthProvider.login(auth0)
                .withScheme("demo")
//                .withAudience(API_IDENTIFIER)
                .withScope("openid offline_access")
                .withAudience(String.format("https://%s/userinfo", getString(R.string.com_auth0_domain)))
                .start(this, new AuthCallback() {
                    @Override
                    public void onFailure(@NonNull final Dialog dialog) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(final AuthenticationException exception) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "Error: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onSuccess(@NonNull final Credentials credentials) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra(EXTRA_ACCESS_TOKEN, credentials.getAccessToken());
                                credentialsManager.saveCredentials(credentials);
                                System.out.println("token: ");
                                System.out.println(credentials.getAccessToken());
                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                });
    }

    private void logout() {
        WebAuthProvider.logout(auth0)
                .withScheme("demo")
                .start(this, new VoidCallback() {
                    @Override
                    public void onSuccess(Void payload) {

                    }

                    @Override
                    public void onFailure(Auth0Exception error) {
                        //Log out canceled, keep the user logged in
                        showNextActivity();
                    }
                });
    }

    private void showNextActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
