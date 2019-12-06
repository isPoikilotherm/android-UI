package com.example.wenjuanapp;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class LooperPagerAdapter extends PagerAdapter {


    private List<Integer> mPics=null;

    @Override
    public int getCount(){
        if (mPics!=null){
            return Integer.MAX_VALUE;
        }
        return 0;

    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int realPosition = position%mPics.size();
        ImageView imageView = new ImageView(container.getContext());
        //imageView.setBackgroundColor(mColors.get(position));
        imageView.setImageResource(mPics.get(realPosition));
        //设置完数据后，添加到容器里
        container.addView(imageView);
        return imageView;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view,Object object){
        return view == object;
    }

    public void setData(List<Integer> colos) {
        this.mPics=colos;
    }
    public int getDataRealSize(){
        if (mPics!=null){
            return mPics.size();
        }
        return 0;
    }
}
