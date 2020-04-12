package com.universityoflimerick.cryptolootfrontend.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.universityoflimerick.cryptolootfrontend.Model.User.ProfilePage;
import com.universityoflimerick.cryptolootfrontend.R;
import com.universityoflimerick.cryptolootfrontend.Utils.httpClient.ClientService;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProfilePageActivity extends AppCompatActivity {
    public static final String EXTRA_FROMPROFILE = "com.universityoflimerick.cryptolootfrontend.PROFILE";
    private EditText eT;
    private String jwt;
    private Handler mHandler;
    private ImageButton imageButton;
    private Button b1, b2, saveBtn, homeBtn, msgButton, buyCoinBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        mHandler = new Handler(Looper.getMainLooper());

        //Retrieve profile data and jwt passed in intent from CodeExchangeActivity
        Intent intent = getIntent();
        String message = intent.getStringExtra(CodeExchangeActivity.EXTRA_MESSAGE);
        jwt = intent.getStringExtra(CodeExchangeActivity.EXTRA_MESSAGETWO);

        eT = findViewById(R.id.profileNameEditText);
        imageButton = findViewById(R.id.imageButton);
        b1 = findViewById(R.id.button3);
        b2 = findViewById(R.id.button4);

        //If user has previously saved profile data
        //map data to ProfilePage object and set text view
        //else set text with default text
        if (message.contains("name")) {
            Gson gson = new Gson();
            ProfilePage profilePage = gson.fromJson(message, ProfilePage.class);
            eT.setText(profilePage.getName());
        } else {
            eT.setText("Save name if you wanna");
        }

        saveBtn = findViewById(R.id.saveProfileBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDetails();
            }
        });

        homeBtn = findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homePage();
            }
        });

        msgButton = findViewById(R.id.msgButton);
        msgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfilePageActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });

        buyCoinBtn = findViewById(R.id.buyCoinBtn);
        buyCoinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfilePageActivity.this, BuyCoinActivity.class);
                startActivity(intent);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfilePageActivity.this, TradeActivity.class);
                startActivity(intent);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfilePageActivity.this, PaymentActivity.class);
                startActivity(intent);
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfilePageActivity.this, QuickViewActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * saveDetails saves edited profile data
     */
    public void saveDetails() {
        String url = "http://"+getString(R.string.ipaddress)+":8080/api/userdetails";
        final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        //add profile data property to json object
        JsonObject json = new JsonObject();
        json.addProperty("name", eT.getText().toString());
        String jsonString = json.toString();

        //create request, passing json string as body
        RequestBody body = RequestBody.create(jsonString, JSON);
        Request request = new Request.Builder()
                .post(body)
                .url(url)
                .addHeader("Authorization", jwt)
                .build();

        //Retrieve http client and enqueue call
        ClientService.client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Failed call");
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful())
                    throw new IOException("Error saving profile " + response);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            showToast(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    /**
     * homePage creates intent and brings user back to CodeExchangeActivity
     * intent used to check if user arrived at CodeExchangeActivity from ProfilePageActivity
     */
    public void homePage(){
        Intent intent = new Intent(this, CodeExchangeActivity.class);
        intent.putExtra(EXTRA_FROMPROFILE, "true");
        startActivity(intent);
    }

    /**
     * showToast displays response from saving profile data
     * @param response backend response from saving profile data
     */
    public void showToast(String response) {
        Toast.makeText(this, response, Toast.LENGTH_LONG).show();
        //Following code just closes keyboard. Overly complex thanks to Android API
        Activity activity = (Activity) this;
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
