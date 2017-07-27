package com.chenhaijun.scoreboard.view.piegraph;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.chenhaijun.scoreboard.R;
import com.chenhaijun.scoreboard.utils.DensityUtil;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.TypeEvaluator;
import com.nineoldandroids.animation.ValueAnimator;

import java.util.ArrayList;

public class PieGraph extends View implements HoloGraphAnimate {

    private int mPadding;
    private int mInnerCircleRatio;
    private ArrayList<PieSlice> mSlices        = new ArrayList<PieSlice>();
    private Paint               mPaint         = new Paint();
    private int                 mSelectedIndex = -1;
    private OnSliceClickedListener mListener;
    private boolean mDrawCompleted         = false;
    private RectF   mRectF                 = new RectF();
    private Bitmap  mBackgroundImage       = null;
    private Point   mBackgroundImageAnchor = new Point(0, 0);
    private boolean mBackgroundImageCenter = false;

    private String title;
    private String total;
    private int    titleTextSize;
    private int    totalTextSize;
    private int titleColor           = 0xffffffff;
    private int totalColor           = 0xffffffff;
    private int dividerColor         = 0xf06c6c6c;
    private int defaultPiesliceColor = 0x0a000000;
    private int defaultDividerWidth  = 40;
    private int mDuration            = 300;//in ms
    private Interpolator              mInterpolator;
    private Animator.AnimatorListener mAnimationListener;
    private ValueAnimator             mValueAnimator;

    public PieGraph(Context context) {
        this(context, null);
    }

    public PieGraph(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieGraph(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);

        titleTextSize = DensityUtil.dp2px(getContext(), 18);
        totalTextSize = DensityUtil.dp2px(getContext(), 20);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PieGraph, 0, 0);
        mInnerCircleRatio = a.getInt(R.styleable.PieGraph_pieInnerCircleRatio, 0);
        mPadding = a.getDimensionPixelSize(R.styleable.PieGraph_pieSlicePadding, 0);
        a.recycle();
    }

    public void onDraw(Canvas canvas) {
        float midX, midY, radius, innerRadius;

        canvas.drawColor(Color.TRANSPARENT);
        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setTextAlign(Paint.Align.CENTER);


        float currentAngle = 270;
        float currentSweep = 0;
        float totalValue   = 0;

        midX = getWidth() / 2;
        midY = getHeight() / 2;
        if (midX < midY) {
            radius = midX;
        } else {
            radius = midY;
        }
        radius -= mPadding;
        innerRadius = radius * mInnerCircleRatio / 255;

        if (mBackgroundImage != null) {
            if (mBackgroundImageCenter)
                mBackgroundImageAnchor.set(
                        getWidth() / 2 - mBackgroundImage.getWidth() / 2,
                        getHeight() / 2 - mBackgroundImage.getHeight() / 2
                                          );
            canvas.drawBitmap(mBackgroundImage, mBackgroundImageAnchor.x, mBackgroundImageAnchor.y, mPaint);
        } else if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(total)) {
            mPaint.setStrokeWidth(3);

            mPaint.setTextSize(titleTextSize);
            mPaint.setColor(titleColor);
            canvas.drawText(title, midX, midY - titleTextSize / 2 - defaultDividerWidth / 2 + 20, mPaint);

            mPaint.setTextSize(totalTextSize);
            mPaint.setColor(dividerColor);
            canvas.drawLine(midX / 2, midY , midX + midX / 2, midY + 1, mPaint);

            mPaint.setTextSize(totalTextSize);
            mPaint.setColor(totalColor);
            canvas.drawText(total, midX, midY + totalTextSize / 2 + 40 + defaultDividerWidth / 2, mPaint);
        }

        for (PieSlice slice : mSlices) {
            totalValue += slice.getGoalValue();
        }

        int count = 0;
        for (PieSlice slice : mSlices) {
            if (slice.getValue() == 0) {
                continue;
            }
            Path p = slice.getPath();
            p.reset();

            if (mSelectedIndex == count && mListener != null) {
                mPaint.setColor(slice.getSelectedColor());
            } else {
                mPaint.setColor(slice.getColor());
            }
            currentSweep = (slice.getValue() / totalValue) * (360);

            mRectF.set(midX - radius, midY - radius, midX + radius, midY + radius);
            createArc(p, mRectF, currentSweep,
                      currentAngle + mPadding, currentSweep - mPadding);
            mRectF.set(midX - innerRadius, midY - innerRadius,
                       midX + innerRadius, midY + innerRadius);
            createArc(p, mRectF, currentSweep,
                      (currentAngle + mPadding) + (currentSweep - mPadding),
                      -(currentSweep - mPadding));

            p.close();

            // Create selection region
            Region r = slice.getRegion();
            r.set((int) (midX - radius),
                  (int) (midY - radius),
                  (int) (midX + radius),
                  (int) (midY + radius));
            canvas.drawPath(p, mPaint);
            currentAngle = currentAngle + currentSweep;

            count++;
        }
        if (count == 0 && mSlices.size() > 0) {
            Path p = mSlices.get(0).getPath();
            p.reset();
//        	mPaint.setColor(mSlices.get(0).getColor());
            mPaint.setColor(defaultPiesliceColor);
            mRectF.set(midX - radius, midY - radius, midX + radius, midY + radius);
            createArc(p, mRectF, 360, 0, 360);
            mRectF.set(midX - innerRadius, midY - innerRadius, midX + innerRadius, midY + innerRadius);
            createArc(p, mRectF, 360, 360, -360);
            canvas.drawPath(p, mPaint);
        }
        mDrawCompleted = true;
    }

    private void createArc(Path p, RectF mRectF, float currentSweep, float startAngle, float sweepAngle) {
        if (currentSweep == 360) {
            p.addArc(mRectF, startAngle, sweepAngle);
        } else {
            p.arcTo(mRectF, startAngle, sweepAngle);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mDrawCompleted) {
            Point point = new Point();
            point.x = (int) event.getX();
            point.y = (int) event.getY();

            int    count = 0;
            Region r     = new Region();
            for (PieSlice slice : mSlices) {
                r.setPath(slice.getPath(), slice.getRegion());
                switch (event.getAction()) {
                    default:
                        break;
                    case MotionEvent.ACTION_DOWN:
                        if (r.contains(point.x, point.y)) {
                            mSelectedIndex = count;
                            postInvalidate();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (count == mSelectedIndex
                            && mListener != null
                            && r.contains(point.x, point.y)) {
                            mListener.onClick(mSelectedIndex);
                        }
                        break;
                }
                count++;
            }
        }
        // Case we click somewhere else, also get feedback!
        if (MotionEvent.ACTION_UP == event.getAction()
            && mSelectedIndex == -1
            && mListener != null) {
            mListener.onClick(mSelectedIndex);
        }
        // Reset selection
        if (MotionEvent.ACTION_UP == event.getAction()
            || MotionEvent.ACTION_CANCEL == event.getAction()) {
            mSelectedIndex = -1;
            postInvalidate();
        }
        return true;
    }

    public Bitmap getBackgroundBitmap() {
        return mBackgroundImage;
    }

    public void setBackgroundBitmap(Bitmap backgroundBitmap, int pos_x, int pos_y) {
        mBackgroundImage = backgroundBitmap;
        mBackgroundImageAnchor.set(pos_x, pos_y);
        postInvalidate();
    }

    public void setBackgroundBitmap(Bitmap backgroundBitmap) {
        mBackgroundImageCenter = true;
        mBackgroundImage = backgroundBitmap;
        postInvalidate();
    }

    public void setTitle(String title) {
        this.title = title;
        postInvalidate();
    }

    public void setTotal(String total) {
        this.total = total;
        postInvalidate();
    }

    /**
     * sets padding
     *
     * @param padding
     */
    public void setPadding(int padding) {
        mPadding = padding;
        postInvalidate();
    }

    public void setInnerCircleRatio(int innerCircleRatio) {
        mInnerCircleRatio = innerCircleRatio;
        postInvalidate();
    }

    public ArrayList<PieSlice> getSlices() {
        return mSlices;
    }

    public void setSlices(ArrayList<PieSlice> slices) {
        mSlices = slices;
        postInvalidate();
    }

    public PieSlice getSlice(int index) {
        return mSlices.get(index);
    }

    public void addSlice(PieSlice slice) {
        mSlices.add(slice);
        postInvalidate();
    }

    public void setOnSliceClickedListener(OnSliceClickedListener listener) {
        mListener = listener;
    }

    public void removeSlices() {
        mSlices.clear();
        postInvalidate();
    }

    @Override
    public int getDuration() {
        return mDuration;
    }

    @Override
    public void setDuration(int duration) {
        mDuration = duration;
    }

    @Override
    public Interpolator getInterpolator() {
        return mInterpolator;
    }

    @Override
    public void setInterpolator(Interpolator interpolator) {
        mInterpolator = interpolator;
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    @Override
    public boolean isAnimating() {
        if (mValueAnimator != null)
            return mValueAnimator.isRunning();
        return false;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    @Override
    public boolean cancelAnimating() {
        if (mValueAnimator != null)
            mValueAnimator.cancel();
        return false;
    }


    /**
     * Stops running animation and starts a new one, animating each slice from their current to goal value.
     * If removing a slice, consider animating to 0 then removing in onAnimationEnd listener.
     * Default inerpolator is linear; constant speed.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    @Override
    public void animateToGoalValues() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR1) {
            return;
        }
        if (mValueAnimator != null)
            mValueAnimator.cancel();

        for (PieSlice s : mSlices)
            s.setOldValue(s.getValue());
        ValueAnimator va = ValueAnimator.ofObject(new TypeEvaluator() {

            @Override
            public Object evaluate(float fraction, Object startValue, Object endValue) {
                float startFloat = ((Number) startValue).floatValue();
                return startFloat + fraction * (((Number) endValue).floatValue() - startFloat);
            }

        }, 0, 1);
        mValueAnimator = va;
        va.setDuration(getDuration());
        if (mInterpolator == null) mInterpolator = new LinearInterpolator();
        va.setInterpolator(mInterpolator);
        if (mAnimationListener != null) va.addListener(mAnimationListener);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float f = Math.max(animation.getAnimatedFraction(), 0.01f);//avoid blank frames; never multiply values by 0
                for (PieSlice s : mSlices) {
                    float x = s.getGoalValue() - s.getOldValue();
                    s.setValue(s.getOldValue() + (x * f));
                }
                postInvalidate();
            }
        });
        va.start();
    }


    @Override
    public void setAnimationListener(Animator.AnimatorListener animationListener) {
        mAnimationListener = animationListener;
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
    }

    public void setTotalColor(int totalColor) {
        this.totalColor = totalColor;
    }

    public interface OnSliceClickedListener {
        public abstract void onClick(int index);
    }
}
