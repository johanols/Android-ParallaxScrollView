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

package se.jmvo.support;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Compatibility class used for setting Y translation prior to Android 3.0 Honeycomb.
 *
 * @author Johan Olsson
 */
public final class ViewCompat extends Animation {

    // ===========================================================
    // Static method
    // ===========================================================

    public static ViewCompat wrap(View view) {
        Animation wrapper = view.getAnimation();
        if (wrapper instanceof ViewCompat) {
            return (ViewCompat) wrapper;
        }
        else {
            return new ViewCompat(view);
        }
    }

    // ===========================================================
    // Private variables
    // ===========================================================

    private final View mView;

    private float mTranslationY;

    // ===========================================================
    // Constructor
    // ===========================================================

    private ViewCompat(View view) {
        setDuration(0);
        setFillAfter(true);
        view.setAnimation(this);
        mView = view;
    }

    public void setTranslationY(float translationY) {
        mTranslationY = translationY;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        if (mView != null) {
            t.getMatrix().postTranslate(0, mTranslationY);
        }
    }
}