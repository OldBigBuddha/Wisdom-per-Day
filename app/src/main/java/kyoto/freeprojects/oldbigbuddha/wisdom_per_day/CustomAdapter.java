package kyoto.freeprojects.oldbigbuddha.wisdom_per_day;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;

import io.realm.RealmResults;
import kyoto.freeprojects.oldbigbuddha.wisdom_per_day.databinding.ItemBinding;

/**
 * Created by developer on 10/8/17.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private RealmResults<Item> mResults;

    public CustomAdapter(RealmResults<Item> results) {
        Log.d("Adapter", "Start");
        mResults = results;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("Adapter", "onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d("Adapter", "onBindViewHolder");
        final Item item = mResults.get(position);
        holder.getBinding().tvContent.setText( item.getContent() );
        holder.getBinding().tvDate.setText( formatData( item.getCreatedDate() ) );

    }

    @Override
    public int getItemCount() {
        Log.d("Adapter", "getItemCount");
        if (mResults == null) {
            return 0;
        }
        return mResults.size();
    }

    private String formatData(long data) {
        return new SimpleDateFormat("yyyy/MM/dd").format(data);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemBinding binding;
        ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        ItemBinding getBinding() {
            return binding;
        }
    }
}
