package com.example.mainactivity.adapter;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.mainactivity.fragment.BursdagFragment;
import com.example.mainactivity.fragment.KalenderSideFragment;

public class PageAdapter extends FragmentStateAdapter {
    public PageAdapter(Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                Log.i("PageAdapter", "Returning kalendere");
                return new KalenderSideFragment();
            case 1:
                Log.i("PageAdapter", "Returning bursdager");
                return new BursdagFragment();
        }
        Log.e("PageAdapter", "Returning null");
        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}