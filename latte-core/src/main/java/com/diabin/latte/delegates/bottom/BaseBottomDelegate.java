package com.diabin.latte.delegates.bottom;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.diabin.latte.R;
import com.diabin.latte.R2;
import com.diabin.latte.delegates.LatteDelegate;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * 这是一个Fragment,
 * <p>
 * 底部是LinearLayout用于存放5个按钮,
 * 上面是一个FrameLayout用于切换不同内容
 * <p>
 * Created by yangwenmin on 2017/10/23.
 */

public abstract class BaseBottomDelegate extends LatteDelegate implements View.OnClickListener{

    // 定义一个存放按钮实体类的集合
    private final ArrayList<BottomTabBean> TAB_BEANS = new ArrayList<>();
    // 定义一个存放Fragment的集合
    private final ArrayList<BottomItemDelegate> ITEM_DELEGATES = new ArrayList<>();
    // 定义一个存放底部导航条目的集合
    private final LinkedHashMap<BottomTabBean, BottomItemDelegate> ITEMS = new LinkedHashMap<>();
    // 当前正展示的条目的位置
    private int mCurrentDelegate = 0;
    // 默认展示的条目位置
    private int mIndexDelegate = 0;
    // 点击后颜色变化
    private int mClickedColor = Color.RED;

    // 底部LinearLayout
    @BindView(R2.id.bottom_bar)
    LinearLayoutCompat mBottomBar = null;


    // 子类设置要展示的各个条目
    public abstract LinkedHashMap<BottomTabBean, BottomItemDelegate> setItem(ItemBuilder builder);

    // 子类设置按钮点击后的颜色改变
    public abstract int setClickedColor();

    // 子类设置默认展示的条目
    public abstract int setIndexDelegate();


    // 设置布局
    @Override
    public Object setLayout() {
        return R.layout.delegate_bottom;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 获取默认展示的条目
        mIndexDelegate = setIndexDelegate();
        // 获取点击后改变的颜色
        if (setClickedColor() != 0) {
            mClickedColor = setClickedColor();
        }
        //
        final ItemBuilder builder = ItemBuilder.builder();
        // 获取所有条目
        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = setItem(builder);
        ITEMS.putAll(items);
        // 处理底部实体类的集合数据 和各个Fragment的集合数据
        for (Map.Entry<BottomTabBean, BottomItemDelegate> item : ITEMS.entrySet()) {
            final BottomTabBean key = item.getKey();
            final BottomItemDelegate value = item.getValue();
            TAB_BEANS.add(key);
            ITEM_DELEGATES.add(value);
        }
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        // 底部导航框架有几个条目
        final int size = ITEMS.size();
        for (int i = 0; i < size; i++) {
            // 填充每个按钮的布局 父容器
            LayoutInflater.from(getContext()).inflate(R.layout.bottom_item_icon_text_layout,mBottomBar);
            final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);
            // 设置每个item的点击事件
            item.setTag(i);
            item.setOnClickListener(this);
            // 初始化控件
            final IconTextView iconTextView = (IconTextView) item.getChildAt(0);
            final AppCompatTextView appCompatTextView = (AppCompatTextView) item.getChildAt(1);
            // 初始化控件数据
            final BottomTabBean tabBean = TAB_BEANS.get(i);
            iconTextView.setText(tabBean.getIcon());
            appCompatTextView.setText(tabBean.getTitle());
            // 将默认展示的条目,更改颜色
            if(i==mIndexDelegate){
                iconTextView.setTextColor(mClickedColor);
                appCompatTextView.setTextColor(mClickedColor);
            }
        }

        // 初始化上方的Fragment
        final SupportFragment[] delegateArray = ITEM_DELEGATES.toArray(new SupportFragment[size]);
        loadMultipleRootFragment(R.id.bottom_bar_delegate_container,mIndexDelegate,delegateArray);
    }

    // 将底部按钮全部置成灰色
    private void resetColor() {
        final int count = mBottomBar.getChildCount();
        for (int i = 0; i < count; i++) {
            // 获取当前位置孩子
            final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);
            // 获取这个孩子的控件组合,并修改颜色
            final IconTextView iconTextView = (IconTextView) item.getChildAt(0);
            final AppCompatTextView appCompatTextView = (AppCompatTextView) item.getChildAt(1);
            iconTextView.setTextColor(Color.GRAY);
            appCompatTextView.setTextColor(Color.GRAY);
        }
    }

    @Override
    public void onClick(View view) {

        // 获取当前点击的按钮的tag
        final int tag = (int) view.getTag();
        // 重置所有按钮颜色
        resetColor();
        // 获取当前点击的控件组合
        RelativeLayout item = (RelativeLayout) view;
        // 修改控件组合的颜色
        final IconTextView iconTextView = (IconTextView) item.getChildAt(0);
        final AppCompatTextView appCompatTextView = (AppCompatTextView) item.getChildAt(1);
        iconTextView.setTextColor(mClickedColor);
        appCompatTextView.setTextColor(mClickedColor);
        // 切换成按钮对应的Fragment  // 第一个参数是要展示的Fragment,第二个参数是要隐藏的Fragment
        showHideFragment(ITEM_DELEGATES.get(tag),ITEM_DELEGATES.get(mCurrentDelegate));
        // 将当前tag赋给mCurrentDelegate
        mCurrentDelegate = tag;
    }
}
