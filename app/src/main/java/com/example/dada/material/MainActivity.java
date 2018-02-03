package com.example.dada.material;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements CourseListFragment.Listener {

    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    private FragmentManager fragmentManager;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initNavigation();
        fragmentManager = getFragmentManager();
        fragment = null;
        home();
    }

    private void home() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        fragment = new IndexFragment();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    private void dashboard() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        fragment = new CourseListFragment();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    private void notification() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        fragment = new MeFragment();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
        setTitle("我的");
    }

    private void initNavigation() {
        BottomNavigationView.OnNavigationItemSelectedListener
                listener = (item) -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    home();
                    return true;
                case R.id.navigation_dashboard:
                    dashboard();
                    return true;
                case R.id.navigation_notifications:
                    notification();
                    return true;
            }
            return false;
        };
        navigation.setOnNavigationItemSelectedListener(listener);
    }

    @Override
    public void onCourseItemClicked(String id, String title) {
        Log.i("MainActivity", "Clicked " + id);
        Intent intent = new Intent(this, CourseDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("title", title);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (fragment instanceof IndexFragment) {
            boolean response = ((IndexFragment)fragment).onKeyDown(keyCode, event);
            if (response) return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}














