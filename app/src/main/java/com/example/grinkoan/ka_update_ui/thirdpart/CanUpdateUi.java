package com.example.grinkoan.ka_update_ui.thirdpart;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.example.grinkoan.ka_update_ui.MainActivity;

/**
 * Created by grinkoan on 10/22/2016.
 */

public class CanUpdateUi
{
    private final String TAG = getClass().getSimpleName();
    Handler extHandler;


    public CanUpdateUi(Handler p_handler)
    {
        extHandler = p_handler;
    }

    public void updateUi(int num)
    {
        Log.d(TAG, "reporting back from the Random Number Thread");
        String text = String.valueOf(num);
        Bundle msgBundle = new Bundle();
        msgBundle.putString("result", text);
        Message msg = new Message();
        msg.setData(msgBundle);
        extHandler.sendMessage(msg);
    }
}
