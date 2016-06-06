package test.suntabu.com.rtsanimationtest;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by gouzhun on 2016/6/3.
 */
public class FittingAnimationCurve {
    private ArrayList<Float> mValues;

    private AnimationCurve xPhasePath;
    private AnimationCurve yPhasePath;


    private int mXRatio;
    private int mYRatio;

    private float[] xValue = new float[2];
    private float[] yValue = new float[2];


    private float xMax, yMax;

    public FittingAnimationCurve(ArrayList<Float> values, int xRatio, int yRatio) {
        mValues = values;
        mXRatio = xRatio;
        mYRatio = yRatio;

        xPhasePath = new AnimationCurve();
        yPhasePath = new AnimationCurve();


        xMax = Float.MIN_VALUE;
        yMax = Float.MIN_VALUE;

        initData();

    }


    private void initData() {
        for (int i = 0; i < mValues.size(); i++) {
            if (i % 3 == 0) {
                float x = mValues.get(i) * mXRatio;
                float y = mValues.get(i + 1) * mYRatio;
                float t = mValues.get(i + 2);

                if (x >= xMax) {
                    xMax = x;
                }

                if (y >= yMax) {
                    yMax = y;
                }

                xPhasePath.addKeyFrame(new KeyFrame(t, x));
                yPhasePath.addKeyFrame(new KeyFrame(t, y));
            }
        }

    }

    private float[] lastValue;

    public float[] getValue(float t) {
        float[] values = new float[2];

        values[0] = xPhasePath.evaluate(t);
        values[1] = yPhasePath.evaluate(t);

        Log.i("", "values: " + values[0] + "    |    " + values[1]);
        lastValue = values;
        return values;
    }

    public float[] getDeltaValue(float t) {
        if (lastValue == null) {
            lastValue = getValue(0);
        }

        float x = lastValue[0];
        float y = lastValue[1];

        float[] vals = getValue(t);

        float[] deltaValues = new float[]{vals[0] - x, vals[1] - y};
        return deltaValues;

    }

    public float[] getLastValue() {
        if (lastValue == null) {
            lastValue = getValue(0);
        }
        return lastValue;
    }

}
