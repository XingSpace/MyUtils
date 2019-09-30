package com.xing.app.myutils;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.xing.app.myutils.Utils.LogUtil;
import com.xing.app.myutils.Utils.ThreadUtil;
import com.xing.app.myutils.Utils.ToastUtil;

public class MainActivity extends AppCompatActivity{

    private TextView textView;
    private ImageView imageView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);
        imageView = findViewById(R.id.image);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show(getBaseContext(),"我的天呐！！！");
            }
        });

        ThreadUtil.runOnChildThreadDelayed(new Runnable() {
            @Override
            public void run() {
                LogUtil.e(ThreadUtil.isRunMainThread() + "");
            }
        },2000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //检查权限获取的回调
        Log.d("测试",""+requestCode);
    }
}
