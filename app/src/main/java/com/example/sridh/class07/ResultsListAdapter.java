package com.example.sridh.class07;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ResultsListAdapter extends RecyclerView.Adapter<ResultsListAdapter.ViewHolder> {
    ArrayList<FilteredApp> list;
    MainActivity mContext;
    Activity context;
    int oResourse;
    DatabaseDataManager dm;

    public ResultsListAdapter(@NonNull Activity context, int oResourse, @NonNull ArrayList<FilteredApp> objects) {
        this.list = objects;
        Log.d("result_list",list.toString());
        this.context = context;
        if(context instanceof MainActivity){
            this.mContext = (MainActivity) context;
        }
        this.oResourse = oResourse;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.results, parent, false);
        ViewHolder viewHolder=new ViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        FilteredApp email = list.get(position);
        Log.d("email",email.toString());
        holder.app_name.setText(email.getName());
        holder.price_value.setText(String.valueOf(email.getPrice()));
        //holder.textViewSubject.setText(email.subject);

        if(email.getPrice() < 1.99)
        {
            holder.star.setImageResource(R.drawable.price_low);
        }
        else if(email.getPrice() > 1.99 && email.getPrice() < 5.99) {
            holder.star.setImageResource(R.drawable.price_medium);
        }
        else {
            holder.star.setImageResource(R.drawable.price_high);
        }
        Picasso.with(context).load(email.getSmallImageURL()).into(holder.image);
        holder.email=email;
        holder.dm = dm;
        holder.context = context;
        holder.list = list;
        holder.position = position;
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewSubject,textViewSummary,textViewEmail;
        public TextView app_name,price_value;
        public ImageView star;
        public ImageView image;
        FilteredApp email;
        DatabaseDataManager dm;
        Activity context;
        ArrayList<FilteredApp> list;
        int position;
        public ViewHolder(final View itemView)
        {
            super(itemView);
            app_name=(TextView)itemView.findViewById(R.id.app_name);
            price_value=(TextView)itemView.findViewById(R.id.priceTextView);
            star=(ImageView) itemView.findViewById(R.id.star);
            image = (ImageView) itemView.findViewById(R.id.default_image);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.d("long clicked","clicked");
                    dm = new DatabaseDataManager(context);
                    dm.saveFilteredApp(email);
                    ArrayList<FilteredApp> li = dm.getAllFilteredApps();
                    Log.d("demo",li.toString());
                    dm.close();
                    list.remove(position);
                    mContext.listView2(context,li);
                    ResultsListAdapter.this.notifyDataSetChanged();
                    return true;
//                    return false;
                }
            });
        }
    }
}
