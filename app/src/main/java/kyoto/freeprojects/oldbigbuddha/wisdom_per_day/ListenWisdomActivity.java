package kyoto.freeprojects.oldbigbuddha.wisdom_per_day;

import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import io.realm.Realm;
import kyoto.freeprojects.oldbigbuddha.wisdom_per_day.databinding.ActivityListenWisdomBinding;

public class ListenWisdomActivity extends FragmentActivity {

    private ActivityListenWisdomBinding mBinding;
    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_listen_wisdom);
        mRealm = Realm.getDefaultInstance();

        ListenAlertFragment fragment = new ListenAlertFragment();
        fragment.setListener(new ListenAlertFragment.ListenDialogInterface() {
            @Override
            public void OnClickPositiveButton(String wisdom) {
                Log.d("OnClickPositive", wisdom);
                long date = System.currentTimeMillis();

                Item item = new Item(wisdom, date);
                mRealm.beginTransaction();
                mRealm.copyToRealm(item);
                mRealm.commitTransaction();

            }
        });
        fragment.show(getFragmentManager(), "ListenWisdomDialog");
    }

    @Override
    public void onAttachedToWindow() {
        Window window = getWindow();
        window.addFlags(
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        );
    }

}
