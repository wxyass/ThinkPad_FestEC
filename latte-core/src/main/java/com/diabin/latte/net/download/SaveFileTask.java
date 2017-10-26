package com.diabin.latte.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.diabin.latte.app.Latte;
import com.diabin.latte.net.callback.IRequest;
import com.diabin.latte.net.callback.ISuccess;
import com.diabin.latte.util.file.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * 异步执行下载成功后的保存操作
 * Created by yangwenmin on 2017/10/17.
 */

public class SaveFileTask extends AsyncTask<Object, Void, File> {

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;

    public SaveFileTask(IRequest request, ISuccess success) {
        this.REQUEST = request;
        this.SUCCESS = success;
    }

    @Override
    protected File doInBackground(Object... params) {
        // 下载路径
        String downloadDir = (String) params[0];
        // 后缀名
        String extension = (String) params[1];
        // 请求体
        final ResponseBody body = (ResponseBody) params[2];
        // 文件名称
        String name = (String) params[3];
        // 得到输入流
        final InputStream is = body.byteStream();
        //
        if (downloadDir == null || "".equals(downloadDir)) {
            downloadDir = "down_loads";
        }
        //
        if (extension == null || "".equals(extension)) {
            extension= "";
        }

        // 创建文件
        if(name==null){
            return FileUtil.writeToDisk(is,downloadDir,extension.toUpperCase(),extension);
        }else{
            return FileUtil.writeToDisk(is,downloadDir,name);
        }
    }

    // 执行完子线程回到主线程后做的操作
    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if(SUCCESS!=null){
            SUCCESS.onSuccess(file.getPath());
        }
        if(REQUEST!=null){
            REQUEST.onRequestEnd();
        }
        autoInstallApk(file);
    }

    // 若是.apk文件, 直接安装
    private void autoInstallApk(File file){
        if(FileUtil.getExtension(file.getPath()).equals("apk")){// 后缀名是apk
            final Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
            Latte.getApplicationContext().startActivity(intent);
        }

    }
}
