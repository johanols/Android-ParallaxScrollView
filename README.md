Android-ParallaxScrollView
==========================

A Parallax [ScrollView](http://developer.android.com/reference/android/widget/ScrollView.html) for Android. Mimics the behavior of the album view in the Google Play Music application.

## Integration
Integrating is as easy as replacing a ScrollView with the ParallaxScrollView and setting a view reference.

    <se.jmvo.widget.ParallaxScrollView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:fillViewport="true"
        app:backgroundView="@+id/imageView"
        app:scrollFactor="0.6"
        android:id="@+id/scrollView">

        ...

    </se.jmvo.widget.ParallaxScrollView>

The child view of the ParallaxScrollView with the reference id set by the attribute
`app:backgroundView` will be used for parallax scrolling. The amount of scrolling can be set
using the attribute `app:scrollFactor`. It is also possible to change these values from the code.

    parallaxScrollView.setBackgroundView(resId);
    parallaxScrollView.setBackgroundView(view);

    parallaxScrollView.setScrollFactor(float);

See the sample project for more information.

## Video

[![Demo Video](http://img.youtube.com/vi/xCnHY1so_lw/0.jpg)](https://www.youtube.com/watch?v=xCnHY1so_lw)

## License

    Copyright 2014 Johan Olsson

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.