package com.example.harshad.rotaract.Fragment;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.harshad.rotaract.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Harshad on 1/9/2018.
 */

public class EventFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;
    Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event,container,false);
        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager.setAdapter(new ViewPagerDataAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    class ViewPagerDataAdapter extends FragmentPagerAdapter{

          String TAG=ViewPagerDataAdapter.class.getSimpleName();
          int FRAGMENT_COUNT=2;

        public ViewPagerDataAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new CurrentEventFragment();
                case 1:
                    return new PastEventFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return FRAGMENT_COUNT;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Past";
                case 1:
                    return "Upcoming";
            }
            return null;
        }
    }
}
