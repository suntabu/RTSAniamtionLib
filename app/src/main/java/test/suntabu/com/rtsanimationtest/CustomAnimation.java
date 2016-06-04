package test.suntabu.com.rtsanimationtest;

import android.content.Context;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by gouzhun on 2016/5/25.
 */
public class CustomAnimation {
    private static final Long PERIODTIME = 50l;


    /************************************************************/
    private float duration;
    private String animationName;

    private ArrayList<CustomEffect> effects;

    /************************************************************/


    private float mCurrentElapsedTime = 0;


    public CustomAnimation(AnimationParameterBean animationParameterBean) {
        effects = new ArrayList<>();

        duration = animationParameterBean.getDuration();
        animationName = animationParameterBean.getAnimationName();
        for (int i = 0; i < animationParameterBean.getEffects().size(); i++) {
            CustomEffect ce = new CustomEffect();

            EffectParameterBean epb = animationParameterBean.getEffects().get(i);

            ce.setEffectName(epb.getEffectName());
            ce.setPathValues(epb.getPathValue());
            ce.setPosition(epb.getPosition());
            ce.setScaleValues(epb.getScaleValues());
            ce.setRotateValues(epb.getRotateValues());
            ce.setSize(epb.getSize());
            ce.setStartTime(epb.getStartTime());
            ce.setStopTime(epb.getStopTime());

            effects.add(ce);
        }
    }

    public void render() {

        for (int i = 0; i < effects.size(); i++) {
            effects.get(i).render();
        }

       /* mCurrentElapsedTime = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mCurrentElapsedTime < duration * 1000) {

                    for (int i = 0; i < effects.size(); i++) {
                        effects.get(i).render(mCurrentElapsedTime);
                    }

                    try {
                        Thread.sleep(PERIODTIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mCurrentElapsedTime += PERIODTIME;
                }

                //TODO：动画结束，资源释放，显示位释放。。。
            }
        }).start();*/

    }

    public void spawnEffects(Context context, ViewGroup parent) {
        for (int i = 0; i < effects.size(); i++) {
            effects.get(i).createEffectView(context, parent);
        }
    }

}
