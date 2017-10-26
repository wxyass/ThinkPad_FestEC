package com.diabin.latte.ec.sign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.diabin.latte.app.AccountManager;
import com.diabin.latte.ec.database.DatabaseManager;
import com.diabin.latte.ec.database.UserProfile;

/**
 * 登录/注册的帮助类
 * Created by yangwenmin on 2017/10/21.
 */

public class SignHandler {

    // 登录成功,将返回的信息插入到数据库中,
    public static void onSignIn(String response,ISignListener signListener){
        // 获取返回json中,data字段对应的数据
        final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");

        // 读取值
        final long userId = profileJson.getLong("userId");
        final String name = profileJson.getString("name");
        final String avatar = profileJson.getString("avatar");
        final String gender = profileJson.getString("gender");
        final String address = profileJson.getString("address");

        // 插入到数据库
        final UserProfile userProfile = new UserProfile(userId,name,avatar,gender,address);
        DatabaseManager.getInstance().getDao().insert(userProfile);

        // 用户数据已经插入到数据库表中,这就表示已经注册并登录成功了
        AccountManager.setSignState(true);
        signListener.onSignInSuccess();
    }

    // 注册成功,将返回的信息插入到数据库中,
    public static void onSignUp(String response,ISignListener signListener){
        // 获取返回json中,data字段对应的数据
        final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");

        // 读取值
        final long userId = profileJson.getLong("userId");
        final String name = profileJson.getString("name");
        final String avatar = profileJson.getString("avatar");
        final String gender = profileJson.getString("gender");
        final String address = profileJson.getString("address");

        // 插入到数据库
        final UserProfile userProfile = new UserProfile(userId,name,avatar,gender,address);
        DatabaseManager.getInstance().getDao().insert(userProfile);

        // 用户数据已经插入到数据库表中,这就表示已经注册并登录成功了
        AccountManager.setSignState(true);
        signListener.onSignUpSuccess();
    }

}
