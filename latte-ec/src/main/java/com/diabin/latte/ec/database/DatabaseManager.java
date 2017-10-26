package com.diabin.latte.ec.database;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

/**
 * 数据库表的管理器
 * Created by yangwenmin on 2017/10/21.
 */

public class DatabaseManager {
    private DaoSession mDaoSession = null;
    private UserProfileDao mUserProfileDao = null;

    // 构造
    private DatabaseManager() {
    }

    // 单例
    public static DatabaseManager getInstance(){
        return Holder.INSTANCE;
    }

    private static final class Holder {
        // 静态创建对象
        private static final DatabaseManager INSTANCE = new DatabaseManager();
    }

    // 对外提供初始化方法
    public DatabaseManager init(Context context) {
        initDao(context);
        return this;
    }

    // 初始化
    private void initDao(Context context) {
        // 传入数据库名字
        final ReleaseOpenHelper helper = new ReleaseOpenHelper(context, "fast_ec.db");
        final Database database = helper.getWritableDb();
        mDaoSession = new DaoMaster(database).newSession();
        mUserProfileDao = mDaoSession.getUserProfileDao();
    }

    public final UserProfileDao getDao(){
        return mUserProfileDao;
    }

}
