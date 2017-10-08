package kyoto.freeprojects.oldbigbuddha.wisdom_per_day;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by developer on 10/8/17.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(getApplicationContext());
    }
}
