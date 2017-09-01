package com.example.sasannaderi.archcomponents.ui.base;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.support.v4.app.Fragment;

/**
 * Fragment that extends LifecycleOwner
 * <p>
 * This class is a temporary implementation detail until Lifecycles are integrated with support
 * library.
 */
public class LifecycleFragment extends Fragment implements LifecycleRegistryOwner {
    LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}
