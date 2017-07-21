package com.android.quieromas.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.quieromas.R;
import com.android.quieromas.activity.BasicRecipeActivity;
import com.android.quieromas.model.receta.RecipeStepElement;

import java.util.List;

/**
 * Created by lucas on 28/6/17.
 */

public class ShoppingListRecyclerViewAdapter extends RecyclerView.Adapter<ShoppingListRecyclerViewAdapter.ViewHolder> {

    private final List<String> mValues;
    int orangeColor;


    public ShoppingListRecyclerViewAdapter(List<String> items) {
        mValues = items;
    }

    @Override
    public ShoppingListRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_shopping_item, parent, false);
        orangeColor = parent.getResources().getColor(R.color.orangePrimary);
        return new ShoppingListRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ShoppingListRecyclerViewAdapter.ViewHolder holder, final int position) {
        holder.txtText.setText(mValues.get(position));
        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mValues.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView txtText;
        public final Button btnRemove;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            txtText = (TextView) view.findViewById(R.id.txt_shopping_item);
            btnRemove = (Button) view.findViewById(R.id.btn_remove_shopping_item);
        }

        @Override
        public String toString() {
            return "'";
        }
    }
}
