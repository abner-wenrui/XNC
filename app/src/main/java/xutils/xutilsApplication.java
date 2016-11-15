package xutils;

import android.app.Application;

import org.xutils.x;

/**
 * Project   xutils
 *
 * @Author Abner
 * Time   2016/10/13.11:29
 */

public class xutilsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
