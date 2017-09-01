package com.example.sasannaderi.archcomponents.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.example.sasannaderi.archcomponents.R;
import com.example.sasannaderi.archcomponents.ui.base.LifecycleActivity;
import com.example.sasannaderi.archcomponents.ui.users.UserDetailFragment;
import com.example.sasannaderi.archcomponents.ui.users.UserListFragment;

public class MainActivity extends LifecycleActivity implements UserListFragment.OnUserListFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.content, new UserListFragment());
            ft.commit();
        }
    }

    @Override
    public void onUserClicked(int userId) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content, UserDetailFragment.newInstance(userId));
        ft.addToBackStack(null);
        ft.commit();
    }
}
