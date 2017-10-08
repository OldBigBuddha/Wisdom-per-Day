package kyoto.freeprojects.oldbigbuddha.wisdom_per_day;

import android.provider.ContactsContract;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by developer on 10/8/17.
 */

public class Item extends RealmObject {
    @PrimaryKey
    private long mCreatedDate;
    private String mContent;

    public Item() {}

    public Item(String content, long createdDate) {
        mContent = content;
        mCreatedDate = createdDate;
    }

    public long getCreatedDate() {
        return mCreatedDate;
    }

    public String getContent() {
        return mContent;
    }

    @Override
    public String toString() {
        return "Item{" +
                "createdDate=" + mCreatedDate +
                ", content='" + mContent + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;

        Item item = (Item) o;

        if (mCreatedDate != item.mCreatedDate) return false;
        return mContent.equals(item.mContent);

    }

    @Override
    public int hashCode() {
        int result = (int) (mCreatedDate ^ (mCreatedDate >>> 32));
        result = 31 * result + mContent.hashCode();
        return result;
    }
}
