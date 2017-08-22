package com.android.quieromas.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.quieromas.R;
import com.android.quieromas.activity.RecipeActivity;
import com.android.quieromas.fragment.BaseRecipeFragment.OnListFragmentInteractionListener;
import com.android.quieromas.helper.FirebaseDatabaseHelper;
import com.android.quieromas.model.receta.Receta;
import com.android.quieromas.model.user.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class MyFavoriteRecipesRecyclerViewAdapter extends RecyclerView.Adapter<MyFavoriteRecipesRecyclerViewAdapter.ViewHolder> {

    private List<Receta> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyFavoriteRecipesRecyclerViewAdapter(List<Receta> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_recipe_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
       // holder.txtMeal.setText(mValues.get(position).id);
        holder.txtTitle.setText(mValues.get(position).getTitulo());

        holder.btnFav.setVisibility(View.VISIBLE);
        if(holder.mItem.isFavorite == false){
           holder.btnFav.setBackground(holder.btnFav.getResources().getDrawable(R.drawable.fav_vacio));
        }

        try{
            Picasso.with(holder.imgBackground.getContext()).load(mValues.get(position).getThumbnail())
                    //.resize(holder.imgBackground.getWidth(),holder.imgBackground.getHeight())
                    .fit()
                    .centerCrop()
                    .into(holder.imgBackground);
        }catch(Exception e){
            e.printStackTrace();
        }


        holder.btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.mItem.isFavorite = !holder.mItem.isFavorite;
                if(holder.mItem.isFavorite == false){
                    holder.btnFav.setBackground(holder.btnFav.getResources().getDrawable(R.drawable.fav_vacio));
                }else{
                    holder.btnFav.setBackground(holder.btnFav.getResources().getDrawable(R.drawable.fav_lleno));
                }
                final FirebaseDatabaseHelper firebaseDatabaseHelper = new FirebaseDatabaseHelper();
                firebaseDatabaseHelper.getCurrentUserReference().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        if(user.recetasFavoritas == null || user.recetasFavoritas.size() == 0){
                            ArrayList<String> array = new ArrayList<String>();
                            array.add(holder.mItem.getTitulo());
                            user.recetasFavoritas = array;
                        }else if(user.recetasFavoritas.contains(holder.mItem.getTitulo())){
                            user.recetasFavoritas.remove(holder.mItem.getTitulo());
                            Toast.makeText(holder.mView.getContext(),"La receta fue removida de favoritos",Toast.LENGTH_LONG).show();
                        }else{
                            user.recetasFavoritas.add(holder.mItem.getTitulo());
                            Toast.makeText(holder.mView.getContext(),"La receta fue añadida a favoritos",Toast.LENGTH_LONG).show();
                        }
                        firebaseDatabaseHelper.getCurrentUserReference().setValue(user);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String name = holder.mItem.getTitulo();
                    Intent intent = new Intent(v.getContext(), RecipeActivity.class);
                    intent.putExtra("RECIPE", name);
                    v.getContext().startActivity(intent);
            }
        });
    }

    public void updateValues(List<Receta> values){
        mValues.clear();
        mValues.addAll(values);
        notifyDataSetChanged();
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
        public final Button btnFav;
        public final ImageView imgArrow;

        public Receta mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            txtMeal = (TextView) view.findViewById(R.id.txt_recipe_meal);
            txtTitle = (TextView) view.findViewById(R.id.txt_recipe_title);
            imgBackground = (ImageView) view.findViewById(R.id.img_fav_background);

            btnFav = (Button) view.findViewById(R.id.btn_fav_recipe);


            imgArrow = (ImageView) view.findViewById(R.id.img_recipe_arrow);
            imgArrow.setVisibility(View.VISIBLE);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + txtTitle.getText() + "'";
        }
    }
}
