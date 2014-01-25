/*
 * Copyright 2014 Johan Olsson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package se.jmvo.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

import se.jmvo.support.ViewCompat;

/**
 * Implementation of a {@link android.widget.ScrollView} with support for
 * <a href="http://en.wikipedia.org/wiki/Parallax_scrolling">parallax scrolling</a>.
 *
 * @author Johan Olsson
 */
public class ParallaxScrollView extends ScrollView {

    // ===========================================================
    // Constants
    // ===========================================================

    private static final float DEFAULT_SCROLL_FACTOR = 0.6f;
    private float mScrollFactor = DEFAULT_SCROLL_FACTOR;

    // ===========================================================
    // Private variables
    // ===========================================================
    private static final boolean PRE_HONEYCOMB = Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB;
    private int mBackgroundResId;
    private View mBackgroundView;

    // ===========================================================
    // Constructors
    // ===========================================================

    public ParallaxScrollView(Context context) {
        super(context);
        initView(context, null, 0);
    }

    public ParallaxScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, 0);
    }

    public ParallaxScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs, defStyle);
    }

    /**
     * Extract the additional attributes needed.
     *
     * @param context  The context used to create the view.
     * @param attrs    The desired attributes to be retrieved.
     * @param defStyle A resource identifier of a style resource that supplies default values for
     *                 the TypedArray. Can be 0 if defaults should not be used.
     */
    private void initView(Context context, AttributeSet attrs, int defStyle) {
        if (isInEditMode()) {
            return;
        }

        if (attrs != null) {
            TypedArray values = context.obtainStyledAttributes(attrs, R.styleable.ParallaxScrollView, defStyle, 0);
            mBackgroundResId = values.getResourceId(R.styleable.ParallaxScrollView_backgroundView, 0);
            mScrollFactor = values.getFloat(R.styleable.ParallaxScrollView_scrollFactor, DEFAULT_SCROLL_FACTOR);
            values.recycle();
        }

        // Disable fading edge
        setVerticalFadingEdgeEnabled(false);
    }

    /**
     * Define which view that will be subject to parallax scrolling.
     *
     * @param resId The identifier of the resource that will be parallax scrolling.
     */
    public void setBackgroundView(int resId) {
        mBackgroundView = findViewById(resId);
    }

    /**
     * Define which view that will be subject to parallax scrolling.
     *
     * @param view The view that will be parallax scrolling.
     */
    public void setBackgroundView(View view) {
        mBackgroundView = view;
    }

    /**
     * Define the pace with witch the background view scrolls in relation to the scrolling
     * of the {@link android.widget.ScrollView}.
     *
     * @param scrollFactor A factor defining the scroll pace of the background view.
     */
    public void setScrollFactor(float scrollFactor) {
        mScrollFactor = scrollFactor;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            // On layout changes (eg. orientation change) scroll offset might have changed.
            // Setting a new Y translation here removes any background view hiccups.
            translateBackgroundView(getScrollY());
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        // If resource was set in XML, that child view will be available upon attaching this view
        // to the view hierarchy.
        if (mBackgroundResId > 0 && mBackgroundView == null) {
            mBackgroundView = findViewById(mBackgroundResId);
            mBackgroundResId = 0;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        // Clean up for versions prior to Honeycomb. Since translation is achieved using the
        // Android animation API all animations are removed when this view is detached.
        if (PRE_HONEYCOMB && mBackgroundView != null) {
            mBackgroundView.clearAnimation();
        }
        mBackgroundView = null;
        super.onDetachedFromWindow();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        translateBackgroundView(t);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void translateBackgroundView(int y) {
        if (mBackgroundView != null) {
            int translationY = (int) (y * mScrollFactor);
            if (PRE_HONEYCOMB) {
                ViewCompat.wrap(mBackgroundView).setTranslationY(translationY);
            } else {
                mBackgroundView.setTranslationY(translationY);
            }
        }
    }
}
