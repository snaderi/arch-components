package com.example.sasannaderi.archcomponents.ui.base;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.support.v7.app.AppCompatActivity;

/**
 * Activity that extends LifecycleOwner
 * <p>
 * This class is a temporary implementation detail until Lifecycles are integrated with support
 * library.
 */
public class LifecycleActivity extends AppCompatActivity implements LifecycleRegistryOwner {
    private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);

    @Override
    public LifecycleRegistry getLifecycle() {
        return mRegistry;
    }

}
