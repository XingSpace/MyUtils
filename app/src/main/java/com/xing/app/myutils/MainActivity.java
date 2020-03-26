package com.xing.app.myutils;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.xing.app.myutils.Utils.DateUtil;
import com.xing.app.myutils.Utils.LogUtil;
import com.xing.app.myutils.Utils.PermissionUtil;
import com.xing.app.myutils.Utils.ThreadUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity{

    private TextView textView;
    private TextView title;
    private ImageView imageView;
    private Button button,button1;

    private boolean isGoOn;

//    private String ip = "119.29.143.55";
//    private String ip = "192.168.2.121";
    private String baiduip = "14.215.177.39";

    private int count,succeed,failed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);
        title = findViewById(R.id.texttitle);

        if (PermissionUtil.permissionEntry(MainActivity.this,
                getApplicationContext(),
                true,
                299)){
            LogUtil.init("测试网络连接");
        }

        isGoOn = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                LogUtil.init("网络连接测试");
                while (isGoOn){
                    try {
                        count++;
//                        boolean b = execPing(ip);
                        boolean b2 = execPing(baiduip);
                        if (b2){
                            succeed++;
                        } else {
                            failed++;
                        }

//                        writeToWindow("访问 119.29.143.55 的结果->"+b + "\n" +"访问 baidu 的结果->"+b2);
                        writeToWindow(DateUtil.getYYYYMMDDHHMMSS()+" 访问 baidu 的结果->"+b2);

//                        writeToLog("访问 119.29.143.55 的结果->"+b+"\n"+"访问 baidu 的结果->"+b2);
                        writeToLog("访问 baidu 的结果->"+b2);
                        updateTitle();

                        Thread.sleep(1000L);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    @Override
    protected void onDestroy() {
        isGoOn = false;
        super.onDestroy();
    }

    private void updateTitle(){
        ThreadUtil.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                title.setText("总尝试轮数："+count+"  成功次数："+succeed+"  失败次数："+failed);
            }
        });
    }

    private void writeToWindow(String s) {
        final StringBuilder sb = new StringBuilder();
        sb.append(s).append("\n").append(textView.getText());
        ThreadUtil.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(sb.toString());
            }
        });

    }

    private void writeToLog(String s) {
        LogUtil.e(s);
    }

    /**
     * 如果第一次没有ping通，还会再尝试一次
     *
     * @param ip 要被请求的IP地址
     * @return 是否能够ping通
     */
    public boolean execPing(String ip) {
        return execPing(ip, 2);
    }

    /**
     * 执行一个ping命令
     *
     * @param ip     要被请求的IP地址
     * @param tryMax 如果没有ping通，继续尝试的次数(tryMax > 0)
     * @return 是否能够ping通
     */
    public boolean execPing(String ip, int tryMax) {
        Runtime runtime = Runtime.getRuntime(); // 获取当前程序的运行进对象
        Process process = null; //声明处理类对象
        String line = null; //返回行信息
        InputStream is = null; //输入流
        InputStreamReader isr = null;// 字节流
        BufferedReader br = null;
        boolean res = false;// 结果
        int count = 0;
        try {
            process = runtime.exec("ping " + ip); // PING
            is = process.getInputStream(); // 实例化输入流
            isr = new InputStreamReader(is);// 把输入流转换成字节流
            br = new BufferedReader(isr);// 从字节中读取文本
            while ((line = br.readLine()) != null) {
                LogUtil.e("网络结果-"+line);
                if (line.toUpperCase().contains("TTL")) {
                    res = true;
                    break;
                }
                count++;
                if (count > tryMax) break;
            }
            is.close();
            isr.close();
            br.close();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
//            runtime.exit(1);
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //检查权限获取的回调
        Log.d("测试",""+requestCode);
    }
}
