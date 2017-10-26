package com.diabin.latte.delegates.bottom;

import android.widget.Toast;

import com.diabin.latte.delegates.LatteDelegate;

/**
 * 各个Fragment的基类 (首页,分类,发现...都需继承这个类)
 * Created by yangwenmin on 2017/10/23.
 */

public abstract  class BottomItemDelegate extends LatteDelegate {

    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;// 2s
    private long  TOUCH_TIME = 0;


    // 双击返回键退出应用
    @Override
    public boolean onBackPressedSupport() {
        if(System.currentTimeMillis()-TOUCH_TIME<TOUCH_TIME){
            _mActivity.fileList();
        }else{
            TOUCH_TIME = System.currentTimeMillis();
            Toast.makeText(_mActivity,"双击退出FestEC",Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
