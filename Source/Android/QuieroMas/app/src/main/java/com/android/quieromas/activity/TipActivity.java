package com.android.quieromas.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.quieromas.R;

public class TipActivity extends BaseActivity {

    private String text;
    private String title;
    private String drawable;
    private TextView txtTitle;
    private TextView txtText;
    private ImageView img;
    private Button btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);

        txtText = (TextView) findViewById(R.id.tip_text);
        txtTitle = (TextView) findViewById(R.id.tip_title);
        img = (ImageView) findViewById(R.id.tip_icon);
        btnClose = (Button) findViewById(R.id.btn_tip_close);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            text = extras.getString("text");
            title = extras.getString("title");
            drawable = extras.getString("drawable");
        }

        if(title != null){
            txtTitle.setText(title);
        }

        if(text != null){
            txtText.setText(text);
        }

        if(drawable != null){
            int resID = getResources().getIdentifier(drawable , "drawable", getPackageName());
            img.setBackground(ContextCompat.getDrawable(getApplicationContext(),resID));
        }

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
