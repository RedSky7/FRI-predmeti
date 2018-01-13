package si.lj.uni.fri.tpo.fripredmeti.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.List;
import java.util.concurrent.ExecutionException;

import si.lj.uni.fri.tpo.fripredmeti.Model.Course;
import si.lj.uni.fri.tpo.fripredmeti.R;
import si.lj.uni.fri.tpo.fripredmeti.REST.GetClassesForArea;
import si.lj.uni.fri.tpo.fripredmeti.RecyclerAdapter;
import si.lj.uni.fri.tpo.fripredmeti.RecyclerAdapterTeacherOverview;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ClassessFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClassessFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClassessFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "title";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String hiddenPredmetID;

    private RecyclerView recyclerView;
    public RecyclerAdapter adapter;

    private OnFragmentInteractionListener mListener;

    public ClassessFragment(){
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public ClassessFragment(String hiddenPredmetID, String title) {
        this.hiddenPredmetID = hiddenPredmetID;
        this.mParam1 = title;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClassessFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClassessFragment newInstance(String param1, String param2) {
        ClassessFragment fragment = new ClassessFragment();
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


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_classess, container, false);
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

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_classes);
        adapter = new RecyclerAdapter(getActivity(), true, mParam1);


        try {
            List<Course> listCourse = new GetClassesForArea().execute(hiddenPredmetID).get();
            for (int i = 0; i < listCourse.size(); i++) {
                //Course c = listCourse.get(i);
                adapter.addCourses(i, listCourse.get(i));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        recyclerView.setAdapter(adapter);

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);


        Spinner vrstniRed = (Spinner) getActivity().findViewById(R.id.spinnerSortByClasses);
        vrstniRed.setSelection(1);
        vrstniRed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //TODO: change vrstni red predmetov
                adapter.fillData(true, position);
                //adapter.fillData(true);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //recyclerView.scrollToPosition(0);

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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
