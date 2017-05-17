package com.android.quieromas.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.andreabaccega.widget.FormEditText;
import com.android.quieromas.R;
import com.android.quieromas.activity.LoginActivity;
import com.android.quieromas.activity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SigninFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SigninFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SigninFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button btnSignin;
    private FormEditText email;
    private FormEditText password;
    private FirebaseAuth mAuth;


    private OnFragmentInteractionListener mListener;

    public SigninFragment() {
        // Required empty public constructor
    }


    public static SigninFragment newInstance(String param1, String param2) {
        SigninFragment fragment = new SigninFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signin, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        email = (FormEditText) view.findViewById(R.id.etxt_signin_email);
        password = (FormEditText) view.findViewById(R.id.etxt_signin_password);
        btnSignin = (Button) view.findViewById(R.id.btn_signin);
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("HOLAAAA",email.getText().toString());
                boolean validations = true;
                FormEditText[] allFields	= { email, password };
                for (FormEditText field: allFields) {
                    validations = field.testValidity() && validations;
                }

                if(validations){
                    mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("", "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(getActivity(), "OK",
                                                Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getContext(), MainActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("", "signInWithEmail:failure", task.getException());
                                        Toast.makeText(getActivity(), "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });
                }
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
