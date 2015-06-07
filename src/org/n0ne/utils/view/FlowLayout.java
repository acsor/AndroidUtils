package org.n0ne.utils.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import org.n0ne.utils.R;

/**
 * TO-DO Fix padding, margin and the like measurements.
 */
public class FlowLayout extends ViewGroup {

    private static final String TAG = FlowLayout.class.getSimpleName();

    private int mLineHeight;

    public static final int LINE_HEIGHT_MATCH_HIGHEST = -1;

    public FlowLayout (Context context) {
        super(context.getApplicationContext());
        init(null);
    }

    public FlowLayout (Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout (Context context, AttributeSet attrs, int defStyleRes) {
        super(context.getApplicationContext(), attrs, defStyleRes);

        final TypedArray a = context.obtainStyledAttributes(
                attrs,
                R.styleable.FlowLayout,
                defStyleRes,
                0
        );

        try {
            init(a);
        } finally {
            a.recycle();
        }
    }

    private void init (TypedArray a) {
        mLineHeight = LINE_HEIGHT_MATCH_HIGHEST;

        if (a != null) {
            mLineHeight = (int) a.getDimension(R.styleable.FlowLayout_lineHeight, mLineHeight);
        }
    }

    /**
     * TO-DO Check layout algorithm.
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
        final int decWidth = MeasureSpec.getSize(widthMeasureSpec),
                decHeight = MeasureSpec.getSize(heightMeasureSpec);

        int currX = 0, currY = 0;

        int maxHeightInLine = 0;
        int maxXInLayout = 0;

        View currChild;
        LayoutParams childParams;

        /**
         * Currently don't know which value decWidth and decHeight get set to
         * when MeasureSpec.getMode() returns UNSPECIFIED.
         * Just handling cases in which the mode is EXACTLY or AT_MOST.
         * TO-DO Include UNSPECIFIED as well.
         */

        // TO-DO Check how many times getChildCount() gets called.
        for (int i = 0; i < getChildCount(); i++) {
            currChild = getChildAt(i);

            measureChild(currChild, widthMeasureSpec, heightMeasureSpec);
            childParams = (LayoutParams) currChild.getLayoutParams();

            maxHeightInLine = Math.max(maxHeightInLine, measureEffectiveChildHeight(currChild));

            if ((currX + measureEffectiveChildWidth(currChild) > decWidth)) {
                currX = 0;
                currY = getNextLineY(currY, maxHeightInLine);
            } else {
            }

            childParams.x = currX;
            childParams.y = currY;

            currX += measureEffectiveChildWidth(currChild);
            maxXInLayout = Math.max(maxXInLayout, currX);
        }

        setMeasuredDimension(
                resolveSize(maxXInLayout, widthMeasureSpec),
                resolveSize(getNextLineY(currY, maxHeightInLine), heightMeasureSpec)
        );
    }

    @Override
    protected void onLayout (boolean changed, int l, int t, int r, int b) {
        View currChild;
        LayoutParams childParams;

        for (int i = 0; i < getChildCount(); i++) {
            currChild = getChildAt(i);
            childParams = (LayoutParams) currChild.getLayoutParams();

            currChild.layout(
                    childParams.x,
                    childParams.y,
                    childParams.x + currChild.getMeasuredWidth(),
                    childParams.y + currChild.getMeasuredHeight()
            );
        }
    }

    @Override
    public LayoutParams generateLayoutParams (AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams () {
        return new LayoutParams();
    }

    @Override
    protected LayoutParams generateLayoutParams (ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams (ViewGroup.LayoutParams p) {
        return p instanceof FlowLayout.LayoutParams;
    }

    private int measureEffectiveChildWidth (View child) {
        return child.getPaddingLeft() +
                child.getMeasuredWidth() +
                child.getPaddingRight();
    }

    private int measureEffectiveChildHeight (View child) {
        return child.getPaddingTop() +
                child.getMeasuredHeight() +
                child.getPaddingBottom();
    }

    private int getNextLineY (int currLineY, int maxHeightInPreviousLine) {
        if (mLineHeight == LINE_HEIGHT_MATCH_HIGHEST) {
            return currLineY + maxHeightInPreviousLine;
        } else {
            return currLineY + mLineHeight;
        }
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {

        private int x, y;

        public LayoutParams () {
            super(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            x = y = 0;
        }

        public LayoutParams (Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams (ViewGroup.LayoutParams parent) {
            super(parent);
        }

    }

}
