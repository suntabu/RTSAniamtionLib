package animation;

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
    private int completedCount = 0;

    public CustomAnimation(AnimationParameterBean animationParameterBean) {
        effects = new ArrayList<>();

        duration = animationParameterBean.getDuration();
        animationName = animationParameterBean.getAnimationName();
        for (int i = 0; i < animationParameterBean.getElements().size(); i++) {
            CustomEffect ce = new CustomEffect();

            ElementParameterBean epb = animationParameterBean.getElements().get(i);

            ce.setEffectName(epb.getElementName());
            ce.setPathValues(epb.getPathValues());
            ce.setPosition(epb.getPosition());
            ce.setScaleValues(epb.getScaleValues());
            ce.setRotateValues(epb.getRotateValues());
            ce.setColorValues(epb.getColorValues());
            ce.setSize(epb.getSize());
            ce.setStartTime(epb.getStartTime());
            ce.setStopTime(epb.getStopTime());
            ce.setTextureName(epb.getTextureName());
            ce.setRotate(epb.getRotate());
            effects.add(ce);
        }
    }

    public void render(final Delegate delegate) {
        completedCount = 0;
        for (int i = 0; i < effects.size(); i++) {
            effects.get(i).render(new Delegate() {
                @Override
                public void invoke() {
                    completedCount++;
                    if (completedCount == effects.size()) {
                        delegate.invoke();
                        for (int j = 0; j < effects.size(); j++) {
                            effects.get(j).dispose();
                        }
                    }
                }
            });
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
            effects.get(i).createEffectView(context, parent, animationName);
        }
    }

}
