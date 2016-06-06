package animation;

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

    public AnimationCurve() {
        this.frames = new ArrayList<>();
    }

    public AnimationCurve(ArrayList<KeyFrame> frames) {
        this.frames = new ArrayList<>();
        this.frames.addAll(frames);
        sortOrder();
    }


    public void addKeyFrame(KeyFrame frame) {
        frames.add(frame);
        sortOrder();
    }

    public void removeKeyFrame(KeyFrame frame) {

    }


    public float evaluate(float t) {
        for (int i = 0; i < frames.size(); i++) {

            KeyFrame kf = frames.get(i);
            if (kf.x >= t) {
                if (i - 1 >= 0) {
                    KeyFrame pre = frames.get(i - 1);

                    float xdelta = kf.x - pre.x;
                    float ydelta = kf.y - pre.y;
                    if (ydelta == 0 || xdelta == 0) {
                        return kf.y;
                    }

                    float k = ydelta / xdelta;

                    float value = k * (t - pre.x) + kf.y;
                    return value;
                } else {
                    return kf.y;
                }
            }


        }

        return frames.size() > 0 ? frames.get(frames.size() - 1).y : 0;
    }


    private void sortOrder() {
        Collections.sort(frames, new SortByX());
    }

    class SortByX implements Comparator {
        public int compare(Object o1, Object o2) {
            KeyFrame s1 = (KeyFrame) o1;
            KeyFrame s2 = (KeyFrame) o2;
            return s1.x > s2.x ? 0 : 1;
        }
    }
}



