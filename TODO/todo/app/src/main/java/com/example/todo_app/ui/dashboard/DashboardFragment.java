package com.example.todo_app.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.todo_app.R;
import com.google.android.material.tabs.TabLayout;

public class DashboardFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager2 pager2;
    FragmentAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);


        tabLayout = root.findViewById(R.id.tabLayout);
        pager2 = root.findViewById(R.id.pager_lists);

        FragmentManager fm = getActivity().getSupportFragmentManager();
        adapter = new FragmentAdapter(fm,getLifecycle());
        pager2.setAdapter(adapter);
        //оголошення назв вкладок в вікні "панель завдань"
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.all_task)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tomorrow)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.later)));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        return root;
    }

}