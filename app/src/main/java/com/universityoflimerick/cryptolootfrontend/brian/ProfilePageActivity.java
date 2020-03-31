package com.universityoflimerick.cryptolootfrontend.brian;

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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.universityoflimerick.cryptolootfrontend.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        mHandler = new Handler(Looper.getMainLooper());

        eT = findViewById(R.id.profileNameEditText);

        Intent intent = getIntent();
        String message = intent.getStringExtra(CodeExchangeActivity.EXTRA_MESSAGE);
        jwt = intent.getStringExtra(CodeExchangeActivity.EXTRA_MESSAGETWO);

        System.out.println("beforeGSON " + message);

        if (message.contains("name")) {
            Gson gson = new Gson();
            ProfilePage profilePage = gson.fromJson(message, ProfilePage.class);
            eT.setText(profilePage.getName());
        } else {
            eT.setText("Save name if you wanna");
        }

        Button saveBtn = findViewById(R.id.saveProfileBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDetails();
            }
        });

        Button homeBtn = findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homePage();
            }
        });
    }

    public void saveDetails() {
        String url = "http://"+getString(R.string.ipaddress)+":8080/api/userdetails";
        final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");
        JsonObject json = new JsonObject();
        json.addProperty("name", eT.getText().toString());
        String jsonString = json.toString();


        RequestBody body = RequestBody.create(jsonString, JSON);
        Request request = new Request.Builder()
                .post(body)
                .url(url)
                .addHeader("Authorization", jwt)
                .build();

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

    public void homePage(){
        Intent intent = new Intent(this, CodeExchangeActivity.class);
        intent.putExtra(EXTRA_FROMPROFILE, "true");
        startActivity(intent);
    }

    public void showToast(String response) {
        Toast.makeText(this, response, Toast.LENGTH_LONG).show();
        //Following code just closes keyboard. Overly complex thanks to Android API
        Activity activity = (Activity) this;
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
