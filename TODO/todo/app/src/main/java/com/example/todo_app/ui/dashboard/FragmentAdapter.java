package com.example.todo_app.ui.dashboard;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.todo_app.ui.dashboard.TabFragment.TabFragment1;
import com.example.todo_app.ui.dashboard.TabFragment.TabFragment2;
import com.example.todo_app.ui.dashboard.TabFragment.TabFragment3;

class FragmentAdapter extends FragmentStateAdapter {

    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1: return new TabFragment2();
            case 2: return new TabFragment3();
        }
        return new TabFragment1();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
