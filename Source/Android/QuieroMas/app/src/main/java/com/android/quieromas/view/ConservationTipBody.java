package com.android.quieromas.view;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.quieromas.R;

/**
 * Created by lucas on 25/7/17.
 */

public class ConservationTipBody extends LinearLayout{

    private TextView subTitle;
    private TextView txtText;
    private String subtitleText;
    private String text;

    public ConservationTipBody(Context context, String subtitleText, String text) {
        super(context);
        this.text = text;
        this.subtitleText = subtitleText;
        init();
    }

    public ConservationTipBody(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ConservationTipBody(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.conservation_tip_body, this);
        this.subTitle = (TextView)findViewById(R.id.txt_conservation_tip_body_subtitle);
        this.txtText = (TextView) findViewById(R.id.txt_conservation_tip_body_text);

        txtText.setText(Html.fromHtml(text));
        subTitle.setText(subtitleText);
    }
}
