package com.diabin.latte.delegates;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diabin.latte.activities.ProxyActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * BaseDelegate(基础Fragment)
 *
 * Created by yangwenmin on 2017/10/14.
 */

public abstract class BaseDelegate extends SwipeBackFragment{

    // 声明Butterknife的一个类型,将用来绑定资源
    private Unbinder mUnbinder = null;

    // 子类传入布局(可以是布局id,可以是View),参考:ExampleDelegate类
    public abstract Object setLayout();

    //protected FragmentActivity _mActivity = null;

    // 对每一个控件做的操作
    public  abstract  void  onBindView(@Nullable Bundle savedInstanceState,View rootView);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = null;
        // setLayout()传入的是 布局id
        if(setLayout() instanceof Integer){
            rootView = inflater.inflate((Integer) setLayout(),container,false);
        // View
        }else if(setLayout() instanceof View){
            rootView = (View) setLayout();
        } else {
            // 传入的类型必须是int或View
            throw new ClassCastException("type of setLayout() 必须是 int 或 View!");
        }

        if(rootView!=null){

            // ButterKnife绑定Fragment资源
            mUnbinder = ButterKnife.bind(this,rootView);
            onBindView(savedInstanceState,rootView);
        }

        // 返回rootView
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null){
            // ButterKnife解除绑定
            mUnbinder.unbind();
        }
    }

    // 返回当前Fragment所在的Activity
    public final ProxyActivity getProxyActivity() {
        return (ProxyActivity) _mActivity;
    }
}
