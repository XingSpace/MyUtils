package com.xing.app.myutils.Activity;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.xing.app.myutils.R;

/**
 * 做一个通用的ActivityBase
 */
public abstract class ActivityBase extends AppCompatActivity {

    private FrameLayout action_bar;

    private FrameLayout content_fl;

    private FrameLayout bottom_bar;

    private FrameLayout loading_fl;

    private ActionBar actionBar;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_root);

        action_bar = findViewById(R.id.action_bar);

        content_fl = findViewById(R.id.content_fl);

        bottom_bar = findViewById(R.id.bottom_bar);

        //当loading界面展示的时候，让触摸事件，单击事件都拦截掉
        loading_fl = findViewById(R.id.loading_fl);
        loading_fl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        content_fl.addView(getLayoutInflater().inflate(layoutResID, null)
                , new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                        , ViewGroup.LayoutParams.MATCH_PARENT));

        findViews();
        initViews();
        registerEvent();
    }

    /**
     * 用来绑定View
     */
    protected abstract void findViews();

    /**
     * 用于初始化View
     */
    protected abstract void initViews();

    /**
     * 注册各类监听事件什么的。。。
     */
    protected abstract void registerEvent();

    /**
     * @return 自定义tag作为标记吧
     */
    protected abstract String getTag();

    /**
     * 展示加载界面
     * ps：加载界面会拦截掉所有的touch、click事件
     */
    protected void showLoading() {
        loading_fl.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏加载界面
     */
    protected void hideLoading() {
        loading_fl.setVisibility(View.GONE);
    }

    //todo 这个action Bar没想好怎么做
    protected void setActionBar(ActionBar actionBar) {
        this.actionBar = actionBar;
        action_bar.addView(actionBar
                ,new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.MATCH_PARENT));
        showActionBar();
    }

    protected ActionBar getMyActionBar() {
        return actionBar;
    }

    protected void showActionBar(){
        action_bar.setVisibility(View.VISIBLE);
    }

    protected void hideActionBar() {
        action_bar.setVisibility(View.GONE);
    }

    //----------- Fragment的相关操作封装  start ----------------
    protected void hideFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.hide(fragment);
        ft.commit();
    }

    protected void showFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.show(fragment);
        ft.commit();
    }

    protected void addFragment(int layoutID, Fragment fragment, String tag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(layoutID, fragment, tag);
        ft.commit();
    }

    protected void removeFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.remove(fragment);
        ft.commit();
    }

    /**
     * 判断当前activity中是否存在某个指定的Fragment
     */
    protected boolean isExistFragment(String tag) {
        FragmentManager fm = getSupportFragmentManager();
        return fm.findFragmentByTag(tag) != null;
    }

    /**
     * 判断当前activity中是否存在某个指定的Fragment
     */
    protected boolean isExistFragment(int id) {
        FragmentManager fm = getSupportFragmentManager();
        return fm.findFragmentById(id) != null;
    }

    /**
     * 判断当前activity中是否存在某个指定的Fragment
     */
    protected boolean isExistFragment(Fragment fragment) {
        return fragment.getActivity() == this;
    }

    //----------- Fragment的相关操作封装  end ----------------

}
