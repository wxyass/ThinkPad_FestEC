package com.diabin.latte.ec.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.diabin.latte.app.AccountManager;
import com.diabin.latte.app.IUserChecker;
import com.diabin.latte.delegates.LatteDelegate;
import com.diabin.latte.ec.R;
import com.diabin.latte.ui.launcher.ILauncherListener;
import com.diabin.latte.ui.launcher.LauncherHolderCreator;
import com.diabin.latte.ui.launcher.OnLauncherFinishiTag;
import com.diabin.latte.ui.launcher.ScrolllauncherTag;
import com.diabin.latte.util.storage.LattePreference;

import java.util.ArrayList;

/**
 * 滑动启动页
 *
 * Created by yangwenmin on 2017/10/21.
 */

public class LauncherScrollDelegate extends LatteDelegate implements OnItemClickListener{

    // 第三方库的轮播图对象
    private ConvenientBanner<Integer> mConvenientBanner = null;
    // 图片资源
    private static final ArrayList<Integer> INTEGERS = new ArrayList<>();
    //

    // 初始化轮播图对象
    private void  initBanner(){
        INTEGERS.add(R.mipmap.launcher_01);
        INTEGERS.add(R.mipmap.launcher_02);
        INTEGERS.add(R.mipmap.launcher_03);
        INTEGERS.add(R.mipmap.launcher_04);
        INTEGERS.add(R.mipmap.launcher_05);

        mConvenientBanner
                .setPages(new LauncherHolderCreator(),INTEGERS)// 设置资源
                .setPageIndicator(new int[]{R.drawable.dot_normal,R.drawable.dot_focus})// 黑点(默认),白点(焦点)
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)// 水平居中
                .setOnItemClickListener(this)// 条目点击事件
                .setCanLoop(false);// 可以循环
    }

    private ILauncherListener mILanucherListener = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof ILauncherListener){
            mILanucherListener = (ILauncherListener)activity;
        }
    }


    @Override
    public Object setLayout() {
        // 初始化ConvenientBanner
        mConvenientBanner = new ConvenientBanner<Integer>(getContext());
        return mConvenientBanner;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        // 初始化轮播图对象
        initBanner();
    }

    @Override
    public void onItemClick(int position) {
        // 如果点击最后一个
        if(position==INTEGERS.size()-1){
            LattePreference.setAppFlag(ScrolllauncherTag.HAS_FIRST_LANUNCHER_APP.name(),true);
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
