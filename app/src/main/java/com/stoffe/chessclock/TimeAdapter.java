package com.stoffe.chessclock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TimeAdapter extends BaseAdapter {

    private ArrayList<TimeItem> listData;
    private LayoutInflater layoutInflater;

    public TimeAdapter(Context aContext, ArrayList<TimeItem> listData, boolean showEditButton) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void deleteItem(int position) {
        listData.remove(position);
    }

    public View getView(int position, View v, ViewGroup vg) {
        ViewHolder holder;
        if (v == null) {
            v = layoutInflater.inflate(R.layout.row_item, null);
            holder = new ViewHolder();
            holder.uName = v.findViewById(R.id.time);
            holder.uDesignation = v.findViewById(R.id.increment);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.uName.setText(Integer.toString(listData.get(position).getTime()));
        holder.uDesignation.setText(Integer.toString(listData.get(position).getIncrement()));

        return v;
    }

    static class ViewHolder {
        TextView uName;
        TextView uDesignation;
    }
}
