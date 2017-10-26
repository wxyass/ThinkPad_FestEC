package com.diabin.festec.example;

import android.app.Application;

import com.diabin.latte.app.Latte;
import com.diabin.latte.ec.database.DatabaseManager;
import com.diabin.latte.ec.icon.FontEcModule;
import com.diabin.latte.net.interceptors.DebugInterceptor;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * Application应用
 *
 * Created by yangwenmin on 2017/10/14.
 */

public class ExampleApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        // 通过全局配置器,配置参数
        Latte.init(this)// 配置ApplicationContext
                .withIcon(new FontAwesomeModule())// 配置字体图标
                .withIcon(new FontEcModule())// 配置另一种字体图标
                .withApiHost("http://127.0.0.1/")// 配置ApiHost
                .withInterceptor(new DebugInterceptor("index",R.raw.test))// 拦截url请求中包含index的url请求
                .configure();// 修改→配置完成的标记true
        // 初始化数据库
        DatabaseManager.getInstance().init(this);
    }
}
