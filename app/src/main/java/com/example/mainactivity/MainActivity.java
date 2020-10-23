package com.example.mainactivity;

import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends Activity {

    private TabLayout tablayout;
    private ViewPager viewPager;
    private TabItem Hovedside, Gruppeinfo, Profil;
    public PageAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tablayout = findViewById(R.id.tabLayoutMain);
        Hovedside = findViewById(R.id.goToMainMain);
        Gruppeinfo = findViewById(R.id.goToGruppeinformasjonMain);
        Profil = findViewById(R.id.goToProfileMain);
        viewPager = findViewById(R.id.viewPagerMain);
        pagerAdapter = new PageAdapter(getSupportFragmentManager(), tablayout.getTabCount());


    }
}