package com.example.mainactivity;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PageAdapter extends FragmentStateAdapter {
    public PageAdapter(Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 0:
                Log.d("createFragment", "returning kalendere");
                return  new KalenderSideFragment();
            case 1:
                Log.d("createFragment", "returning bursdager");
                return new BursdagFragment();

        }
        Log.d("createFragment", "returning null");
        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}