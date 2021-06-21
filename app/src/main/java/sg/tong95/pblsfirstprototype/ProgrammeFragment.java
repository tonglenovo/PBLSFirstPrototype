package sg.tong95.pblsfirstprototype;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.realm.Realm;
import sg.tong95.pblsfirstprototype.Model.Event;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProgrammeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProgrammeFragment extends Fragment {

    RealmHelper helper;
    RecyclerView rvEventList;
    ProgrammeRecyclerViewAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    List<Event> eventList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
//    private String mParam2;

    public ProgrammeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
     * @return A new instance of fragment ProgrammeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProgrammeFragment newInstance(String param1) {
        ProgrammeFragment fragment = new ProgrammeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_programme2, container, false);

        String name = getActivity().getIntent().getStringExtra("name");
        String p_id = getActivity().getIntent().getStringExtra("p_id");
        System.out.println("name: " + name);

        if(name == null){
            name = "programme";
        }

        if(name.equals("presentation")){
            Realm.init(getContext());
            helper = new RealmHelper(getContext());
            eventList = helper.getTest();
            rvEventList = view.findViewById(R.id.rvEventList);
            System.out.println("Check mParam1: " + mParam1);
            System.out.println("Check1: " + eventList.size());
            adapter = new ProgrammeRecyclerViewAdapter(getContext(), helper.getEventFromPresentationDay(p_id, mParam1));
            rvEventList.setHasFixedSize(true);
            rvEventList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
            layoutManager = new LinearLayoutManager(getContext());
            rvEventList.setLayoutManager(layoutManager);
            rvEventList.setAdapter(adapter);
        } else if(name.equals("programme")){
            Realm.init(getContext());
            helper = new RealmHelper(getContext());
            eventList = helper.getTest();
            rvEventList = view.findViewById(R.id.rvEventList);
            System.out.println("Check mParam1: " + mParam1);
            System.out.println("Check1: " + eventList.size());
            adapter = new ProgrammeRecyclerViewAdapter(getContext(), helper.getEventByDay(mParam1));
            rvEventList.setHasFixedSize(true);
            rvEventList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
            layoutManager = new LinearLayoutManager(getContext());
            rvEventList.setLayoutManager(layoutManager);
            rvEventList.setAdapter(adapter);
        }


        return view;
    }
}