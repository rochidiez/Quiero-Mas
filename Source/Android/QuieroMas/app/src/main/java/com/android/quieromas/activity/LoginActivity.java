package com.android.quieromas.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import com.android.quieromas.R;
import com.android.quieromas.fragment.VideoFragment;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AuthFbActivity implements VideoFragment.OnFragmentInteractionListener {


    private static final String TAG = "LoginActivity";

    private TextView txt1;
    private TextView txt2;
    private Button btnLoginEmail;
    private int interval = 10000; // 5 seconds by default, can be changed later
    private Handler mHandler;

    String text1ToShow[] = {"Recetas aprobadas por nutricionistas y pediatras para bebés de 6 a 12 meses.","Plan de nutrición y recetas para cada etapa de tu bebé."};
    String text2ToShow[] = {"","Queremos que tu hijo desarrolle todo su inmenso potencial."};
    int index = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);


        btnLoginEmail = (Button) findViewById(R.id.btn_login_email) ;
        btnLoginEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });

        txt1 = (TextView) findViewById(R.id.txt_login_text1);
        txt2 = (TextView) findViewById(R.id.txt_login_text2);

        mHandler = new Handler();
        startRepeatingTask();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                // If index reaches maximum reset it
                if (index == text1ToShow.length){
                    index = 0;
                }
                txt1.setText(text1ToShow[index]);
                txt2.setText(text2ToShow[index]);
            } finally {
                index++;
                mHandler.postDelayed(mStatusChecker, interval);
            }
        }
    };

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }

    @Override
    public void onFragmentInteraction(Uri uri){
        Log.w(TAG, "Hello");
    }
}
