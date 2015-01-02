package com.github.jyunjikondo.typewriterview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextSwitcher;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class TypewriterView extends TextSwitcher {
    private static int DEFAULT_INTERVAL_IN_MSEC = 66;
    private static int DEFAULT_DELAY_IN_MSEC = 500;
    private static float DEFAULT_TEXT_SIZE_IN_SP = 18f;
    private static int DEFAULT_TEXT_COLOR_ID = android.R.color.black;
    private static int FADE_DURATION = 300;

    private TimerTask timerTask;
    private int currentLength;
    private final Timer timer = new Timer();
    private final int interval;
    private final int delay;
    private Handler handler = new Handler();
    private Listener listener;

    public TypewriterView(Context context) {
        this(context, null);
    }

    public TypewriterView(final Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TypewriterView);
        final float textSize = a.getFloat(R.styleable.TypewriterView_textSize, DEFAULT_TEXT_SIZE_IN_SP);
        final int textColor = a.getColor(R.styleable.TypewriterView_textColor, getResources().getColor(DEFAULT_TEXT_COLOR_ID));
        interval = a.getInt(R.styleable.TypewriterView_typingInterval, DEFAULT_INTERVAL_IN_MSEC);
        delay = a.getInt(R.styleable.TypewriterView_startDelay, DEFAULT_DELAY_IN_MSEC);
        Animation fadeIn = new AlphaAnimation(0f, 1f);
        fadeIn.setDuration(FADE_DURATION);
        Animation noFade = new AlphaAnimation(1f, 1f);
        noFade.setDuration(FADE_DURATION);
        setInAnimation(fadeIn);
        setOutAnimation(noFade);
        setFactory(new ViewFactory() {
            @Override
            public View makeView() {
                TextView view = new TextView(context);
                view.setTextSize(textSize);
                view.setTextColor(textColor);
                return view;
            }
        });
    }

    @Override
    public void setText(final CharSequence text) {
        if (timerTask != null) {
            cancel();
        }
        if (text.length() == 0) {
            return;
        }
        timerTask = new TimerTask() {
            @Override
            public void run() {
                currentLength++;
                final String current = text.toString().substring(0, currentLength);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        TypewriterView.super.setText(current);
                    }
                });
                if (current.equals(text)) {
                    // すべて表示した
                    timerTask.cancel();
                    timerTask = null;
                    currentLength = 0;
                    if (listener != null) {
                        listener.onTypeFinished();
                    }
                }
            }
        };
        timer.schedule(timerTask, delay, interval);
    }

    public void clearText() {
        cancel();
        setCurrentText("");
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    private void cancel() {
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
        currentLength = 0;
    }

    public interface Listener {
        void onTypeFinished();
    }
}
