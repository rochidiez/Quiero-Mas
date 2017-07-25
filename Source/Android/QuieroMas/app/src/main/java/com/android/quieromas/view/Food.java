package com.android.quieromas.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.quieromas.R;

/**
 * Created by lucas on 25/7/17.
 */

public class Food extends RelativeLayout {

    private TextView title;
    private TextView subTitle;
    private TextView months;
    private String titleText;
    private String subtitleText;
    private String monthstext;

    public Food(Context context, String titleText, String subtitleText, String months) {
        super(context);
        this.titleText = titleText;
        this.subtitleText = subtitleText;
        this.monthstext = months;
        init();
    }

    public Food(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Food(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.food, this);
        this.title = (TextView)findViewById(R.id.txt_food_title);
        this.subTitle = (TextView)findViewById(R.id.txt_food_subtitle);
        this.months = (TextView) findViewById(R.id.txt_food_months);

        title.setText(titleText);
        subTitle.setText(subtitleText);
        months.setText(monthstext + " meses");
    }


}
