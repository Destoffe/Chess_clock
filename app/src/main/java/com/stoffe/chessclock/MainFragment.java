package com.stoffe.chessclock;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.stoffe.chessclock.databinding.MainFragmentBinding;

import com.stoffe.chessclock.elements.Player;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainFragment extends Fragment {

    MainFragmentBinding binding;
    SharedPreferences preferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.main_fragment, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        preferences = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        long startTimeInMs = TimeUnit.MINUTES.toMillis(Integer.parseInt(preferences.getString("Time", "1")));
        long incrementOnClick = TimeUnit.SECONDS.toMillis(Integer.parseInt(preferences.getString("Increment", "1")));

        final Player player1 = new Player(view, R.id.player1, startTimeInMs, incrementOnClick, getContext());
        final Player player2 = new Player(view, R.id.player2, startTimeInMs, incrementOnClick, getContext());
        player1.setButtonPressedEvent(player2::start);
        player2.setButtonPressedEvent(player1::start);

        final ImageButton optionsButton = view.findViewById(R.id.options);
        final ImageButton resetButton = view.findViewById(R.id.resetButton);
        final ImageButton incrementButton1 = view.findViewById(R.id.increment1);
        final ImageButton incrementButton2 = view.findViewById(R.id.increment2);
        final ImageButton pauseButton = view.findViewById(R.id.pause);

        resetButton.setOnClickListener(v -> {
            player1.reset();
            player2.reset();
        });

        optionsButton.setOnClickListener(v -> {
            FragmentManager fragmentManager2 = getParentFragmentManager();
            FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
            OptionFragment fragment2 = new OptionFragment();
            fragmentTransaction2.addToBackStack("xyz");
            fragmentTransaction2.hide(MainFragment.this);
            fragmentTransaction2.add(android.R.id.content, fragment2);
            fragmentTransaction2.commit();
        });

        incrementButton2.setOnClickListener(v -> player1.incrementTime(1000));
        incrementButton1.setOnClickListener(v -> player2.incrementTime(1000));

        pauseButton.setOnClickListener(v -> {
            player1.pause();
            player2.pause();
        });
    }
}
