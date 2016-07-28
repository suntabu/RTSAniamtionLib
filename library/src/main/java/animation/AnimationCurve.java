package animation;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by gouzhun on 2016/6/6.
 * <p/>
 * just like unity's animationcurve,but only implemented linear type
 * http://docs.unity3d.com/ScriptReference/AnimationCurve.html
 */
public class AnimationCurve {
    private ArrayList<KeyFrame> frames;
    private float duration = 0;
    private float startTime = 0;
    private float stopTime =0;



    public AnimationCurve(ArrayList<KeyFrame> frames) {
        this.frames = new ArrayList<>();
        this.frames.addAll(frames);
        sortOrder();
    }

    public AnimationCurve(float startTime, float stopTime) {
        duration = stopTime - startTime;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.frames = new ArrayList<>();
    }


    public void addKeyFrame(KeyFrame frame) {
        frames.add(frame);
        sortOrder();

    }

    public void removeKeyFrame(KeyFrame frame) {

    }


    public float evaluate(float t) {
        float trueTime = t * duration + startTime ;

        int index = -1;
        float value = 0;
        for (int i = 0; i < frames.size(); i++) {

            KeyFrame kf = frames.get(i);
            if (kf.x >= trueTime) {
                index = i - 1;
            }
        }
        if (index >= 0 && frames.size() > 0) {
            KeyFrame kf = frames.get(index);
            if (index + 1 < frames.size()) {
                KeyFrame next = frames.get(index + 1);

                float xdelta = next.x - kf.x;
                float ydelta = next.y - kf.y;
                if (ydelta == 0 || xdelta == 0) {
                    value = kf.y;
                } else {
                    float k = ydelta / xdelta;
                    value = k * (trueTime - kf.x) + kf.y;
                }

            } else {
                value = kf.y;
            }

        } else {
            value = frames.size() > 0 ? frames.get( frames.size() - 1).y : 0;
        }

        LogMgr.i("AnimationCurve", trueTime + "    |    " + value + "   |    " + t  + "    " + duration + "     " + t *duration);
        return value;
    }


    private void sortOrder() {
        Collections.sort(frames, new SortByX());
//        if (frames.size() >= 2) {
//            duration = frames.get(frames.size() - 1).x - frames.get(0).x;
//
//        }
        duration = stopTime - startTime;

    }

    class SortByX implements Comparator {
        public int compare(Object o1, Object o2) {
            KeyFrame s1 = (KeyFrame) o1;
            KeyFrame s2 = (KeyFrame) o2;
            return s1.x > s2.x ? 0 : 1;
        }
    }
}



