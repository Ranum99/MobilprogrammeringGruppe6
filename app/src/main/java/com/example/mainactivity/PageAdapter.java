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
                Log.d("createFragment", "returning hovedside");
                return  new HovedsideFragment();
            case 1:
                Log.d("createFragment", "returning gruppeinformasjon");
                return new GruppeinformasjonFragment();
            case 2:
                Log.d("createFragment", "returning profil");
                return new ProfilFragment();

        }
        Log.d("createFragment", "returning null");
        return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}