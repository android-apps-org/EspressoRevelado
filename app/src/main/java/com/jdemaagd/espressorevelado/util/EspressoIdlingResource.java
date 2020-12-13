package com.jdemaagd.espressorevelado.util;

import androidx.test.espresso.IdlingResource;

/**
 * Contains static reference to {@link IdlingResource} {@link androidx.test.espresso.IdlingRegistry}
 * only available in 'mock' build type
 */
public class EspressoIdlingResource {

    private static final String RESOURCE = "GLOBAL";

    private static SimpleCountingIdlingResource mCountingIdlingResource =
            new SimpleCountingIdlingResource(RESOURCE);

    public static void increment() {
        mCountingIdlingResource.increment();
    }

    public static void decrement() {
        mCountingIdlingResource.decrement();
    }

    public static IdlingResource getIdlingResource() {
        return mCountingIdlingResource;
    }
}