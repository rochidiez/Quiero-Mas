package com.android.quieromas.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.quieromas.R;
import com.android.quieromas.activity.MainActivity;
import com.android.quieromas.helper.AgeHelper;
import com.android.quieromas.helper.FirebaseDatabaseHelper;
import com.android.quieromas.model.user.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends BaseFragment {

    private LinearLayout lactationView;
    private LinearLayout abcView;
    private LinearLayout nutritionPlanView;
    private LinearLayout developmentView;
    private TextView txtNames;
    private Button btnMenu;
    private DrawerLayout drawerLayout;
    FirebaseDatabaseHelper firebaseDatabaseHelper;
    User user;

    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("");
        ((MainActivity) getActivity()).getSupportActionBar().hide();

    }

    @Override
    public void onPause() {
        super.onPause();
        ((MainActivity) getActivity()).getSupportActionBar().show();

    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        android.support.v7.app.ActionBar bar =  ((MainActivity) getActivity()).getSupportActionBar();



        lactationView = (LinearLayout) view.findViewById(R.id.view_lactancia_home);
        abcView = (LinearLayout) view.findViewById(R.id.view_abc_home);
        nutritionPlanView = (LinearLayout) view.findViewById(R.id.view_receta_home);
        developmentView = (LinearLayout) view.findViewById(R.id.view_estimulacion_home);
        txtNames = (TextView) view.findViewById(R.id.home_title_name);
        btnMenu = (Button) view.findViewById(R.id.btn_home_menu);
        drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);


        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        developmentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.main_content, new DevelopmentFragment(),"development");
                ft.addToBackStack("development");
                ft.commit();
            }
        });


        lactationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.main_content, new LactationFragment(),"lactation");
                ft.addToBackStack("lactation");
                ft.commit();
            }
        });

        abcView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.main_content, new AbcFragment(),"abc");
                ft.addToBackStack("abc");
                ft.commit();
            }
        });


        firebaseDatabaseHelper = new FirebaseDatabaseHelper();
        firebaseDatabaseHelper.getCurrentUserReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                updateUI();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void updateUI(){
        String names;
        names = user.datos.nombreCompleto.split(" ")[0];
        if(user.bebe != null && user.bebe.apodo != null){
            names = names + " y " + user.bebe.apodo;
        }else if(user.bebe != null && user.bebe.nombre != null){
            names = names + " y " + user.bebe.nombre;
        }
        txtNames.setText(names);

        AgeHelper ageHelper = new AgeHelper();
        final Class nutritionPlanFragment;
        if(user.bebe != null){
            if(ageHelper.canAccessPlan(user.bebe.fechaDeNacimiento)){
                nutritionPlanFragment = NutritionPlanFragment.class;
            }else{
                nutritionPlanFragment = EmptyNutritionPlanFragment.class;
            }
        }else{
            nutritionPlanFragment = EmptyNutritionPlanFragment.class;
        }



        nutritionPlanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment fragment = null;
                try {
                    fragment = (Fragment) nutritionPlanFragment.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ft.replace(R.id.main_content, fragment,"nutrition_plan");
                ft.addToBackStack("nutrition_plan");
                ft.commit();
            }
        });
    }

}
