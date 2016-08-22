package animation;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.ImageView;


/**
 * Created by gouzhun on 2016/5/25.
 */
public class RTSAnimation extends Animation {

    private FittingAnimationCurve scaleMeasure, translateMeasure, rotateMeasure, colorMeasure;
    private float[] pos = new float[2];
    private float[] scale = new float[2];
    private float[] rotate = new float[2];
    private float[] color = new float[2];
    float centerX, centerY;

    private View mView;
    private Rect rect;

    public RTSAnimation(FittingAnimationCurve translatePath, FittingAnimationCurve scalePath, FittingAnimationCurve rotatePath, FittingAnimationCurve colorPath, View view, int width, int height) {
        translateMeasure = translatePath;
        scaleMeasure = scalePath;
        rotateMeasure = rotatePath;
        colorMeasure = colorPath;
        mView = view;
        rect = new Rect();
        mView.measure(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        centerX = width / 2f;//mView.getWidth() / 2f;
        centerY = height / 2f;//mView.getHeight() / 2f;

        setFillEnabled(true);
        setFillBefore(true);
        setFillAfter(true);
    }


    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
//        Log.i("", centerX + " | " + centerY);
        super.applyTransformation(interpolatedTime, t);
        Matrix matrix = t.getMatrix();
        pos = translateMeasure.getValue(interpolatedTime);
        // Log.i("RTSAnimation", pos[0] + " | " + pos[1]);
        matrix.preTranslate(-centerX, -centerY);
        if (scaleMeasure != null) {
            scale = scaleMeasure.getValue(interpolatedTime);
            if (scale[0] != 0 && scale[1] != 0) {
                matrix.postScale(scale[0], scale[1]);
            }

//            matrix.postScale(scale[0], scale[1], rect.centerX(), rect.centerY());
        }
//        matrix.preTranslate(centerX, centerY);
        rotate = rotateMeasure.getValue(interpolatedTime);
        matrix.postRotate(rotate[0]);
        matrix.postTranslate(pos[0], pos[1]);

        if (colorMeasure != null && colorMeasure.size() > 0) {
            color = colorMeasure.getValue(interpolatedTime);
            ((ImageView) mView).setAlpha((int) (color[0] * 255));
        } else {
            ((ImageView) mView).setAlpha((int) (255));
        }


    }


    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
    }
}