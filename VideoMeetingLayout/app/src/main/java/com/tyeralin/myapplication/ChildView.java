package com.tyeralin.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class ChildView extends LinearLayout {
    public static final int type_0 = 0; //main
    public static final int type_1 = 1; //other video
    public int pos = -1;
    public int type = -1;
    private TextView textView;

    public ChildView(Context context) {
        super(context);
        initView(context);
    }


    public ChildView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ChildView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);

    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.child,this,true);
        textView = findViewById(R.id.context);
    }


    public void setText(String title) {
        textView.setText(title);
    }
}
