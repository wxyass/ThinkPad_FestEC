package com.diabin.latte.delegates.bottom;

import java.util.LinkedHashMap;

/**
 * 条目(底部按钮及对应Fragment)的建造者类
 * Created by yangwenmin on 2017/10/23.
 */

public final class ItemBuilder {

    // 创建存放条目的集合
    private final LinkedHashMap<BottomTabBean, BottomItemDelegate> ITEMS = new LinkedHashMap<>();

    static ItemBuilder builder() {
        return new ItemBuilder();
    }

    // 添加条目
    public final ItemBuilder addItem(BottomTabBean bean, BottomItemDelegate delegate) {
        ITEMS.put(bean, delegate);
        return this;
    }

    // 添加条目
    public final ItemBuilder addItem(LinkedHashMap<BottomTabBean, BottomItemDelegate> item) {
        ITEMS.putAll(item);
        return this;
    }

    // 返回存放所有条目的集合
    public final LinkedHashMap<BottomTabBean, BottomItemDelegate> build(){
        return ITEMS;
    }
}
