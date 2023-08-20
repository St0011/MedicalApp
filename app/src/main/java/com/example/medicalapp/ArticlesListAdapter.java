package com.example.medicalapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ArticlesListAdapter extends BaseAdapter {

    private Context context;
    private String[][] articles;

    public ArticlesListAdapter(Context context, String[][] articles) {
        this.context = context;
        this.articles = articles;
    }

    @Override
    public int getCount() {
        return articles.length;
    }

    @Override
    public Object getItem(int position) {
        return articles[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        String[] articleData = articles[position];
        TextView textView = convertView.findViewById(android.R.id.text1);
        StringBuilder sb = new StringBuilder();
        for (String item : articleData) {
            sb.append(item);
        }

        textView.setText(sb.toString());

        return convertView;
    }
}
