package com.jdemaagd.espressorevelado.tasks;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.view.ViewCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Extends {@link SwipeRefreshLayout} to support non-direct descendant scrolling views
 * <p>
 * {@link SwipeRefreshLayout} works as expected when a scroll view is a direct child:
 * triggers refresh only when view is on top
 * adds way (@link #setScrollUpChild} to define which view controls this behavior
 */
public class ScrollChildSwipeRefreshLayout extends SwipeRefreshLayout {

    private View mScrollUpChild;

    public ScrollChildSwipeRefreshLayout(Context context) {
        super(context);
    }

    public ScrollChildSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean canChildScrollUp() {
        if (mScrollUpChild != null) {
            return ViewCompat.canScrollVertically(mScrollUpChild, -1);
        }
        return super.canChildScrollUp();
    }

    public void setScrollUpChild(View view) {
        mScrollUpChild = view;
    }
}