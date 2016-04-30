package com.drunkpiano.zhihuselection.activities;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.drunkpiano.zhihuselection.R;
import com.drunkpiano.zhihuselection.fragments.FMFavorite;
import com.drunkpiano.zhihuselection.fragments.FMRecent;
import com.drunkpiano.zhihuselection.fragments.FmThird;
import com.drunkpiano.zhihuselection.fragments.NoNetWorkFragment;
import com.drunkpiano.zhihuselection.utilities.Utilities;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FMFavorite.OnFragmentInteractionListener{

    public static final String PREFS_NAME = "MyPrefsFile";
    boolean netWorkAvailable = false ;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
//        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
//        // Set up the ViewPager with the sections adapter.
//        mViewPager = (ViewPager) findViewById(R.id.container);
//        mViewPager.setAdapter(mSectionsPagerAdapter);
//
//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(mViewPager);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            getWindow().setStatusBarColor(Color.parseColor("#598A32"));
            //setStatusBarColor在v21/styles.xml中设置了（其实无需设置,因为可以沿用5.0以下配色）
            getWindow().setNavigationBarColor(Color.parseColor("#C33A29"));
        }

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        Boolean user_first = settings.getBoolean("FirstLaunch",true);//defValue - Value to return if this preference does not exist.
        if(user_first) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("FirstLaunch", false);
            editor.apply();
            System.out.println("first launch");
        }
        else{
            System.out.println("not first launch");
        }
        if(Utilities.isNetworkAvailable(MainActivity.this)){
            netWorkAvailable = true ;
            System.out.println("有网络有网络有网络有网络有网络有网络有网络");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0:
                {
                    if(netWorkAvailable)
                        return new FmThird() ;
                    else
                        return new NoNetWorkFragment();
                }
                case 1:
                    if(netWorkAvailable)
                        return new FMRecent() ;
                    else
                    return new NoNetWorkFragment();
                case 2:
                    if(netWorkAvailable)
                        return new FmThird() ;
                    else
                        return new NoNetWorkFragment() ;
                case 3:
                    if(netWorkAvailable)
                        return new FmThird() ;
                    else
                        return new NoNetWorkFragment() ;

            }
            return null ;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "日常";
                case 1:
                    return "一周";
                case 2:
                    return "经典";
                case 3:
                    return "闲逛";
            }
            return null;
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();

        if (id == R.id.nav_home_page) {
            // Handle the camera action
        } else if (id == R.id.nav_favorites) {
            getSupportFragmentManager().beginTransaction().replace(R.id.drawer_layout, new FMFavorite()).commitAllowingStateLoss();
            Toast.makeText(MainActivity.this,"gallery",Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_about) {
            Toast.makeText(MainActivity.this,"nav_slideshow",Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_change_theme) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}