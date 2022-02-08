package com.developers.serenity.rimointerview;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TransAdapter extends ArrayAdapter <TransClass> {

    private final Context context;
    private final int layoutResourceId;
    private ArrayList<TransClass> arrayList;

    public TransAdapter(Context context1, int layoutResourceId1, ArrayList<TransClass> arrayList1){
        super(context1, layoutResourceId1, arrayList1);
        this.context = context1;
        this.layoutResourceId = layoutResourceId1;
        this.arrayList = arrayList1;
    }

    public void setListData(ArrayList<TransClass> transClasses) {
        this.arrayList = transClasses;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView (int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.img = view.findViewById(R.id.pic);
            holder.desc = view.findViewById(R.id.desc);
            holder.amt = view.findViewById(R.id.amount);
            holder.sta = view.findViewById(R.id.state);
            holder.date = view.findViewById(R.id.date);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        TransClass  transClass = arrayList.get(position);

        holder.desc.setText(transClass.getDesc());
        holder.amt.setText(transClass.getAmount());
        holder.sta.setText(transClass.getState());
        holder.date.setText(transClass.getDate());

        Picasso.get()
                .load(transClass.getImg())
                .fit()
                .placeholder(R.drawable.ic_def)
                .transform(new PicassoCircular())
                .centerInside()
                .into(holder.img);

        return view;
    }

    private static class ViewHolder {
        private TextView desc, amt, sta, date;
        private ImageView img;
    }

}
