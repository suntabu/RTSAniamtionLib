package animation;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by gouzhun on 2016/5/25.
 */
public class AnimationMgr {
    private static final String TAG = "AnimationMgr";

    //TODO:队列储存需要显示的动画

    private ArrayList<AnimationInfoBean> queueAnimation;

    private Context context;
    private FrameLayout mContainer;
    private boolean working = true;

    //    private CustomAnimation currentAnim;
    private boolean isRendering;

    private ArrayList<AnimationParameterBean> animationParameterBeanArrayList;
    private HashMap<Integer, AnimationParameterBean> animationParameterBeanHashMap;


    private AnimationMgr() {
        animationParameterBeanArrayList = new ArrayList<>();
        animationParameterBeanHashMap = new HashMap<>();

        queueAnimation = new ArrayList<>();
    }

    private static AnimationMgr mInstance;

    public static AnimationMgr getInstance() {
        if (mInstance == null) {
            mInstance = new AnimationMgr();
        }
        return mInstance;
    }


    public void init(final Context context, FrameLayout container) {
        this.context = context;
        this.mContainer = container;


        AssetManager am = context.getResources().getAssets();
        loadAnimationData(am, "animationData.json");

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (working) {
                    if (queueAnimation.size() > 0 && !isRendering) {


                        isRendering = true;
                        final AnimationInfoBean aib = queueAnimation.get(0);
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (aib != null) {
                                    LogMgr.i(TAG, "render anim :" + aib.animationName);
                                    renderAnimation(aib.animationName);
                                }

                            }
                        });
                        if (queueAnimation.size() > 0) {
                            queueAnimation.remove(0);
                        }

                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                    LogMgr.i(TAG, "isRendering: " + isRendering);
                }
            }
        }).start();
    }

    public void renderAnimation(AnimationInfoBean aib) {
        LogMgr.i(TAG, "add anim :" + aib.animationName);
        queueAnimation.add(aib);
    }

    public void destroy() {
        context = null;
        this.mContainer = null;
        working = false;
    }

    private void renderAnimation(String animationName) {
        CustomAnimation ca = spawnAniamtion(animationName);
        ca.render(new Delegate() {
            @Override
            public void invoke() {
                isRendering = false;
                LogMgr.i(TAG, "animation end");
            }
        });
    }


    private CustomAnimation spawnAniamtion(String animationName) {
        LogMgr.i(TAG, animationParameterBeanHashMap.size() + "   " + animationName + "   " + animationName.hashCode());
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

    public AnimationInfoBean getRandomAnimation() {
        Random ran = new Random();
        int index = ran.nextInt(animationParameterBeanArrayList.size());
        AnimationParameterBean paraBean = animationParameterBeanArrayList.get(index);
        LogMgr.i(TAG, "animation random:" + paraBean.getAnimationName());
        return new AnimationInfoBean(paraBean.getAnimationName());
    }


}
