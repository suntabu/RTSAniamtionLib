package animation;

import java.util.List;

/**
 * Created by gouzhun on 2016/5/25.
 */
public class AnimationParameterBean {


    /**
     * animationName : dog
     * duration : 5
     * elements : [{"effectName":"dog.gif","startTime":0.3,"stopTime":2.7,"size":[0.13,0.3],"pathValue":[0.5,0.5,0.9,0.2],"scaleValues":[0.5,0.5,0.9,0.2],"rotateValues":[0.5,0.5,0.9,0.2],"position":[0,0.8]}]
     */

    private String animationName;
    private int duration;
    /**
     * effectName : dog.gif
     * startTime : 0.3
     * stopTime : 2.7
     * size : [0.13,0.3]
     * pathValue : [0.5,0.5,0.9,0.2]
     * scaleValues : [0.5,0.5,0.9,0.2]
     * rotateValues : [0.5,0.5,0.9,0.2]
     * position : [0,0.8]
     */

    private List<ElementParameterBean> elements;

    public String getAnimationName() {
        return animationName;
    }

    public void setAnimationName(String animationName) {
        this.animationName = animationName;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<ElementParameterBean> getElements() {
        return elements;
    }

    public void setElements(List<ElementParameterBean> elements) {
        this.elements = elements;
    }


}
