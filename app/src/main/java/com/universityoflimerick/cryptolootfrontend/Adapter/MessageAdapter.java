package com.universityoflimerick.cryptolootfrontend.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.universityoflimerick.cryptolootfrontend.Model.User.Message;
import com.universityoflimerick.cryptolootfrontend.R;
import java.util.ArrayList;

public class MessageAdapter  extends RecyclerView.Adapter<MessageAdapter.MyViewHolder>{

    private ArrayList<Message> messages;


    public MessageAdapter(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView t1, t2;

        public MyViewHolder(final View view) {
            super(view);
            t1 = view.findViewById(R.id.textView8);
            t2 = view.findViewById(R.id.textView9);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int position) {
        int pos = position;
        String msg = messages.get(pos).getMsg();
        String username = messages.get(pos).getUserName();
        holder.t1.setText(msg);
        holder.t2.setText(username);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

}
