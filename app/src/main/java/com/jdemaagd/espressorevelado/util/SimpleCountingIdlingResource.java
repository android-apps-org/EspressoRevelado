package com.jdemaagd.espressorevelado.util;

import android.util.Log;

import java.util.concurrent.atomic.AtomicInteger;

import androidx.test.espresso.IdlingResource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Counter implementation of {@link IdlingResource}
 * to determine idleness by maintaining an internal counter
 * When counter is 0 - it is considered to be idle
 * when it is non-zero it is not idle
 * Similar to way {@link java.util.concurrent.Semaphore} behaves
 * <p>
 * Use to wrap up operations that while in progress should block tests from accessing UI
 */
public final class SimpleCountingIdlingResource implements IdlingResource {

    private final String mResourceName;

    private final AtomicInteger counter = new AtomicInteger(0);

    // written from main thread, read from any thread
    private volatile ResourceCallback resourceCallback;

    /**
     * Creates a SimpleCountingIdlingResource
     *
     * @param resourceName resource name this resource should report to Espresso
     */
    public SimpleCountingIdlingResource(String resourceName) {
        mResourceName = checkNotNull(resourceName);
    }

    @Override
    public String getName() {
        return mResourceName;
    }

    @Override
    public boolean isIdleNow() {
        Log.d(getName(), "Counter value is " + counter.get());

        return counter.get() == 0;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
        this.resourceCallback = resourceCallback;
    }

    /**
     * Increments count of in-flight transactions to resource being monitored
     */
    public void increment() {
        counter.getAndIncrement();
    }

    /**
     * Decrements count of in-flight transactions to resource being monitored
     *
     * @throws IllegalStateException if counter is below 0
     */
    public void decrement() {
        int counterVal = counter.decrementAndGet();

        Log.d(getName(), "Counter decremented. Value is " + counterVal);

        if (counterVal == 0) {
            // have gone from non-zero to zero
            // means are idle now! Tell espresso
            if (null != resourceCallback) {
                resourceCallback.onTransitionToIdle();
            }
        }

        if (counterVal < 0) {
            throw new IllegalArgumentException("Counter has been corrupted!");
        }
    }
}