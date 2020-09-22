package com.stoffe.chessclock;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Path;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class OptionFragment extends Fragment {

    private ListView mListView;
    private ArrayAdapter aAdapter;
    private ArrayList<TimeItem> times;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.option_fragment, container, false);
        setupTimes();
        sharedpreferences = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        final ListView lv = (ListView) view.findViewById(R.id.listView);
        lv.setAdapter(new TimeAdapter(getContext(), times));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                TimeItem user = (TimeItem) lv.getItemAtPosition(position);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("Time", user.getTime());
                editor.putString("Increment", user.getIncrement());
                editor.apply();
                Toast.makeText(getActivity(), "Selected :" + " " + user.getTime()+", "+ user.getIncrement(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Button button2 = view.findViewById(R.id.doneButton);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager2 = getFragmentManager();
                FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                MainFragment fragment2 = new MainFragment();
                fragmentTransaction2.addToBackStack("xyz");
                fragmentTransaction2.hide(OptionFragment.this);
                fragmentTransaction2.add(android.R.id.content, fragment2);
                fragmentTransaction2.commit();
            }
        });

    }

    private void setupTimes(){
        times = new ArrayList<>();
        times.add(new TimeItem("1","0"));
        times.add(new TimeItem("2","1"));
        times.add(new TimeItem("3","0"));
        times.add(new TimeItem("3","2"));
        times.add(new TimeItem("5","0"));
        times.add(new TimeItem("5","3"));
        times.add(new TimeItem("10","0"));
        times.add(new TimeItem("10","5"));
        times.add(new TimeItem("15","5"));
    }
}
