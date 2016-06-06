package com.suntabu.animation;

import android.app.Activity;
import android.content.Context;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;


import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by gouzhun on 2016/5/25.
 */
public class BaseEffect {
    protected static final float DEFAULT_DURATION = 2f;


    protected static int CONTAINER_WIDTH = 0;
    protected static int CONTAINER_HEIGHT = 0;

    private int width, height;

    protected ImageView mGiv;
    /**
     * 当前动画执行的阶段（相对于Animation持续时间的百分比）
     */
    protected float mPeriod;


    protected boolean mStarted;

    private float startTime;
    private float stopTime;

    private ArrayList<Float> pathValues;

    private ArrayList<Float> scaleValues;

    private ArrayList<Float> rotateValues;

    private ArrayList<Float> size;

    private ArrayList<Float> position;

    private String effectName;

    private ViewGroup mContainer;

    private Context context;

    /****************************************************/

    public void render() {
        if (mGiv == null) {
            return;
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!mStarted) {
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //启动动画
                            mGiv.setVisibility(View.VISIBLE);

                            FittingAnimationCurve posPath = new FittingAnimationCurve(pathValues, CONTAINER_WIDTH, CONTAINER_HEIGHT);
                            FittingAnimationCurve scalePath = new FittingAnimationCurve(scaleValues, 1, 1);
//                            com.suntabu.animation.FittingPath rotatePath = new com.suntabu.animation.FittingPath(rotateValues, 1, 1);


                            RTSAnimation rts = new RTSAnimation(posPath, scalePath, null, mGiv,width,height);
//                    ScaleAnimation rts = new ScaleAnimation(0.3f,4.3f,0.5f,2.7f);
                            rts.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    mGiv.setVisibility(View.GONE);
                                    mContainer.removeView(mGiv);
                                    mGiv = null;
                                    mContainer = null;
                                    context = null;
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });

                            rts.setDuration((long) (1000 * (stopTime - startTime)));

                            mGiv.startAnimation(rts);
                            mStarted = false;
                        }
                    });

                }
            }
        }, (long) (startTime * 1000));

    }

    private void fillPath(Path posPath, ArrayList<Float> pathValues, int ratioWidth, int ratioHeight) {
        for (int i = 0; i < pathValues.size(); i++) {
            if (i % 2 == 0) {
                int x = (int) (pathValues.get(i) * ratioWidth);
                int y = (int) (pathValues.get(i + 1) * ratioHeight);
                if (i == 0) {
                    posPath.moveTo(x, y);
                } else {
                    posPath.lineTo(x, y);
                }
            }

        }
    }


    public void createEffectView(Context context, ViewGroup parent) {
        mGiv = new ImageView(context);
        this.context = context;
        try {
            mContainer = parent;
            CONTAINER_WIDTH = parent.getWidth();
            CONTAINER_HEIGHT = parent.getHeight();
            String path = "anim/" + effectName;
            if (path.toLowerCase().endsWith(".gif")) {
              /*  GifDrawable gifFromAssets = new GifDrawable(context.getAssets(), path);
                mGiv.setImageDrawable(gifFromAssets);*/
            } else {
                BitmapDrawable gifFromAssets = new BitmapDrawable(context.getAssets().open(path));
                mGiv.setImageDrawable(gifFromAssets);
            }
            width = (int) (size.get(0) * CONTAINER_WIDTH);
            height = (int) (size.get(1) * CONTAINER_HEIGHT);
            FrameLayout.LayoutParams tparams = new FrameLayout.LayoutParams(width, height);//定义显示组件参数
            parent.addView(mGiv, tparams);
            mGiv.setVisibility(View.GONE);
        } catch (IOException e) {
            mGiv = null;
            e.printStackTrace();
        }


    }

    public void setStartTime(float startTime) {
        this.startTime = startTime;
    }

    public void setStopTime(float stopTime) {
        this.stopTime = stopTime;
    }

    public void setPathValues(ArrayList<Float> pathValues) {
        this.pathValues = pathValues;
    }

    public void setScaleValues(ArrayList<Float> scaleValues) {
        this.scaleValues = scaleValues;
    }

    public void setRotateValues(ArrayList<Float> rotateValues) {
        this.rotateValues = rotateValues;
    }

    public void setSize(ArrayList<Float> size) {
        this.size = size;
    }

    public void setPosition(ArrayList<Float> position) {
        this.position = position;
    }

    public void setEffectName(String effectName) {
        this.effectName = effectName;
    }
}
