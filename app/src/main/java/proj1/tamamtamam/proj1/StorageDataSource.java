package proj1.tamamtamam.proj1;

import android.content.Context;
import android.content.SharedPreferences;

import io.reactivex.Single;

class StorageDataSource {

    private String fileName = "proj1-storage";
    private SharedPreferences sharedPreferences;

    StorageDataSource(Context context) {
        sharedPreferences = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
    }

    Single<Integer> load() {
        Integer savedNumber = sharedPreferences.getInt("integer", 0);
        return Single.just(savedNumber);
    }

    void save(int lastNumber) {
        sharedPreferences.edit().putInt("integer", lastNumber).apply();
    }
}
