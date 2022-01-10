package com.tyeralin.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


public class ParentView extends RelativeLayout {
    private static final String TAG = "ParentView";
    private int containerWidth, containerHeight;

    private int largeWidth = 0;
    private int largeHeight = 0;
    private int smallWidth = 0;
    private int smallHeight = 0;
    private int defaultMargin = 4;

    public ParentView(Context context) {
        super(context);
        init(context);
    }

    public ParentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ParentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    public ParentView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        containerWidth = getMeasuredWidth();
        containerHeight = getMeasuredHeight();
        onMeasureChild(containerWidth - getPaddingLeft() - getPaddingRight(), containerHeight - getPaddingTop() - getPaddingRight());
    }

    private void onMeasureChild(int width, int height) {
        int count = getChildCount();

        switch (count) {
            case 0:
                break;
            case 1:
                largeWidth = width;
                largeHeight = height;

                break;
            case 2:
            case 3:
            case 4:
            case 5:
                smallHeight = height / 4;
                smallWidth = width / 4;
                largeWidth = width - smallWidth;
                largeHeight = height - smallHeight;
                Log.e(TAG, "sw:" + smallWidth + ",sh:" + smallHeight + ",lw:" + largeWidth + ",lh:" + largeHeight);
                break;
            case 6:
                smallWidth = width / 3;
                smallHeight = height / 3;
                largeWidth = width - smallWidth;
                largeHeight = height - smallHeight;
                Log.e(TAG, "sw:" + smallWidth + ",sh:" + smallHeight + ",lw:" + largeWidth + ",lh:" + largeHeight);

                break;
            case 7:
            case 8:
                smallWidth = width / 4;
                smallHeight = height / 4;
                largeWidth = width - smallWidth;
                largeHeight = height - smallHeight;
                Log.e(TAG, "sw:" + smallWidth + ",sh:" + smallHeight + ",lw:" + largeWidth + ",lh:" + largeHeight);

                break;
            case 9:
            case 10:
                smallWidth = width / 5;
                smallHeight = height / 5;
                largeWidth = width - smallWidth;
                largeHeight = height - smallHeight;
                Log.e(TAG, "sw:" + smallWidth + ",sh:" + smallHeight + ",lw:" + largeWidth + ",lh:" + largeHeight);

                break;
        }
        // exactly
        int widthMeasureSpecLarge = MeasureSpec.makeMeasureSpec(largeWidth, MeasureSpec.EXACTLY);
        int heightMeasureSpecLarge = MeasureSpec.makeMeasureSpec(largeHeight, MeasureSpec.EXACTLY);
        int widthMeasureSpecSmall = MeasureSpec.makeMeasureSpec(smallWidth, MeasureSpec.EXACTLY);
        int heightMeasureSpecSmall = MeasureSpec.makeMeasureSpec(smallHeight, MeasureSpec.EXACTLY);

        //ViewGroup.LayoutParams layoutParams = getChildAt(i);
        //不启用child的margin

        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i);
            if (i == 0) {
                childView.measure(widthMeasureSpecLarge, heightMeasureSpecLarge);
            } else {
                childView.measure(widthMeasureSpecSmall, heightMeasureSpecSmall);
            }
        }
    }


    /**
     * 左右上下居中
     * child 0-5          1    child 6         5    child 7   4               8 N = 8    9  0 N4  10  0 N5
     * 0 -  2               0   4            0  5                              N4N        N5
     * 3             1  2  3               6
     * 4                            1 2 3  N
     */

//结合changed 自己控制 child layout 
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //自定义布局
        final int count = getChildCount();
        switch (count) {
            case 0:
                break;
            case 1:
                View child = getChildAt(0);
                if (child.getVisibility() != GONE) {
                    int left = 0;
                    int right = largeWidth;
                    int top = 0;
                    int bottom = largeHeight;
                    child.layout(left, top, right, bottom);
                }
                break;
            case 2:
            case 3:
            case 4:
            case 5:
                for (int i = 0; i < count; i++) {
                    View child2 = getChildAt(i);
                    //居中对齐
                    if (child2.getVisibility() != GONE) {
                        int left = 0;
                        int right = 0;
                        int top = 0;
                        int bottom = 0;
                        if (i == 0) {
                            right = largeWidth;
                            top = (containerHeight - largeHeight) / 2;
                            bottom = largeHeight + top;
                        } else {
                            int valuePos = i - 1;
                            int firstTop = (containerHeight - (count - 1) * smallHeight) / 2;
                            left = largeWidth;
                            right = largeWidth + smallWidth;
                            top = firstTop + valuePos * smallHeight;
                            bottom = top + smallHeight;
                        }
                        Log.e(TAG, "i:" + i + ",left:" + left + ",top:" + top + ",right:" + right + ",bottom:" + bottom);
                        child2.layout(left, top, right, bottom);
                    }
                }

                break;
            case 6:
            case 7:
            case 8:
                for (int i = 0; i < count; i++) {
                    ChildView child2 = (ChildView) getChildAt(i);
                    //居中对齐
                    if (child2.getVisibility() != GONE) {
                        int left = 0;
                        int right = 0;
                        int top = 0;
                        int bottom = 0;
                        if (i == 0) {
                            right = largeWidth;
                            top = 0;
                            bottom = largeHeight;
                        } else if (i < 4) {
                            //right
                            left = largeWidth;
                            right = largeWidth + smallWidth;
                            top = (i - 1) * smallHeight;
                            bottom = top + smallHeight;
                        } else {
                            //bottom
                            left = smallWidth * (i - 4);
                            right = left + smallWidth;
                            bottom = containerHeight;
                            top = bottom - smallHeight;
                        }
                        Log.e(TAG, "i:" + i + ",left:" + left + ",top:" + top + ",right:" + right + ",bottom:" + bottom);
                        child2.layout(left, top, right, bottom);
                    }
                }
                break;
            case 9:
            case 10:
                for (int i = 0; i < count; i++) {
                    View child2 = getChildAt(i);
                    //居中对齐
                    if (child2.getVisibility() != GONE) {
                        int left = 0;
                        int right = 0;
                        int top = 0;
                        int bottom = 0;
                        if (i == 0) {
                            right = largeWidth;
                            top = 0;
                            bottom = largeHeight;
                        } else if (i < 5) {
                            //right
                            left = largeWidth;
                            right = largeWidth + smallWidth;
                            top = (i - 1) * smallHeight;
                            bottom = top + smallHeight;
                        } else {
                            //bottom
                            left = smallWidth * (i - 5);
                            right = left + smallWidth;
                            bottom = containerHeight;
                            top = bottom - smallHeight;
                        }
                        child2.layout(left, top, right, bottom);
                    }
                }
                break;
        }

    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams layoutParams) {
        child.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        int count = getChildCount();
        if (count != 0) {
            child.setPadding(defaultMargin, defaultMargin, defaultMargin, defaultMargin);
            getChildAt(0).setPadding(defaultMargin, defaultMargin, defaultMargin, defaultMargin);
        } else {
            child.setPadding(0, 0, 0, 0);
        }
        super.addView(child, index, layoutParams);
    }


}
