package com.example.grinkoan.ka_update_ui;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity
{
    private final String TAG = getClass().getSimpleName();
    Handler resultHandler;
    DoSomethingThread randomWork;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button button = (Button) findViewById(R.id.btn_produce_random_nr);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startGenerating();
            }
        });
        resultHandler = new HandlerExtension(this);


    }

    private void startGenerating()
    {
        randomWork = new DoSomethingThread();
        randomWork.start();
    }

    private class HandlerExtension extends Handler
    {
        private final WeakReference<MainActivity> currentActivity;

        public HandlerExtension(MainActivity activity)
        {
            currentActivity = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message message)
        {
            MainActivity activity = currentActivity.get();
            if (activity != null)
            {
                TextView tV = (TextView)findViewById(R.id.tV_shows_random_num);
                tV.setText(message.getData().getString("result"));

            }
        }
    }

    public class DoSomethingThread extends Thread
    {
        private static final String TAG = "DoSomethingThread";
        private static final int DELAY = 5000;
        private static final int RANDOM_MULTIPLIER = 10;

        @Override
        public void run()
        {
            Log.d(TAG,"doing work in Random Number Thread");
            while(true)
            {
                int randNum = (int) (Math.random()*RANDOM_MULTIPLIER);
                publishProgress(randNum);
                try
                {
                    Thread.sleep(5000);
                }
                catch (InterruptedException p_e)
                {
                    Log.d(TAG, "Interrupting and stopping the Random Number Thread");
                    return;
                }

            }

        }

        private void publishProgress(int p_randNum)
        {
            Log.d(TAG, "reporting back from the Random Number Thread");
            String text = String.valueOf(p_randNum);
            Bundle msgBundle = new Bundle();
            msgBundle.putString("result", text);
            Message msg = new Message();
            msg.setData(msgBundle);
            resultHandler.sendMessage(msg);
        }
    }
}
