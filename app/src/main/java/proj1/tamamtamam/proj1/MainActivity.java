package proj1.tamamtamam.proj1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends Activity {

    private Button button1;
    private Button button2;
    private Button button3;
    private LinearLayout linearLayout;
    private MessageController messageController;
    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messageController = new MessageController(getApplicationContext());
        compositeDisposable = new CompositeDisposable();

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        linearLayout = findViewById(R.id.list_linear_layout);

        button1.setOnClickListener(view -> clear());
        button2.setOnClickListener(view -> refresh());
        button3.setOnClickListener(view -> get());
    }

    @Override
    protected void onResume() {
        super.onResume();

        refresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        compositeDisposable.dispose();
    }

    private void showList(int lastNumber) {
        linearLayout.removeAllViews();
        for (int i = 1; i <= lastNumber; i++) {
            TextView tempView = new TextView(this);
            tempView.setText(String.valueOf(i));
            linearLayout.addView(tempView);
        }
    }

    private void get() {
        compositeDisposable.add(messageController.fetch(false)
                .subscribe(this::showList));
    }


    private void refresh() {
        compositeDisposable.add(messageController.fetch(true)
                .subscribe(this::showList));
    }

    private void clear() {
        linearLayout.removeAllViews();
        messageController.delete();
    }
}
