package com.example.booknoteapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class Adapter_ViewPager_BookDetail extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentArrayList;
    private ArrayList<String> tapText = new ArrayList<String>();

    public Adapter_ViewPager_BookDetail(@NonNull FragmentManager fm) {
        super(fm);
        fragmentArrayList = new ArrayList<Fragment>();
        fragmentArrayList.add(new Fragment_Note());
        fragmentArrayList.add(new Fragment_PageLog());
       // fragmentArrayList.add(new Fragment_Review());

        tapText.add("독서 노트");
        tapText.add("페이지 기록");
        //tapText.add("서평");
   }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tapText.get(position);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }
}
