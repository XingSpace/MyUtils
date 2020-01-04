package com.xing.app.myutils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.xing.app.myutils.Utils.BitmapUtil;
import com.xing.app.myutils.Utils.LogUtil;
import com.xing.app.myutils.Utils.PermissionUtil;

public class MainActivity extends AppCompatActivity{

    private TextView textView;
    private ImageView imageView;
    private Button button,button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);
        imageView = findViewById(R.id.image);
        button = findViewById(R.id.button);
        button1 = findViewById(R.id.button1);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.miao);
//        Bitmap newBitmap = BitmapUtil.gaussBlur(this,bitmap,25);
        Bitmap sss = BitmapUtil.gaussBlur(this,bitmap,25);
        imageView.setImageBitmap(sss);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PermissionUtil.permissionEntry(MainActivity.this,
                        getApplicationContext(),
                        true,
                        299)){
                    LogUtil.init("0你大爷");
//                    LogUtil.d("获取权限成功！");
                }
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.e("难受AAACCCabcabc");
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //检查权限获取的回调
        Log.d("测试",""+requestCode);
    }
}
