package com.diabin.festec.example;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.widget.Toast;

import com.diabin.latte.activities.ProxyActivity;
import com.diabin.latte.delegates.LatteDelegate;
import com.diabin.latte.ec.launcher.LauncherDelegate;
import com.diabin.latte.ec.main.ECBottomDelegate;
import com.diabin.latte.ec.sign.ISignListener;
import com.diabin.latte.ec.sign.SignInDelegate;
import com.diabin.latte.ui.launcher.ILauncherListener;
import com.diabin.latte.ui.launcher.OnLauncherFinishiTag;

/**
 * 主Activity: 相当于一个容器,存放根Fragment
 */
public class ExampleActivity extends ProxyActivity implements
        ISignListener,
        ILauncherListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            // 隐藏ActionBar
            actionBar.hide();
        }
    }

    @Override
    public LatteDelegate setRootDelegate() {
       return new LauncherDelegate();
    }

    @Override
    public void onSignInSuccess() {
        Toast.makeText(this,"登录成功",Toast.LENGTH_SHORT).show();
        startWithPop(new ECBottomDelegate());
    }

    @Override
    public void onSignUpSuccess() {
        Toast.makeText(this,"注册成功",Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onLauncherFinish(OnLauncherFinishiTag tag) {
        switch (tag){
            // 用户已登录(成功插入数据库一条用户数据)
            case SIGNED:
                // 跳到首页 startWithPop()方法在start的同时将栈中的上一个元素清除掉
                startWithPop(new ECBottomDelegate());
                break;
            // 用户没有登录(没能成功插入一条用户数据)
            case NOT_SIGNED:
                // 跳到注册页面
                startWithPop(new SignInDelegate());

                break;
            default:
                break;
        }
    }
}