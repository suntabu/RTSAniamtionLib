package test.suntabu.com.rtsanimationtest;

import java.util.ArrayList;

/**
 * Created by gouzhun on 2016/6/3.
 */
public class EffectParameterBean {
    private String effectName;
    private float startTime;
    private float stopTime;
    private ArrayList<Float> size;
    private ArrayList<Float> pathValue;
    private ArrayList<Float> scaleValues;
    private ArrayList<Float> rotateValues;
    private ArrayList<Float> position;

    public String getEffectName() {
        return effectName;
    }

    public void setEffectName(String effectName) {
        this.effectName = effectName;
    }

    public float getStartTime() {
        return startTime;
    }

    public void setStartTime(float startTime) {
        this.startTime = startTime;
    }

    public float getStopTime() {
        return stopTime;
    }

    public void setStopTime(float stopTime) {
        this.stopTime = stopTime;
    }

    public ArrayList<Float> getSize() {
        return size;
    }

    public void setSize(ArrayList<Float> size) {
        this.size = size;
    }

    public ArrayList<Float> getPathValue() {
        return pathValue;
    }

    public void setPathValue(ArrayList<Float> pathValue) {
        this.pathValue = pathValue;
    }

    public ArrayList<Float> getScaleValues() {
        return scaleValues;
    }

    public void setScaleValues(ArrayList<Float> scaleValues) {
        this.scaleValues = scaleValues;
    }

    public ArrayList<Float> getRotateValues() {
        return rotateValues;
    }

    public void setRotateValues(ArrayList<Float> rotateValues) {
        this.rotateValues = rotateValues;
    }

    public ArrayList<Float> getPosition() {
        return position;
    }

    public void setPosition(ArrayList<Float> position) {
        this.position = position;
    }
}

