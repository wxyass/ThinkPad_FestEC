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
 * 注册的Fragment
 * Created by yangwenmin on 2017/10/21.
 */

public class SignUpDelegate extends LatteDelegate {

    // 姓名
    @BindView(R2.id.edit_sign_up_name)
    TextInputEditText mName = null;
    // 邮箱
    @BindView(R2.id.edit_sign_up_email)
    TextInputEditText mEmail = null;
    // 电话
    @BindView(R2.id.edit_sign_up_phone)
    TextInputEditText mPhone = null;
    // 密码
    @BindView(R2.id.edit_sign_up_password)
    TextInputEditText mPassword = null;
    // 重复密码
    @BindView(R2.id.edit_sign_up_re_password)
    TextInputEditText mRePassword = null;

    private ISignListener mISignListener = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof ISignListener){
            mISignListener = (ISignListener)activity;
        }
    }

    // 注册按钮
    @OnClick(R2.id.btn_sign_up)
    void onClickSignUp() {
        if(checkForm()){// 输入正确
            RestClient.builder()
                    .url("http://images.wxyass.com/RestServer/data/user_profile.json")
                    //.url("http://127.0.0.1:8080/RestServer/api/user_profile.php")// 失败
                    .params("name",mName.getText().toString())
                    .params("email",mEmail.getText().toString())
                    .params("phone",mPhone.getText().toString())
                    .params("password",mPassword.getText().toString())
                    .loader(getContext())
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            //
                            SignHandler.onSignUp(response,mISignListener);

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

    // 跳转登录按钮
    @OnClick(R2.id.tv_link_sign_in)
    void onClickLink() {

        getSupportDelegate().start(new SignInDelegate());
    }

    // 校验输入是否正确 true:正确   false:有错误
    private boolean checkForm() {
        boolean isPass = true;

        final String name = mName.getText().toString();
        final String email = mEmail.getText().toString();
        final String phone = mPhone.getText().toString();
        final String password = mPassword.getText().toString();
        final String rePassword = mRePassword.getText().toString();

        if (name.isEmpty()) {
            mName.setError("请输入姓名");
            isPass = false;
        } else {
            mName.setError(null);
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("错误的邮箱格式");
            isPass = false;
        } else {
            mEmail.setError(null);
        }

        if (phone.isEmpty() || phone.length() != 11) {
            mPhone.setError("手机号错误");
            isPass = false;
        } else {
            mPhone.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            mPassword.setError("请输入至少6位数密码");
            isPass = false;
        } else {
            mPassword.setError(null);
        }

        if (rePassword.isEmpty() || password.length() < 6 || !(rePassword.equals(password))) {
            mRePassword.setError("密码验证错误");
            isPass = false;
        } else {
            mRePassword.setError(null);
        }
        return isPass;
    }


    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_up;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
