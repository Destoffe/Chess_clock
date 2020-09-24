package com.stoffe.chessclock.elements;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import androidx.core.content.ContextCompat;
import com.stoffe.chessclock.Constants;
import com.stoffe.chessclock.R;
import java.util.concurrent.Semaphore;

public class Player {

    private final static int COUNTDOWN_INTERVAL = 1000;

    private final Button button;
    private final long defaultTimeInMillis;
    private final Context context;
    private final long incrementOnClick;

    private ButtonCountdown countDownTimer;
    private long remainingMSOnClock;
    private ButtonPressedEvent buttonPressedEvent;

    public Player(View view, int id, long startMSOnClock, long incrementOnClick, Context context) {
        this.context = context;
        button = view.findViewById(id);
        button.setOnClickListener(new ButtonClick());
        this.remainingMSOnClock = startMSOnClock;
        this.defaultTimeInMillis = startMSOnClock;
        this.incrementOnClick = incrementOnClick;
        countDownTimer = createButtonCountdown();
        updateButtonText();
    }

    public void setButtonPressedEvent(ButtonPressedEvent buttonPressedEvent) {
        this.buttonPressedEvent = buttonPressedEvent;
    }

    public void start() {
        button.setBackgroundColor(ContextCompat.getColor(context, R.color.orange));
        countDownTimer = createButtonCountdown();
        countDownTimer.begin();
    }

    private ButtonCountdown createButtonCountdown() {
        if (countDownTimer != null) {
            countDownTimer.pause();
        }
        return new ButtonCountdown(remainingMSOnClock, COUNTDOWN_INTERVAL);
    }

    public void reset() {
        button.setBackgroundColor(ContextCompat.getColor(context, R.color.grey));
        countDownTimer.pause();
        remainingMSOnClock = defaultTimeInMillis;
        updateButtonText();
    }

    public void pause()  {
        countDownTimer.pause();
        button.setBackgroundColor(ContextCompat.getColor(context, R.color.grey));
    }

    public void incrementTime(long increment) {
        remainingMSOnClock += increment;
        updateCountdownOnIncrement();
        updateButtonText();
    }

    private void updateCountdownOnIncrement() {
        boolean wasOldTimerRunning = countDownTimer.isRunning();
        countDownTimer = createButtonCountdown();
        if (wasOldTimerRunning) {
            countDownTimer.begin();
        }
    }

    public void updateButtonText() {
        button.setText(createButtonText(remainingMSOnClock));
    }

    private class ButtonClick implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            pause();
            incrementTime(incrementOnClick);
            if (buttonPressedEvent != null) {
                buttonPressedEvent.onClick();
            }
        }
    }

    private String createButtonText(long millisUntilFinished) {
        long seconds = (millisUntilFinished / 1000) % 60;
        long minutes = (millisUntilFinished / (1000 * 60)) % 60;
        return String.format(Constants.defaultLocale,"%d:%02d", minutes, seconds);
    }

    private class ButtonCountdown extends CountDownTimer {

        boolean isRunning;

        public ButtonCountdown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            remainingMSOnClock = millisUntilFinished;
            button.setText(createButtonText(remainingMSOnClock));
        }

        @Override
        public void onFinish() {
            button.setText(R.string.finished_text);
            button.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
        }

        public void pause() {
            cancel();
            isRunning = false;
        }

        public void begin() {
            start();
            isRunning = true;
        }

        public boolean isRunning() {
            return isRunning;
        }
    }

    public interface ButtonPressedEvent {
        void onClick();
    }
}
