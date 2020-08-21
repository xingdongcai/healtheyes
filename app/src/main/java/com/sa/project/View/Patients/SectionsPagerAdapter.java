package com.sa.project.View.Patients;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.sa.project.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;

    /**
     * constructor
     * @param context: the current context
     * @param fm: manager of fragment
     */
    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    /**
     * get fragment
     * @param position: the fragment index
     * @return: the fragment of index
     */
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch(position){
            case 0:
                fragment = new AllPatientsFragment();
                break;
            case 1:
                fragment = new SelectedPatientsFragment();
                break;


        }
        return fragment;
    }


    /**
     * get the fragment tab title
     * @param position: the index of fragment
     * @return: the title of fragment
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    /**
     * return the number of fragment
     * @return: the number of fragment
     */
    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}