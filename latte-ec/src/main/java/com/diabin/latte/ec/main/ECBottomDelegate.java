package com.diabin.latte.ec.main;

import android.graphics.Color;

import com.diabin.latte.delegates.bottom.BaseBottomDelegate;
import com.diabin.latte.delegates.bottom.BottomItemDelegate;
import com.diabin.latte.delegates.bottom.BottomTabBean;
import com.diabin.latte.delegates.bottom.ItemBuilder;
import com.diabin.latte.ec.main.cart.ShopCartDelegate;
import com.diabin.latte.ec.main.discover.DiscoverDelegate;
import com.diabin.latte.ec.main.index.IndexDelegate;
import com.diabin.latte.ec.main.persional.PersionalDelegate;
import com.diabin.latte.ec.main.sort.SortDelegate;

import java.util.LinkedHashMap;

/**
 * Created by yangwenmin on 2017/10/24.
 */

public class ECBottomDelegate extends BaseBottomDelegate {

    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItem(ItemBuilder builder) {

        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = new LinkedHashMap<>();

        items.put(new BottomTabBean("{fa-home}","首页"),new IndexDelegate());
        items.put(new BottomTabBean("{fa-sort}","分类"),new SortDelegate());
        items.put(new BottomTabBean("{fa-compass}","发现"),new DiscoverDelegate());
        items.put(new BottomTabBean("{fa-shopping-cart}","购物车"),new ShopCartDelegate());
        items.put(new BottomTabBean("{fa-user}","我的"),new PersionalDelegate());

        return builder.addItem(items).build();
    }

    @Override
    public int setClickedColor() {
        return Color.parseColor("#ffff8800");

    }

    @Override
    public int setIndexDelegate() {
        return 0;
    }
}
