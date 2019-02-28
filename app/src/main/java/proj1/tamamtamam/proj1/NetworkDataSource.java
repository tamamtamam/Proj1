package proj1.tamamtamam.proj1;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import io.reactivex.Single;

class NetworkDataSource {

    NetworkDataSource(Context context) {

    }

    Single<Integer> load(int lastSavedInteger) {
        return Single.just(lastSavedInteger + 10)
                .delay(100, TimeUnit.MILLISECONDS);
    }
}
