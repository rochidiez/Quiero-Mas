package com.android.quieromas.fragment;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.quieromas.R;
import com.android.quieromas.activity.DevelopmentDetailActivity;
import com.android.quieromas.activity.MainActivity;

public class DevelopmentFragment extends BaseFragment {

    public DevelopmentFragment() {
        // Required empty public constructor
    }

    public static DevelopmentFragment newInstance() {
        DevelopmentFragment fragment = new DevelopmentFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Desarrollo");
        android.support.v7.app.ActionBar bar = ((MainActivity) getActivity()).getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getActivity(),R.color.blue)));

    }

    public int getActionBarHeight() {
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (getActivity().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
        {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        android.support.v7.app.ActionBar bar = ((MainActivity) getActivity()).getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getActivity(),R.color.orangePrimary)));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window =  ((MainActivity) getActivity()).getWindow();

            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            window.setStatusBarColor(ContextCompat.getColor(getActivity(),R.color.orangePrimary));
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getActivity(),R.color.blue)));
        Window window =  ((MainActivity) getActivity()).getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {


            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            window.setStatusBarColor(ContextCompat.getColor(getActivity(),R.color.blue));
        }
//        else{
//            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            //status bar height
//            int actionBarHeight = getActionBarHeight();
//            int statusBarHeight = getStatusBarHeight();
//            //action bar height
//            statusBar.getLayoutParams().height = actionBarHeight + statusBarHeight;
//        statusBar.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.blue));
//            statusBar.setVisibility(View.VISIBLE);
//        }

        TextView txt = (TextView) view.findViewById(R.id.txt_development_title);
        txt.setText(Html.fromHtml("<p><font face=\\\"Cera\\\">El desarrollo de tu hijo no s&oacute;lo depende de su gen&eacute;tica, sino tambi&eacute;n de la nutrici&oacute;n espec&iacute;fica para cada edad, junto con un patr&oacute;n de sue&ntilde;o adecuado, y las&nbsp;experiencias que tu beb&eacute; tenga en su vida diaria. Estas interacciones cotidianas hacen que avance en el desarrollo de sus habilidades. Por lo tanto, la forma como sosten&eacute;s en brazos a tu hijo, como lo hac&eacute;s jugar pancita abajo, como le cant&aacute;s, lo mir&aacute;s o sonre&iacute;s, estar&aacute;n dejando huellas en su arquitectura cerebral.&nbsp;&nbsp;Y ese v&iacute;nculo que gener&aacute;s con &eacute;l es el eje, la base fundamental, para que progrese en los aprendizajes de cada una de las &aacute;reas del desarrollo infantil.&nbsp;<u></u><u></u></font></p>\n" +
                "<p><font face=\\\"Cera\\\">Por esta raz&oacute;n, te proponemos juegos para que puedas fortalecer este v&iacute;nculo y disfrutar de acompa&ntilde;ar el desarrollo de tu bebe en las 4 &aacute;reas principales:<u></u><u></u></font></p>\n" +
                "<p><font face=\\\"Cera\\\"><strong>&Aacute;rea Cognitiva:</strong>&nbsp;referente a la exploraci&oacute;n, manipulaci&oacute;n y relaci&oacute;n con los objetos, permanencia de objeto, memoria.<u></u><u></u></font></p>\n" +
                "<p><font face=\\\"Cera\\\"><strong>&Aacute;rea de Comunicaci&oacute;n:</strong>&nbsp;relacionado con el balbuceo, gestos, identificaci&oacute;n y nombres objetos y figuras, el saber esperar el turno.<u></u><u></u></font></p>\n" +
                "<p><font face=\\\"Cera\\\"><strong>&Aacute;rea Motriz:</strong>&nbsp;enfocado en la coordinaci&oacute;n viso-manual, alcance y manipulaci&oacute;n de objetos de diversa informaci&oacute;n t&aacute;ctil, el sentarse, gatear, trepar, perfecci&oacute;n del equilibrio y la planificaci&oacute;n motora.<u></u><u></u></font></p>\n" +
                "<p><font face=\\\"Cera\\\"><strong>&Aacute;rea Social:</strong>&nbsp;relativo a la autorregulaci&oacute;n, autoestima, inter&eacute;s por el mundo y las personas, la identidad, comunicaci&oacute;n de necesidades, reglas de juego, respuesta a las consignas, realizaci&oacute;n de elecciones, participaci&oacute;n en tareas de la casa; cuidado de los objetos personales y el ambiente, la higiene personal, los modales.</font></p>"));

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.development_linear_layout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(24,12,24,12);
        for(int i = 6; i <= 12; i++){
            Button button = createMonthButton(i);
            linearLayout.addView(button, lp);
        }
    }

    Button createMonthButton(final int month){
        Button button = new Button(getContext());
        button.setText(month + " meses");
        button.generateViewId();
        button.setBackground(getResources().getDrawable(R.drawable.btn_development));
        button.setTextColor(getResources().getColor(R.color.colorPrimary));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DevelopmentDetailActivity.class);
                intent.putExtra("month", month);
                startActivity(intent);
            }
        });

        return button;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_development, container, false);
    }
}
