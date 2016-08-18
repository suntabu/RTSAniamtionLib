package animation;

import java.util.ArrayList;

/**
 * Created by gouzhun on 2016/6/3.
 */
public class ElementParameterBean {
    private String elementName;
    private String textureName;
    private float startTime;
    private float stopTime;
    private ArrayList<Float> size;
    private ArrayList<Float> pathValues;
    private ArrayList<Float> scaleValues;
    private ArrayList<Float> rotateValues;
    private ArrayList<Float> position;
    private ArrayList<Float> colorValues;

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
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

    public ArrayList<Float> getPathValues() {
        return pathValues;
    }

    public void setPathValues(ArrayList<Float> pathValues) {
        this.pathValues = pathValues;
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

    public ArrayList<Float> getColorValues() {
        return colorValues;
    }

    public void setColorValues(ArrayList<Float> colorValues) {
        this.colorValues = colorValues;
    }

    public String getTextureName() {
        return textureName;
    }

    public void setTextureName(String textureName) {
        this.textureName = textureName;
    }
}

