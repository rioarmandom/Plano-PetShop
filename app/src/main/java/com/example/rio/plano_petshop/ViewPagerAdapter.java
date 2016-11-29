package com.example.rio.plano_petshop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by almanalfaruq on 18/11/2016.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();//fragment array list
    private final List<String> mFragmentTitleList = new ArrayList<>();//title array list
    private Bundle fragmentBundle;

    public ViewPagerAdapter(FragmentManager fm, Bundle fragmentBundle) {
        super(fm);
        this.fragmentBundle = fragmentBundle;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFrag(Fragment fragment, String title, Bundle bundle) {
        fragment.setArguments(bundle);
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
        this.fragmentBundle = bundle;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
