package proj1.tamamtamam.proj1;

import android.content.Context;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MessageController {

    private StorageDataSource storageDataSource;
    private NetworkDataSource networkDataSource;
    private int lastShowedNumber = 0;

    MessageController(Context context) {
        networkDataSource = new NetworkDataSource(context);
        storageDataSource = new StorageDataSource(context);
    }

    Single<Integer> fetch(boolean fromCache) {
        Single<Integer> stream;
        if (fromCache)
            stream = storageDataSource.load(lastShowedNumber);
        else
            stream = networkDataSource.load(lastShowedNumber)
                    .doOnSuccess(integer -> storageDataSource.save(integer));

        return stream.doOnSuccess(integer -> lastShowedNumber = integer)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    void clear() {
        lastShowedNumber = 0;
    }
}
