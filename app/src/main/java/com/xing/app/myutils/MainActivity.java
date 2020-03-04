package com.xing.app.myutils;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.xing.app.myutils.Utils.LogUtil;
import com.xing.app.myutils.Utils.PermissionUtil;
import com.xing.app.myutils.Views.RoundImageView.Corner;
import com.xing.app.myutils.Views.RoundImageView.RoundedTransformationBuilder;

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

        LogUtil.e("此时的PicassoProvider.Context->");

//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.miao);

//        Bitmap sss = BitmapUtil.setAlpha(bitmap,0f);
//        Bitmap sss = BitmapUtil.getRoundedBitmapDrawable(getBaseContext(),bitmap,50f);

//        imageView.setImageDrawable(BitmapUtil.getRoundedBitmapDrawable(getBaseContext(),bitmap,300f));
        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.BLACK)
                .borderWidthDp(3)
                .cornerRadiusDp(Corner.TOP_LEFT,90)
                .oval(false)
                .build();
        Picasso.get()
                .load(R.mipmap.miao)
                .fit()
                .transform(transformation)
                .into(imageView);

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
