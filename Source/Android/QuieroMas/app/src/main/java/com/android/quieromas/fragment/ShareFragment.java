package com.android.quieromas.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.quieromas.R;
import com.android.quieromas.adapter.ShareRecyclerViewAdapter;
import com.android.quieromas.adapter.ShoppingListRecyclerViewAdapter;
import com.android.quieromas.api.FirebaseFunctionApi;
import com.android.quieromas.api.ServiceFactory;
import com.android.quieromas.model.api.ShareParams;
import com.android.quieromas.model.api.ShoppingListParams;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ShareFragment extends BaseFragment {

    RecyclerView rvShare;
    Button btnPlus;
    Button btnSend;
    int amount = 3;

    public ShareFragment() {
        // Required empty public constructor
    }

    public static ShareFragment newInstance() {
        ShareFragment fragment = new ShareFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_share, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvShare = (RecyclerView) view.findViewById(R.id.share_rv);
        btnPlus = (Button) view.findViewById(R.id.btn_share_add_element);
        btnSend = (Button) view.findViewById(R.id.btn_share_send);
        final ShareRecyclerViewAdapter adapter = new ShareRecyclerViewAdapter(amount);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvShare.setLayoutManager(linearLayoutManager);
        rvShare.setAdapter(adapter);

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(amount < 10){
                    amount++;
                    adapter.setAmount(amount);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> emailList = new ArrayList<String>();
                for(int i = 0; i < amount; i++){
                    EditText etxt = (EditText) linearLayoutManager.findViewByPosition(i).findViewById(R.id.etxt_share_email);
                    String text = etxt.getText().toString();
                    if(isValidEmail(text)){
                        emailList.add(text);
                    }
                }
                if(emailList.size() > 0){
                    FirebaseFunctionApi api;
                    api = ServiceFactory.createRetrofitService(FirebaseFunctionApi.class, FirebaseFunctionApi.SERVICE_ENDPOINT);
                    api.share(new ShareParams(emailList)).subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<ResponseBody>() {
                                @Override
                                public final void onCompleted() {
                                }

                                @Override
                                public final void onError(Throwable e) {
                                    Toast.makeText(getContext(),"Se ha producido un error",Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public final void onNext(ResponseBody rb){
                                    Toast.makeText(getContext(),"Los emails han sido enviados",Toast.LENGTH_LONG).show();
                                    amount = 3;
                                    adapter.setAmount(amount);
                                    adapter.notifyDataSetChanged();
                                    for(int i = 0; i < amount; i++){
                                        EditText etxt = (EditText) linearLayoutManager.findViewByPosition(i).findViewById(R.id.etxt_share_email);
                                        etxt.setText("");
                                    }
                                }
                            });
                }else{
                    Toast.makeText(getContext(),"Por favor ingrese algún email válido",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

}
