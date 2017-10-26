package com.diabin.latte.app;

import com.diabin.latte.util.storage.LattePreference;

/**
 * 用于用户管理的类
 * Created by yangwenmin on 2017/10/21.
 */

public class AccountManager {

    private enum SignTag {
        SIGN_TAG //是否登录
    }

    // 保存用户登录状态,登录后调用
    public static void setSignState(boolean state) {
        LattePreference.setAppFlag(SignTag.SIGN_TAG.name(), state);
    }

    // 获取用户状态(是否登录)
    private static boolean isSignIn() {
        return LattePreference.getAppFlag(SignTag.SIGN_TAG.name());
    }

    //
    public static void checkAccount(IUserChecker checker) {
        if (isSignIn()) {
            checker.onSignIn();
        } else {
            checker.onNotSigin();
        }
    }


}
