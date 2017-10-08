package kyoto.freeprojects.oldbigbuddha.wisdom_per_day;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.v4.app.AppLaunchChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmResults;
import kyoto.freeprojects.oldbigbuddha.wisdom_per_day.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 0;

    private ActivityMainBinding mBinding;

    private Realm mRealm;
    private RealmResults mResults;
    private CustomAdapter mAdapter;

    private Calendar now;

    private SharedPreferences mSettings;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mSettings = getSharedPreferences("setting", Context.MODE_PRIVATE);
        mEditor   = mSettings.edit();

        now = Calendar.getInstance();
        if(!AppLaunchChecker.hasStartedFromLauncher(this)){
            // 初回
            TimePickerDialog dialog = new TimePickerDialog(this, onTimeSetListener, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true);
            dialog.setTitle("When Listen?");
            dialog.show();
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis( mSettings.getLong("time", System.currentTimeMillis()) );
            mBinding.tvAlarmDate.setText( formatData(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)) );
        }

        initOnClick();

        mRealm   = Realm.getDefaultInstance();
        mResults = mRealm.where(Item.class).findAll();

        mAdapter = new CustomAdapter(mResults);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mBinding.recyclerItemList.setLayoutManager(manager);
        mBinding.recyclerItemList.setAdapter(mAdapter);

        AppLaunchChecker.onActivityCreate(this);
    }

    public void initOnClick() {
        mBinding.btEditDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                now        = Calendar.getInstance();
                int hour   = now.get(Calendar.HOUR_OF_DAY);
                int minute = now.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        setDate(hour, minute);
                    }
                }, hour, minute, true);
                dialog.show();
            }
        });
    }

    public void setDate(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        Intent intent = new Intent(MainActivity.this, ListenWisdomActivity.class);
        PendingIntent pending = PendingIntent.getActivity(MainActivity.this, REQUEST_CODE, intent, 0);
        AlarmManager manager = (AlarmManager)MainActivity.this.getSystemService(Context.ALARM_SERVICE);

        pending.cancel();
        manager.cancel(pending);

        manager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY , pending);
        mBinding.tvAlarmDate.setText( formatData(hour,minute) );
    }


    private String formatData(int hour, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        return new SimpleDateFormat("HH:MM").format(c.getTime());
    }


    private TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
            setDate(hour, minute);
            MainActivity.this.finish();
        }
    };
}
