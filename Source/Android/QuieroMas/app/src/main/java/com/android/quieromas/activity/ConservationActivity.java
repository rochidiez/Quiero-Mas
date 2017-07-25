package com.android.quieromas.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android.quieromas.R;
import com.android.quieromas.view.ConservationTip;
import com.android.quieromas.view.ConservationTipBody;
import com.android.quieromas.view.Food;

import java.util.ArrayList;
import java.util.List;

public class ConservationActivity extends BaseActivity {

    LinearLayout foodLinearLayout;
    LinearLayout tipLinearLayout;
    Button btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conservation);

        btnClose = (Button) findViewById(R.id.btn_conservation_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        foodLinearLayout = (LinearLayout) findViewById(R.id.conservation_food_linear_layout);
        foodLinearLayout.addView(new Food(this, "VEGETALES", "cocidos, trozados o en puré","6"));
        foodLinearLayout.addView(new Food(this, "FRUTAS", "cocidas, trozadas o en puré","4"));
        foodLinearLayout.addView(new Food(this, "LÁCTEOS", "o preparaciones con lácteos","2"));
        foodLinearLayout.addView(new Food(this, "CARNE", "cruda trozada","3"));
        foodLinearLayout.addView(new Food(this, "CARNE", "cocida","3"));
        foodLinearLayout.addView(new Food(this, "POLLO", "crudo o cocido","3"));
        foodLinearLayout.addView(new Food(this, "PESCADO", "crudo o cocido","3"));


        tipLinearLayout = (LinearLayout) findViewById(R.id.conservation_tip_linear_layout);

        ArrayList<ConservationTipBody> list1 = new ArrayList<>();
        list1.add(new ConservationTipBody(this, "BAÑO MARIA", "este método consiste en disponer el recipiente con la comida dentro de una olla que contenga agua caliente y esté ubicada sobre el fuego. También, se aplica para cocciones al horno, colocando el recipiente con la preparación sobre una asadera con agua en el fondo."));
        tipLinearLayout.addView(new ConservationTip(this, "DESCONGELAR Y CALENTAR", list1 ));


        ArrayList<ConservationTipBody> list2 = new ArrayList<>();
        list2.add(new ConservationTipBody(this, "AL VAPOR", "se realiza en una olla o recipiente con una red metálica; sobre ella se ubican las hortalizas peladas y trozadas, que de esta manera no entran en contacto con el agua hirviente del fondo y conservan su sabor y valor nutricional. Ya que los nutrientes no pasan al agua de cocción, concentrándolos, en el alimento."));
        list2.add(new ConservationTipBody(this, "POR HERVIDO", "colocar sobre el fuego una cacerola con suficiente cantidad de agua sin sal. Esperar a que ésta hierva, y recién en este momento, incorporar las hortalizas peladas, cortadas en cubitos. Dejar que retome el hervor y cocinar hasta que estén tiernas."));
        tipLinearLayout.addView(new ConservationTip(this, "METODOS DE COCCIÓN de Hortalizas", list2 ));

        ArrayList<ConservationTipBody> list3 = new ArrayList<>();
        list3.add(new ConservationTipBody(this, "A LA PLANCHA", "es una forma de cocinar los alimentos, sin agregar aceite. Se utiliza una placa especial muy caliente y sobre ella se coloca el alimento. Ideal para la cocción de carnes, pescado u hortalizas."));
        tipLinearLayout.addView(new ConservationTip(this, "METODOS DE COCCIÓN de Carnes", list3 ));

        ArrayList<ConservationTipBody> list4 = new ArrayList<>();
        list4.add(new ConservationTipBody(this, "POR HERVIDO", "Una vez escogidas y lavadas, se sugiere dejarlas en remojo al menos 4 horas. Pasado el tiempo, enjuagarlas y están listas para usarse. Colocar sobre el fuego una cacerola con abundante cantidad de agua sin sal. Incorporar las legumbres enteras (lentejas, arvejas). Dejar que hiervan, bajar el fuego y cocinar hasta que estén tiernas (30 minutos aprox.).\n     Si se trata de legumbres partidas (lentejas rojas, verdes, del Puy) no deben remojarse, y se debe calcular con exactitud la cantidad de agua, ya que de otro modo podría salir una mezcla muy aguada. El volumen de agua debe duplicar la unidad de medida del peso de las legumbres.\n"));
        tipLinearLayout.addView(new ConservationTip(this, "METODOS DE COCCIÓN de Legumbres", list4 ));

        ArrayList<ConservationTipBody> list5 = new ArrayList<>();
        list5.add(new ConservationTipBody(this, "POR HERVIDO", "Pelar y descarozar las frutas (manzanas, membrillo, ciruelas). Cortar las frutas en cuartos enteras o peladas en finas rodajas. Colocar en una cacerola la fruta junto con el azúcar y el agua. Tapar y cocinar sobre fuego bajo hasta que estén bien blandas. Revolver ocasionalmente. Pisar para obtener el puré.\n"));
        tipLinearLayout.addView(new ConservationTip(this, "METODOS DE COCCIÓN de Frutas", list5 ));
    }
}
