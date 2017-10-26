package com.diabin.latte.delegates.bottom;

/**
 * 底部按钮的实体类
 * Created by yangwenmin on 2017/10/23.
 */

public final class BottomTabBean {

    private final CharSequence ICON; // 图标
    private final CharSequence TITLE;// 文字

    public BottomTabBean(CharSequence icon, CharSequence title) {
        this.ICON = icon;
        this.TITLE = title;
    }

    public CharSequence getIcon() {
        return ICON;
    }

    public CharSequence getTitle() {
        return TITLE;
    }
}
