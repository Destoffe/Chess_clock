package com.stoffe.chessclock;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Path;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.stoffe.chessclock.db.ClockDatabase;
import com.stoffe.chessclock.db.TimeEntity;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Room;

public class OptionFragment extends Fragment {

    private ArrayList<TimeItem> times;
    SharedPreferences sharedpreferences;
    private TimeViewmodel viewModel;
    ListView lv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel= ViewModelProviders.of(this).get(TimeViewmodel .class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.option_fragment, container, false);

        sharedpreferences = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);

        setupTimes();

        lv =view.findViewById(R.id.listView);
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
        LiveData<List<TimeEntity>> s = viewModel.getAllTimes();


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


        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Select start time and increment");

                LinearLayout LL = new LinearLayout(getContext());
                LL.setOrientation(LinearLayout.HORIZONTAL);

                final NumberPicker startTimePicker = new NumberPicker(getContext());
                startTimePicker.setMaxValue(100);
                startTimePicker.setMinValue(1);

                final NumberPicker incrementPicker = new NumberPicker(getContext());
                incrementPicker.setMaxValue(100);
                incrementPicker.setMinValue(1);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(50, 50);
                params.gravity = Gravity.CENTER;

                LinearLayout.LayoutParams numPickerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                numPickerParams.weight = 1;

                LinearLayout.LayoutParams incPickerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                incPickerParams.weight = 1;

                LL.setLayoutParams(params);
                LL.addView(startTimePicker,numPickerParams);
                LL.addView(incrementPicker,incPickerParams);

                final EditText input = new EditText(getContext());
                input.setInputType(InputType.TYPE_NUMBER_VARIATION_NORMAL);
                builder.setView(LL);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int startTime = startTimePicker.getValue();
                        int increment = incrementPicker.getValue();
                        String id = startTime + "-" + increment;
                        TimeEntity time = new TimeEntity(id,startTime,increment);
                        viewModel.insert(time);
                        lv.setAdapter(new TimeAdapter(getContext(), times));
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        s.observe(getViewLifecycleOwner(), new Observer<List<TimeEntity>>() {
            @Override
            public void onChanged(List<TimeEntity> timeEntities) {
                for(int i=0; i<timeEntities.size(); i++){
                    times.add(new TimeItem(Integer.toString(timeEntities.get(i).startTime),Integer.toString(timeEntities.get(i).increment)));
                    lv.setAdapter(new TimeAdapter(getContext(), times));
                }
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
