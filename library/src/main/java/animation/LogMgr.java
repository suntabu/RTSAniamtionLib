package animation;

import android.util.Log;

/**
 * Created by gouzhun on 2016/6/6.
 */
public class LogMgr {
    public static boolean enableLog = false;


    public static void i(String tag, String msg) {
        if (enableLog)
            Log.i(tag, msg);
    }
}
