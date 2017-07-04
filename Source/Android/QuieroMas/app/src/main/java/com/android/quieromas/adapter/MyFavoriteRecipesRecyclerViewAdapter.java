package com.android.quieromas.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.quieromas.R;
import com.android.quieromas.fragment.FavoriteRecipesFragment.OnListFragmentInteractionListener;
import com.android.quieromas.fragment.PlanRecipesFragment;
import com.android.quieromas.model.receta.Receta;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MyFavoriteRecipesRecyclerViewAdapter extends RecyclerView.Adapter<MyFavoriteRecipesRecyclerViewAdapter.ViewHolder> {

    private final List<Receta> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyFavoriteRecipesRecyclerViewAdapter(List<Receta> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_favoriterecipes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
       // holder.txtMeal.setText(mValues.get(position).id);
        holder.txtTitle.setText(mValues.get(position).getTitulo());

        Picasso.with(holder.imgBackground.getContext()).load(mValues.get(position).getThumbnail())
                .resize(holder.imgBackground.getWidth(),holder.imgBackground.getHeight())
                .fit()
                .into(holder.imgBackground);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView txtMeal;
        public final TextView txtTitle;
        public final ImageView imgBackground;

        public Receta mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            txtMeal = (TextView) view.findViewById(R.id.txt_recipe_meal);
            txtTitle = (TextView) view.findViewById(R.id.txt_recipe_title);
            imgBackground = (ImageView) view.findViewById(R.id.img_fav_background);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + txtTitle.getText() + "'";
        }
    }
}
