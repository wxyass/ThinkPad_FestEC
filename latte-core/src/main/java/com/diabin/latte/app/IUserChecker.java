package com.diabin.latte.app;

/**
 * Created by yangwenmin on 2017/10/21.
 */

public interface IUserChecker {
    void onSignIn();// 有用户信息
    void onNotSigin();// 没有用户信息
}
