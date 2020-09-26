package com.stoffe.chessclock;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.stoffe.chessclock.databinding.OptionFragmentBinding;
import com.stoffe.chessclock.db.TimeEntity;
import com.stoffe.chessclock.db.TimeViewmodel;
import com.stoffe.chessclock.elements.AddTimeLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

public class OptionFragment extends Fragment {

    private ArrayList<TimeItem> times;
    private SharedPreferences sharedpreferences;
    private TimeViewmodel viewModel;
    private ListView lv;
    boolean showEditButton = false;
    private OptionFragmentBinding binding;
    private TimeAdapter timeAdapter;
    private TimeEntity selectedTime;
    private int selectedItemId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(TimeViewmodel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.option_fragment, container, false);

        sharedpreferences = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        times = new ArrayList<>();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lv = view.findViewById(R.id.listView);

        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    viewModel.deleteTime(selectedTime);
                    timeAdapter.deleteItem(selectedItemId);
                    timeAdapter.notifyDataSetChanged();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        };

        lv.setOnItemClickListener((a, v, position, id) -> {
            TimeItem user = (TimeItem) lv.getItemAtPosition(position);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("Time", Integer.toString(user.getTime()));
            editor.putString("Increment", Integer.toString(user.getIncrement()));
            editor.apply();
            Toast.makeText(getActivity(), "Selected :" + " " + user.getTime() + ", " + user.getIncrement(), Toast.LENGTH_SHORT).show();
        });

        lv.setOnItemLongClickListener((adapterView, view13, i, l) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Delete this time format?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
            TextView startTxtView = view13.findViewById(R.id.time);
            TextView incrementTxtView = view13.findViewById(R.id.increment);
            String text = startTxtView.getText().toString() + "-" + incrementTxtView.getText().toString();
            selectedTime = new TimeEntity(text, Integer.parseInt(startTxtView.getText().toString()), Integer.parseInt(incrementTxtView.getText().toString()));
            selectedItemId = i;
            return false;
        });
        LiveData<List<TimeEntity>> s = viewModel.getAllTimes();

        final Button button2 = view.findViewById(R.id.doneButton);

        button2.setOnClickListener(v -> {
            FragmentManager fragmentManager2 = getFragmentManager();
            FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
            MainFragment fragment2 = new MainFragment();
            fragmentTransaction2.addToBackStack("xyz");
            fragmentTransaction2.hide(OptionFragment.this);
            fragmentTransaction2.add(android.R.id.content, fragment2);
            fragmentTransaction2.commit();
        });

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(view1 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Select start time and increment");

            AddTimeLayout LL = new AddTimeLayout(getContext());

            final EditText input = new EditText(getContext());
            input.setInputType(InputType.TYPE_NUMBER_VARIATION_NORMAL);
            builder.setView(LL);

            builder.setPositiveButton("OK", (dialog, which) -> {
                int startTime = LL.getStartTimePickerValue();
                int increment = LL.getIncrementPickerValue();
                String id = startTime + "-" + increment;
                TimeEntity time = new TimeEntity(id, startTime, increment);
                viewModel.insert(time);
                lv.setAdapter(timeAdapter);
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            builder.show();
        });

        s.observe(getViewLifecycleOwner(), timeEntities -> {
            times = new ArrayList<>();
            for (int i = 0; i < timeEntities.size(); i++) {
                times.add(new TimeItem(timeEntities.get(i).startTime, timeEntities.get(i).increment));
                Collections.sort(times, (o1, o2) -> o1.getTime() - (o2.getTime()));
                timeAdapter = new TimeAdapter(getContext(), times, showEditButton);
                lv.setAdapter(timeAdapter);
            }
        });

    }
}
