package com.example.eve.eve.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eve.eve.R;
import com.example.eve.eve.fragment.CardContentFragment;
import com.example.eve.eve.fragment.TileContentFragment;

import java.util.ArrayList;
import java.util.List;

public class AboutFragment extends Fragment {

    public static AboutFragment newInstance() {
        AboutFragment fragment = new AboutFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false);
        // Setting ViewPager for each Tabs
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        if (viewPager != null) {
            viewPager.setOffscreenPageLimit(3);

            setupViewPager(viewPager);
        }

        // Set Tabs inside Toolbar
        TabLayout tabs =  view.findViewById(R.id.tabs);
        assert viewPager != null;
        viewPager.setOffscreenPageLimit(3);

        tabs.setupWithViewPager(viewPager);


        // Adding Floating Action Button to bottom right of main view

        return view;
    }

    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(CardContentFragment.newInstance(), "Hoje");
        adapter.addFragment( TileContentFragment.newInstance(), "Amanhã");
        //adapter.addFragment( ListContentFragment.newInstance(), "Próximos");
        //adapter.addFragment( ListNextMonthFragment.newInstance(), "Month");

        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}

