package com.bewithmaurya.withu.utils;


import com.bewithmaurya.withu.fragments.RootFragment;

public interface OnBackPressListener {

    public boolean onBackPressed();

    public RootFragment getFragment();

}
