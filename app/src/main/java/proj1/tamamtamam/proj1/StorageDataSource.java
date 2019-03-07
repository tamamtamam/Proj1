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

    Single<Integer> load(int lastShowedNumber) {
        int returnNumber;
        if (lastShowedNumber == -1)
            returnNumber = sharedPreferences.getInt("integer", 0);
        else {
            int storedNumber = sharedPreferences.getInt("integer", 0);
            if (lastShowedNumber + 10 <= storedNumber)
                returnNumber = lastShowedNumber + 10;
            else
                returnNumber = storedNumber;
        }
        return Single.just(returnNumber);
    }

    void save(int lastNumber) {
        int storedNumber = sharedPreferences.getInt("integer", 0);
        sharedPreferences.edit().putInt("integer", Math.max(storedNumber, lastNumber)).apply();
    }
}
