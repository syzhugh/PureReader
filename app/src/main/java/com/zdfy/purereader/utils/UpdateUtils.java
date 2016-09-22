package com.zdfy.purereader.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.zdfy.purereader.R;
import com.zdfy.purereader.constant.Constant;
import org.json.JSONException;
import org.json.JSONObject;
import im.fir.sdk.FIR;
import im.fir.sdk.VersionCheckCallback;

/**
 * Created by ZhangPeng on 2016/7/19.
 */
public class UpdateUtils {
    private static AlertDialog dialog;

    public static void CheckVersion(final Context context) {
        FIR.checkForUpdateInFIR(Constant.firToken, new VersionCheckCallback() {
            @Override
            public void onSuccess(String versionJson) {
                try {
                    JSONObject jo = new JSONObject(versionJson);
                    final String update_url = jo.getString("update_url");
                    String changelog = jo.getString("changelog");
                    String version = jo.getString("version");
                    int versionCode = VersionUtils.getVersionCode(context);
                    Log.i("fir", update_url);
                    Log.i("fir", version + ":" + versionCode);
                    if (Integer.parseInt(version) > versionCode) {
                        if (update_url != null) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            dialog = builder.create();
                            View view = View.inflate(context, R.layout.dialog_update, null);
                            TextView tv_desc = (TextView) view.findViewById(R.id.tv_desc);
                            tv_desc.setText(changelog);
                            TextView tv_next = (TextView) view.findViewById(R.id.tv_next);
                            TextView tv_update = (TextView) view.findViewById(R.id.tv_update);
                            tv_next.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            tv_update.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Uri uri = Uri.parse(update_url);//指定网址
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_VIEW);
                                    intent.setData(uri);
                                    context.startActivity(intent);
                                    dialog.dismiss();
                                }
                            });
                            dialog.setCancelable(false);
                            dialog.setView(view);
                            dialog.show();
                        }
                    } else {
                        ToastUtils.showToast(context, "已经是最新版本!");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("fir", "check from fir.im success! " + "\n" + versionJson);
            }

            @Override
            public void onFail(Exception exception) {
                ToastUtils.showToast(context, "当前网路不佳,请稍后再试!");
                Log.i("fir", "check fir.im fail! " + "\n" + exception.getMessage());
            }
            @Override
            public void onStart() {
            }

            @Override
            public void onFinish() {
//                Toast.makeText(getApplicationContext(), "获取更新完成", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
