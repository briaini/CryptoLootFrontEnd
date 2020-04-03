package com.universityoflimerick.cryptolootfrontend.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.universityoflimerick.cryptolootfrontend.R;
import com.universityoflimerick.cryptolootfrontend.Utils.bridge.CoinInfo;
import com.universityoflimerick.cryptolootfrontend.Utils.bridge.InfoView;
import com.universityoflimerick.cryptolootfrontend.Utils.bridge.InfoViewActivity;
import com.universityoflimerick.cryptolootfrontend.Utils.bridge.QuickView;
import com.universityoflimerick.cryptolootfrontend.Adapter.CustomAdapter;
import com.universityoflimerick.cryptolootfrontend.Utils.bridge.BtcView;
import com.universityoflimerick.cryptolootfrontend.Utils.bridge.LtcView;
import com.universityoflimerick.cryptolootfrontend.Utils.bridge.RipView;
import com.universityoflimerick.cryptolootfrontend.Utils.bridge.EthView;
import java.util.ArrayList;

public class QuickViewActivity extends AppCompatActivity {
    GridView simpleGrid;
    private CustomAdapter.RecyclerViewClickListener listener;
    ArrayList<CoinInfo> viewList = new ArrayList<>();
    int logos[] = {R.drawable.ic_bitcoin};
    CustomAdapter customAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        recyclerView = findViewById(R.id.simpleGridView);
        setCustomAdapter();
    }

    /**
     * setViewList creates instances of both views for each coin type
     * @return ArrayList of CoinInfo objects which define different views
     * */
    private ArrayList<CoinInfo> setViewList() {
        int btcPic =  R.drawable.ic_bitcoin;
        int ethPic =  R.drawable.ic_ethereum;
        int ltcPic =  R.drawable.ic_ripple;
        int ripPic = R.drawable.ic_litecoin;

        viewList.add(new QuickView( new BtcView(), "Bitcoin", btcPic));
        viewList.add(new InfoView( new BtcView(), "Bitcoin", btcPic, "Bitcoin Description", 200000));
        viewList.add(new QuickView( new EthView(), "Ethereum", ethPic));
        viewList.add(new InfoView( new EthView(), "Ethereum", ethPic, "Ethereum Description", 14));
        viewList.add(new QuickView( new LtcView(), "Litecoin", ltcPic));
        viewList.add(new InfoView( new LtcView(), "Litecoin", ltcPic, "Litcoin Description", 44));
        viewList.add(new QuickView( new RipView(), "Ripple", ripPic));
        viewList.add(new InfoView( new RipView(), "Ripple", ripPic, "Ripple Description", 201));

        return viewList;
    }


    //Sets up the adapter for the recyclerview
    private void setCustomAdapter() {
        setOnClickListeners();
        viewList = setViewList();
        customAdapter = new CustomAdapter(viewList, listener);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(customAdapter);

    }

    //Sets on click listeners for list items
    private void setOnClickListeners() {
        listener = new CustomAdapter.RecyclerViewClickListener(){
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(QuickViewActivity.this, InfoViewActivity.class);
                intent.putExtra("view", viewList.get(position+1));
                startActivity(intent);
            }
        };
    }
}

