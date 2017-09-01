package com.example.sasannaderi.archcomponents.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.sasannaderi.archcomponents.models.User;
import com.example.sasannaderi.archcomponents.models.repositories.Resource;
import com.example.sasannaderi.archcomponents.models.repositories.UserRepository;

/**
 * Created by sasannaderi on 2017-06-20.
 */

public class UserDetailViewModel extends ViewModel {

    private LiveData<Resource<User>> mResource;
    private UserRepository mRepository;

    public UserDetailViewModel() {
        mRepository = new UserRepository();
    }

    public LiveData<Resource<User>> getUser(int userId) {
        if (mResource == null) {
            return mResource = mRepository.get(userId);
        } else {
            return mResource;
        }

    }
}
