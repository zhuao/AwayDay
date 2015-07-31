package me.zhuao.android.stepIndicator.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.zhuao.android.stepIndicator.R;

public class StepsBar extends LinearLayout {

    private static int STEPS = 4;

    private View[] progressBar;
    private int currentStep = 1;
    private View[] circles;
    private LinearLayout textLayout;
    private int textLayoutHeight;

    public StepsBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepsBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.progress_bar, this);
        final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.StepsBar, defStyleAttr, 0);
        CharSequence[] textArray;
        try {
            textArray = typedArray.getTextArray(R.styleable.StepsBar_stepText);
            STEPS = textArray.length;
        } finally {
            typedArray.recycle();
        }
        findView();

        if (STEPS == 3) {
            ((TextView)textLayout.getChildAt(0)).setText(textArray[0]);
            ((TextView)textLayout.getChildAt(1)).setText(textArray[1]);
            ((TextView)textLayout.getChildAt(3)).setText(textArray[2]);

            textLayout.getChildAt(2).setVisibility(GONE);
            findViewById(R.id.step_3).setVisibility(GONE);
        }

        if (STEPS == 4) {
            for (int i = 0; i < STEPS; i++) {
                ((TextView)textLayout.getChildAt(i)).setText(textArray[i]);
            }
        }
    }

    public void nextStep() {
        if (currentStep == STEPS) {
            return;
        }
        playAnimationOnStep(currentStep + 1);
        currentStep++;
    }

    public void backwardStep() {
        if (currentStep == 1) {
            return;
        }

        switchToStepInStatus(currentStep - 1);
        hideStepIndicator(currentStep);

        getTargetProgressBar().setPivotX(0);
        float previousScaleX = getTargetProgressBar().getScaleX();
        if (previousScaleX == 1) {
            getTargetProgressBar().setScaleX(Math.max(0, (currentStep - 2) / Float.valueOf(currentStep - 1)));
        } else {
            getTargetProgressBar().setScaleX(Math.max(0, (currentStep - 2) / Float.valueOf(STEPS - 1)));
        }
        currentStep --;
    }


    private void hideStepIndicator(int step) {
        View stepOverIndicator = getTargetCircle(step).findViewWithTag(getResources().getString(R.string.step_bar_step_over_indicator_tag));
        View stepInIndicator = getTargetCircle(step).findViewWithTag(getResources().getString(R.string.step_bar_step_in_indicator_tag));
        stepOverIndicator.setVisibility(GONE);
        stepInIndicator.setVisibility(View.GONE);
    }

    private void playAnimationOnStep(int step) {
        getTargetProgressBar().setVisibility(VISIBLE);
        getTargetProgressBar().setScaleX(1);
        getTargetProgressBar().startAnimation(compositedAnimation(step));

        int originalStep = step - 1;
        getTargetCircle(originalStep).startAnimation(createOriginCycleZoomAnimation());
        switchToStepOverStatus(originalStep);
    }

    private void switchToStepOverStatus(int step) {
        View stepOverIndicator = getTargetCircle(step).findViewWithTag(getResources().getString(R.string.step_bar_step_over_indicator_tag));
        View stepInIndicator = getTargetCircle(step).findViewWithTag(getResources().getString(R.string.step_bar_step_in_indicator_tag));
        stepOverIndicator.setVisibility(VISIBLE);
        stepInIndicator.setVisibility(View.GONE);
    }

    private void switchToStepInStatus(int step) {
        View stepOverIndicator = getTargetCircle(step).findViewWithTag(getResources().getString(R.string.step_bar_step_over_indicator_tag));
        View stepInIndicator = getTargetCircle(step).findViewWithTag(getResources().getString(R.string.step_bar_step_in_indicator_tag));
        stepOverIndicator.setVisibility(GONE);
        stepInIndicator.setVisibility(View.VISIBLE);
    }

    private ScaleAnimation createOriginCycleZoomAnimation() {
        final ScaleAnimation zoomAnimation = new ScaleAnimation(1, 1.5f, 1, 1.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        zoomAnimation.setInterpolator(new CycleInterpolator(0.5f));
        zoomAnimation.setDuration(300);
        zoomAnimation.setFillAfter(true);
        return zoomAnimation;
    }

    private ScaleAnimation compositedAnimation(final int step) {
        ScaleAnimation growingAnimation = new ScaleAnimation(Math.max(0, (step - 2) / Float.valueOf(STEPS - 1)), (step - 1) / Float.valueOf(STEPS - 1), 1, 1, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        growingAnimation.setDuration(300);
        growingAnimation.setFillAfter(true);

        final ScaleAnimation zoomAnimation = createOriginCycleZoomAnimation();

        growingAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                getTargetCircle(step).setVisibility(View.VISIBLE);
                getTargetCircle(step).startAnimation(zoomAnimation);

                switchToStepInStatus(step);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        return growingAnimation;
    }

    private void findView() {
        progressBar = new View[STEPS];
        progressBar[0] = findViewById(R.id.steps_progress_bar);

        circles = new View[STEPS];
        circles[0] = findViewById(R.id.circle_step_1);
        circles[1] = findViewById(R.id.circle_step_2);
        if (STEPS == 3) {
            circles[2] = findViewById(R.id.circle_step_3);
        } else if (STEPS == 4) {
            circles[2] = findViewById(R.id.circle_step_3);
            circles[3] = findViewById(R.id.circle_step_4);
        }

        textLayout = (LinearLayout) findViewById(R.id.steps_text);
        textLayoutHeight = getResources().getDimensionPixelSize(R.dimen.steps_bar_height);
    }

    private View getTargetProgressBar() {
        return progressBar[0];
    }

    private View getTargetCircle(int step) {
        return circles[step - 1];
    }

    public void slideText(int offsetY, boolean toDisappear) {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) textLayout.getLayoutParams();
        int topMargin = params.topMargin;
        if (toDisappear) {
            if (topMargin > 0) {
                params.setMargins(getResources().getDimensionPixelSize(R.dimen.steps_Indicator_container_margin), Math.max(topMargin - offsetY / 2, 0), 0, 0);
                textLayout.setLayoutParams(params);
            }
        } else {
            if (topMargin < textLayoutHeight) {
                params.setMargins(getResources().getDimensionPixelSize(R.dimen.steps_Indicator_container_margin), Math.min(topMargin + offsetY / 2, textLayoutHeight), 0, 0);
                textLayout.setLayoutParams(params);
            }
        }
    }

    public void finishSlide() {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) textLayout.getLayoutParams();
        if ((params.topMargin > 0) && params.topMargin < textLayoutHeight) {
            if (params.topMargin < textLayoutHeight / 2) {
                params.setMargins(getResources().getDimensionPixelSize(R.dimen.steps_Indicator_container_margin), 0, 0, 0);
            } else {
                params.setMargins(getResources().getDimensionPixelSize(R.dimen.steps_Indicator_container_margin), textLayoutHeight, 0, 0);
            }
            textLayout.setLayoutParams(params);
        }

    }

    public interface StepBarOnTouchListener extends OnTouchListener {

    }
}
