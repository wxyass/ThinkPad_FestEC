package com.diabin.festec.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.diabin.latte.delegates.LatteDelegate;
import com.diabin.latte.net.RestClient;
import com.diabin.latte.net.callback.IError;
import com.diabin.latte.net.callback.IFailure;
import com.diabin.latte.net.callback.ISuccess;

/**
 * 根LatteDelegate(÷÷÷÷)
 * Created by yangwenmin on 2017/10/15.
 */

public class ExampleDelegate extends LatteDelegate {

    // 设置布局
    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

    // 对每个控件做的操作
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

        testRestClient();
    }

    private void testRestClient() {
        RestClient.builder()
                .url("https://127.0.0.1/index")
                //.params("","")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {

                        Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Toast.makeText(getContext(), "Failure", Toast.LENGTH_SHORT).show();

                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();

                    }
                })
                .builde()
                .get();

    }
}
