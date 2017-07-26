package com.android.quieromas.view;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.quieromas.R;

import java.util.List;

/**
 * Created by lucas on 25/7/17.
 */

public class ConservationTip extends LinearLayout {

    private TextView title;
    private LinearLayout linearLayout;
    private String titleText;
    private List<ConservationTipBody> list;

    public ConservationTip(Context context, String titleText, List<ConservationTipBody> list) {
        super(context);
        this.titleText = titleText;
        this.list = list;

        init();
    }

    public ConservationTip(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ConservationTip(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.conservation_tip, this);
        this.title = (TextView)findViewById(R.id.txt_conservation_tip_title);
        title.setText(Html.fromHtml(titleText));
        this.linearLayout = (LinearLayout) findViewById(R.id.conservation_tip_linear_layout);
        if(list != null){
            for(int i = 0; i < list.size(); i++){
                linearLayout.addView(list.get(i));
            }
        }


    }
}
