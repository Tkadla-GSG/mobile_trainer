package cz.zeleznakoule.kebap.shared;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

/**
 * Tab Listener for tab switching
 * Via stackoverflow
 * @author Meg Clark
 *
 * @param <T>
 */
public class TabListener<T extends Fragment> implements ActionBar.TabListener {
	private final FragmentActivity mActivity;
	private final String mTag;
	private final Class<T> mClass;
	private final Bundle mArgs;
	private Fragment mFragment;

	public TabListener(FragmentActivity activity, String tag, Class<T> clz,
			Bundle args) {
		mActivity = activity;
		mTag = tag;
		mClass = clz;
		mArgs = args;
		FragmentTransaction ft = mActivity.getSupportFragmentManager()
				.beginTransaction();

		// Check to see if we already have a fragment for this tab, probably
		// from a previously saved state. If so, deactivate it, because our
		// initial state is that a tab isn't shown.
		mFragment = mActivity.getSupportFragmentManager().findFragmentByTag(
				mTag);
		if (mFragment != null && !mFragment.isDetached()) {
			ft.detach(mFragment);
		}
	}

	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		ft = mActivity.getSupportFragmentManager().beginTransaction();

		if (mFragment == null) {
			mFragment = Fragment
					.instantiate(mActivity, mClass.getName(), mArgs);
			ft.add(android.R.id.content, mFragment, mTag);
			ft.commit();
		} else {
			ft.attach(mFragment);
			ft.commit();
		}

	}

	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		ft = mActivity.getSupportFragmentManager().beginTransaction();

		if (mFragment != null) {
			ft.detach(mFragment);
			ft.commitAllowingStateLoss();
		}

	}

	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

}