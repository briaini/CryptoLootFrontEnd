package com.universityoflimerick.cryptolootfrontend.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.universityoflimerick.cryptolootfrontend.Adapter.CustomAdapter;
import com.universityoflimerick.cryptolootfrontend.Adapter.MessageAdapter;
import com.universityoflimerick.cryptolootfrontend.Model.User.Message;
import com.universityoflimerick.cryptolootfrontend.Model.User.PremiumUser;
import com.universityoflimerick.cryptolootfrontend.Model.User.User;
import com.universityoflimerick.cryptolootfrontend.R;

import java.util.ArrayList;


public class ChatActivity extends AppCompatActivity {
    private static ArrayList<Message> messages = new ArrayList<>();
    private static MessageAdapter adapter;
    private RecyclerView recyclerView;
    private EditText t;
    private Button b;
    private User loggedInUser = new PremiumUser("John","Jonh");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        recyclerView = findViewById(R.id.recyc);
        t = findViewById(R.id.editText);
        b = findViewById(R.id.button5);
        setOnClickListener();
        setAdapter();
    }

    /**
     * showMessage adds a message object to the arraylist of messages and updates the view
     * @param user represents the user sending the message
     * @param message represents the message as a string
     **/
    public static void showMessage(User user, String message){
        messages.add(new Message(message, user));
        adapter.notifyDataSetChanged();
    }

    /**
     * setAdapter creates an instance of the MessageAdapter.
     * It populates the recyclerView with the meetings by using the adapter.
     **/
    private void setAdapter() {
        adapter = new MessageAdapter(messages);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setOnClickListener() {
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String m = t.getText().toString();
                loggedInUser.sendMessage(m);
            }
        });
    }

}
