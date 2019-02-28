package proj1.tamamtamam.proj1;

import android.content.Context;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MessageController {

    private StorageDataSource storageDataSource;
    private NetworkDataSource networkDataSource;

    MessageController(Context context) {
        networkDataSource = new NetworkDataSource(context);
        storageDataSource = new StorageDataSource(context);
    }

    Single<Integer> fetch(boolean fromCache) {
        Single<Integer> stream;
        if (fromCache)
            stream = storageDataSource.load();
        else
            stream = storageDataSource.load()
                    .flatMap(integer -> networkDataSource.load(integer)
                            .doOnSuccess(integer1 -> {
                                storageDataSource.save(integer1);
                            }));

        return stream.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread());
    }

    void delete() {
        storageDataSource.save(0);
    }
}
