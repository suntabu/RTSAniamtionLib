package animation;

import android.app.Activity;
import android.content.Context;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.IOException;
import java.util.ArrayList;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;


/**
 * Created by gouzhun on 2016/5/25.
 */
public class BaseEffect {
    protected static final float DEFAULT_DURATION = 2f;
    private static final String TAG = "BaseEffect";

    protected static int CONTAINER_WIDTH = 0;
    protected static int CONTAINER_HEIGHT = 0;

    private int width, height;

    protected GifImageView mGiv;
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

    private ArrayList<Float> colorValues;

    private ArrayList<Float> size;

    private ArrayList<Float> position;

    private String effectName;

    private String textureName;

    private ViewGroup mContainer;

    private Context context;

    /**
     * @param delegate
     **************************************************/

    public void render(final Delegate delegate) {
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

                            pathValues.add(0, position.get(0));
                            pathValues.add(1, position.get(1));
                            pathValues.add(2, startTime);
                            FittingAnimationCurve posPath = new FittingAnimationCurve(pathValues, CONTAINER_WIDTH, CONTAINER_HEIGHT, startTime, stopTime);
                            FittingAnimationCurve scalePath = new FittingAnimationCurve(scaleValues, 1, 1, startTime, stopTime);
                            FittingAnimationCurve rotatePath = new FittingAnimationCurve(rotateValues, 1, 1, startTime, stopTime);
                            FittingAnimationCurve colorPath = new FittingAnimationCurve(colorValues, 1, 1, startTime, stopTime);
//                            test.suntabu.com.rtsanimationtest.FittingPath rotatePath = new test.suntabu.com.rtsanimationtest.FittingPath(rotateValues, 1, 1);


                            RTSAnimation rts = new RTSAnimation(posPath, scalePath, rotatePath, colorPath, mGiv, width, height);
//                    ScaleAnimation rts = new ScaleAnimation(0.3f,4.3f,0.5f,2.7f);
                            rts.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    delegate.invoke();
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });

                            rts.setDuration((long) (1000 * (stopTime - startTime)));

                            mGiv.startAnimation(rts);
//                            mGiv.setVisibility(View.VISIBLE);
                            mStarted = true;
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


    public void createEffectView(Context context, ViewGroup parent, String animationName) {
        mGiv = new GifImageView(context);
        this.context = context;
        try {
            mContainer = parent;
            CONTAINER_WIDTH = parent.getWidth();
            CONTAINER_HEIGHT = parent.getHeight();
            String path = "anim/" + animationName + "/" + textureName + ".png";
            Log.e(TAG, "load:" + path);
            if (path.toLowerCase().endsWith(".gif")) {
                GifDrawable gifFromAssets = new GifDrawable(context.getAssets(), path);
                mGiv.setImageDrawable(gifFromAssets);
            } else {
                BitmapDrawable gifFromAssets = new BitmapDrawable(context.getAssets().open(path));
                mGiv.setImageDrawable(gifFromAssets);
            }
            if (size.size() == 2) {
                width = (int) (size.get(0) * CONTAINER_WIDTH);
                height = (int) (size.get(1) * CONTAINER_HEIGHT);
            } else {
                width = (int) (0.15f * CONTAINER_WIDTH);
                height = (int) (0.15f * CONTAINER_HEIGHT);
                Log.e("ERROR", "animationData.json 沒有給定原始size");
            }
            mGiv.setScaleType(ImageView.ScaleType.FIT_XY);
            FrameLayout.LayoutParams tparams = new FrameLayout.LayoutParams(width, height);//定义显示组件参数
            mGiv.setLayoutParams(tparams);
            parent.addView(mGiv, tparams);
//            mGiv.setVisibility(View.INVISIBLE);
            mGiv.setAlpha(0);
        } catch (IOException e) {
            dispose();
            e.printStackTrace();
        }
    }

    public void dispose() {
        if (mGiv != null) {
            mGiv.setVisibility(View.GONE);
            mContainer.removeView(mGiv);
            mGiv = null;

        }
        mContainer = null;
        context = null;
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

    public ArrayList<Float> getColorValues() {
        return colorValues;
    }

    public void setColorValues(ArrayList<Float> colorValues) {
        this.colorValues = colorValues;
    }

    public void setTextureName(String textureName) {
        this.textureName = textureName;
    }
}
