package com.cresol.buzzapplication.util;

import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by Nitesh on 10/4/2016.
 */
public class ResizeAnimation extends Animation {

    private float mToHeight;
    private float mFromHeight;

    private float mToWidth;
    private float mFromWidth;
    private RelativeLayout layoutView;

    public ResizeAnimation(RelativeLayout layout,float fromHeight,float toHeight,float fromWidth,float toWidth) {

        mToHeight=fromWidth;
        mFromWidth=toWidth;
        mToHeight = toHeight;

        mFromHeight = fromHeight;
        layoutView=layout;

        //setDuration(50);
    }

    @Override
     protected void applyTransformation(float interpolatedTime, Transformation t) {
        float height = (mToHeight - mFromHeight) * interpolatedTime + mFromHeight;
        //float width = (mToWidth - mFromWidth) * interpolatedTime + mFromWidth;
        ViewGroup.LayoutParams p = layoutView.getLayoutParams();
        p.height = (int) height;
        // p.width = (int) width;
        layoutView.requestLayout();
    }
}



