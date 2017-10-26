package com.diabin.latte.ec.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.diabin.latte.delegates.LatteDelegate;
import com.diabin.latte.ec.R;
import com.diabin.latte.ec.R2;
import com.diabin.latte.net.RestClient;
import com.diabin.latte.net.callback.IError;
import com.diabin.latte.net.callback.IFailure;
import com.diabin.latte.net.callback.ISuccess;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录的Fragment
 * Created by yangwenmin on 2017/10/21.
 */

public class SignInDelegate extends LatteDelegate {

    @BindView(R2.id.edit_sign_in_email)
    TextInputEditText mEmail = null;
    @BindView(R2.id.edit_sign_in_password)
    TextInputEditText mPassword = null;

    private ISignListener mISignListener = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof ISignListener){
            mISignListener = (ISignListener)activity;
        }
    }

    // 登录
    @OnClick(R2.id.btn_sign_in)
    void onClickSignIn(){
        if(checkForm()){// 输入正确
            RestClient.builder()
                    .url("http://images.wxyass.com/RestServer/data/user_profile.json")
                    //.url("http://127.0.0.1:8080/RestServer/api/user_profile.php")// 失败
                    .params("email",mEmail.getText().toString())
                    .params("password",mPassword.getText().toString())
                    .loader(getContext())
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            SignHandler.onSignIn(response,mISignListener);
                        }
                    })
                    .error(new IError() {
                        @Override
                        public void onError(int code, String msg) {
                            Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
                        }
                    })
                    .failure(new IFailure() {
                        @Override
                        public void onFailure() {
                            Toast.makeText(getContext(),"请求失败",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .builde()
                    .post();
        }
    }

    // 跳转注册
    @OnClick(R2.id.tv_link_sign_up)
    void onClickLink(){
        getSupportDelegate().start(new SignUpDelegate());

    }

    // 微信登录
    @OnClick(R2.id.icon_sign_in_wechat)
    void onClickWeChat(){

    }

    // 校验输入是否正确 true:正确   false:有错误
    private boolean checkForm() {
        boolean isPass = true;

        final String email = mEmail.getText().toString();
        final String password = mPassword.getText().toString();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("错误的邮箱格式");
            isPass = false;
        } else {
            mEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            mPassword.setError("请输入至少6位数密码");
            isPass = false;
        } else {
            mPassword.setError(null);
        }

        return isPass;
    }


    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
