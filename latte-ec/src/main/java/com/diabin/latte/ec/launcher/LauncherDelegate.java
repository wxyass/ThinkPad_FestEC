package com.diabin.latte.ec.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.diabin.latte.app.AccountManager;
import com.diabin.latte.app.IUserChecker;
import com.diabin.latte.delegates.LatteDelegate;
import com.diabin.latte.ec.R;
import com.diabin.latte.ec.R2;
import com.diabin.latte.ui.launcher.ILauncherListener;
import com.diabin.latte.ui.launcher.OnLauncherFinishiTag;
import com.diabin.latte.ui.launcher.ScrolllauncherTag;
import com.diabin.latte.util.storage.LattePreference;
import com.diabin.latte.util.timer.BaseTimerTask;
import com.diabin.latte.util.timer.ITimerListener;

import java.text.MessageFormat;
import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 倒计时启动页
 * Created by yangwenmin on 2017/10/21.
 */

public class LauncherDelegate extends LatteDelegate implements ITimerListener{

    // 右上角倒计时TextView
    @BindView(R2.id.tv_launcher_timer)
    AppCompatTextView mTvTimer = null;

    //
    private Timer mTimer = null;
    // 定义一个倒计时的数字 // 5s
    private int mCount = 5;

    // 点击按钮,跳转到下一页
    @OnClick(R2.id.tv_launcher_timer)
    void onClickTimerView(){
        // 关闭定时器任务,跳转到下一页
        if(mTimer != null){
            mTimer.cancel();
            mTimer= null;
            checkIsShowScroll();
        }

    }

    private ILauncherListener mILanucherListener = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof ILauncherListener){
            mILanucherListener = (ILauncherListener)activity;
        }
    }

    // 初始化定时任务
    private void initTimer(){
        mTimer = new Timer();
        final BaseTimerTask task = new BaseTimerTask(this);
        mTimer.schedule(task,0,1000);// (要执行的任务,延迟执行,每隔1秒执行一次)
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_launcher;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

        // 初始化定时任务
        initTimer();
    }



    @Override
    public void onTimer() {

        // 在UI线程,改变UI
        getProxyActivity()
                .runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mTvTimer!=null){
                    // 刷新页面UI
                    mTvTimer.setText(MessageFormat.format("跳过\n{0}s",mCount));
                    mCount--;
                    // 当计数器减小到0,关闭定时器任务,跳转到下一页
                    if(mCount<0){
                        if(mTimer != null){
                            mTimer.cancel();
                            mTimer= null;
                            checkIsShowScroll();
                        }
                    }
                }
            }
        });
    }

    // 判断是否跳转滑动启动页
    private  void checkIsShowScroll(){
        if (!LattePreference.getAppFlag(ScrolllauncherTag.HAS_FIRST_LANUNCHER_APP.name())){// 第一次
            // 跳转->滑动启动页
            getSupportDelegate().start(new LauncherScrollDelegate(),SINGLETASK);
       }else{
            // 检测用户是否登录了App
            AccountManager.checkAccount(new IUserChecker() {
                @Override
                public void onSignIn() { // 数据库插入用户数据-成功->表示用户登录成功
                    if(mILanucherListener!=null){
                        mILanucherListener.onLauncherFinish(OnLauncherFinishiTag.SIGNED);
                    }
                }

                @Override
                public void onNotSigin() { // 数据库插入用户数据-失败->表示用户登录失败
                    if(mILanucherListener!=null){
                        mILanucherListener.onLauncherFinish(OnLauncherFinishiTag.NOT_SIGNED);
                    }
                }
            });

        }
    }


}
