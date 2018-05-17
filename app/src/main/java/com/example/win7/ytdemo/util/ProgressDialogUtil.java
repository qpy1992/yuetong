package com.example.win7.ytdemo.util;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * @创建者 AndyYan
 * @创建时间 2018/4/9 14:33
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */
public class ProgressDialogUtil {
    private static ProgressDialog mProgressDialog;

    public static void startShow(Context context, String message) {
        if (context == null) {
            return;
        }
        if (null==mProgressDialog){
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setInverseBackgroundForced(false);//对话框后面的窗体不获得焦点
            mProgressDialog.setCanceledOnTouchOutside(false);//旁击不消失
            mProgressDialog.setMessage(message);
            mProgressDialog.setCancelable(true);
        }
        mProgressDialog.show();
    }

    public static void hideDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
}
