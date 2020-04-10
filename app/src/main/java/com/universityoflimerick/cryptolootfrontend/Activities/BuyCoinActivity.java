package com.universityoflimerick.cryptolootfrontend.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.universityoflimerick.cryptolootfrontend.Model.User.JwtObj;
import com.universityoflimerick.cryptolootfrontend.R;
import com.universityoflimerick.cryptolootfrontend.Utils.httpClient.ClientService;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BuyCoinActivity extends AppCompatActivity {
    private EditText purchaseAmountEditText;
    private Button purchaseCoinBtn;
    private Handler mHandler;
    private JwtObj jwt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_coin);

        mHandler = new Handler(Looper.getMainLooper());
        purchaseAmountEditText = findViewById(R.id.purchaseAmountEditText);
        purchaseCoinBtn = findViewById(R.id.purchaseCoinBtn);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("ShPref", 0);
        jwt = new JwtObj();
        jwt.setAccess_token(pref.getString("accesstoken", null));

        purchaseCoinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                purchaseCoin();
            }
        });
    }

    public String getJwtHeader(){
        return "Bearer " + jwt.getAccess_token();
    }

    public void purchaseCoin() {
        String url = "http://"+getString(R.string.ipaddress)+":8080/api/purchase";

        final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");
        JsonObject json = new JsonObject();
        json.addProperty("amount", purchaseAmountEditText.getText().toString());

        String jsonString = json.toString();

        RequestBody body = RequestBody.create(jsonString, JSON);
        Request request = new Request.Builder()
                .post(body)
                .url(url)
                .addHeader("Authorization", getJwtHeader())
                .build();

        ClientService.client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Failed call");
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful())
                    throw new IOException("Error purchasing coin " + response);
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

    public void showToast(String response) {
        Toast.makeText(this, response, Toast.LENGTH_LONG).show();
//        //Following code just closes keyboard. Overly complex thanks to Android API
        Activity activity = (Activity) this;
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
