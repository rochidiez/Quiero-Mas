package com.android.quieromas.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.quieromas.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucas on 28/6/17.
 */

public class ShareRecyclerViewAdapter extends RecyclerView.Adapter<ShareRecyclerViewAdapter.ViewHolder> {

    private int mValues;


    public ShareRecyclerViewAdapter(int items) {
        mValues = items;
    }

    @Override
    public ShareRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_share, parent, false);
        return new ShareRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ShareRecyclerViewAdapter.ViewHolder holder, final int position) {
    }

    @Override
    public int getItemCount() {
        return mValues;
    }

    public void setAmount(int amount){
        mValues = amount;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final EditText etxtEmail;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            etxtEmail = (EditText) view.findViewById(R.id.etxt_share_email);
        }

        @Override
        public String toString() {
            return "'";
        }
    }
}
