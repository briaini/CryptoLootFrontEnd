package com.universityoflimerick.cryptolootfrontend.ben.bridge;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.universityoflimerick.cryptolootfrontend.R;
import com.universityoflimerick.cryptolootfrontend.ben.bridge.coinViews.LtcView;
import com.universityoflimerick.cryptolootfrontend.ben.bridge.coinViews.RipView;
import com.universityoflimerick.cryptolootfrontend.ben.bridge.coinViews.BtcView;
import com.universityoflimerick.cryptolootfrontend.ben.bridge.coinViews.EthView;

import java.util.Map;

public class InfoViewActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_view);
        CoinInfo view = (CoinInfo) getIntent().getSerializableExtra("view");
        TextView t1,t2,t3;
        ImageView i;
        t1 = findViewById(R.id.textView4);
        t2 = findViewById(R.id.textView5);
        t3 = findViewById(R.id.textView6);
        i = findViewById(R.id.imageView2);

        //drawView returns a map which contains the relevant information for each coin
        Map<String, Object> drawing = view.drawView();

        //Setting the view using the information from the map
        t1.setText((String) drawing.get("Title"));
        i.setImageResource((int) drawing.get("Image"));
        t2.setText((String) drawing.get("Desc"));

        //Checking if there are different instances of CoinInfo and dealing with them in different ways
        if(view.myDrawing instanceof BtcView){
            t3.setText("Bitcoin Market Cap:\n" + drawing.get("num").toString());
        }
        else if(view.myDrawing instanceof EthView){
            t3.setText("Change in last 30 days:\n " + drawing.get("num").toString() + "%");
        }
        else if(view.myDrawing instanceof LtcView){
            t3.setText("Percentage of users that own Litecoin:\n " + drawing.get("num").toString() + "%");
        }
        else if(view.myDrawing instanceof RipView){
            t3.setText("Price:\n $" + drawing.get("num").toString());
        }
    }
}
