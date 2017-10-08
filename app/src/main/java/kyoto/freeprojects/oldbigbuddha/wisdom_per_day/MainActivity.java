package kyoto.freeprojects.oldbigbuddha.wisdom_per_day;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


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

        mRealm   = Realm.getDefaultInstance();
        mResults = mRealm.where(Item.class).findAll();

        mAdapter = new CustomAdapter(mResults);
        mBinding.btAddContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = mBinding.etFieldContent.getText().toString();
                long   date    = System.currentTimeMillis();

                Item item = new Item(content, date);
                mRealm.beginTransaction();
                mRealm.copyToRealm(item);
                mRealm.commitTransaction();

                mAdapter.notifyDataSetChanged();
                mBinding.etFieldContent.setText("");

            }
        });
        mBinding.recyclerItemList.setAdapter(mAdapter);
    }
}
