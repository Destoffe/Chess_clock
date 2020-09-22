package com.stoffe.chessclock;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.stoffe.chessclock.databinding.MainFragmentBinding;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainFragment extends Fragment {

    MainFragmentBinding binding;
    CountDownTimer countDownTimer1;
    CountDownTimer countDownTimer2;
    long millisUntilFinished1 = 300000;
    long millisUntilFinished2 = 300000;
    boolean playerOneTicking = false;
    private long increment;
    SharedPreferences preferences;
    private boolean notStarted = true;

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

        final Button button1 = view.findViewById(R.id.player1);
        final Button button2 = view.findViewById(R.id.player2);
        final ImageButton optionsButton = view.findViewById(R.id.options);
        final ImageButton resetButton = view.findViewById(R.id.resetButton);
        final ImageButton incrementButton1 = view.findViewById(R.id.increment1);
        final ImageButton incrementButton2 = view.findViewById(R.id.increment2);
        final ImageButton pauseButton = view.findViewById(R.id.pause);

        preferences = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);

        millisUntilFinished1 = TimeUnit.MINUTES.toMillis(Integer.parseInt(preferences.getString("Time", "1")));
        millisUntilFinished2 = TimeUnit.MINUTES.toMillis(Integer.parseInt(preferences.getString("Time", "1")));
        increment = Integer.parseInt(preferences.getString("Increment", "1")) * 1000;

        button1.setText("" + String.format("%d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished1),
                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished1) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished1))));

        button2.setText("" + String.format("%d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished2),
                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished2) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished2))));

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!playerOneTicking || notStarted) {
                    notStarted = false;
                    if (countDownTimer2 != null) {
                        countDownTimer2.cancel();
                    }


                    playerOneTicking = true;
                    button1.setBackgroundColor(getResources().getColor(R.color.orange));
                    button2.setBackgroundColor(getResources().getColor(R.color.grey));
                    countDownTimer1 = new CountDownTimer(millisUntilFinished1, 1000) { // adjust the milli seconds here

                        public void onTick(long millisUntilFinished) {
                            button1.setText("" + String.format("%d:%02d",
                                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                            millisUntilFinished1 = millisUntilFinished + increment;
                        }

                        public void onFinish() {
                            button1.setText("DONE!");
                            button1.setBackgroundColor(getResources().getColor(R.color.red));
                        }
                    }.start();


                }
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playerOneTicking || notStarted) {
                    notStarted = false;
                    playerOneTicking = false;

                    button2.setBackgroundColor(getResources().getColor(R.color.orange));
                    button1.setBackgroundColor(getResources().getColor(R.color.grey));
                    if (countDownTimer1 != null) {
                        countDownTimer1.cancel();
                    }
                    countDownTimer2 = new CountDownTimer(millisUntilFinished2, 1000) { // adjust the milli seconds here

                        public void onTick(long millisUntilFinished) {

                            button2.setText("" + String.format("%d:%02d",
                                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                            millisUntilFinished2 = millisUntilFinished + increment;
                        }

                        public void onFinish() {
                            button2.setText("DONE!");
                            button2.setBackgroundColor(getResources().getColor(R.color.red));
                        }
                    }.start();

                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (countDownTimer2 != null && countDownTimer1 != null) {
                    millisUntilFinished1 = TimeUnit.MINUTES.toMillis(Integer.parseInt(preferences.getString("Time", "1")));
                    millisUntilFinished2 = TimeUnit.MINUTES.toMillis(Integer.parseInt(preferences.getString("Time", "1")));
                    countDownTimer1.cancel();
                    countDownTimer2.cancel();
                    button1.setBackgroundColor(getResources().getColor(R.color.grey));
                    button2.setBackgroundColor(getResources().getColor(R.color.grey));
                    button1.setText("" + String.format("%d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished1),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished1) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished1))));
                    button2.setText("" + String.format("%d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished2),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished2) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished2))));
                }
            }
        });

        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager2 = getFragmentManager();
                FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                OptionFragment fragment2 = new OptionFragment();
                fragmentTransaction2.addToBackStack("xyz");
                fragmentTransaction2.hide(MainFragment.this);
                fragmentTransaction2.add(android.R.id.content, fragment2);
                fragmentTransaction2.commit();
            }
        });

        incrementButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (countDownTimer1 != null && playerOneTicking && !notStarted) {

                    countDownTimer1.cancel();
                    millisUntilFinished1 += 1000;
                    countDownTimer1 = new CountDownTimer(millisUntilFinished1, 1000) { // adjust the milli seconds here

                        public void onTick(long millisUntilFinished) {
                            button1.setText("" + String.format("%d:%02d",
                                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                            millisUntilFinished1 = millisUntilFinished;
                        }

                        public void onFinish() {
                            button1.setText("DONE!");
                            button1.setBackgroundColor(getResources().getColor(R.color.red));
                        }
                    }.start();

                }
            }
        });

        incrementButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (countDownTimer2 != null && !playerOneTicking && !notStarted) {
                    countDownTimer2.cancel();
                    millisUntilFinished2 += 1000;
                    countDownTimer2 = new CountDownTimer(millisUntilFinished2, 1000) { // adjust the milli seconds here

                        public void onTick(long millisUntilFinished) {
                            button2.setText("" + String.format("%d:%02d",
                                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                            millisUntilFinished2 = millisUntilFinished;
                        }

                        public void onFinish() {
                            button2.setText("DONE!");
                            button2.setBackgroundColor(getResources().getColor(R.color.red));
                        }
                    }.start();

                }
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!notStarted) {
                    notStarted = true;
                    if (countDownTimer1 != null) {
                        countDownTimer1.cancel();
                    }

                    if (countDownTimer2 != null) {
                        countDownTimer2.cancel();
                    }
                    button1.setBackgroundColor(getResources().getColor(R.color.grey));
                    button2.setBackgroundColor(getResources().getColor(R.color.grey));
                }

            }
        });
    }
}
