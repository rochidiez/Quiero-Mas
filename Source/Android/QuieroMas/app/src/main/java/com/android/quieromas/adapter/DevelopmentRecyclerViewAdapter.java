package com.android.quieromas.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.quieromas.R;
import com.android.quieromas.activity.VideoActivity;
import com.android.quieromas.fragment.FavoriteRecipesFragment;
import com.android.quieromas.model.DevelopmentItem;
import com.android.quieromas.model.receta.RecipeStepElement;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by lucas on 4/7/17.
 */

public class DevelopmentRecyclerViewAdapter extends RecyclerView.Adapter<DevelopmentRecyclerViewAdapter.ViewHolder> {

    private final List<DevelopmentItem> mValues;

    public DevelopmentRecyclerViewAdapter(List<DevelopmentItem> items) {
        mValues = items;
    }

    @Override
    public DevelopmentRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_development, parent, false);
        return new DevelopmentRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DevelopmentRecyclerViewAdapter.ViewHolder holder,final int position) {
        holder.txtTitle.setText(mValues.get(position).getName());

        Picasso.with(holder.imgThumbnail.getContext()).load(mValues.get(position).getThumbnail())
                //.resize(holder.imgBackground.getWidth(),holder.imgBackground.getHeight())
                .fit()
                .into(holder.imgThumbnail);

        holder.btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.btnPlay.getContext(), VideoActivity.class);
                intent.putExtra("URL",mValues.get(position).getVideo());
                holder.btnPlay.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView txtTitle;
        public final ImageView imgThumbnail;
        public final Button btnPlay;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            txtTitle = (TextView) view.findViewById(R.id.txt_development_title);
            imgThumbnail = (ImageView) view.findViewById(R.id.img_development_image);
            btnPlay = (Button) view.findViewById(R.id.btn_development_watch);
        }

        @Override
        public String toString() {
            return "'";
        }
    }
}

