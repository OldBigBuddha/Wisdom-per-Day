package kyoto.freeprojects.oldbigbuddha.wisdom_per_day;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TimePicker;


import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmResults;
import kyoto.freeprojects.oldbigbuddha.wisdom_per_day.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;

    private Realm mRealm;
    private RealmResults mResults;
    private CustomAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initOnClick();

        mRealm   = Realm.getDefaultInstance();
        mResults = mRealm.where(Item.class).findAll();

        mAdapter = new CustomAdapter(mResults);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mBinding.recyclerItemList.setLayoutManager(manager);
        mBinding.recyclerItemList.setAdapter(mAdapter);
    }

    public void initOnClick() {
//        mBinding.btAddContent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String content = mBinding.etFieldContent.getText().toString();
//                long   date    = System.currentTimeMillis();
//
//                Item item = new Item(content, date);
//                mRealm.beginTransaction();
//                mRealm.copyToRealm(item);
//                mRealm.commitTransaction();
//
//                mAdapter.notifyDataSetChanged();
//                mBinding.etFieldContent.setText("");
//
//            }
//        });
        mBinding.btGoListen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                int hour    = now.get(Calendar.HOUR_OF_DAY);
                int minute = now.get(Calendar.MINUTE);
                Intent intent = new Intent(MainActivity.this, ListenWisdomActivity.class);
                final PendingIntent pending = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);

                TimePickerDialog dialog = new TimePickerDialog(
                        MainActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY, i);
                        calendar.set(Calendar.MINUTE, i1);
                        AlarmManager manager = (AlarmManager)MainActivity.this.getSystemService(Context.ALARM_SERVICE);
                        manager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pending);
                        MainActivity.this.finish();
                    }
                },
                hour, minute, true);
                dialog.show();
//                startActivity( new Intent(MainActivity.this, ListenWisdomActivity.class));
            }
        });
    }
}
