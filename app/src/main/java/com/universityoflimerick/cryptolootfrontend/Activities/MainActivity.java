package com.universityoflimerick.cryptolootfrontend.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;

import com.universityoflimerick.cryptolootfrontend.Adapter.CustomTabsHelper;
import com.universityoflimerick.cryptolootfrontend.R;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import static org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString;

public class MainActivity extends AppCompatActivity {
    private static final String API_URL = "https://dev-4d3z8kfx.eu.auth0.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button authBtn = findViewById(R.id.authorizeBtn);
        authBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    openCustomTab();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * openCustomTab
     * create cryptographically random key "code verifier" as well as hash digest "code challenge"
     * store code verifier in shared preferences
     * create CustomTab launching OAuth login uri, passing code challenge and client id
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public void openCustomTab() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        SecureRandom sr = new SecureRandom();
        byte[] code = new byte[32];
        sr.nextBytes(code);
        String verifier = Base64.encodeToString(code, Base64.URL_SAFE | Base64.NO_WRAP | Base64.NO_PADDING);

        byte[] bytes = verifier.getBytes("US-ASCII");
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(bytes, 0, bytes.length);
        byte[] digest = md.digest();
        //Use Apache "Commons Codec" dependency. Import the Base64 class
        String challenge = encodeBase64URLSafeString(digest);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("ShPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("verifier", verifier);
        editor.commit();

        CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder().build();
        customTabsIntent.intent.setPackage(CustomTabsHelper.getPackageNameToUse(this));
        customTabsIntent.launchUrl(this, Uri.parse("https://dev-4d3z8kfx.eu.auth0.com/authorize?response_type=code&client_id=vlx4Jr4CxWqIaMGn5wf0lOTx29ukbx8E&code_challenge=" + challenge +"&code_challenge_method=S256&redirect_uri=iamthewalrusdontyouthinkthejokerlaughsatyou://brian&scope=read:messages&audience=https://cryptoloot/api"));
    }
}
