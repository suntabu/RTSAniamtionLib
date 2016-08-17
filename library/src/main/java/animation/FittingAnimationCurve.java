package animation;

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




    public FittingAnimationCurve(ArrayList<Float> values, int xRatio, int yRatio,float startTime,float stopTime) {
        mValues = values;
        mXRatio = xRatio;
        mYRatio = yRatio;

        xPhasePath = new AnimationCurve(startTime,stopTime);
        yPhasePath = new AnimationCurve(startTime,stopTime);


        initData();

    }


    private void initData() {
        for (int i = 0; i < mValues.size(); i++) {
            if (i % 3 == 0) {
                float x = mValues.get(i) * mXRatio;
                float y = mValues.get(i + 1) * mYRatio;
                float t = mValues.get(i + 2);


                xPhasePath.addKeyFrame(new KeyFrame(t, x));
                yPhasePath.addKeyFrame(new KeyFrame(t, y));
            }
        }

    }


    public float[] getValue(float t) {
        float[] values = new float[2];

        values[0] = xPhasePath.evaluate(t);
        values[1] = yPhasePath.evaluate(t);

        //LogMgr.i("", "values: " + values[0] + "    |    " + values[1]);
        return values;
    }



}
