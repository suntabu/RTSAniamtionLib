package com.suntabu.animation;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.widget.FrameLayout;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by gouzhun on 2016/5/25.
 */
public class AnimationMgr {
    private static final String TAG = "AnimationMgr";

    //TODO:队列储存需要显示的动画
    private Context context;
    private FrameLayout mContainer;

    private CustomAnimation currentAnim;


    private ArrayList<AnimationParameterBean> animationParameterBeanArrayList;
    private HashMap<Integer, AnimationParameterBean> animationParameterBeanHashMap;




    private AnimationMgr() {
        animationParameterBeanArrayList=new ArrayList<>();
        animationParameterBeanHashMap=new HashMap<>();



    }

    private static AnimationMgr mInstance;

    public static AnimationMgr getInstance() {
        if (mInstance == null) {
            mInstance = new AnimationMgr();
        }
        return mInstance;
    }


    public void init(Context context, FrameLayout container) {
        this.context = context;
        this.mContainer = container;


        AssetManager am = context.getResources().getAssets();
        loadAnimationData(am,"animationData.json");
    }

    public void renderAnimation(String animationName) {
        CustomAnimation ca = spawnAniamtion(animationName);
        currentAnim = ca;
        ca.render();
    }


    private CustomAnimation spawnAniamtion(String animationName) {
        Log.i(TAG, animationParameterBeanHashMap.size() + "   " + animationName + "   " + animationName.hashCode());
        if (animationParameterBeanHashMap.containsKey(animationName.hashCode())) {
            CustomAnimation ca = new CustomAnimation(animationParameterBeanHashMap.get(animationName.hashCode()));
            ca.spawnEffects(context, mContainer);
            return ca;
        }


        return null;
    }


    private void loadAnimationData(AssetManager am, String fileName) {
        String json = readAssetsTextFile(am, fileName);
        animationParameterBeanArrayList = new Gson().fromJson(json, new TypeToken<ArrayList<AnimationParameterBean>>() {
        }.getType());
        for (int i = 0; i < animationParameterBeanArrayList.size(); i++) {
            AnimationParameterBean ab = animationParameterBeanArrayList.get(i);
            animationParameterBeanHashMap.put(ab.getAnimationName().hashCode(), ab);
        }
    }


    private String readAssetsTextFile(AssetManager am, String fileName) {
        try {
            InputStream is = am.open(fileName);
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            String json = new String(buffer, "utf-8");
            is.close();
            return json;
        } catch (IOException e) {
            return "";
        }


    }
}
