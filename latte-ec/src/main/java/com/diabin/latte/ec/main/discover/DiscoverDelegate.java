package com.diabin.latte.ec.main.discover;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.diabin.latte.delegates.bottom.BottomItemDelegate;
import com.diabin.latte.ec.R;

/**
 * Created by yangwenmin on 2017/10/24.
 */

public class DiscoverDelegate extends BottomItemDelegate {

    // 
    @Override
    public Object setLayout() {
        return R.layout.delegate_discover;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
