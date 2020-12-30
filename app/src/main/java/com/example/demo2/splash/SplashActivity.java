package com.example.demo2.splash;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.demo2.MainActivity;
import com.example.demo2.R;
import com.example.demo2.base.BaseActivity;
import com.example.demo2.interfaces.IBasePresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends BaseActivity {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.img_1_normal)
    ImageView img_1_normal;
    @BindView(R.id.img_1_select)
    ImageView img_1_select;
    @BindView(R.id.img_2_normal)
    ImageView img_2_normal;
    @BindView(R.id.img_2_select)
    ImageView img_2_select;
    @BindView(R.id.img_3_normal)
    ImageView img_3_normal;
    @BindView(R.id.img_3_select)
    ImageView img_3_select;

    List<SplaceFragment> list;

    @Override
    protected int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected IBasePresenter createPrenter() {
        return null;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        list.add(SplaceFragment.getInstance(1));
        list.add(SplaceFragment.getInstance(2));
        list.add(SplaceFragment.getInstance(3));
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                resetDots();
                if(position == 0){
                    img_1_normal.setVisibility(View.GONE);
                    img_1_select.setVisibility(View.VISIBLE);
                }else if(position == 1){
                    img_2_normal.setVisibility(View.GONE);
                    img_2_select.setVisibility(View.VISIBLE);
                }else if(position == 2){
                    img_3_normal.setVisibility(View.GONE);
                    img_3_select.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private void resetDots(){
        img_1_normal.setVisibility(View.VISIBLE);
        img_2_normal.setVisibility(View.VISIBLE);
        img_3_normal.setVisibility(View.VISIBLE);
        img_1_select.setVisibility(View.GONE);
        img_2_select.setVisibility(View.GONE);
        img_3_select.setVisibility(View.GONE);
    }

    class MyViewPagerAdapter extends FragmentPagerAdapter {

        public MyViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }
}
