package com.huyha.huyha.activities.drawer;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.util.Log;

import com.huyha.huyha.fragments.ArirangFragment;
import com.huyha.huyha.fragments.favoriteFragment.FavoriteFragment;
import com.huyha.van.karaoke.R;

/**
 * Created by huyva on 1/10/2018.
 */

public class DrawerPresenter {
    private static final String TAG = "DrawerPresenter";
    private Activity mContext;

    public DrawerPresenter(Activity activity){
        this.mContext = activity;
    }


    public void updateDisplay(int position) {
        ArirangFragment fragment = null;
        switch (position) {
            case 0:
                fragment  = new ArirangFragment();
                fragment.setmContext(mContext);
                fragment.setTypeSong(5);
                changeFragment(fragment);
                break;
            case 1:
                fragment  = new ArirangFragment();
                fragment.setmContext(mContext);
                fragment.setTypeSong(6);
                changeFragment(fragment);
                break;
            case 2:
                FavoriteFragment favoriteFragment  = new FavoriteFragment();
                favoriteFragment.setmContext(mContext);
                changeFragment(favoriteFragment);
                break;
            default:
                break;
        }
    }

    private void changeFragment(Fragment fragment){
        if (fragment != null) {
            FragmentManager fragmentManager = mContext.getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();

            // update selected item and title, then close the drawer

        } else {
            // error in creating fragment
            Log.e(TAG, "Error in creating fragment");
        }
    }
}
