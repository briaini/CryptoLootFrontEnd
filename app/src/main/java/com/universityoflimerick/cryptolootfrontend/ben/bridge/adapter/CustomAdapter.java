package com.universityoflimerick.cryptolootfrontend.ben.bridge.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;



import com.universityoflimerick.cryptolootfrontend.R;
import com.universityoflimerick.cryptolootfrontend.ben.bridge.CoinInfo;

import java.util.ArrayList;
import java.util.Map;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{

    private ArrayList<CoinInfo> viewList;
    private RecyclerViewClickListener mListener;



    public CustomAdapter(ArrayList<CoinInfo> viewList, RecyclerViewClickListener listener) {
        this.viewList = viewList;
        this.mListener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private RecyclerViewClickListener mListener;
        //private ImageView iv;
        private TextView t1,t2, t3;
        private  ImageView iv;
        private Button b;


        public MyViewHolder(final View view, RecyclerViewClickListener listener) {
            super(view);
            t1 = view.findViewById(R.id.textView);
            t2 = view.findViewById(R.id.textView2);
            t3 = view.findViewById(R.id.textView3);
            iv = view.findViewById(R.id.imageView);
            b  = view.findViewById(R.id.button);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onClick(view, getAdapterPosition());
                }
            });
            mListener = listener;
        }


        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
        }
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid, parent, false);

        return new MyViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int position) {
        int pos = position;
        Map<String, Object> map;

        if(pos % 2 == 0){
            //holder.layout.addView(viewList.get(pos).drawView());
            map = viewList.get(pos).drawView();
            String tv1 = (String) map.get("Title");
            holder.t1.setVisibility(View.VISIBLE);
            holder.t1.setText(tv1);
            holder.iv.setImageResource((Integer) map.get("Image"));
            holder.b.setVisibility(View.VISIBLE);
        }



       // holder.iv.setImageBitmap(BitmapFactory.decodeResource(R.drawable.ic_ethereum));


      /*  if(position %2 == 1)
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#c0d6e4"));
        }*/



    }

    @Override
    public int getItemCount() {
        return viewList.size();
    }




    public interface RecyclerViewClickListener {

        void onClick(View v, int position);
    }

}
