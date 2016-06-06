package animation;

import android.graphics.Path;
import android.graphics.PathMeasure;

import java.util.ArrayList;

/**
 * Created by gouzhun on 2016/6/3.
 */
public class FittingPath {
    private ArrayList<Float> mValues;

    private Path xPhasePath;
    private Path yPhasePath;

    private PathMeasure xMeasure, yMeasure;

    private int mXRatio;
    private int mYRatio;

    private float[] xValue = new float[2];
    private float[] yValue = new float[2];

    private ArrayList<Vector2> xPathData;
    private ArrayList<Vector2> yPathData;

    private float xMax, yMax;

    public FittingPath(ArrayList<Float> values, int xRatio, int yRatio) {
        mValues = values;
        mXRatio = xRatio;
        mYRatio = yRatio;

        xPhasePath = new Path();
        yPhasePath = new Path();


        xPathData = new ArrayList<>();
        yPathData = new ArrayList<>();
        xMax = Float.MIN_VALUE;
        yMax = Float.MIN_VALUE;

        initData();

        xMeasure = new PathMeasure(xPhasePath, false);
        yMeasure = new PathMeasure(yPhasePath, false);
    }


    private void initData() {
        for (int i = 0; i < mValues.size(); i++) {
            if (i % 3 == 0) {
                float x = mValues.get(i);
                float y = mValues.get(i + 1);
                float t = mValues.get(i + 2);//TODO 勉强符合我的要求

                if (x >= xMax) {
                    xMax = x;
                }

                if (y >= yMax) {
                    yMax = y;
                }

                xPathData.add(new Vector2(t, x));
                yPathData.add(new Vector2(t, y));
            }
        }


        for (int j = 0; j < xPathData.size(); j++) {
            Vector2 xPoint = xPathData.get(j);
            Vector2 yPoint = yPathData.get(j);

            xPoint.x *= mXRatio * xMax;
            xPoint.y *= mXRatio;

            yPoint.x *= mYRatio * yMax;
            yPoint.y *= mYRatio;
            if (j == 0) {
                xPhasePath.moveTo(xPoint.x, xPoint.y);
                yPhasePath.moveTo(yPoint.x, yPoint.y);
            } else {
                xPhasePath.lineTo(xPoint.x, xPoint.y);
                yPhasePath.lineTo(yPoint.x, yPoint.y);
            }
        }


    }

    private float[] lastValue;

    public float[] getValue(float t) {
        float[] values = new float[2];
        xMeasure.getPosTan(xMeasure.getLength() * t, xValue, null);
        yMeasure.getPosTan(yMeasure.getLength() * t, yValue, null);

        values[0] = xValue[1];
        values[1] = yValue[1];
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
