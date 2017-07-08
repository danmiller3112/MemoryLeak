package com.roll.memoryleak;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private AsyncTask asyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textid);

        asyncTask = new BackgroundTask(textView).execute();


    }

    @Override
    protected void onDestroy() {
        asyncTask.cancel(true);
        super.onDestroy();

    }

    private class BackgroundTask extends AsyncTask<Void, Void, String> {

        private final WeakReference<TextView> textViewWeakReference;

        private BackgroundTask(TextView textView) {
            this.textViewWeakReference = new WeakReference<>(textView);
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                Bitmap bitmap = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888); // big object
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "some string";
        }

        @Override
        protected void onCancelled() {

        }

        @Override
        protected void onPostExecute(String s) {
            TextView view = textViewWeakReference.get();
            if (view != null)
                view.setText(s);
        }
    }

}
