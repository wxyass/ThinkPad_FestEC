package com.diabin.latte.util.timer;

import java.util.TimerTask;

/**
 * Created by yangwenmin on 2017/10/16.
 */

public class BaseTimerTask extends TimerTask {

    private ITimerListener mITimerListener = null;

    // 构造中传入接口
    public BaseTimerTask(ITimerListener timerListener) {
        this.mITimerListener = timerListener;
    }

    // run()中写回调
    @Override
    public void run() {
        if (mITimerListener != null) {
            mITimerListener.onTimer();
        }
    }
}
