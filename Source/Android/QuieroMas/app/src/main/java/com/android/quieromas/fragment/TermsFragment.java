package com.android.quieromas.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.quieromas.R;
import com.android.quieromas.activity.MainActivity;

public class TermsFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    public TermsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TermsFragment newInstance(String param1, String param2) {
        TermsFragment fragment = new TermsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Términos y Condiciones");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_terms, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView terms = (TextView) view.findViewById(R.id.txt_terms_text);
        terms.setText("Lorem ipsum dolor sit amet, est in affert graece eligendi, usu at partiendo maiestatis persequeris. Cu dicant legendos nec, et cetero efficiendi pro, in tota dicant maluisset sed. Iudico omnium sapientem qui ea, no summo eripuit laoreet eum. His te alii eirmod intellegam, cu legere vivendo duo. Ad accusata temporibus intellegebat mei, no facilis laboramus vix. Sit stet abhorreant ne, magna imperdiet no mei.\n" +
                "\n" +
                "Causae feugiat qui at. Mel nihil laboramus theophrastus ut, et ius ferri ponderum fabellas, impedit omnesque suscipit eu mea. Qui te veri moderatius reprehendunt, at cum doming delicatissimi, te cum wisi impedit civibus. Sit ut velit nonumy consetetur, an eos diceret oportere.\n");

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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
