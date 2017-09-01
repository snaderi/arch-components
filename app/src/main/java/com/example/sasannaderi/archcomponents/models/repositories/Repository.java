package com.example.sasannaderi.archcomponents.models.repositories;

import android.arch.lifecycle.LiveData;

import java.util.List;

/**
 * Created by sasannaderi on 2017-06-19.
 */

public interface Repository<T> {

    LiveData<Resource<T>> get(int id);

    LiveData<Resource<List<T>>> getAll();

}
