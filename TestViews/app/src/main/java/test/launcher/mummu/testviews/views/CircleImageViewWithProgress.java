package test.launcher.mummu.testviews.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by muhammed on 8/9/2016.
 */
public class CircleImageViewWithProgress extends ImageView {
    private int mDuration = 100;
    private int mProgress = 75;
    private Paint mPaint = new Paint();
    private RectF mRectF = new RectF();
    private int mBackgroundColor = Color.LTGRAY;
    private int mPrimaryColor = Color.parseColor("#1A06E5");
    private float mStrokeWidth = 5F;
    private OnProgressChangeListener mOnChangeListener;

    public interface OnProgressChangeListener {
        void onChange(int duration, int progress, float rate);
    }

    public CircleImageViewWithProgress(Context context) {
        super(context);
    }

    public void setMax(int max) {
        if (max < 0) {
            max = 0;
        }
        mDuration = max;
    }


    public void setProgress(int progress) {
        if (progress > mDuration) {
            progress = mDuration;
        }
        mProgress = progress;
        if (mOnChangeListener != null) {
            mOnChangeListener.onChange(mDuration, progress, getRateOfProgress());
        }
        invalidate();
    }

    private float getRateOfProgress() {
        return (float) mProgress / mDuration;
    }

    public int getProgress() {
        return mProgress;
    }

    public void setBackgroundColor(int color) {
        mBackgroundColor = color;
    }

    public void setPrimaryColor(int color) {
        mPrimaryColor = color;
    }

    public void setCircleWidth(float width) {
        mStrokeWidth = width;
    }

    public int getMax() {
        return mDuration;
    }

    public void setOnProgressChangeListener(OnProgressChangeListener l) {
        mOnChangeListener = l;
    }

    public CircleImageViewWithProgress(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public CircleImageViewWithProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int halfWidth = getWidth() / 2;
        int halfHeight = getHeight() / 2;
        int radius = halfWidth < halfHeight ? halfWidth : halfHeight;
        float halfStrokeWidth = (mStrokeWidth / 2) + 10;

        mPaint.setColor(mBackgroundColor);
        mPaint.setDither(true);
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);

        canvas.drawCircle(halfWidth, halfHeight, radius - halfStrokeWidth, mPaint);

        mPaint.setColor(mPrimaryColor);
        mRectF.top = halfHeight - radius + halfStrokeWidth;
        mRectF.bottom = halfHeight + radius - halfStrokeWidth;
        mRectF.left = halfWidth - radius + halfStrokeWidth;
        mRectF.right = halfWidth + radius - halfStrokeWidth;
        canvas.drawArc(mRectF, -90, getRateOfProgress() * 360, false, mPaint);
        canvas.save();
    }
}
