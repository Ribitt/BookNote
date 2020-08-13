package com.example.booknoteapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class Adapter_ViewPager extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentArrayList;
    private ArrayList<String> tapText = new ArrayList<String>();

    public Adapter_ViewPager(@NonNull FragmentManager fm) {
        super(fm);
        fragmentArrayList = new ArrayList<Fragment>();
        fragmentArrayList.add(new Fragment_reading());
        fragmentArrayList.add(new Fragment_read());
        fragmentArrayList.add(new Fragment_toRead());

        tapText.add("읽고 있는 책");
        tapText.add("읽은 책");
        tapText.add("읽을 책");
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
