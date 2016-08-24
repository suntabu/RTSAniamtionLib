package animation;

/**
 * Created by gouzhun on 2016/6/5.
 */
public class AnimationInfoBean implements Comparable<AnimationInfoBean> {
    public AnimationInfoBean(String animName) {
        animationName = animName;
    }

    public AnimationInfoBean(String animName, int priority) {
        animationName = animName;
        this.priority = priority;
    }

    public String animationName;

    public int priority = 0;

    @Override
    public int compareTo(AnimationInfoBean another) {
        if (this.priority > another.priority) {
            return 1;
        } else {
            return -1;
        }
    }
}