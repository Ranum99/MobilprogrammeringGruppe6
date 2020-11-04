package com.example.mainactivity.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.mainactivity.GruppeinformasjonFragment;
import com.example.mainactivity.HovedsideFragment;
import com.example.mainactivity.ProfilFragment;

public class PageAdapter extends FragmentStatePagerAdapter {

    private int numoftabs;

    public PageAdapter(@NonNull FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numoftabs = numOfTabs;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return  new HovedsideFragment();
            case 1:
                return new GruppeinformasjonFragment();
            case 2:
                return new ProfilFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return numoftabs;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
